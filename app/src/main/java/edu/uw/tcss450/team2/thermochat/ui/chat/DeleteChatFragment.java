package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentDeleteChatBinding;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentDeleteContactBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.ui.contacts.DeleteContactFragmentArgs;
import edu.uw.tcss450.team2.thermochat.ui.contacts.DeleteContactFragmentDirections;
import edu.uw.tcss450.team2.thermochat.ui.contacts.DeleteContactViewModel;

/**
 * The fragment for deleting a contact connection
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class DeleteChatFragment extends Fragment {

    private DeleteChatViewModel mModel;

    private UserInfoViewModel mUserModel;

    private FragmentDeleteChatBinding binding;

    private DeleteChatFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = DeleteChatFragmentArgs.fromBundle(getArguments());



        mUserModel = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel = new ViewModelProvider(getActivity()).get(DeleteChatViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delete_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentDeleteChatBinding.bind(getView());

        binding.buttonYes.setOnClickListener(button -> {
            mModel.connectDelete(mUserModel.getmJwt(), args.getChat().getmChatId(), mUserModel.getEmail());

            Navigation.findNavController(getView()).navigate(
                    DeleteChatFragmentDirections.
                            actionDeleteChatFragmentToNavigationChat());
        });

        binding.buttonNo.setOnClickListener(button -> {
            Navigation.findNavController(getView()).navigate(
                    DeleteChatFragmentDirections.
                            actionDeleteChatFragmentToNavigationChat());
        });

    }
}
