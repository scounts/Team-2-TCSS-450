package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentChatCardBinding;

import edu.uw.tcss450.team2.thermochat.ui.chat.ChatRoom;

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<edu.uw.tcss450.team2.thermochat.ui.chat.ChatListRecyclerViewAdapter.ChatListViewHolder> {

    //Store all of the blogs to present
    private final List<ChatRoom> mChats;

    //Store all of the blogs to present
    public ChatListRecyclerViewAdapter(List<ChatRoom> items) {

        this.mChats = items;


    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.setChat(mChats.get(position));
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    /**
     * An inner class which hold the view for a contact.
     */
    public class ChatListViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentChatCardBinding binding;
        public ChatRoom mChat;

        /**
         * Constructor for the contact view holder.
         *
         * @param view the view.
         */
        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCardBinding.bind(view);



            view.setOnClickListener(v -> {
                ChatListFragmentDirections.ActionNavigationChatToChatFragment directions =
                        ChatListFragmentDirections.
                                actionNavigationChatToChatFragment(mChat);

                Navigation.findNavController(mView).navigate(directions);
            });

//            view.setOnLongClickListener(v -> {
//
//            });

        }

        /**
         * Sets the contact.
         *
         * @param chat the chat room
         */
        void setChat(final ChatRoom chat) {
            mChat = chat;
        }

    }

}
