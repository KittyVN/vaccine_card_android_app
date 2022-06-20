package com.example.MOCO;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountryNecessaryAdapter extends RecyclerView.Adapter<CountryNecessaryAdapter.CountryNecessaryViewHolder>{
    private ArrayList<String> countryNecessary = new ArrayList<>();
    private ArrayList<Boolean> countryNecessaryBoolean = new ArrayList<>();
    private Context ctx;

    public CountryNecessaryAdapter(Context ct, ArrayList<String> s1, ArrayList<Boolean> s2){
        ctx = ct;
        countryNecessary = s1;
        countryNecessaryBoolean = s2;
    }


    @NonNull
    @Override
    public CountryNecessaryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.country_necessary_rv_row,viewGroup,false);
        return new CountryNecessaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryNecessaryViewHolder countryViewHolder, int i) {
        countryViewHolder.necessary.setText(countryNecessary.get(i));
        if (!countryNecessaryBoolean.isEmpty() && countryNecessaryBoolean.get(i) == true){
            countryViewHolder.trafficBoolean.setBackgroundResource(R.color.colorGreenTraffic);
        }else{
            countryViewHolder.trafficBoolean.setBackgroundResource(R.color.colorRedTraffic);
        }
    }

    @Override
    public int getItemCount() {
        if (countryNecessary == null){
            return 0;
        }else{
            return countryNecessary.size();
        }
    }

    public class CountryNecessaryViewHolder extends RecyclerView.ViewHolder{
        TextView necessary, trafficBoolean;


        //zuordnung recycler
        public CountryNecessaryViewHolder(@NonNull View itemView) {
            super(itemView);
            necessary = itemView.findViewById(R.id.tvNecessaryVaccines);
            trafficBoolean = itemView.findViewById(R.id.tvTrafficLightNecessary);
        }
    }


}
