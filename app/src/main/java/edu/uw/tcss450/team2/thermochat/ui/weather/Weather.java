package edu.uw.tcss450.team2.thermochat.ui.weather;

import android.util.EventLogTags;

public class Weather {


    private String mCurrent;
    private String mCity;
    private String mCountry;
    private String mDescription;
    private String mHigh;
    private String mLow;
    private String mDate;

    //for current
    public Weather(String Current, String City, String Country, String Description, String high, String low) {
        mCurrent = Current;
        mCity = City;
        mCountry = Country;
        mDescription = Description;
        mHigh = high;
        mLow = low;
        //mDate = "";
    }
    //for daily
    public Weather(String high, String low, String date, String desc) {
        mHigh = high;
        mLow = low;
        mDate = date;
        mDescription = desc;
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

    public String getDate() {
        return mDate;
    }

    public String getHigh() {
        return mHigh;
    }

    public String getLow() {
        return mLow;
    }

    public String getDescription() {
        return mDescription;
    }


    @Override
    public String toString() {
        return mCity + ", " + mCountry + '\n' +
                mDescription + " " + mCurrent;
    }

    public String getHighLow() {

        return "H:" + mHigh + " " + "L:" + mLow;
    }

    public String getDayFormat() {
        return mDate + "    " + mDescription + "    " + mHigh + " " + mLow;
    }

}
