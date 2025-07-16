package com.example.medicineandappointment;

public class Appointment {
    private int id;
    private String patientName;
    private String doctorName;
    private String reminderTime;
    private String notes;

    public Appointment(int id, String name, String doctorName, String reminderTime, String notes) {
        this.id = id;
        this.patientName = name;
        this.doctorName = doctorName;
        this.reminderTime = reminderTime;
        this.notes = notes;
    }


    // Getters
    public int getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getReminderTime() { return reminderTime; }
    public String getNotes() { return notes; }

    public long getTimeMillis() {
        return 0;
    }
}
