package com.example.restaurantapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantapp.R;

import org.w3c.dom.Text;

public class InfoFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView restaurant_name = view.findViewById(R.id.info_name);
        TextView average_cost = view.findViewById(R.id.info_average_cost);
        TextView distance = view.findViewById(R.id.info_basic); // 如果需要显示距离，可以调整
        TextView rating = view.findViewById(R.id.info_socre);
        ImageView restaurant_icon = view.findViewById(R.id.icon_view);
        Button close_button = view.findViewById(R.id.close_button);
        Button like_button = view.findViewById(R.id.like_button);
        Button book_button = view.findViewById(R.id.book_button);

        // Pass data
        Bundle args = getArguments();
        if (args != null) {
            restaurant_name.setText(args.getString("name"));
            average_cost.setText(args.getString("averageCost"));
            distance.setText(args.getString("distance"));
            rating.setText(String.valueOf(args.getFloat("rating")));
            restaurant_icon.setImageResource(args.getInt("iconResId")); // 设置图标
        }

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 关闭对话框
            }
        });

        like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavoriteRestaurants();
            }
            // TODO: once: liked; twice: not liked
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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_info);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    private void updateFavoriteRestaurants() {
        // TODO: need to have list of favorite restaurants for account
    }
}

