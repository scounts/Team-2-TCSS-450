package edu.uw.tcss450.team2.thermochat.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactCardBinding;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {
    //Store all of the blogs to present
    private final List<Contact> mContacts;

    //Store all of the blogs to present
    public ContactRecyclerViewAdapter(List<Contact> items) {
        this.mContacts = items;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_contact_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * An inner class which hold the view for a contact.
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public FragmentContactCardBinding binding;
        public Contact mContact;

        /**
         * Constructore for teh contact view holder.
         *
         * @param view the view.
         */
        public ContactViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactCardBinding.bind(view);


        }

        /**
         * Sets the contact.
         *
         * @param contact the contact
         */
        void setContact(final Contact contact) {
            mContact = contact;
            binding.textContactUsername.setText(contact.getUsername());
            String contactName = contact.getContactFirstName() + " " + contact.getContactLastName();
            binding.textContactName.setText(contactName);
        }

    }

}