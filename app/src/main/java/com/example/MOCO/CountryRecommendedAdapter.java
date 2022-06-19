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
    private String recommended = "";

    private Context ctx;

    public CountryRecommendedAdapter(Context ct, ArrayList<String> s1){
        ctx = ct;
        countryRecommended = s1;
    }



    @NonNull
    @Override
    public CountryRecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.country_recyclerview_row,viewGroup,false);
        return new CountryRecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryRecommendedViewHolder countryViewHolder, int i) {
        countryViewHolder.recommended.setText(countryRecommended.get(i));
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
        TextView recommended;


        //zuordnung recycler
        public CountryRecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            recommended = itemView.findViewById(R.id.tvRecommendedVaccines);
        }
    }


}
