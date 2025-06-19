package com.example.medicineandappointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText regUsername, regPassword;
    Button registerBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regUsername = findViewById(R.id.username);
        regPassword = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerButton);
        db = new DatabaseHelper(this);

        registerBtn.setOnClickListener(v -> {
            String username = regUsername.getText().toString().trim();
            String password = regPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean registered = db.registerUser(username, password);
                if (registered) {
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
