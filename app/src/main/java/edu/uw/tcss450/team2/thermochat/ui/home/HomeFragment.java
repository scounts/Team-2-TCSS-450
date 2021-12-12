package edu.uw.tcss450.team2.thermochat.ui.home;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uw.tcss450.team2.thermochat.MainActivity;
import edu.uw.tcss450.team2.thermochat.R;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentHomeBinding;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentRegisterBinding;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.ui.weather.LocationViewModel;
import edu.uw.tcss450.team2.thermochat.ui.weather.WeatherViewModel;
import edu.uw.tcss450.team2.thermochat.ui.weather.Weather;
import edu.uw.tcss450.team2.thermochat.ui.weather.WeatherFragment;



/**
 * A fragment that represents the home page of the app.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class HomeFragment extends Fragment {

    WeatherViewModel mModelH;

    /**
     * An empty constructor to instantiate the fragment
     */


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FragmentHomeBinding bind = FragmentHomeBinding.bind(getView());

        mModelH = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
//
        mModelH.addWeatherObserver(getViewLifecycleOwner(), weatherhome ->
                bind.textViewWeatherLabel.setText("" + mModelH.getCurrentWeather()));

    }
}
