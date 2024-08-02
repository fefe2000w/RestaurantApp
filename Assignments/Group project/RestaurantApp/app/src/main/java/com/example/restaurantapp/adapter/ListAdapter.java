package com.example.restaurantapp.adapter;

import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.restaurantapp.R;
import com.example.restaurantapp.ui.InfoFragment;

import java.util.List;

public class ListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> items;

    public ListAdapter(Context context, List<String> items) {
        super(context, R.layout.list_item_layout, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
        }

        // 获取 item_text TextView
        TextView textView = convertView.findViewById(R.id.item_text);

        // 设置 TextView 的文本为当前列表项的内容
        String currentItem = items.get(position);
        textView.setText(currentItem);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment dialogFragment = new InfoFragment();
                // 传递数据到 DialogFragment
                Bundle args = new Bundle();
                args.putString("item_text", currentItem);
                dialogFragment.setArguments(args);
                dialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "fullScreenDialog");
            }
        });

        return convertView;
    }
}
