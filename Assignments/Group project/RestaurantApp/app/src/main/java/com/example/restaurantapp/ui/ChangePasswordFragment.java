package com.example.restaurantapp.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.restaurantapp.R;

public class ChangePasswordFragment  extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        final EditText currentPasswordEditText = view.findViewById(R.id.current_password);
        final EditText newPasswordEditText = view.findViewById(R.id.new_password);
        final EditText confirmPasswordEditText = view.findViewById(R.id.confirm_password);
        Button submit_button = view.findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getContext(), "New passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: Handle password change logic here
                // For example, validate the current password and change to the new password
                // If successful:
                Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                dismiss(); // Close the dialog
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setContentView(R.layout.fragment_change_password);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return dialog;
    }
}
