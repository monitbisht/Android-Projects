package io.github.monitbisht.dewy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
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
        RecyclerView rv = view.findViewById(R.id.hourly_forecast_rv);
        rv.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        List<HourlyForecastItem> mockData = Arrays.asList(
                new HourlyForecastItem("3 AM", R.drawable.ic_cloudy, "18°"),
                new HourlyForecastItem("6 AM", R.drawable.ic_cloudy, "18°"),
                new HourlyForecastItem("9 AM", R.drawable.ic_cloudy, "18°"),
                new HourlyForecastItem("12 AM", R.drawable.ic_cloudy, "18°"),
                new HourlyForecastItem("3 PM", R.drawable.ic_clear_day, "19°"),
                new HourlyForecastItem("6 PM", R.drawable.ic_clear_day, "20°"),
                new HourlyForecastItem("9 PM", R.drawable.ic_cloudy, "19°"),
                new HourlyForecastItem("12 AM", R.drawable.ic_cloudy, "19°")
        );

        rv.setAdapter(new HourlyForecastAdapter(mockData));


    }
}