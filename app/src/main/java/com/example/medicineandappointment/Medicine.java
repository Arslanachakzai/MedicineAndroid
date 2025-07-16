package com.example.medicineandappointment;

public class Medicine {
    private int id;
    private String name;
    private String dosage;
    private String reminderTime;
    private String notes;

    public Medicine(int id, String name, String dosage, String reminderTime, String notes) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.reminderTime = reminderTime;
        this.notes = notes;
    }


    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public String getReminderTime() { return reminderTime; }
    public String getNotes() { return notes; }
}
