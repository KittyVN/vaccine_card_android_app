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

    private Context ctx;

    public CountryRecommendedAdapter(Context ct, ArrayList<String> s1, ArrayList<Boolean> s2){
        ctx = ct;
        countryRecommended = s1;
        countryRecommendedBoolean = s2;
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
        countryViewHolder.recommended.setText(countryRecommended.get(i));
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
        TextView recommended,trafficBoolean;


        //zuordnung recycler
        public CountryRecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            recommended = itemView.findViewById(R.id.tvRecommendedVaccines);
            trafficBoolean = itemView.findViewById(R.id.tvTrafficLightRecommended);
        }
    }


}
