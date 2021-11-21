package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactCardBinding;
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
    public edu.uw.tcss450.team2.thermochat.ui.chat.ChatListRecyclerViewAdapter.ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new edu.uw.tcss450.team2.thermochat.ui.chat.ChatListRecyclerViewAdapter.ChatListViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull edu.uw.tcss450.team2.thermochat.ui.chat.ChatListRecyclerViewAdapter.ChatListViewHolder holder, int position) {
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
        public FragmentContactCardBinding binding;
        public ChatRoom mChat;

        /**
         * Constructore for teh contact view holder.
         *
         * @param view the view.
         */
        public ChatListViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactCardBinding.bind(view);


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
