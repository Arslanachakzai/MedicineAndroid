package com.example.medicineandappointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMedicineActivity extends AppCompatActivity {

    Calendar calendar;
    EditText etMedicineName, etDosage, etDateTime, etNotes;
    Button btnSaveMedicine;
    long reminderTimeMillis;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addmedicine);

        db=new DatabaseHelper(this);
        etMedicineName = findViewById(R.id.etMedicineName);
        etDosage = findViewById(R.id.etDosage);
        etDateTime = findViewById(R.id.etDateTime);
        etNotes = findViewById(R.id.etNotes);
        btnSaveMedicine = findViewById(R.id.btnSaveMedicine);

        calendar = Calendar.getInstance();

        etDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSaveMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etMedicineName.getText().toString().trim();
                String dosage = etDosage.getText().toString().trim();
                String notes = etNotes.getText().toString().trim();

                if (name.isEmpty() || dosage.isEmpty() || reminderTimeMillis == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // *** MODIFICATION START ***
                // Create an Intent to start DisplayMedicineInformationActivity
                Intent intent = new Intent(AddMedicineActivity.this, DisplayMedicineInformationActivity.class);

                // Put the data into the Intent as "extras"
                intent.putExtra("MEDICINE_NAME", name);
                intent.putExtra("MEDICINE_DOSAGE", dosage);
                intent.putExtra("MEDICINE_NOTES", notes);
                intent.putExtra("MEDICINE_TIME_MILLIS", reminderTimeMillis);
                boolean added= db.registerMedicine(name,dosage,String.valueOf(reminderTimeMillis),notes);

                // Start the new activity
                if (added)
                    startActivity(intent);
                // *** MODIFICATION END ***
            }
        });

    }

    void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                showTimePicker();
            }
        }, year, month, day);

        // Prevent selecting past dates
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    void showTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int h, int m) {
                calendar.set(Calendar.HOUR_OF_DAY, h);
                calendar.set(Calendar.MINUTE, m);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // Format the date and time
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
                etDateTime.setText(sdf.format(calendar.getTime()));

                // Save the final time in milliseconds
                reminderTimeMillis = calendar.getTimeInMillis();
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }
}