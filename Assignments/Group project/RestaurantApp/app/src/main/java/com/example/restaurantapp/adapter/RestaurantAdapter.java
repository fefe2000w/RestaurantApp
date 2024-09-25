package com.example.restaurantapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantapp.R;
import com.example.restaurantapp.backend.Restaurant;

import java.util.List;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private final Context context;
    private final List<Restaurant> restaurants;

    public RestaurantAdapter(Context context, List<Restaurant> restaurants) {
        super(context, R.layout.list_item_restaurant, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_restaurant, parent, false);
        }

        ImageView restaurantIcon = convertView.findViewById(R.id.restaurant_icon);
        TextView restaurantName = convertView.findViewById(R.id.restaurant_name);
        TextView rating = convertView.findViewById(R.id.rating);
        TextView distance = convertView.findViewById(R.id.distance);
        TextView averageCost = convertView.findViewById(R.id.average_cost);

        // 假设有一个 Restaurant 对象
        Restaurant restaurant = restaurants.get(position);

        // 设置数据
        restaurantIcon.setImageResource(restaurant.getIconResId());
        restaurantName.setText(restaurant.getName());
        rating.setText(String.valueOf(restaurant.getRating()) + "★");
        distance.setText("Distance: " + restaurant.getDistance() + " km");
        averageCost.setText("Average Cost: " + restaurant.getAverageCost());

        return convertView;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView averageCost;
        TextView distance;
        TextView rating;
    }

    public void setData(List<Restaurant> newData) {
        this.restaurants.clear();
        this.restaurants.addAll(newData);
        notifyDataSetChanged();
    }
}
