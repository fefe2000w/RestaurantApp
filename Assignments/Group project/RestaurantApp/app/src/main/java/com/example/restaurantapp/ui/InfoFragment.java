package com.example.restaurantapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantapp.R;

public class InfoFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView textView = view.findViewById(R.id.full_screen_text);
        Button close_button = (Button) view.findViewById(R.id.close_button);
        Button book_button = (Button) view.findViewById(R.id.book_button);

        Bundle args = getArguments();
        if (args != null) {
            String itemText = args.getString("item_text");
            textView.setText(itemText);
        }

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 关闭对话框
            }
        });

        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建并显示 BookingFragment 对话框
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
}
