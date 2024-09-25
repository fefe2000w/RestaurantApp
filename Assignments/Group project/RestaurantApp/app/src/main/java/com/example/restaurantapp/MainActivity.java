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

        // Request all required permissions
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean gpsGranted = false;
            String firstDeniedPermission = null;

            // 检查每个权限的状态
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    gpsGranted = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
                } else if (grantResults[i] != PackageManager.PERMISSION_GRANTED && firstDeniedPermission == null) {
                    firstDeniedPermission = permissions[i].replace("android.permission.", "");
                }
            }

            if (!gpsGranted) {
                // GPS 权限被拒绝，提示用户这是必须的
                Toast.makeText(this, "GPS permission is required to use this app.", Toast.LENGTH_SHORT).show();
            } else {
                // GPS 权限已授予，跳转到登录页面
                startActivity(new Intent(this, ActivityLogin.class));
            }

            if (firstDeniedPermission != null) {
                // 其他权限被拒绝，提示用户可以在设置中授权
                Toast.makeText(this, "Permission denied: " + firstDeniedPermission + ". You can enable it in settings if needed.", Toast.LENGTH_LONG).show();
                // 仍然跳转到登录页面
                startActivity(new Intent(this, ActivityLogin.class));
            }
        }
    }

    private void showDisagreeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notice")
                .setMessage("Permission is required to use this app.")
                .setPositiveButton("YES", (dialog, which) -> {
                    Toast.makeText(this, "Please select again", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("NO", (dialog, which) -> finish())
                .show();
    }
}