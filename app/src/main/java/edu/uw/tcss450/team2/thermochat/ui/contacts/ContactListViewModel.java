package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Objects;
import java.util.function.IntFunction;

import edu.uw.tcss450.team2.thermochat.R;

/**
 * A ViewModel for a list of the users contacts.
 */
public class ContactListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Contact>> mContactList;
    private final MutableLiveData<JSONObject> mResponse;

    /**
     * The constructor for the contact list view model.
     *
     * @param application the application.
     */
    public ContactListViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Add an observer to the contact list view model.
     *
     * @param owner the owner
     * @param observer the observer
     */
    public void addContactListObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super List<Contact>> observer){
        mContactList.observe(owner, observer);
    }


    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Connects to webservice endpoint to retrieve a list of contacts.
     *
     * @param jwt a valid jwt.
     */
    public void connectGet (String jwt){
        String url = "https://team-2-tcss-450-project.herokuapp.com/contacts";
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleSuccess,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    private void handleSuccess(final JSONObject result) {

        ArrayList<Contact> temp = new ArrayList<>();
        try {
            JSONArray contacts = result.getJSONArray("contacts");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);

                String username= contact.getString("username");
                int memberID = contact.getInt("memberid");
                String firstName = contact.getString("firstname");
                String lastName = contact.getString("lastname");

                Contact entry = new Contact(username, memberID, firstName, lastName);
                temp.add(entry);

            }
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ContactViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mContactList.setValue(temp);
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

    /**
     * Conntect to the webservice endpoint to add a contact connection
     *
     * @param jwt The users jwt
     * @param uName The username of the person being sent the request
     */
    public void addContact(String jwt, String uName) {

        String url = "https://team-2-tcss-450-project.herokuapp.com/contacts";


        JSONObject body = new JSONObject();

        try {
            body.put("username", uName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                mResponse::setValue,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }
}