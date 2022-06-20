package com.example.MOCO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountryRecommendedAdapter extends RecyclerView.Adapter<CountryRecommendedAdapter.CountryRecommendedViewHolder>{
    private ArrayList<String> countryRecommended = new ArrayList<>();
    private ArrayList<Boolean> countryRecommendedBoolean = new ArrayList<>();
    private ArrayList<String>  countryRecommendedWithoutDescription = new ArrayList<>();

    private Context ctx;

    public CountryRecommendedAdapter(Context ct, ArrayList<String> s1, ArrayList<Boolean> s2, ArrayList<String> s3){
        ctx = ct;
        countryRecommended = s1;
        countryRecommendedBoolean = s2;
        countryRecommendedWithoutDescription = s3;
    }


    @NonNull
    @Override
    public CountryRecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.country_recommended_rv_row,viewGroup,false);
        return new CountryRecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecommendedViewHolder countryViewHolder, int i) {
        countryViewHolder.recommendedDescription.setText(countryRecommended.get(i));
        countryViewHolder.recommendedTargetDisease.setText(countryRecommendedWithoutDescription.get(i));
        if (!countryRecommendedBoolean.isEmpty() && countryRecommendedBoolean.get(i) == true){
            countryViewHolder.trafficBoolean.setBackgroundResource(R.color.colorGreenTraffic);
        }else{
            countryViewHolder.trafficBoolean.setBackgroundResource(R.color.colorRedTraffic);
        }
    }

    @Override
    public int getItemCount() {
        if (countryRecommended == null){
            return 0;
        }else{
            return countryRecommended.size();
        }
    }

    public class CountryRecommendedViewHolder extends RecyclerView.ViewHolder{
        TextView recommendedDescription,trafficBoolean,recommendedTargetDisease;


        //zuordnung recycler
        public CountryRecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            recommendedDescription = itemView.findViewById(R.id.tvVaccineDescription);
            trafficBoolean = itemView.findViewById(R.id.tvTrafficLightRecommended);
            recommendedTargetDisease = itemView.findViewById(R.id.tvRecommendedVaccines);
        }
    }


}
