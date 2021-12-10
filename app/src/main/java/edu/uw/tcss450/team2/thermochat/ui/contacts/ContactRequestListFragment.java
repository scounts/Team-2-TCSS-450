package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactRequestsBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

/**
 * A fragment to display the list of the users contact requests.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ContactRequestListFragment extends Fragment {

    private ContactRequestListViewModel mModel;

    /**
     * An empty constructor to instantiate the fragment
     */
    public ContactRequestListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ContactRequestListViewModel.class);

        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel.connectGet(model.getmJwt());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Local access to the ViewBinding object. No need to create as Instance Var as it is only
        //used here.
        FragmentContactRequestsBinding binding = FragmentContactRequestsBinding.bind(getView());

        mModel.addContactRequestListObserver(getViewLifecycleOwner(), contactList -> {
            //if (!contactList.isEmpty()) {
            binding.listRoot.setAdapter(
                    new ContactRequestRecyclerViewAdapter(contactList, getActivity())
            );
            //binding.layoutWait.setVisibility(View.GONE);
            //}
        });
    }

}
