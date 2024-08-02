package com.example.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText username = (EditText) findViewById(R.id.set_username);
        EditText password = (EditText) findViewById(R.id.set_password);
        EditText confirmation = (EditText) findViewById(R.id.confirmation);
        Button register = (Button) findViewById(R.id.register1);

        // incomplete register: need to check if account exists and satisfies requirements
        register.setOnClickListener(v -> {
            String username_set = username.getText().toString();
            String password_set = password.getText().toString();
            String password_confirm = confirmation.getText().toString();
            if (!username_set.isEmpty()) {
                if (password_confirm.equals(password_set)) {
                    Toast.makeText(this, "Register successfully! Please log in your account.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ActivityLogin.class));
                } else {
                    Toast.makeText(this, "Sorry, the passwords you entered do not match. Please check and try again.", Toast.LENGTH_SHORT).show();
                    confirmation.setText("");
                }
            }
        });
    }
}