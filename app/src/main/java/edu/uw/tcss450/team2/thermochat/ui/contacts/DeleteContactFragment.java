package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import edu.uw.tcss450.team2.thermochat.R;

import edu.uw.tcss450.team2.thermochat.databinding.FragmentDeleteContactBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

public class DeleteContactFragment extends Fragment {

    private DeleteContactViewModel mModel;

    private UserInfoViewModel mUserModel;

    private FragmentDeleteContactBinding binding;

    private ArrayList<String> userNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            mModel.deleteContact(mUserModel.getmJwt(),
        });

        binding.buttonNo.setOnClickListener(button -> {

        });

    }
}
