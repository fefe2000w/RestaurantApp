package com.example.restaurantapp.ui.search;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurantapp.adapter.RestaurantAdapter;
import com.example.restaurantapp.backend.Restaurant;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantapp.R;
import com.example.restaurantapp.databinding.FragmentSearchBinding;
import com.example.restaurantapp.ui.InfoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private EditText search_input;
    private Button search_button;
    private Button sort_by;
    private Button filter_by;
    private ListView searched_results;

    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList = new ArrayList<>();

    private String sortBy = "Distance";
    private String filterBy = "None";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSearch;
        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        search_input = root.findViewById(R.id.search_bar);
        search_button = root.findViewById(R.id.search_button);
        searched_results = root.findViewById(R.id.searched_results);
        sort_by = root.findViewById(R.id.sort);
        filter_by = root.findViewById(R.id.filter);

        // Need to remove later
        List<String> promotions = Arrays.asList("Happy Hour 5-7 PM", "Free Dessert with Meal");
        List<String> menu = Arrays.asList("Pasta", "Pizza", "Salad");
        List<String> comments = Arrays.asList("Great food!", "Friendly staff!", "Will visit again!");

        // TODO: construct restaurants by id
        // ID format: country-city-code. On HomeFragment, the restaurants belonging to the selected country and city are searched.
        // Transfer these restaurants' id to SearchFragment
        // By these id, add new Restaurant to restaurantList

        // Example restaurants
        String iconUrl1 = convertGsToHttp("gs://restaurantapp-e7cbc.appspot.com/restaurant_icon_a.png");
        String iconUrl2 = convertGsToHttp("gs://restaurantapp-e7cbc.appspot.com/RestaurantIcon/restaurant_icon_b.png");
        String iconUrl3 = convertGsToHttp("gs://restaurantapp-e7cbc.appspot.com/RestaurantIcon/restaurant_icon_c.png");

        restaurantList.add(new Restaurant("AU-CBR-0001", "Badger", 70.5f, 2.5f, 4.5f, iconUrl1,
                "123 Badger St", "10:00 AM - 10:00 PM", "123-456-7890", promotions, menu, comments));
        restaurantList.add(new Restaurant("AU-SYD-0002", "Cafe Lab", 100.6f, 3.5f, 4.0f, iconUrl2,
                "456 Cafe Rd", "9:00 AM - 9:00 PM", "987-654-3210", promotions, menu, comments));
        restaurantList.add(new Restaurant("AU-CBR-0003", "Golden Drum", 54f, 3.0f, 4.8f, iconUrl3,
                "789 Golden Ave", "11:00 AM - 11:00 PM", "555-123-4567", promotions, menu, comments));

        // Use RestaurantAdapter
        adapter = new com.example.restaurantapp.adapter.RestaurantAdapter(getContext(), restaurantList);
        searched_results.setAdapter(adapter);



        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = search_input.getText().toString();
                updateListView(searchQuery, sortBy, filterBy);
            }
        });

        // Todo: not updating list as expected
        // The ListView was reloaded, but the results are the same as initial
        // Could that because search_query?
        sort_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortMenu(v);
            }
        });

        filter_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterMenu(v);
            }
        });

        // Click event on searched_results list
        searched_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant selectedRestaurant = restaurantList.get(position);

                // Create corresponding InfoFragment
                InfoFragment infoFragment = new InfoFragment();

                // Pass restaurant id to InfoFragment
                Bundle args = new Bundle();
                args.putString("restaurantId", selectedRestaurant.getId()); // Assuming getId() returns the restaurant's unique id
                infoFragment.setArguments(args);

                // Show InfoFragment
                FragmentManager fragmentManager = getParentFragmentManager();
                infoFragment.show(fragmentManager, "InfoFragment");
            }
        });

        updateListView("", sortBy, filterBy);

        return root;
    }

    public String convertGsToHttp(String gsUrl) {
        // 确保 gsUrl 是有效的并以 "gs://" 开头
        if (gsUrl.startsWith("gs://")) {
            // 从 gs:// 中提取路径
            String path = gsUrl.substring(5); // 去掉 "gs://"

            // 生成 HTTP URL
            return "https://firebasestorage.googleapis.com/v0/b/" + path.replace("/", "/o/") + "?alt=media";
        }
        return null; // 或者可以抛出异常
    }


    private void updateListView(String query, String sortBy, String filterBy) {
        // TODO: need to reset filteredList to contain all restaurants every time
        // However, restaurantList is changed if filterBy is selected
        // Need to reset restaurant or deep copy (resetting could be more efficient)
        // Wait for list of id to construct restaurants -- create a method: getAllRestaurants
        List<Restaurant> filteredList = new ArrayList<>(restaurantList);
        List<Restaurant> sortedDataList = getResults(query, sortBy, filterBy, filteredList);
        if (sortedDataList.isEmpty()) {
            // 提示用户没有找到任何餐馆
            Toast.makeText(getContext(), "There are no restaurants that match your criteria. You may try adjusting your filters.", Toast.LENGTH_LONG).show();
        }
        adapter.setData(sortedDataList);
        adapter.notifyDataSetChanged();
    }

    private List<Restaurant> getResults(String query, String sortBy, String filterBy, List<Restaurant> currentList) {
        Log.d("RestaurantList", "Current restaurantList size: " + restaurantList.size());
        for (Restaurant r : restaurantList) {
            Log.d("RestaurantList", "Restaurant: " + r.getName());
        }

        List<Restaurant> results = new ArrayList<>();
        for (Restaurant restaurant : currentList) {
            if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                results.add(restaurant);
            }
        }
        // 2. 根据 filterBy 进行过滤
        if (!filterBy.equals("None")) {
            if (filterBy.equals("Distance")) {
                results = filterByDistance(results);
            } else if (filterBy.equals("Average cost")) {
                results = filterByCost(results);
            } else if (filterBy.equals("Rating")) {
                results = filterByRating(results);
            }
        }


        // 3. 根据 sortBy 进行排序
        if (sortBy.equals("Distance")) {
            results.sort((r1, r2) -> Float.compare(r1.getDistance(), r2.getDistance()));  // 假设 getDistance() 返回 String
        } else if (sortBy.equals("Average cost")) {
            results.sort((r1, r2) -> Float.compare(r1.getAverageCost(), r2.getAverageCost()));
        } else if (sortBy.equals("Rating")) {
            results.sort((r1, r2) -> Float.compare(r2.getRating(), r1.getRating()));  // 按评分降序排列
        }

        Log.d("Sort", "Sort by: " + sortBy);
        Log.d("Filter", "Filter by: " + filterBy);



        return results;
    }

    private void showSortMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.sort_by_distance) {
                    sortBy = "Distance";
                } else if (itemId == R.id.sort_by_average_cost) {
                    sortBy = "Average cost";
                } else if (itemId == R.id.sort_by_rating) {
                    sortBy = "Rating";
                } else {
                    return false; // 处理未知情况
                }
                // 更新列表
                String searchQuery = search_input.getText().toString();
                updateListView(searchQuery, sortBy, filterBy);
                return true;
            }
        });

        popupMenu.show();
    }

    private void showFilterMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.filter_by_distance) {
                    filterBy = "Distance";
                } else if (itemId == R.id.filter_by_average_cost) {
                    filterBy = "Average cost";
                } else if (itemId == R.id.filter_by_rating) {
                    filterBy = "Rating";
                } else {
                    return false; // 处理未知情况
                }
                // 更新列表
                String searchQuery = search_input.getText().toString();
                updateListView(searchQuery, sortBy, filterBy);
                return true;
            }
        });

        popupMenu.show();
    }

    private List<Restaurant> filterByDistance(List<Restaurant> restaurants) {
        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getDistance()<2) {
                filteredList.add(restaurant);
            }
        }
        return filteredList;
    }

    private List<Restaurant> filterByCost(List<Restaurant> restaurants) {
        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getAverageCost()<(50f)) {  // 你可以根据需求设置条件
                filteredList.add(restaurant);
            }
        }
        return filteredList;
    }

    private List<Restaurant> filterByRating(List<Restaurant> restaurants) {
        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getRating() >= 4.5) {  // 过滤评分大于或等于 4 的餐馆
                filteredList.add(restaurant);
            }
        }
        return filteredList;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}