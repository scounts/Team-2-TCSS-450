package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.CardAddContactToChatBinding;
import edu.uw.tcss450.team2.thermochat.ui.contacts.Contact;

public class AddContactToChatRecycleViewAdapter extends RecyclerView.Adapter<AddContactToChatRecycleViewAdapter.AddContactToChatViewHolder> {

    private final List<Contact> mContacts;
    private ArrayList<Integer> mMemberID;

    /**
     * A constructor for the new contact recycler view.
     *
     * @param items a list of contacts.
     */
    public AddContactToChatRecycleViewAdapter (List<Contact> items) {
        this.mContacts = items;
        this.mMemberID = new ArrayList<>();
    }

    public ArrayList<Integer> getSelected(){
        return mMemberID;
    }

    /**
     * Cerates a view holder.
     *
     * @param parent the parent.
     * @param viewType the view type
     *
     * @return a contact view holder.
     */
    public AddContactToChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddContactToChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_add_contact_to_chat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddContactToChatViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    /**
     * An inner class which hold the view for a contact.
     */
    public class AddContactToChatViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public CardAddContactToChatBinding binding;
        public int memberID;

        /**
         * Constructor for the contact view holder.
         *
         * @param view the view.
         */
        public AddContactToChatViewHolder(View view) {
            super(view);
            mView = view;
            binding = CardAddContactToChatBinding.bind(view);


            view.setOnClickListener(v -> {
                updateSelected();
            });
        }

        /**
         * navigates to a contacts profile.
         */
        private void updateSelected(){
            if(mMemberID.contains(memberID)){
                binding.imageSelected.setImageResource(R.drawable.ic_radio_button_unchecked_24);
                int index = mMemberID.indexOf(memberID);
                mMemberID.remove(index);
            }else{
                binding.imageSelected.setImageResource(R.drawable.ic_radio_button_checked_24);
                mMemberID.add(memberID);
            }
        }

        /**
         * Sets the contact.
         *
         * @param contact the contact
         */
        void setContact(final Contact contact) {
            binding.textUsername.setText(contact.getUsername());
            memberID = contact.getContactMemberID();
        }

    }
}
