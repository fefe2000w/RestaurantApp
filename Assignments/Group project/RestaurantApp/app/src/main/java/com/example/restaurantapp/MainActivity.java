package com.example.restaurantapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

// may need to consider if the users haved used the app: remember login info
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckBox finishReading = findViewById(R.id.finish_reading);
        Button agreeButton = findViewById(R.id.agree_button);
        Button disagreeButton = findViewById(R.id.disagree_button);

        agreeButton.setOnClickListener(v -> {
            if (finishReading.isChecked()) {
                showPermissionDialog();
            } else {
                Toast.makeText(this, "Sorry, you need to read and agree to the terms of use.", Toast.LENGTH_SHORT).show();
            }
        });

        disagreeButton.setOnClickListener(v -> showDisagreeDialog());
    }

    private void showPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Request")
                .setMessage("Would you like to allow notifications and enable GPS, camera, and photo album permissions?")
                .setPositiveButton("YES", (dialog, which) -> requestPermissions())
                .setNegativeButton("NO", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        // Check if GPS permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // GPS permission is granted, proceed to the next activity
            Toast.makeText(this, "You can enable other permissions in settings if needed.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ActivityLogin.class));
        } else {
            // Request permissions
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean gpsGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
            boolean cameraGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            boolean storageGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

            if (gpsGranted) {
                // GPS permission granted, proceed to the next activity
                startActivity(new Intent(this, ActivityLogin.class));
                if (!cameraGranted || !storageGranted) {
                    Toast.makeText(this, "You can enable camera and storage permissions in settings if needed.", Toast.LENGTH_LONG).show();
                }
            } else {
                // GPS permission denied, show a message
                Toast.makeText(this, "GPS permission is required to use this app.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDisagreeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notice")
                .setMessage("Permission is required to use this app.")
                .setPositiveButton("YES", (dialog, which) -> {
                    // Return to the previous screen or do nothing
                    Toast.makeText(this, "Please select again", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("NO", (dialog, which) -> finish())
                .show();
    }

}