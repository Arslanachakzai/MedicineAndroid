package com.example.medicineandappointment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentList extends AppCompatActivity {
    RecyclerView recyclerView;
    AppointmentAdapter adapter;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_lists);

        recyclerView = findViewById(R.id.recyclerViewMedicines);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        List<Appointment> appointmentList = dbHelper.fetchAllAppointments();

        adapter = new AppointmentAdapter(appointmentList,dbHelper);
        recyclerView.setAdapter(adapter);
    }
}