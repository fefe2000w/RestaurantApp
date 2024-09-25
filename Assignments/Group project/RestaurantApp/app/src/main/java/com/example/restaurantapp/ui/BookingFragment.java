package com.example.restaurantapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantapp.R;
import com.example.restaurantapp.backend.Reservation;

public class BookingFragment extends DialogFragment {
    private String selectedDate;
    private String selectedTimeSlot;
    private int numberOfPeople;
    private String note;

    private CalendarView calendar;
    private Button close_button;
    private Button select_timeslot;
    private TextView selected_date;
    private TextView selected_timeslot;
    private EditText number_of_people;
    private EditText note_optional;
    private Button book_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        TextView textView = view.findViewById(R.id.full_screen_text1);
        calendar = view.findViewById(R.id.calendar);
        close_button = view.findViewById(R.id.close_button1);
        select_timeslot = view.findViewById(R.id.time_slot);
        selected_date = view.findViewById(R.id.selected_date);
        selected_timeslot = view.findViewById(R.id.selected_timeslot);
        number_of_people = view.findViewById(R.id.number_people);
        note_optional = view.findViewById(R.id.note);
        book_button = view.findViewById(R.id.book);

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 关闭对话框
            }
        });

        calendar.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            selected_date.setText(selectedDate);
        });

        select_timeslot.setOnClickListener(v -> showTimeSlotPicker());
        book_button.setOnClickListener(v -> {
            // 获取输入的数据
            numberOfPeople = Integer.parseInt(number_of_people.getText().toString());
            note = note_optional.getText().toString();

            // 创建 Reservation 对象并保存到数据库
            if (selectedDate != null && selectedTimeSlot != null && numberOfPeople > 0) {
                Reservation reservation = new Reservation(selectedDate, selectedTimeSlot, numberOfPeople, note);
                saveReservationToDatabase(reservation);
                Toast.makeText(getContext(), "Reservation made!", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Please fill all the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_booking);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    // 显示时间段选择对话框
    private void showTimeSlotPicker() {
        String[] timeSlots = {"11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM",
                "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM",
                "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM",
                "8:00 PM", "8:30 PM", "9:00 PM"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Time Slot");
        builder.setItems(timeSlots, (dialog, which) -> {
            selectedTimeSlot = timeSlots[which];
            // 更新显示的时间段
            selected_timeslot.setText(selectedTimeSlot);
        });
        builder.show();
    }

    // 将 Reservation 保存到数据库（此处需要实现数据库保存逻辑）
    private void saveReservationToDatabase(Reservation reservation) {
        // 你可以在这里使用 SQLite、Room 或者其他数据库来存储 Reservation
    }
}
