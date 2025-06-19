package com.example.medicineandappointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private List<Medicine> medicineList;
    DatabaseHelper dbHelper;

    public MedicineAdapter(List<Medicine> medicineList,DatabaseHelper dbHelper) {
        this.medicineList = medicineList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_medicine, parent, false);  // Layout for each item
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.textName.setText("Medicine Name: " + medicine.getName());
        holder.textDosage.setText("Dosage: " + medicine.getDosage());

        long timeInMillis = Long.parseLong(medicine.getReminderTime()); // "1751208600000"
        Date date = new Date(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String time = sdf.format(date);
        holder.textTime.setText("Time: " + time);
        holder.textNotes.setText("Notes: " + medicine.getNotes());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted= dbHelper.deleteMedicineById(medicine.getId());

                if (deleted) {
                    medicineList.remove(position); // remove from the list
                    notifyItemRemoved(position);   // notify adapter
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textDosage, textTime, textNotes;
        Button btnDelete;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.tvName);
            textDosage = itemView.findViewById(R.id.tvDosage);
            textTime = itemView.findViewById(R.id.tvTime);
            textNotes = itemView.findViewById(R.id.tvNotes);
            btnDelete = itemView.findViewById(R.id.Remove_button);
        }


    }
}
