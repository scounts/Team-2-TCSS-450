package edu.uw.tcss450.team2.thermochat.ui.home;

public class HomeWeather {


    private String mCurrent;
    private String mCity;
    private String mCountry;


    public HomeWeather(String Current, String City, String Country) {
        mCurrent = Current;
        mCity = City;
        mCountry = Country;

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
        return "Weather {" +
                "Current Temp='" + mCurrent + '\n' +
                ", City='" + mCity + '\n' +
                ", Country='" + mCountry + '\n' +
                '}';
    }
}
