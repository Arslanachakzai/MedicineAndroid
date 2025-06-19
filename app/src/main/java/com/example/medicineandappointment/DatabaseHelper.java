package com.example.medicineandappointment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "UserData.db";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE MedicineTable (id INTEGER PRIMARY KEY AUTOINCREMENT, MedicineName TEXT, Dosage TEXT, ReminderTime TEXT, Notes TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS MedicineTable");
        onCreate(db);
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }
    public boolean registerMedicine(String mName, String dosage,String r_time,String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MedicineName", mName);
        values.put("Dosage", dosage);
        values.put("ReminderTime", r_time);
        values.put("Notes", notes);
        long result = db.insert("MedicineTable", null, values);
        Log.d("LOG_M", "registerMedicine: "+result);
        return result != -1;
    }

    public List<Medicine> fetchAllMedicines() {
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

                // Optional Log
                Log.d("LOG_MEDICINE", "ID: " + id + ", Name: " + name + ", Dosage: " + dosage + ", Time: " + time + ", Notes: " + notes);

            } while (cursor.moveToNext());
        } else {
            Log.d("LOG_MEDICINE", "No medicine records found.");
        }

        cursor.close();
        db.close();
        return medicineList;
    }

    public boolean deleteMedicineById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("MedicineTable", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0; // returns true if any row was deleted
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

}
