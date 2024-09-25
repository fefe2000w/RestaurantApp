package com.example.restaurantapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_restaurant, parent, false);
            holder = new ViewHolder();
            holder.icon = convertView.findViewById(R.id.restaurant_icon);
            holder.name = convertView.findViewById(R.id.restaurant_name);
            holder.rating = convertView.findViewById(R.id.rating);
            holder.distance = convertView.findViewById(R.id.distance);
            holder.averageCost = convertView.findViewById(R.id.average_cost);
            convertView.setTag(holder);  // 将 ViewHolder 绑定到 convertView
        } else {
            holder = (ViewHolder) convertView.getTag();  // 重新使用现有的 ViewHolder
        }

        // 获取 Restaurant 对象
        Restaurant restaurant = restaurants.get(position);

        // 设置数据
        String imageURL = restaurant.getIconURL();
        Glide.with(getContext())
                .load(imageURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .timeout(60000)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.icon);  // 使用 ViewHolder

        holder.name.setText(restaurant.getName());
        holder.rating.setText(String.valueOf(restaurant.getRating()) + "★");
        holder.distance.setText("Distance: " + restaurant.getDistance() + " km");
        holder.averageCost.setText("Average Cost: $" + restaurant.getAverageCost());

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
