package com.example.restaurantapp.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.restaurantapp.R;
import com.example.restaurantapp.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private Button switch_city;
    private TextView current_location;
    private Spinner country_selection;
    private Spinner city_selection;
    private Button confirm_selection;

    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private Map<String, List<String>> countryCityMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null && fineLocationGranted) {
                        getUserLocation();
                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        getUserLocation();
                    } else {
                        Toast.makeText(requireContext(), "GPS permission required", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        countryCityMap = new HashMap<>();

        // 创建国家列表
        List<String> countries = new ArrayList<>();
        countries.add("United States");
        countries.add("China");
        countries.add("Australia");

        // 对国家列表进行排序
        Collections.sort(countries);

        // 将国家和对应的城市列表放入映射中
        countryCityMap.put("United States", new ArrayList<>(List.of("New York", "Washington", "Chicago")));
        countryCityMap.put("China", new ArrayList<>(List.of("Beijing", "Shanghai", "Guangzhou")));
        countryCityMap.put("Australia", new ArrayList<>(List.of("Canberra", "Sydney", "Melbourne")));

        // 对城市进行排序
        for (Map.Entry<String, List<String>> entry : countryCityMap.entrySet()) {
            Collections.sort(entry.getValue()); // 对每个国家的城市列表进行排序
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        current_location = root.findViewById(R.id.current_location);
        switch_city = root.findViewById(R.id.switch_city);
        country_selection = root.findViewById(R.id.country_spinner);
        city_selection = root.findViewById(R.id.city_spinner);
        confirm_selection = root.findViewById(R.id.confirm_selection);

        List<String> countries = new ArrayList<>(countryCityMap.keySet());
        Collections.sort(countries);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_selection.setAdapter(countryAdapter);

        country_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = (String) parent.getItemAtPosition(position);
                updateCitySpinner(selectedCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        checkLocationPermission();

        switch_city.setOnClickListener(v -> {
            country_selection.setVisibility(View.VISIBLE);
            city_selection.setVisibility(View.VISIBLE);
            confirm_selection.setVisibility(View.VISIBLE);
        });

        confirm_selection.setOnClickListener(v -> {
            String selectedCountry = country_selection.getSelectedItem().toString();
            String selectedCity = city_selection.getSelectedItem().toString();
            current_location.setText(selectedCountry + " - " + selectedCity);

            country_selection.setVisibility(View.GONE);
            city_selection.setVisibility(View.GONE);
            confirm_selection.setVisibility(View.GONE);

            Toast.makeText(requireContext(), "Switched to " + selectedCountry + " - " + selectedCity, Toast.LENGTH_SHORT).show();
            switchCityDatabase(selectedCity);
        });

        return root;
    }

    private void updateCitySpinner(String country) {
        List<String> cities = countryCityMap.get(country);

        if (cities != null) {
            ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, cities);
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city_selection.setAdapter(cityAdapter);
            city_selection.setVisibility(View.VISIBLE); // 确保城市选择器可见
            city_selection.setSelection(0); // 设置默认选择第一个城市
        }
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        } else {
            getUserLocation();
        }
    }

    private void getUserLocation() {
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            getLocationInfo(location);
                        } else {
                            Toast.makeText(requireContext(), "Failed to acquire current location", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "GPS permission required", Toast.LENGTH_SHORT).show();
        }
    }

    private void getLocationInfo(Location location) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String country = addresses.get(0).getCountryName();
                String city = addresses.get(0).getLocality();
                current_location.setText(("Current city: " + country != null ? country : "Unknown") + " - " + (city != null ? city : "Unknown"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to acquire current location", Toast.LENGTH_SHORT).show();
        }
    }

    private void switchCityDatabase(String city) {
        // TODO: Switch city dataset
        Toast.makeText(requireContext(), "Switch to new dataset: " + city, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
