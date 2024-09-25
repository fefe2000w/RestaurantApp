package com.example.restaurantapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.R;
import com.example.restaurantapp.backend.Account;
import com.example.restaurantapp.backend.Restaurant;

import org.w3c.dom.Text;

public class InfoFragment extends DialogFragment {
    private boolean isLiked = false;
    private String restaurantId;
    private Account account; // TODO: how to interact with Account?

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView restaurant_name = view.findViewById(R.id.info_name);
        TextView average_cost = view.findViewById(R.id.info_average_cost);
        TextView distance = view.findViewById(R.id.info_basic);
        TextView rating = view.findViewById(R.id.info_socre);
        ImageView restaurant_icon = view.findViewById(R.id.icon_view);
        Button close_button = view.findViewById(R.id.close_button);
        Button like_button = view.findViewById(R.id.like_button);
        Button book_button = view.findViewById(R.id.book_button);
        // TODO: need to enable comment, which is linked to Restaurant comments and Account comments
        // will it be difficult? need id? optional?

        // Retrieve restaurant ID
        Bundle args = getArguments();
        if (args != null) {
            restaurantId = args.getString("restaurantId");
            // Use the restaurant ID to get restaurant details (you might have a method to fetch this)
            Restaurant restaurant = getRestaurantById(restaurantId); // Assume this method fetches restaurant details by ID

            if (restaurant != null) {
                restaurant_name.setText(restaurant.getName());
                average_cost.setText(String.valueOf(restaurant.getAverageCost()));
                distance.setText(String.valueOf(restaurant.getDistance())); // Assuming distance is a float
                rating.setText(String.valueOf(restaurant.getRating()));
                // Assuming you have a method to load image from URL
                Glide.with(this).load(restaurant.getIconURL()).into(restaurant_icon);
            }
        }


        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Close dialog window
            }
        });

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLiked) {
                    // 取消收藏的逻辑
                    updateFavorites(false); // 这里可以传入一个参数来表示是否收藏
                    Toast.makeText(getContext(), "This restaurant has been removed from your favorites.", Toast.LENGTH_SHORT).show();
                } else {
                    // 收藏的逻辑
                    updateFavorites(true); // 这里可以传入一个参数来表示是否收藏
                    Toast.makeText(getContext(), "This restaurant has been added to your favorites.", Toast.LENGTH_SHORT).show();
                }
                // 切换收藏状态
                isLiked = !isLiked;
            }
        });

        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show BookingFragment
                BookingFragment bookingFragment = new BookingFragment();
                bookingFragment.show(getChildFragmentManager(), "BookingFragment");
            }
        });

        return view;
    }

    private Restaurant getRestaurantById(String id) {
        // TODO: retrieve the restaurant by id from the database
        // split info and use the detailed info to construct restaurant
        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_info);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    private void updateFavorites(boolean isLiked) {
        // TODO: need to have list of favorite restaurants for account
        if (isLiked) {
            account.addFavoriteRestaurant(restaurantId);
        } else {
            account.removeFavoriteRestaurant(restaurantId);
        }
    }
}

