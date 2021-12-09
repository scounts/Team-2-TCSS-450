package edu.uw.tcss450.team2.thermochat.ui.contacts;

import java.io.Serializable;

public class Contact implements Serializable {

    //private final String myEmail;
    //private final String myFirstName;
   // private final String myLastName;
    private final String myUserName;
    private final int myMemberID;



    public Contact( String userName, int id) {
        this.myUserName = userName;
        this.myMemberID = id;
    }



    /**
     * Get the contacts username.
     * @return the username.
     */
    public String getUsername(){
        return myUserName;
    }

    /**
     * Get the contact memberID.
     * @return the memeberID.
     */
    public int getContactMemberID(){
        return myMemberID;
    }

}