package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactRequestCardBinding;
import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

/**
 * The recycler view for displaying the users contact requests.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ContactRequestRecyclerViewAdapter extends
        RecyclerView.Adapter<ContactRequestRecyclerViewAdapter.ContactRequestViewHolder> {

    private final List<Contact> mContactRequests;

    private final MutableLiveData<JSONObject> mResponse;

    private UserInfoViewModel mModel;

    private FragmentActivity mFragAct;


    /**
     * The constructor for the recycler view.
     *
     * @param items a list of contacts.
     * @param a The parent FragmentActivity
     */
    public ContactRequestRecyclerViewAdapter(List < Contact > items, FragmentActivity a) {
        this.mContactRequests = items;
        mModel = new ViewModelProvider(a).get(UserInfoViewModel.class);
        mFragAct = a;

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Creates a view holder.
     *
     * @param parent the parent.
     * @param viewType the view type
     * @return a contact view holder.
     */
    public ContactRequestViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        return new ContactRequestViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_request_card, parent, false));
    }

    @Override
    public void onBindViewHolder (@NonNull ContactRequestViewHolder holder,int position){
        holder.setContact(mContactRequests.get(position));
    }

    @Override
    public int getItemCount () {
        return mContactRequests.size();
    }


    /**
     * An inner class to hold the view for a contact.
     */
    public class ContactRequestViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentContactRequestCardBinding binding;
        public Contact mContact;

        /**
         * Constructor for the contact view holder.
         *
         * @param view the view.
         */
        public ContactRequestViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactRequestCardBinding.bind(view);
        }


        /**
         * Sets the contact and card UI functions.
         *
         * @param contact the contact
         */
        void setContact(final Contact contact) {
            binding.textUsername.setText(contact.getUsername());
            mContact = contact;


            binding.buttonAcceptContact.setOnClickListener( button -> {
                String url = "https://team-2-tcss-450-project.herokuapp.com/contacts?memberId=" +
                        mContact.getContactMemberID();


                Request request = new JsonObjectRequest(
                        Request.Method.PUT,
                        url,
                        null, //no body for this request
                        mResponse::setValue,
                        this::handleError) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        // add headers <key,value>
                        headers.put("Authorization", mModel.getmJwt());
                        return headers;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(
                        10_000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //Instantiate the RequestQueue and add the request to the queue
                Volley.newRequestQueue(mFragAct.getApplicationContext())
                        .add(request);
            });



            binding.buttonDeleteContact.setOnClickListener(button -> {

                String url = "https://team-2-tcss-450-project.herokuapp.com/contacts/?memberId=" +
                        mContact.getContactMemberID();


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
                        headers.put("Authorization", mModel.getmJwt());
                        return headers;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(
                        10_000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //Instantiate the RequestQueue and add the request to the queue
                Volley.newRequestQueue(mFragAct.getApplicationContext())
                        .add(request);

                deleteRequest();
            });

        }

        private void deleteRequest(){
            mContactRequests.remove(mContact);
            notifyDataSetChanged();
        }

        private void handleError(final VolleyError error) {
            Log.e("CONNECTION ERROR", "Error deleting contact Request");
            //throw new IllegalStateException(error.getMessage());
        }

    }
}