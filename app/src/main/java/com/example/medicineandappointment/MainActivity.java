package com.example.medicineandappointment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button addMedicineButton;
    Button addAppointmentButton;
    Button ViewHIstoryButton;
    Button FindDoctorButton;
    Button HealthTipsButton;
    Button medicineListBtn;

    Button SettingsButton;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHelper(this);
        // test commit
/*

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(ReminderWorker.class,
                1, TimeUnit.SECONDS)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "medicine_reminder",
                ExistingPeriodicWorkPolicy.KEEP,
                request
        );
*/

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class); // A BroadcastReceiver
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                60 * 1000, // 1 minute
                pendingIntent
        );

        addMedicineButton = findViewById(R.id.AddMedicine);
        addAppointmentButton = findViewById(R.id.AddAppointment);
        ViewHIstoryButton = findViewById(R.id.view_history_button);
        FindDoctorButton = findViewById(R.id.find_doctor_button);
        HealthTipsButton = findViewById(R.id.health_tips_button);
        SettingsButton = findViewById(R.id.settings_button);
        medicineListBtn = findViewById(R.id.medicine_list_btn);

        SettingsButton.setOnClickListener(v -> SwitchToSettingsScreen());
        addMedicineButton.setOnClickListener(v -> SwitchToAddMedicineScreen());
        addAppointmentButton.setOnClickListener(v -> SwitchToAddAppointmentScreen());
        ViewHIstoryButton.setOnClickListener(v -> SwitchToViewHistoryScreen());
        FindDoctorButton.setOnClickListener(v -> SwitchToFindDoctorScreen());
        HealthTipsButton.setOnClickListener(v -> SwitchToHealthTipsScreen());
        medicineListBtn.setOnClickListener(v -> SwitchToMedicineListScreen());

    }


    private void SwitchToSettingsScreen(){
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }
    private void SwitchToHealthTipsScreen() {

        Intent intent = new Intent(MainActivity.this, HealthTips.class);
        startActivity(intent);

    }

    private void SwitchToMedicineListScreen() {

        Intent intent = new Intent(MainActivity.this, MedicineListsActivity.class);
        startActivity(intent);

    }

    private void SwitchToFindDoctorScreen() {
        Intent intent = new Intent(MainActivity.this, FindDoctor.class);
        startActivity(intent);
    }

    private void SwitchToViewHistoryScreen() {
        Intent intent = new Intent(MainActivity.this, ViewHistory.class);
        startActivity(intent);
    }

    void SwitchToAddMedicineScreen(){
        Intent intent = new Intent(MainActivity.this, AddMedicineActivity.class);
        startActivity(intent);
    }

    void SwitchToAddAppointmentScreen(){
        Intent intent = new Intent(MainActivity.this, AddAppointmentActivity.class);
        startActivity(intent);
    }

}