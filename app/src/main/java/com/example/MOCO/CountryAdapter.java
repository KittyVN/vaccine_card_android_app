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
    private ArrayList<ArrayList<String>> countryNecessary = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> countryRecommended = new ArrayList<ArrayList<String>>();
    private String recommended = "";
    private String neccessary = "";

    private Context ctx;

    public CountryAdapter(Context ct, List<String> s1, ArrayList<ArrayList<String>> s2, ArrayList<ArrayList<String>> s3){
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

        if (countryRecommended.get(i).size() > 0){
            for (int x = 0; x < countryRecommended.get(i).size(); x++){
                recommended = recommended + countryRecommended.get(i).get(x) + "\n";
            }
        }else {
            recommended = "none";
        }
        if (countryNecessary.get(i).size() > 0){
            for (int x = 0; x < countryNecessary.get(i).size(); x++){
                neccessary = neccessary + countryNecessary.get(i).get(x) + "\n";
            }
        }else {
            neccessary = "none";
        }

        countryViewHolder.recommended.setText(recommended);
        countryViewHolder.necessary.setText(neccessary);
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


        //zuordnung recycler
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.tvCountry);
            necessary = itemView.findViewById(R.id.tvNecessaryVaccines);
            recommended = itemView.findViewById(R.id.tvRecommendedVaccines);
        }
    }


}
