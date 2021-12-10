package edu.uw.tcss450.team2.thermochat.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * The ViewModel class with will store the users information while logged into
 * the application.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;

    /**
     * Constructor for the View Model
     * @param email The users email
     * @param jwt The java web token associated with the user
     */
    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
    }

    /**
     * Returns the email from the UserInfoViewModel
     *
     * @return mEmail
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Returns the Java web token from the UserInfoViewModel
     *
     * @return mJwt
     */
    public String getmJwt() {
        return mJwt;
    }

    /**
     * An inner class that help instantiate the ViewModel
     *
     * @author Sierra C
     * @version Dec. 2021
     */
    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;

        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}
