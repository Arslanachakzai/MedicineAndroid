package com.example.medicineandappointment;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewHistory extends AppCompatActivity {

    TextView historyTitle, historyDate, historyDoctor, historyDiagnosis, historyPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history); // Make sure your XML is named correctly

        // Binding TextViews to their IDs in XML
        historyTitle = findViewById(R.id.history_title);
        historyDate = findViewById(R.id.history_date);
        historyDoctor = findViewById(R.id.history_doctor);
        historyDiagnosis = findViewById(R.id.history_diagnosis);
        historyPrescription = findViewById(R.id.history_prescription);

        // Set example/dynamic content (could be from intent/database later)
        historyDate.setText("🗓 Date: 2025-06-01");
        historyDoctor.setText("👨‍⚕️ Doctor: Dr. Ahsan Raza");
        historyDiagnosis.setText("🩺 Diagnosis: Viral Flu");
        historyPrescription.setText("💊 Prescription: Panadol, Vitamin C");

        // Optional: you can fetch from database or intent extras to populate dynamically
    }
}
