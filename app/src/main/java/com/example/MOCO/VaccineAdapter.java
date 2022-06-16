package com.example.MOCO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder> {

    private List<String> vaccineKrankheit = new ArrayList<String>();
    private List<String> vaccineHersteller = new ArrayList<String>();
    private List<String> vaccineDate = new ArrayList<String>();
    private List<String> vaccineLotNumber = new ArrayList<String>();
    private Context ctx;
    private ProgressBar spinner;
    private WebView webview;

public VaccineAdapter(Context ct, List<String> s1, List<String> s2, List<String> s3, List<String> s4){
 ctx = ct;
 vaccineKrankheit = s1;
 vaccineHersteller = s2;
 vaccineDate = s3;
 vaccineLotNumber = s4;

}


    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.vaccine_recyclerview_row,viewGroup,false);
        webview.setVisibility(webview.INVISIBLE);
        return new VaccineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder vaccineViewHolder, int i) {
        vaccineViewHolder.krankheit.setText(vaccineKrankheit.get(i));
        vaccineViewHolder.hersteller.setText(vaccineHersteller.get(i));
        vaccineViewHolder.date.setText(vaccineDate.get(i));
        vaccineViewHolder.lotNumber.setText(vaccineLotNumber.get(i));
    }

    @Override
    public int getItemCount() {
    if (vaccineDate == null){
        return 0;
    }else{
        return vaccineDate.size();
        }
    }

    public class VaccineViewHolder extends RecyclerView.ViewHolder{
    TextView krankheit,hersteller,date,lotNumber;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);
            krankheit = itemView.findViewById(R.id.tvKrankheit);
            hersteller = itemView.findViewById(R.id.tvHersteller);
            date = itemView.findViewById(R.id.tvDate);
            lotNumber = itemView.findViewById(R.id.tvLotNumber);
        }
    }
}
