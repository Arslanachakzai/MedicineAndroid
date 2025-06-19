package com.example.medicineandappointment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ALARM_CHECK", "onReceive: Triggered");

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        List<Medicine> medicineList = dbHelper.fetchAllMedicines();

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        for (Medicine med : medicineList) {
            try {
                long timeMillis = Long.parseLong(med.getReminderTime());
                String reminderFormatted = new SimpleDateFormat("HH:mm", Locale.getDefault())
                        .format(new Date(timeMillis));

                if (reminderFormatted.equals(currentTime)) {
                    sendNotification(context, med.getName(), med.getDosage());
                }

            } catch (Exception e) {
                Log.e("ALARM_ERROR", "Error parsing time", e);
            }
        }
    }

    private void sendNotification(Context context, String title, String message) {
        String channelId = "med_channel";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, "Medicine Reminder", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo_icon)
                .setContentTitle("Time to take: " + title)
                .setContentText("Dosage: " + message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
