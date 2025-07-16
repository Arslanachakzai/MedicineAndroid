package com.example.medicineandappointment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class AddAppointmentActivity extends AppCompatActivity {

    Calendar calendar;
    EditText etPatientName, etDoctorName, etDateTime, etPurpose;
    Button btnSaveAppointment;
    long appointmentTimeMillis;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addappointment);

        db = new DatabaseHelper(this);

        etPatientName = findViewById(R.id.etPatientName);
        etDoctorName = findViewById(R.id.Doctoe_name); // Note: consider fixing typo in ID: "Doctoe_name" → "etDoctorName"
        etDateTime = findViewById(R.id.etDateTime);
        etPurpose = findViewById(R.id.etNotes);
        btnSaveAppointment = findViewById(R.id.btnSaveAppointment);

        calendar = Calendar.getInstance();

        etDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSaveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientName = etPatientName.getText().toString().trim();
                String doctorName = etDoctorName.getText().toString().trim();
                String purpose = etPurpose.getText().toString().trim();

                if (patientName.isEmpty() || doctorName.isEmpty() || appointmentTimeMillis == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean added = db.registerAppointment(patientName, doctorName, String.valueOf(appointmentTimeMillis), purpose);


                if (added) {
                    Intent intent = new Intent(AddAppointmentActivity.this, AppointmentList.class);
                    intent.putExtra("PatientName", patientName);
                    intent.putExtra("DoctorName", doctorName);
                    intent.putExtra("Notes", purpose);
                    intent.putExtra("ReminderTime", appointmentTimeMillis);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to save appointment", Toast.LENGTH_SHORT).show();
                }
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

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
                etDateTime.setText(sdf.format(calendar.getTime()));
                appointmentTimeMillis = calendar.getTimeInMillis();
            }
        }, hour, minute, false);

        timePickerDialog.show();
    }
}
