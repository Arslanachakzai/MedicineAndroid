package com.example.medicineandappointment;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView; // IMPORTANT: Changed from EditText
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AddAppointmentActivity extends AppCompatActivity {

    // --- MODIFIED: We now use AutoCompleteTextView for the doctor name ---
    private AutoCompleteTextView actvDoctorName;

    // --- UNCHANGED ---
    private ImageButton backButton;
    private EditText etFullName;
    private HorizontalScrollView hsvDateSelector;
    private ChipGroup chipGroupTime;
    private MaterialButton btnBookAppointment;

    private View selectedDateCard = null;
    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addappointment); // Make sure this matches your XML file name

        initializeViews();
        setupDoctorDropdown(); // NEW: A method to set up the doctor list
        setupClickListeners();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.ibBack);

        // --- MODIFIED: Find the AutoCompleteTextView by its correct ID ---
        actvDoctorName = findViewById(R.id.actvDoctorName);

        // --- UNCHANGED ---
        etFullName = findViewById(R.id.etFullName);
        hsvDateSelector = findViewById(R.id.hsvDateSelector);
        chipGroupTime = findViewById(R.id.chipGroupTime);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
    }

    /**
     * NEW METHOD: Sets up the list of doctors for the dropdown menu.
     */
    private void setupDoctorDropdown() {
        // 1. Create the list of doctor names you want to show
        String[] doctors = new String[]{"Dr. Ali Raza", "Dr. Fatima Sheikh", "Dr. Usman Baig", "Dr. Ayesha Malik"};

        // 2. Create an adapter that Android uses to show the list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                doctors
        );

        // 3. Set the adapter on your AutoCompleteTextView
        actvDoctorName.setAdapter(adapter);
    }


    private void setupClickListeners() {
        backButton.setOnClickListener(v -> finish());
        setupDateSelection();

        chipGroupTime.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) {
                selectedTime = "";
            } else {
                Chip selectedChip = findViewById(checkedIds.get(0));
                selectedTime = selectedChip.getText().toString();
            }
        });

        btnBookAppointment.setOnClickListener(v -> performBooking());
    }

    private void performBooking() {
        // --- MODIFIED: Get text from the AutoCompleteTextView ---
        String doctorName = actvDoctorName.getText().toString().trim();
        String patientName = etFullName.getText().toString().trim();

        // The rest of the validation is the same
        if (doctorName.isEmpty()) {
            Toast.makeText(this, "Please select a doctor", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
            return;
        }
        if (patientName.isEmpty()) {
            Toast.makeText(this, "Please enter the patient's name", Toast.LENGTH_SHORT).show();
            return;
        }

        String summary = "Appointment Booked!\n\n" +
                "Doctor: " + doctorName + "\n" +
                "Patient: " + patientName + "\n" +
                "Date: " + selectedDate + "\n" +
                "Time: " + selectedTime;

        Toast.makeText(this, summary, Toast.LENGTH_LONG).show();
    }


    private void setupDateSelection() {
        LinearLayout dateContainer = (LinearLayout) hsvDateSelector.getChildAt(0);
        for (int i = 0; i < dateContainer.getChildCount(); i++) {
            final View dateCard = dateContainer.getChildAt(i);
            dateCard.setOnClickListener(view -> {
                if (selectedDateCard != null) {
                    resetDateCardStyle(selectedDateCard);
                }
                selectDateCardStyle(view);
                selectedDateCard = view;
                extractDateFromCard(view);
            });
        }
    }


    private void selectDateCardStyle(View card) {
        MaterialCardView cardView = (MaterialCardView) card;
        cardView.setCardBackgroundColor(Color.parseColor("#00796B"));
        LinearLayout innerLayout = (LinearLayout) cardView.getChildAt(0);
        ((TextView) innerLayout.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) innerLayout.getChildAt(1)).setTextColor(Color.WHITE);
    }

    private void resetDateCardStyle(View card) {
        MaterialCardView cardView = (MaterialCardView) card;
        cardView.setCardBackgroundColor(Color.WHITE);
        LinearLayout innerLayout = (LinearLayout) cardView.getChildAt(0);
        ((TextView) innerLayout.getChildAt(0)).setTextColor(Color.parseColor("#424242"));
        ((TextView) innerLayout.getChildAt(1)).setTextColor(Color.parseColor("#424242"));
    }

    private void extractDateFromCard(View card) {
        MaterialCardView cardView = (MaterialCardView) card;
        LinearLayout innerLayout = (LinearLayout) cardView.getChildAt(0);
        TextView tvDayOfWeek = (TextView) innerLayout.getChildAt(0);
        TextView tvDayOfMonth = (TextView) innerLayout.getChildAt(1);
        selectedDate = tvDayOfWeek.getText().toString() + ", " + tvDayOfMonth.getText().toString();
    }
}