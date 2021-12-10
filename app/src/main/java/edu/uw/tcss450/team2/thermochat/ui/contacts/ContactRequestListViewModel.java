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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.team2.thermochat.io.RequestQueueSingleton;

/**
 * The view model for displaying the users contact requests
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ContactRequestListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Contact>> mContactList;
    private final MutableLiveData<JSONObject> mResponse;

    /**
     * The constructor for the View Model
     *
     * @param application
     */
    public ContactRequestListViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Adds an observer to the list of requests
     *
     * @param owner
     * @param observer
     */
    public void addContactRequestListObserver(@NonNull LifecycleOwner owner,
                                              @NonNull Observer<? super List<Contact>> observer){
        mContactList.observe(owner, observer);
    }

    /**
     * Adds an observer to the response from the web service connection
     *
     * @param owner
     * @param observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }

    /**
     * Connects to webservice endpoint to retrieve a list of contact requests.
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
            JSONArray contacts = result.getJSONArray("contactReqs");
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject contact = contacts.getJSONObject(i);

                String username= contact.getString("username");
                int memberID = contact.getInt("memberid");

                Contact entry = new Contact(username, memberID);
                temp.add(entry);

            }
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ContactViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mContactList.setValue(temp);
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", "Oops no contacts");
    }
}
