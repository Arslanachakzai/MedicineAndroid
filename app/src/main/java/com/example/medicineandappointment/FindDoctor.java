package com.example.medicineandappointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FindDoctor extends AppCompatActivity {

    Button bookDoctor1, bookDoctor2,bookDoctor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        // Button IDs from XML
        bookDoctor1 = findViewById(R.id.bookBtn1);
        bookDoctor2 = findViewById(R.id.bookBtn1);
        bookDoctor3 = findViewById(R.id.bookBtn3);

        // Doctor 1
        bookDoctor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDoctor.this, BookDoctor.class);
                intent.putExtra("doctorName", "Dr. Ahmed Khan (Cardiologist)");
                startActivity(intent);
            }
        });

        // Doctor 2
        bookDoctor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDoctor.this, BookDoctor.class);
                intent.putExtra("doctorName", "Dr. Sara Malik (Dermatologist)");
                startActivity(intent);
            }
        });
        bookDoctor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindDoctor.this, BookDoctor.class);
                intent.putExtra("doctorName", "Dr. Sara Malik (Dermatologist)");
                startActivity(intent);
            }
        });
    }
}
