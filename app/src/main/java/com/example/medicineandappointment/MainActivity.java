package com.example.medicineandappointment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button addMedicineButton;
    Button addAppointmentButton;
    Button ViewHIstoryButton;
    Button FindDoctorButton;
    Button HealthTipsButton;
    Button medicineListBtn;
    Button SettingsButton;
    Button AppointmentListBtn;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        // AlarmManager to trigger ReminderReceiver every 1 minute
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    60 * 1000,  // every minute
                    pendingIntent
            );
        }

        // Initialize buttons
        addMedicineButton = findViewById(R.id.AddMedicine);
        addAppointmentButton = findViewById(R.id.AddAppointment);
        ViewHIstoryButton = findViewById(R.id.view_history_button);
        FindDoctorButton = findViewById(R.id.find_doctor_button);
        HealthTipsButton = findViewById(R.id.health_tips_button);
        SettingsButton = findViewById(R.id.settings_button);
        medicineListBtn = findViewById(R.id.medicine_list_btn);
        AppointmentListBtn = findViewById(R.id.appointment_list_btn);


        // Set click listeners
        SettingsButton.setOnClickListener(v -> SwitchToSettingsScreen());
        addMedicineButton.setOnClickListener(v -> SwitchToAddMedicineScreen());
        addAppointmentButton.setOnClickListener(v -> SwitchToAddAppointmentScreen());
        ViewHIstoryButton.setOnClickListener(v -> SwitchToViewHistoryScreen());
        FindDoctorButton.setOnClickListener(v -> SwitchToFindDoctorScreen());
        HealthTipsButton.setOnClickListener(v -> SwitchToHealthTipsScreen());
        medicineListBtn.setOnClickListener(v -> SwitchToMedicineListScreen());
        AppointmentListBtn.setOnClickListener(v -> SwitchToAppointmentList());
    }

    private void SwitchToSettingsScreen() {
        startActivity(new Intent(MainActivity.this, Settings.class));
    }

    private void SwitchToHealthTipsScreen() {
        startActivity(new Intent(MainActivity.this, HealthTips.class));
    }

    private void SwitchToMedicineListScreen() {
        startActivity(new Intent(MainActivity.this, MedicineListsActivity.class));
    }

    private void SwitchToFindDoctorScreen() {
        startActivity(new Intent(MainActivity.this, FindDoctor.class));
    }

    private void SwitchToViewHistoryScreen() {
        startActivity(new Intent(MainActivity.this, ViewHistory.class));
    }

    private void SwitchToAddMedicineScreen() {
        startActivity(new Intent(MainActivity.this, AddMedicineActivity.class));
    }

    private void SwitchToAddAppointmentScreen() {
        startActivity(new Intent(MainActivity.this, AddAppointmentActivity.class));
    }

    private void SwitchToAppointmentList() {
        startActivity(new Intent(MainActivity.this, AppointmentList.class));
    }
}
