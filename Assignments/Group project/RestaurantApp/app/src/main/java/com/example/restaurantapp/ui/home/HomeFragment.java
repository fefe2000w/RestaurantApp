package com.example.restaurantapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantapp.R;
import com.example.restaurantapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button switch_city = (Button) root.findViewById(R.id.switch_city);
        EditText search_input = (EditText) root.findViewById(R.id.search_bar);
        Button search_button = (Button) root.findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_string = search_input.getText().toString();
                // Todo: tokeniser and parser
                if (search_string.equals("Badger")) {
                    // Save search_string, show it on the search page later
                    Bundle bundle = new Bundle();
                    bundle.putString("search_query", search_string);
                    // 使用 NavController 进行导航
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_home);
                    navController.navigate(R.id.searchFragment, bundle);
                } else {
                    Toast.makeText(getContext(), "Sorry, search field cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Todo: click event on button switch_city
        // A list containing different cities, choosing one will switch to a different list of local restaurants?

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}