package com.example.restaurantapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText username = (EditText) findViewById(R.id.input_username);
        EditText password = (EditText) findViewById(R.id.input_password);
        Button register = (Button) findViewById(R.id.register);
        Button login = (Button) findViewById(R.id.login);

        // incomplete method: haven't checked if username and password exist and are correct
        login.setOnClickListener(v -> {
            String username_string = username.getText().toString();
            String password_string = password.getText().toString();
            if (username_string.equals("comp6442@anu.edu.au") && password_string.equals("comp6442")) {
                startActivity(new Intent(this, ActivityHome.class));
            } else {
                Toast.makeText(this, "Login unsuccessfully", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityRegister.class));
        });
    }
}