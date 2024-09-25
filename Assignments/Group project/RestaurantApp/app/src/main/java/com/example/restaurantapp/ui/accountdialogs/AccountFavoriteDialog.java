package com.example.restaurantapp.ui.accountdialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantapp.R;
import com.example.restaurantapp.adapter.RestaurantAdapter;
import com.example.restaurantapp.backend.Account;
import com.example.restaurantapp.backend.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class AccountFavoriteDialog extends DialogFragment {
    private Account account; // TODO: interaction with Account

    private Button close_button;
    private ListView favorites;

    private RestaurantAdapter adapter;
    private List<Restaurant> favorite_restaurants = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_favorites, container, false);

        close_button = view.findViewById(R.id.close_button_account_favorites);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        favorites = view.findViewById(R.id.account_favorite_restaurants);
        favorite_restaurants = getAllRestaurants();
        adapter = new com.example.restaurantapp.adapter.RestaurantAdapter(getContext(), favorite_restaurants);
        favorites.setAdapter(adapter);

        return view;
    }

    private List<Restaurant> getAllRestaurants() {
        // TODO: same as on SearchPage, return all restaurants according to account favorites' id
        return null;
    }
}
