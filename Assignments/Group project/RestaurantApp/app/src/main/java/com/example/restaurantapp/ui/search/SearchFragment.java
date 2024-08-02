package com.example.restaurantapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.restaurantapp.adapter.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.restaurantapp.R;
import com.example.restaurantapp.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private EditText search_input;
    private ListView searched_results;
    private String sortBy = "distance";
    private String filterBy = "distannce";
    private ListAdapter adapter;
    private List<String> resultsList = new ArrayList<>();
    private static final int SORT_BY_DISTANCE = 1;
    private static final int SORT_BY_AVERAGE_COST = 2;
    private static final int SORT_BY_RATING = 3;
    private static final int FILTER_BY_DISTANCE = 1;
    private static final int FILTER_BY_AVERAGE_COST = 2;
    private static final int FILTER_BY_RATING = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSearch;
        searchViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button back_button = (Button) root.findViewById(R.id.back_button);
        search_input = (EditText) root.findViewById(R.id.search_bar1);
        Button search_button = (Button) root.findViewById(R.id.search_button1);
        searched_results = (ListView) root.findViewById(R.id.searched_results);
        Button switch_area = (Button) root.findViewById(R.id.switch_area);
        Button sort_by = (Button) root.findViewById(R.id.sort);
        Button filter_by = (Button) root.findViewById(R.id.filter);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_home);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });

        resultsList.add("Badger");
        adapter = new com.example.restaurantapp.adapter.ListAdapter(getContext(), resultsList);
        searched_results.setAdapter(adapter);

        Bundle args = getArguments();
        if (args != null) {
            String search_string = args.getString("search_query");
            if (!search_string.isEmpty()) {
                search_input.setText(search_string);
                updateListView(search_string, sortBy);
            }
        }

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

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = search_input.getText().toString();
                updateListView(searchQuery, sortBy);
            }
        });




        return root;
    }

    private void updateListView(String query, String sortBy) {
        // 假设你有一个数据列表和适配器
        List<String> sortedDataList = getResults(query, sortBy);
        sortedDataList.add(0, "Badger");
        adapter.clear();
        adapter.addAll(sortedDataList);
        adapter.notifyDataSetChanged();
    }

    private List<String> getResults(String query, String sort_or_filter) {
        // 根据查询内容从数据源获取数据，这里是示例
        List<String> results = new ArrayList<>();
        if (query.isEmpty()) {
            // 返回默认数据
        } else {
            // 返回匹配查询内容的数据
        }
        return results;
    }

    private void showSortMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case SORT_BY_DISTANCE:
                        sortBy = "Distance";
                        break;
                    case SORT_BY_AVERAGE_COST:
                        sortBy = "Average cost";
                        break;
                    case SORT_BY_RATING:
                        sortBy = "Rating";
                        break;
                }
                // 获取当前的搜索内容并更新列表
                String searchQuery = search_input.getText().toString();
                updateListView(searchQuery, sortBy);
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
                switch (item.getItemId()) {
                    case FILTER_BY_DISTANCE:
                        filterBy = "Distance";
                        break;
                    case FILTER_BY_AVERAGE_COST:
                        filterBy = "Average cost";
                        break;
                    case FILTER_BY_RATING:
                        filterBy = "Rating";
                        break;
                }
                // 获取当前的搜索内容并更新列表
                String searchQuery = search_input.getText().toString();
                updateListView(searchQuery, filterBy);
                return true;
            }
        });

        popupMenu.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}