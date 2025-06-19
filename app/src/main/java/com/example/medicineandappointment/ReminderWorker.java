package com.example.medicineandappointment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReminderWorker extends Worker {

    private DatabaseHelper dbHelper;

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("LOG_WORK", "doWork: ");
        List<Medicine> medicineList = dbHelper.fetchAllMedicines();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        for (Medicine med : medicineList) {
            long timeInMillis = Long.parseLong(med.getReminderTime()); // "1751208600000"
            Date date = new Date(timeInMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String time = sdf.format(date);
            Log.d("LOG_WORK", "doWork:loop ");
            if (time.equals(currentTime)) {
                sendNotification(med.getName(), med.getDosage());
            }
        }

        return Result.success();
    }

    private void sendNotification(String title, String message) {
        Context context = getApplicationContext();
        String channelId = "med_channel";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Medicine Reminders",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Time to take: " + title)
                .setContentText("Dosage: " + message)
                .setSmallIcon(R.drawable.logo_icon)
                .setAutoCancel(true);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
