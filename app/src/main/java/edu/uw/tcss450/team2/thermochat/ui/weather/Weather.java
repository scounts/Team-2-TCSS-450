package edu.uw.tcss450.team2.thermochat.ui.weather;

import android.util.EventLogTags;

public class Weather {


    private String mCurrent;
    private String mCity;
    private String mCountry;
    private String mDescription;


    public Weather(String Current, String City, String Country) {
        mCurrent = Current;
        mCity = City;
        mCountry = Country;
        //mDescription = Description;

    }

    public String getCurrent() {
        return mCurrent;
    }

    public String getCity() {
        return mCity;
    }

    public String getCountry() {
        return mCountry;
    }


    @Override
    public String toString() {
        return mCity + ", " + mCountry + '\n' +
                mCurrent;
    }
}
