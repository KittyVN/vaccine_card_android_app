package com.example.MOCO;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;
import java.util.Objects;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder> {

    private List<VaccineDto> allVaccines;

    private Context ctx;

    public VaccineAdapter(Context ctx, List<VaccineDto> allVaccines) {
        this.ctx = ctx;
        this.allVaccines = allVaccines;
    }


    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.vaccine_recyclerview_row, viewGroup, false);
        return new VaccineViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder vaccineViewHolder, int i) {
        vaccineViewHolder.title.setText(allVaccines.get(i).getVaccineTitle());
        vaccineViewHolder.manufacturer.setText(allVaccines.get(i).getVaccineManufacturer());
        vaccineViewHolder.date.setText(allVaccines.get(i).getVaccineDate());
        vaccineViewHolder.lotNumber.setText(allVaccines.get(i).getVaccineLotNumber());
        vaccineViewHolder.performer.setText(allVaccines.get(i).getVaccinePerformer());
        if (Objects.equals(allVaccines.get(i).getVaccineSeriesNumberTotal(), "0")) {
            vaccineViewHolder.doses.setText(allVaccines.get(i).getVaccineDoseNumber());
        } else {
            String text = allVaccines.get(i).getVaccineDoseNumber() + "/" + allVaccines.get(i).getVaccineSeriesNumberTotal();
            vaccineViewHolder.doses.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        if (allVaccines == null) {
            return 0;
        } else {
            return allVaccines.size();
        }
    }

    public class VaccineViewHolder extends RecyclerView.ViewHolder {
        TextView title, manufacturer, date, lotNumber, performer, doses;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            manufacturer = itemView.findViewById(R.id.tvManufacturer);
            date = itemView.findViewById(R.id.tvDate);
            lotNumber = itemView.findViewById(R.id.tvLotNumber);
            performer = itemView.findViewById(R.id.tvPerformer);
            doses = itemView.findViewById(R.id.tvDoses);
        }
    }
}
