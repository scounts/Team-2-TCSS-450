package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The view model for deleting a contact.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class DeleteContactViewModel extends AndroidViewModel {

    private final MutableLiveData<JSONObject> mResponse;

    /**
     * The constructor for the viewmodel
     *
     * @param application
     */
    public DeleteContactViewModel(@NonNull Application application) {
        super(application);

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Connects to the web service to delete the contact the user chooses.
     *
     * @param jwt The users jwt
     * @param memberID The user id of the contact being removed
     */
    public void deleteContact (String jwt, int memberID){
        String url = "https://team-2-tcss-450-project.herokuapp.com/contacts/?memberId=" +
                memberID;


        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, //no body for this request
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
        Volley.newRequestQueue(getApplication())
                .add(request);
    }

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
