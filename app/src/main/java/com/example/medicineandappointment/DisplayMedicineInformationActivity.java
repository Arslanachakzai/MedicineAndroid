package com.example.medicineandappointment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DisplayMedicineInformationActivity extends AppCompatActivity {

    private TextView tvName, tvDosage, tvNotes, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicine_information);

        // Initialize views
        tvName = findViewById(R.id.tvName);
        tvDosage = findViewById(R.id.tvDosage);
        tvNotes = findViewById(R.id.tvNotes);
        tvTime = findViewById(R.id.tvTime);

        // Retrieve data from intent
        String name = getIntent().getStringExtra("MEDICINE_NAME");
        String dosage = getIntent().getStringExtra("MEDICINE_DOSAGE");
        String notes = getIntent().getStringExtra("MEDICINE_NOTES");
        long timeMillis = getIntent().getLongExtra("MEDICINE_TIME_MILLIS", 0);

        // Format the time to readable string
        String formattedTime = formatTime(timeMillis);

        // Set values to the views
        tvName.setText("Medicine Name: " + name);
        tvDosage.setText("Dosage: " + dosage);
        tvNotes.setText("Notes: " + notes);
        tvTime.setText("Reminder Time: " + formattedTime);
    }

    // Utility function to format time
    private String formatTime(long millis) {
        if (millis == 0) return "Not Set";
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        return sdf.format(new Date(millis));
    }
}
