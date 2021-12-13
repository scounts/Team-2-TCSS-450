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
        mWeatherList = new MutableLiveData<>(new ArrayList<>());
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
                temp.add(weather);


            } catch (JSONException e) {
                Log.e("JSON PARSE ERROR", "Found in handle Success WeatherViewModel");
                Log.e("JSON PARSE ERROR", "Error: " + e.getMessage());
            }

            //mWeatherList.setValue(temp);
        } else if (result.has("day1")) {



        }
    }

    //For home fragment / current weather use:
    public String getCurrentWeather() {
        return mWeather.getValue().toString();

    }

    public String getHL() {
        return mWeather.getValue().getHighLow();

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





