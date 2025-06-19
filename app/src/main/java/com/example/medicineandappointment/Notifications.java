package com.example.medicineandappointment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class Notifications extends AppCompatActivity {

    private TextView tvNoNotifications;
    private NestedScrollView scrollNotifications;
    private LinearLayout notificationsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize views
        tvNoNotifications = findViewById(R.id.tv_no_notifications);
        scrollNotifications = findViewById(R.id.scroll_notifications);
        notificationsContainer = findViewById(R.id.notifications_container);

        // Check for notifications (you can load from database or API)
        boolean hasNotifications = false; // Change this based on real data

        if (hasNotifications) {
            tvNoNotifications.setVisibility(View.GONE);
            scrollNotifications.setVisibility(View.VISIBLE);
            loadNotifications(); // Optional method if you want to add dummy or real notifications
        } else {
            tvNoNotifications.setVisibility(View.VISIBLE);
            scrollNotifications.setVisibility(View.GONE);
        }
    }

    // Optional: add dummy notifications here if needed
    private void loadNotifications() {
        for (int i = 1; i <= 3; i++) {
            TextView notification = new TextView(this);
            notification.setText("🔔 Notification " + i);
            notification.setTextSize(16);
            notification.setTextColor(getResources().getColor(android.R.color.white));
            notification.setPadding(16, 16, 16, 16);
            notificationsContainer.addView(notification);
        }
    }
}
