package edu.uw.tcss450.team2.thermochat.ui.chat;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.team2.thermochat.io.RequestQueueSingleton;

public class AddContactToChatViewModel extends AndroidViewModel {

    private final MutableLiveData<JSONObject> mResponse;

    /**
     * The constructor for the ViewModel.
     *
     * @param application
     */
    public AddContactToChatViewModel(@NonNull Application application) {
        super(application);

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Adds a response observer to the view model.
     *
     * @param owner
     * @param observer
     */
    public void addResponseObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super JSONObject> observer) {
        mResponse.observe(owner, observer);
    }


    /**
     * A method connecting to a webservice endpoint for adding users to a chat room
     *
     * @param jwt a valid jwt
     * @param chatID a valid chat ID
     * @param memberID the users member id
     */
    public void putMembers(final String jwt, int chatID, int memberID) {
        String url = "https://team-2-tcss-450-project.herokuapp.com/chats/?chatnum=" + chatID + "/?membernum=" + memberID;


        JSONObject body = new JSONObject();
        try {
            body.put("chatnum", chatID);
            body.put("membernum" , memberID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
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
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    /**
     * handles errors with connecting the the webservice.
     */
    private void handleError(final VolleyError error) {
        try {
            mResponse.setValue(new JSONObject("{" +
                    "error:\"" + error.getMessage() +
                    "\"}"));
        } catch (JSONException e) {
            Log.e("JSON PARSE", "JSON Parse Error in NCVM");
        }
    }
}
