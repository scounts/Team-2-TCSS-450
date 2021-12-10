package edu.uw.tcss450.team2.thermochat.ui.chat;

import java.io.Serializable;

/**
 * A java class to store the information of
 * any given chat room instantiated in it.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class ChatRoom implements Serializable {

    private final String mChatName;

    private final int mChatID;

    /**
     * The constructor for the chatroom object class
     *
     * @param chatName
     * @param chatID
     */
    public ChatRoom(String chatName, int chatID) {
        mChatName = chatName;
        mChatID = chatID;
    }

    /**
     * Returns the name of the chat room
     *
     * @return mChatName
     */
    public String getmChatName() {return mChatName;}


    /**
     * Returns the ID number of the chat room for database references.
     *
     * @return mChatID
     */
    public int getmChatId() {return mChatID;}
}
