package com.example.medicineandappointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<Appointment> appointmentList;
    private DatabaseHelper dbHelper;

    public AppointmentAdapter(List<Appointment> appointmentList, DatabaseHelper dbHelper) {
        this.appointmentList = appointmentList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_appointment_item, parent, false); // Make sure this layout exists
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

        holder.textDoctorName.setText("Doctor: " + appointment.getDoctorName());
        holder.textPurpose.setText("Patient: " + appointment.getPatientName());
        holder.textNotes.setText("Notes: " + appointment.getNotes());

        long timeInMillis = Long.parseLong(appointment.getReminderTime());
        Date date = new Date(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String time = sdf.format(date);
        holder.textTime.setText("Time: " + time);

        holder.btnDelete.setOnClickListener(v -> {
            boolean deleted = dbHelper.deleteAppointmentById(appointment.getId());
            if (deleted) {
                appointmentList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView textDoctorName, textPurpose, textNotes, textTime;
        Button btnDelete;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            textDoctorName = itemView.findViewById(R.id.tvDoctorName);
            textPurpose = itemView.findViewById(R.id.tvPurpose);
            textNotes = itemView.findViewById(R.id.tvNotes);
            textTime = itemView.findViewById(R.id.tvTime);
            btnDelete = itemView.findViewById(R.id.Remove_button);
        }
    }
}
