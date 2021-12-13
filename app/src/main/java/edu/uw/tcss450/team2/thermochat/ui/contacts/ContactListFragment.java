package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactListBinding;
import edu.uw.tcss450.team2.thermochat.ui.chat.ChatListFragmentDirections;

/**
 * A fragment that displays a list of the users verified contacts
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ContactListFragment extends Fragment {

    private UserInfoViewModel mInfoModel;
    private ContactListViewModel mModel;

    /**
     * An empty cinstructor to instantiate the fragment.
     */
    public ContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactListViewModel.class);

        mInfoModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel.connectGet(mInfoModel.getmJwt());
    }

    /**
     * Inflates the container for the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    /**
     * Creates an instance of the fragment for when the user returns to it.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentContactListBinding binding = FragmentContactListBinding.bind(getView());

        mModel.addContactListObserver(getViewLifecycleOwner(), contactList -> {
            //if (!contactList.isEmpty()) {
            binding.listRoot.setAdapter(
                    new ContactRecyclerViewAdapter(contactList)
            );
        });

        binding.imageButtonRequestContact.setOnClickListener(button ->{
            AddContactDialog dialog = new AddContactDialog(mInfoModel, mModel);
            dialog.show(getChildFragmentManager(), "add");
        });
//            Navigation.findNavController(getView()).navigate(
//                    ContactListFragmentDirections
//                            .actionNavigationContactsToAddContactFragment()));

        binding.textViewRequestContact.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        ContactListFragmentDirections.
                                actionNavigationContactsToContactRequestListFragment()));
    }
}