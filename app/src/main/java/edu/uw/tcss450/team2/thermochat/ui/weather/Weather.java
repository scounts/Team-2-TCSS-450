package edu.uw.tcss450.team2.thermochat.ui.weather;

public class Weather {


    private String mCurrent;
    private String mCity;
    private String mCountry;
    private String mDescription;
    private String mIcon;


    public Weather(String Current, String City, String Country, String Description, String Icon) {
        mCurrent = Current;
        mCity = City;
        mCountry = Country;
        mDescription = Description;
        mIcon = Icon;

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

    public String getDescription() { return mDescription; }

    public String getIcon() { return mIcon; }


    @Override
    public String toString() {
        return mCity + '\n' + mCurrent + '\n' + mDescription;
                //+ '\n' + mIcon;
    }
}
