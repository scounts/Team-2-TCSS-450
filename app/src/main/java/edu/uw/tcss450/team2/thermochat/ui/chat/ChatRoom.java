package edu.uw.tcss450.team2.thermochat.ui.chat;

import java.io.Serializable;

public class ChatRoom implements Serializable {

    private final String mChatName;

    private final int mChatID;

    public ChatRoom(String chatName, int chatID) {
        mChatName = chatName;
        mChatID = chatID;
    }

    public String getmChatName() {return mChatName;}

    public int getmChatId() {return mChatID;}
}
