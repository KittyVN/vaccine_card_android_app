package com.example.MOCO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder> {

    private String[] vaccineKrankheit;
    private String[] vaccineHersteller;
    private String[] vaccineDate;
    private Context ctx;

public VaccineAdapter(Context ct, String[] s1, String[] s2, String[] s3){
 ctx = ct;
 vaccineKrankheit = s1;
 vaccineHersteller = s2;
 vaccineDate = s3;

}


    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.vaccine_recyclerview_row,viewGroup,false);
        return new VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder vaccineViewHolder, int i) {
        vaccineViewHolder.krankheit.setText(vaccineKrankheit[i]);
        vaccineViewHolder.hersteller.setText(vaccineHersteller[i]);
        vaccineViewHolder.date.setText(vaccineDate[i]);
}

    @Override
    public int getItemCount() {
        return vaccineDate.length;
    }

    public class VaccineViewHolder extends RecyclerView.ViewHolder{
    TextView krankheit,hersteller,date;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);
            krankheit = itemView.findViewById(R.id.tvKrankheit);
            hersteller = itemView.findViewById(R.id.tvHersteller);
            date = itemView.findViewById(R.id.tvDate);
        }
    }
}
