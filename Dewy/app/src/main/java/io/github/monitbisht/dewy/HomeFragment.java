package io.github.monitbisht.dewy;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    ActivityResultLauncher<String[]> locationPermissionRequest;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationPermissionRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {

                    Boolean fineLocationGranted = null;
                    Boolean coarseLocationGranted = null;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                        coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false);
                    }

                    if ( (fineLocationGranted != null && fineLocationGranted )|| (coarseLocationGranted != null && coarseLocationGranted)) {
                        fetchLocation();
                    }
                    else {
                        Toast.makeText(getContext(), "Location Permission Denied" , Toast.LENGTH_LONG )
                                .show();
                    }
                });
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

        // Checking the location permission
        if(isLocationPermissionGranted()){
            fetchLocation();
        }
        // Else requesting it from the user
        else {
            requestPermission();
        }


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

    private boolean isLocationPermissionGranted() {
        boolean fineGranted =  ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ;

        boolean coarseGranted = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return fineGranted || coarseGranted ;
    }

    public void requestPermission(){
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }

        private void fetchLocation(){
            //TODO
        }
}