package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;

import edu.uw.tcss450.team2.thermochat.R;

import edu.uw.tcss450.team2.thermochat.databinding.FragmentDeleteContactBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

/**
 * The fragment for deleting a contact.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class DeleteContactFragment extends Fragment {

    private DeleteContactViewModel mModel;

    private UserInfoViewModel mUserModel;

    private FragmentDeleteContactBinding binding;

    private DeleteContactFragmentArgs args;

    private int mUserID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = DeleteContactFragmentArgs.fromBundle(getArguments());

        mUserID = args.getContact().getContactMemberID();

        mUserModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel = new ViewModelProvider(getActivity()).get(DeleteContactViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delete_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentDeleteContactBinding.bind(getView());

        binding.buttonYes.setOnClickListener(button -> {
            mModel.deleteContact(mUserModel.getmJwt(), mUserID);

            Navigation.findNavController(getView()).navigate(
                    DeleteContactFragmentDirections.
                            actionDeleteContactFragmentToNavigationContacts());
        });

        binding.buttonNo.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    DeleteContactFragmentDirections.
                            actionDeleteContactFragmentToNavigationContacts());
        });

    }
}
