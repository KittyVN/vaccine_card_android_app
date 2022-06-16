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

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{
    private List<String> countryName = new ArrayList<String>();
    private List<String> countryNecessary = new ArrayList<String>();
    private List<String> countryRecommended = new ArrayList<String>();

    private Context ctx;

    public CountryAdapter(Context ct, List<String> s1, List<String> s2, List<String> s3){
        ctx = ct;
        countryName = s1;
        countryNecessary = s2;
        countryRecommended = s3;
    }


    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.country_recyclerview_row,viewGroup,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder countryViewHolder, int i) {
        countryViewHolder.country.setText(countryName.get(i));
        countryViewHolder.necessary.setText(countryNecessary.get(i));
        countryViewHolder.recommended.setText(countryRecommended.get(i));
    }

    @Override
    public int getItemCount() {
        if (countryName == null){
            return 0;
        }else{
            return countryName.size();
        }
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder{
        TextView country,necessary,recommended;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.tvCountry);
            necessary = itemView.findViewById(R.id.tvNecessaryVaccines);
            recommended = itemView.findViewById(R.id.tvRecommendedVaccines);
        }
    }


}
