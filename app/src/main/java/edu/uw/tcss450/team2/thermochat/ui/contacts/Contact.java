package edu.uw.tcss450.team2.thermochat.ui.contacts;

public class Contact {

    //private final String myEmail;
    //private final String myFirstName;
   // private final String myLastName;
    private final String myUserName;
    private final int myMemberID;

//    /**
//     * A class to represent a contact.
//     *
//     * @param email the contacts email
//     * @param firstName contacts first name
//     * @param lastName contacts last name
//     * @param userName contacts username
//     * @param id contacts id
//     */
//    public Contact(String email, String firstName, String lastName, String userName, int id) {
//        this.myEmail = email;
//        this.myFirstName = firstName;
//        this.myLastName = lastName;
//        this.myUserName = userName;
//        this.myMemberID = id;
//    }


    public Contact( String userName, int id) {
        this.myUserName = userName;
        this.myMemberID = id;
    }

//    /**
//     * Get the contacts email.
//     * @return the user email.
//     */
//    public String getContactEmail(){
//        return myEmail;
//    }
//
//    /**
//     * Get the contacts first name.
//     * @return returns the first name.
//     */
//    public String getContactFirstName(){
//        return myFirstName;
//    }
//
//    /**
//     * Get the contacts last name.
//     * @return the last name.
//     */
//    public String getContactLastName(){
//        return myLastName;
//    }

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