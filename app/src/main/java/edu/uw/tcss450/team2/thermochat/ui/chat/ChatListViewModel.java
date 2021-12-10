package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

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

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.io.RequestQueueSingleton;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.ui.contacts.Contact;


/**
 * A ViewModel for a list of chats.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ChatListViewModel extends AndroidViewModel {

    private MutableLiveData<List<ChatRoom>> mChatList;
    private final MutableLiveData<JSONObject> mResponse;
    private UserInfoViewModel mUserModel;


    /**
     * The constructor for the chat list view model.
     *
     * @param application the application.
     */
    public ChatListViewModel(@NonNull Application application) {
        super(application);
        mChatList = new MutableLiveData<>(new ArrayList<>());
        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Add an observer to the chat list view model.
     *
     * @param owner the owner
     * @param observer the observer
     */
    public void addChatListObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super List<ChatRoom>> observer){
        mChatList.observe(owner, observer);
    }

    /**
     * Connects to webservice endpoint to retrieve a list of chats.
     *
     * @param jwt a valid jwt.
     */
    public void connectGet (String jwt){
        String url = getApplication().getResources().getString(R.string.base_url) + "memberchats";
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

    /**
     * Handles a successful connection with the webservice.
     *
     * @param result result from webservice.
     */
    private void handleSuccess(final JSONObject result) {
        ArrayList<ChatRoom> temp = new ArrayList<>();
        try {
            JSONArray chats = result.getJSONArray("chats");
            for (int i = 0; i < chats.length(); i++) {
                JSONObject chat = chats.getJSONObject(i);
                //String name = chat.getString("name");
                String name = "Example Chat Name";
                int key = chat.getInt("chatid");
                ChatRoom post = new ChatRoom(name, key);
                temp.add(post);
            }
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
        mChatList.setValue(temp);
    }

    private void handleError(final VolleyError error) {
        Log.e("CONNECTION ERROR", "Oops no chats");
        //throw new IllegalStateException(error.getMessage());
    }

    /**
     * Connects to webservice endpoint to add a new chat to the
     * web service database.
     *
     * @param jwt a valid jwt.
     * @param name The name of the chat room
     */
    public void addChat(final String jwt, final String name) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats";

        JSONObject body = new JSONObject();
        try {
            body.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //push token found in the JSONObject body
                response -> handleAdd(jwt, response),
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
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
    }


    /**
     * Connects to webservice endpoint to add the user to the
     * associated chat room.
     *
     * @param jwt
     * @param chatID
     */
    public void addMembers(final String jwt, int chatID) {
        String url = getApplication().getResources().getString(R.string.base_url) + "chats/" + chatID;

        JSONObject body = new JSONObject();

        try {
            body.put("chatid", chatID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(body.toString());

        Request request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                body, //push token found in the JSONObject body
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
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleAdd(final String jwt, final JSONObject response) {
        try {
            int chatID = response.getInt("chatID");
            addMembers(jwt, chatID);
            connectGet(jwt);
        } catch (JSONException e) {
            Log.e("JSON PARSE ERROR", "Found in handle Success ChatViewModel");
            Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
        }
    }
}