package com.example.medicineandappointment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProfile extends AppCompatActivity {

    EditText etName, etEmail, etPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        // Linking UI elements
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnSave = findViewById(R.id.btn_save);

        // Dummy pre-fill (optional)
        etName.setText("John Doe");
        etEmail.setText("john@example.com");
        etPhone.setText("03123456789");

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Save profile info to database or shared preferences
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_LONG).show();
                finish(); // Optionally go back to settings
            }
        });
    }
}
