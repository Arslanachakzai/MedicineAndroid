package com.example.medicineandappointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "UserData.db";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE MedicineTable (id INTEGER PRIMARY KEY AUTOINCREMENT, MedicineName TEXT, Dosage TEXT, ReminderTime TEXT, Notes TEXT)");
        db.execSQL("CREATE TABLE AppointmentTable (id INTEGER PRIMARY KEY AUTOINCREMENT, PatientName TEXT, DoctorName TEXT, ReminderTime TEXT, Notes TEXT)");

       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS MedicineTable");
        db.execSQL("DROP TABLE IF EXISTS AppointmentTable");
        onCreate(db);
    }

    // ----------------- USER METHODS -----------------

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // ----------------- MEDICINE METHODS -----------------

    public boolean registerMedicine(String mName, String dosage, String r_time, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MedicineName", mName);
        values.put("Dosage", dosage);
        values.put("ReminderTime", r_time);
        values.put("Notes", notes);
        long result = db.insert("MedicineTable", null, values);
        return result != -1;
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MedicineTable", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("MedicineName"));
                String dosage = cursor.getString(cursor.getColumnIndexOrThrow("Dosage"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("ReminderTime"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));

                Medicine med = new Medicine(id, name, dosage, time, notes);
                medicineList.add(med);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return medicineList;
    }

    public List<Medicine> fetchAllMedicines() {
        List<Medicine> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM MedicineTable", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Medicine medicine = new Medicine(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("MedicineName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Dosage")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ReminderTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Notes"))
                );
                medicineList.add(medicine);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return medicineList;
    }

    public boolean deleteMedicineById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("MedicineTable", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    public boolean deleteAppointmentById(int id) {
        SQLiteDatabase db = null;
        int result = 0;
        try {
            db = this.getWritableDatabase();
            result = db.delete("AppointmentTable", "id = ?", new String[]{String.valueOf(id)});
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return result > 0;
    }



    // ----------------- APPOINTMENT METHODS -----------------

    public boolean registerAppointment(String patientName, String doctorName, String purpose,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PatientName", patientName);
        values.put("DoctorName", doctorName);
        values.put("Notes", purpose);
        values.put("ReminderTime", time);
        long result = db.insert("AppointmentTable", null, values);
        return result != -1;
    }

    public List<Appointment> fetchAllAppointments() {
        List<Appointment> appointmentlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM AppointmentTable", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("PatientName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("DoctorName")),
                        cursor.getString(cursor.getColumnIndexOrThrow("ReminderTime")),
                        cursor.getString(cursor.getColumnIndexOrThrow("Notes"))
                );
                appointmentlist.add(appointment);
            } while (cursor.moveToNext());
            cursor.close();
        }

        cursor.close();
        return appointmentlist;
    }

}
