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
import edu.uw.tcss450.team2.thermochat.databinding.FragmentContactListBinding;
import edu.uw.tcss450.team2.thermochat.databinding.FragmentWeatherBinding;
import edu.uw.tcss450.team2.thermochat.model.UserInfoViewModel;
import edu.uw.tcss450.team2.thermochat.ui.contacts.ContactListViewModel;
import edu.uw.tcss450.team2.thermochat.ui.contacts.ContactRecyclerViewAdapter;

/**
 * The fragment for the main weather page of the application.
 *
 * @author Sierra C
 * @version Dec. 2021
 */
public class WeatherFragment extends Fragment {

    WeatherViewModel mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);

        UserInfoViewModel model = new ViewModelProvider(getActivity())
                .get(UserInfoViewModel.class);

        mModel.connectGet(model.getmJwt());
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
        //mModel.
        mModel.addWeatherObserver(getViewLifecycleOwner(), weather ->
                binding.weatherCondition.setText("" + mModel.getCurrentWeather()));

    }


}
