package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.MainActivity;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentAddContactBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

/**
 * The fragment for adding a contact
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class AddContactFragment extends Fragment {

    private AddContactViewModel mModel;

    private UserInfoViewModel mUserModel;

    private FragmentAddContactBinding binding;

    private ArrayList<String> userNames;

    /**
     * An empty constructor to instantiate the fragment
     */
    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel = new ViewModelProvider(getActivity()).get(AddContactViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentAddContactBinding.bind(getView());

        binding.imageButtonSearch.setOnClickListener(button -> {
            mModel.addContact(mUserModel.getmJwt(),
                    binding.editTextSearch.getText().toString());
        });


    }
}