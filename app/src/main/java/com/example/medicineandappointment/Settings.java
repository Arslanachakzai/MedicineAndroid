package com.example.medicineandappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    Button btnUpdateProfile, btnNotifications, btnPrivacyPolicy, btnAboutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); // make sure this matches your XML filename

        // Initialize buttons
        btnUpdateProfile = findViewById(R.id.btn_update_profile);
        btnNotifications = findViewById(R.id.btn_notifications);
        btnPrivacyPolicy = findViewById(R.id.btn_privacy_policy);
        btnAboutApp = findViewById(R.id.btn_about_app);

        // Set click listeners
        btnUpdateProfile.setOnClickListener(v -> {
            // Example: open UpdateProfileActivity (create if needed)
            Intent intent = new Intent(Settings.this, UpdateProfile.class);
            startActivity(intent);
        });

        btnNotifications.setOnClickListener(v -> {
            // Example: open NotificationsActivity (create if needed)
            Intent intent = new Intent(Settings.this, Notifications.class);
            startActivity(intent);
        });

        btnPrivacyPolicy.setOnClickListener(v -> {
            // Example: open PrivacyPolicyActivity or show dialog
            Intent intent = new Intent(Settings.this, PrivacyPolicy.class);
            startActivity(intent);
        });

        btnAboutApp.setOnClickListener(v -> {
            // Example: show toast or open AboutActivity
            Intent intent = new Intent(Settings.this, AboutApp.class);
            startActivity(intent);
        });
    }
}
