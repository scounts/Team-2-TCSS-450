package edu.uw.tcss450.team2.thermochat.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

/**
 * View model that used to observe
 * and manipulate data from chat message fragment.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class NewMessageCountViewModel extends ViewModel {
    private MutableLiveData<Integer> mNewMessageCount;

    /**
     * Constructor for the View Model
     */
    public NewMessageCountViewModel() {
        mNewMessageCount = new MutableLiveData<>();
        mNewMessageCount.setValue(0);
    }

    /**
     * Adds an observer to the View Model
     *
     * @param owner lifespan of given owner.
     * @param observer callback from live data.
     */
    public void addMessageCountObserver(@NonNull LifecycleOwner owner,
                                        @NonNull Observer<? super Integer> observer) {
        mNewMessageCount.observe(owner, observer);
    }

    /**
     * Increments the message count.
     */
    public void increment() {
        mNewMessageCount.setValue(mNewMessageCount.getValue() + 1);
    }

    /**
     * Resets the message count.
     */
    public void reset() {
        mNewMessageCount.setValue(0);
    }
}
