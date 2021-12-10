package edu.uw.tcss450.team2.thermochat.ui.chat;

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

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentChatListBinding;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactListBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.ui.chat.ChatListViewModel;
import edu.uw.tcss450.team2.thermochat.ui.chat.ChatListRecyclerViewAdapter;

/**
 * The fragment that displays all of the chat rooms that
 * the user is a member of.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ChatListFragment extends Fragment {

    private ChatListViewModel mModel;
    private ChatListRecyclerViewAdapter mAdapter;
    private FragmentChatListBinding binding;
    private UserInfoViewModel model;

    /**
     * An empty constructor to instantiate the fragment
     */
    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(ChatListViewModel.class);

        model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel.connectGet(model.getmJwt());
    }

    /**
     * Inflates the container for the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    /**
     * Creates an instance of the fragment for when the user returns to it.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentChatListBinding.bind(getView());

        binding.imageButtonAddChat.setOnClickListener(button ->{
            createChat();
        } );

        mModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
            //if (!chatList.isEmpty()) {
            binding.listRoot.setAdapter(
                    new ChatListRecyclerViewAdapter(chatList)
            );
            //}
        });
    }

    /**
     * creating chat room with title that user typed
     */
    private void createChat() {
        String title = binding.textViewChatTitle.getText().toString().trim();
        if(title.length() < 2){
            binding.textViewChatTitle.setError("Please enter a valid chat room name");
        }else{
            mModel.addChat(model.getmJwt(), title);
        }
    }
}