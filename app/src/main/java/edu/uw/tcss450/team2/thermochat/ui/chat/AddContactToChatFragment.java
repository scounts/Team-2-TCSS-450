package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.uw.tcss450.team2.thermochat.MainActivity;
import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentAddContactToChatBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.ui.contacts.ContactListViewModel;

/**
 * A Fragment to handle adding contacts to the associated chat room.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class AddContactToChatFragment extends Fragment {

    private ContactListViewModel mContactModel;
    private AddContactToChatRecycleViewAdapter mAdapter;
    private AddContactToChatViewModel mAddModel;
    private UserInfoViewModel mUserInfoModel;
    private FragmentAddContactToChatBinding binding;
    private int mChatID;
    private String mTitle;
    private ChatRoom mChat;

    /**
     * An empty constructor to instantiate the fragment
     */
    public AddContactToChatFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        AddContactToChatFragmentArgs args = AddContactToChatFragmentArgs.fromBundle(getArguments());
        mChat = args.getChat();
        mChatID = args.getChat().getmChatId();
        mTitle = args.getChat().getmChatName();

        ((MainActivity) getActivity())
                .setActionBarTitle(" Add members to " + mTitle);

        mContactModel = provider.get(ContactListViewModel.class);

        mUserInfoModel = provider.get(UserInfoViewModel.class);
        mContactModel.connectGet(mUserInfoModel.getmJwt());
        mAddModel = provider.get(AddContactToChatViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_contact_to_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentAddContactToChatBinding.bind(getView());

        mContactModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {

            mAdapter = new AddContactToChatRecycleViewAdapter(contactList);
            binding.listRoot.setAdapter(mAdapter);

        });

        mAddModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);


        binding.buttonCancel.setOnClickListener(button -> {
            Navigation.findNavController(getView())
                    .navigate(AddContactToChatFragmentDirections
                            .actionAddContactToChatFragmentToChatFragment(mChat));
        });

        binding.buttonAdd.setOnClickListener(button -> {
            try {
                handleAddContacts();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Navigation.findNavController(getView())
                    .navigate(AddContactToChatFragmentDirections
                            .actionAddContactToChatFragmentToChatFragment(mChat));
        });
    }

    /**
     * Handles adding a contact to the chat room
     *
     * @throws JSONException
     */
    private void handleAddContacts() throws JSONException {

        ArrayList<Integer> selectedContacts = mAdapter.getSelected();

        for(int i = 0 ; i < selectedContacts.size(); i++){
            mAddModel.putMembers(mUserInfoModel.getmJwt(), mChatID, selectedContacts.get(i));
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Observes the response from the server.
     *
     * @param response the response.
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                Log.e("CHAT", "Failed to create chat room");
            } else {
                try {
                    if(response.has("chatID")){
                        System.out.println("Created a chat room");
                        System.out.println("moving on to populate chat room");
                        handleAddContacts();
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }

}
