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
        Log.d("LOG_WORK", "doWork started");

        checkMedicineReminders();
        checkAppointmentReminders();

        return Result.success();
    }

    private void checkMedicineReminders() {
        List<Medicine> medicineList = dbHelper.fetchAllMedicines();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        for (Medicine med : medicineList) {
            try {
                long timeInMillis = Long.parseLong(med.getReminderTime());
                String medicineTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(timeInMillis));

                Log.d("LOG_WORK", "Checking medicine: " + med.getName() + " at time: " + medicineTime);

                if (medicineTime.equals(currentTime)) {
                    sendNotification("Time to take: " + med.getName(), "Dosage: " + med.getDosage(), "med_channel");
                }

            } catch (NumberFormatException e) {
                Log.e("LOG_WORK", "Invalid reminder time: " + med.getReminderTime(), e);
            }
        }
    }

    private void checkAppointmentReminders() {
        List<Appointment> appointmentList = dbHelper.fetchAllAppointments();
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        for (Appointment appt : appointmentList) {
            try {
                long apptMillis = appt.getTimeMillis();
                String apptTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(apptMillis));
                String apptDate = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date(apptMillis));

                Log.d("LOG_WORK", "Checking appointment with Dr. " + appt.getDoctorName() + " at " + apptDate + " " + apptTime);

                if (apptTime.equals(currentTime) && apptDate.equals(currentDate)) {
                    sendNotification("Upcoming Appointment with Dr. " + appt.getDoctorName(),
                            "Patient: " + appt.getPatientName(), "appt_channel");
                }

            } catch (Exception e) {
                Log.e("LOG_WORK", "Error parsing appointment time", e);
            }
        }
    }

    private void sendNotification(String title, String message, String channelId) {
        Context context = getApplicationContext();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelId.equals("med_channel") ? "Medicine Reminders" : "Appointment Reminders",
                    NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.logo_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        if (manager != null) {
            manager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}
