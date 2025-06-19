package com.example.medicineandappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookDoctor extends AppCompatActivity {

    EditText editName, editDate, editIssue;
    Button confirmBookingBtn;
    TextView textDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_doctor);

        editName = findViewById(R.id.editName);
        editDate = findViewById(R.id.editDate);
        editIssue = findViewById(R.id.editIssue);
        confirmBookingBtn = findViewById(R.id.confirmBookingBtn);
        textDoctorName = findViewById(R.id.textDoctorName);

        String doctorName = getIntent().getStringExtra("doctorName");
        textDoctorName.setText("Booking for: " + doctorName);

        confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String date = editDate.getText().toString().trim();
                String issue = editIssue.getText().toString().trim();

                if (name.isEmpty() || date.isEmpty() || issue.isEmpty()) {
                    Toast.makeText(BookDoctor.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookDoctor.this, "Booking Confirmed!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(BookDoctor.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    // Animation
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    finish(); // Close current screen
                }
            }
        });
    }
}
