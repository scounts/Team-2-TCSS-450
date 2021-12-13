package edu.uw.tcss450.team2.thermochat.ui.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.team2.thermochat.R;

import edu.uw.tcss450.team2.thermochat.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;

/**
 * The fragment for the main weather page of the application.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class WeatherFragment extends Fragment {

    WeatherViewModel mModel;
    LocationViewModel mModelL;

    WeatherViewModel mModelList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
//        mModelL = new ViewModelProvider(getActivity()).get(LocationViewModel.class);
//        UserInfoViewModel model = new ViewModelProvider(getActivity())
//                .get(UserInfoViewModel.class);
//        mModel.connectGet("current", model.getmJwt(), String.valueOf(mModelL.getCurrentLocation().getLatitude()),
//                String.valueOf(mModelL.getCurrentLocation().getLongitude()));

//        mModel.connectGet("daily", model.getmJwt(), String.valueOf(mModelL.getCurrentLocation().getLatitude()),
//                String.valueOf(mModelL.getCurrentLocation().getLongitude()));
//        mModelList = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
//        mModelList.connectGet("daily", model.getmJwt(), String.valueOf(mModelL.getCurrentLocation().getLatitude()),
//                String.valueOf(mModelL.getCurrentLocation().getLongitude()));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentWeatherBinding binding = FragmentWeatherBinding.bind(getView());

        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);

        mModel.addWeatherObserver(getViewLifecycleOwner(), weather ->
                binding.temp.setText(mModel.getCurrentWeather()));
                binding.weatherCondition.setText(mModel.getHL());

        mModel.addWeatherObserverList(getViewLifecycleOwner(), weatherList -> {
            Weather day1 = weatherList.get(0);
            Weather day2 = weatherList.get(1);
            Weather day3 = weatherList.get(2);
            Weather day4 = weatherList.get(3);
            Weather day5 = weatherList.get(4);
            Weather day6 = weatherList.get(5);
            Weather day7 = weatherList.get(6);

            binding.forecast.setText("\n" + "This week's forecast:");
            binding.weatherdaily.setText("\n" + day1.getDayFormat() + '\n' + '\n' + day2.getDayFormat() + '\n' + '\n' + day3.getDayFormat() + '\n'+ '\n' + day4.getDayFormat() + '\n' + '\n' + day5.getDayFormat() + '\n' + '\n' + day6.getDayFormat() + '\n' + '\n' + day7.getDayFormat())




            ;

        });

    }


}
