package edu.uw.tcss450.team2.thermochat.ui.chat;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

public class AddChatDialog extends DialogFragment {

    private UserInfoViewModel mUserInfoModel;
    private ChatListViewModel mChatViewModel;
    private TextView userInput;

    public AddChatDialog(UserInfoViewModel userInfoViewModel,
                            ChatListViewModel contactListViewModel) {
        this.mChatViewModel = contactListViewModel;
        this.mUserInfoModel = userInfoViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_chat, null);

        userInput = view.findViewById(R.id.chat_add_name);
        Button addOkButton = view.findViewById(R.id.chat_add_ok);


        addOkButton.setOnClickListener(v -> {
            String title = userInput.getText().toString().trim();
            if(title.length() < 2){
                userInput.setError("Please enter a valid chat room name");
            }else{
                mChatViewModel.addChat(mUserInfoModel.getmJwt(), title);
                mChatViewModel.addResponseObserver(this.getActivity(),
                        this::observeAddUserResponse);
            }

        });



        builder.setView(view);
        return builder.create();
    }

    /**
     * An observer on the HTTP Response from the web server.
     *
     * @param response the Response from the server
     */
    private void observeAddUserResponse(JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    userInput.setError("Error Adding: " +
                            response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                dismiss();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
