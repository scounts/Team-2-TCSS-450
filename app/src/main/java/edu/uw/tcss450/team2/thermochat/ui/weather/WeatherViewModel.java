package edu.uw.tcss450.team2.thermochat.ui.weather;

import android.app.Application;
import android.util.EventLogTags;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.team2.thermochat.ui.contacts.Contact;

public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<List<Weather>> mWeatherList;

    private MutableLiveData<Weather> mWeather;
    private final MutableLiveData<JSONObject> mResponse;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mWeatherList = new MutableLiveData<>(new ArrayList<Weather>());
        mWeather = new MutableLiveData<Weather>();

        mResponse = new MutableLiveData<>();
        mResponse.setValue(new JSONObject());
    }

    /**
     * Add an observer to the weather list view model.
     *
     * @param owner the owner
     * @param observer the observer
     */
    public void addWeatherObserver(@NonNull LifecycleOwner owner,
                                       @NonNull Observer<? super Weather> observer){
        mWeather.observe(owner, observer);
    }

    public void addWeatherObserverList( @NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<Weather>> observer){
        mWeatherList.observe(owner, observer);
    }


    /**
     * Connects to webservice endpoint to retrieve a list of contacts.
     *
     * @param jwt a valid jwt.
     */
    public void connectGet (String type, String jwt, String latitude, String longitude){
        String url = "";
        if (type == "current") {
            url = url + "https://team-2-tcss-450-project.herokuapp.com/weather/current/?latitude=" + latitude +"&longitude=" + longitude;
        } else if (type == "daily") {
            url = url + "https://team-2-tcss-450-project.herokuapp.com/weather/daily/?latitude=" + latitude +"&longitude=" + longitude;
        } else {
            url = null;
        }

        //String url = "https://team-2-tcss-450-project.herokuapp.com/weather/current/?latitude=" + latitude +"&longitude=" + longitude;
        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //no body for this get request
                this::handleSuccess,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    /**
     * Handles a successful connection with the webservice.
     *
     * @param result result from webservice.
     */
    private void handleSuccess(final JSONObject result) {
        ArrayList<Weather> temp = new ArrayList<>();

        if (result.has("location")) {

            try {

                JSONObject location = result.getJSONObject("location");
                JSONObject temperature = result.getJSONObject("temperature");
                JSONObject description = location.getJSONObject("desc");

                String current = temperature.getString("current_temp");
                String city = location.getString("city");
                String country = location.getString("country");
                String desc = description.getString("main");
                String high = temperature.getString("high_temp");
                String low = temperature.getString("low_temp");

                Weather weather = new Weather(current, city, country, desc, high, low);
                mWeather.setValue(weather);
                //temp.add(weather);



            } catch (JSONException e) {
                Log.e("JSON PARSE ERROR", "Found in handle Success WeatherViewModel");
                Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
            }

            //mWeatherList.setValue(temp);
        } else if (result.has("day1")) {
            try {


                JSONObject day1 = result.getJSONObject("day1");
                JSONObject day2 = result.getJSONObject("day2");
                JSONObject day3 = result.getJSONObject("day3");
                JSONObject day4 = result.getJSONObject("day4");
                JSONObject day5 = result.getJSONObject("day5");
                JSONObject day6 = result.getJSONObject("day6");
                JSONObject day7 = result.getJSONObject("day7");

                String day1high = day1.getString("high");
                String day1low = day1.getString("low");
                String day1date = day1.getString("date");
                String day1desc = day1.getString("desc");

                Weather weatherday1 = new Weather(day1high, day1low, day1date, day1desc);
                temp.add(weatherday1);

                String day2high = day2.getString("high");
                String day2low = day2.getString("low");
                String day2date = day2.getString("date");
                String day2desc = day2.getString("desc");

                Weather weatherday2 = new Weather(day2high, day2low, day2date, day2desc);
                temp.add(weatherday2);

                String day3high = day3.getString("high");
                String day3low = day3.getString("low");
                String day3date = day3.getString("date");
                String day3desc = day3.getString("desc");

                Weather weatherday3 = new Weather(day3high, day3low, day3date, day3desc);
                temp.add(weatherday3);

                String day4high = day4.getString("high");
                String day4low = day4.getString("low");
                String day4date = day4.getString("date");
                String day4desc = day4.getString("desc");

                Weather weatherday4 = new Weather(day4high, day4low, day4date, day4desc);
                temp.add(weatherday4);

                String day5high = day5.getString("high");
                String day5low = day5.getString("low");
                String day5date = day5.getString("date");
                String day5desc = day5.getString("desc");

                Weather weatherday5 = new Weather(day5high, day5low, day5date, day5desc);
                temp.add(weatherday5);

                String day6high = day6.getString("high");
                String day6low = day6.getString("low");
                String day6date = day6.getString("date");
                String day6desc = day6.getString("desc");

                Weather weatherday6 = new Weather(day6high, day6low, day6date, day6desc);
                temp.add(weatherday6);

                String day7high = day7.getString("high");
                String day7low = day7.getString("low");
                String day7date = day7.getString("date");
                String day7desc = day7.getString("desc");

                Weather weatherday7 = new Weather(day7high, day7low, day7date, day7desc);
                temp.add(weatherday7);


            } catch (JSONException e) {
                Log.e("JSON PARSE ERROR", "Found in handle Success WeatherViewModel");
                Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
            }
        }

        mWeatherList.setValue(temp);

    }

    //For home fragment / current weather use:
    public String getCurrentWeather() {
        return mWeather.getValue().toString();

    }

    public String getHL() {
        return mWeather.getValue().getHighLow();

    }

    public List<Weather> getList() {
        return mWeatherList.getValue();

    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }

}





