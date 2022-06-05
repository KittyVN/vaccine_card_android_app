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

public class TiterAdapter extends RecyclerView.Adapter<TiterAdapter.TiterViewHolder> {

    private List<String> titerTyp = new ArrayList<String>();
    private List<String> titerLabor = new ArrayList<String>();
    private List<String> titerDate = new ArrayList<String>();
    private List<String> titerValue = new ArrayList<String>();

    private Context ctx;

    public TiterAdapter(Context ct, List<String> s1, List<String> s2, List<String> s3, List<String> s4){
        ctx = ct;
        titerTyp = s1;
        titerLabor = s2;
        titerDate = s3;
        titerValue = s4;
    }


    @NonNull
    @Override
    public TiterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.titer_recyclerview_row,viewGroup,false);
        return new TiterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiterViewHolder titerViewHolder, int i) {
        titerViewHolder.typ.setText(titerTyp.get(i));
        titerViewHolder.labor.setText(titerLabor.get(i));
        titerViewHolder.date.setText(titerDate.get(i));
        titerViewHolder.value.setText(titerValue.get(i));
    }

    @Override
    public int getItemCount() {
        if (titerDate == null){
            return 0;
        }else{
            return titerDate.size();
        }
    }

    public class TiterViewHolder extends RecyclerView.ViewHolder{
        TextView typ,labor,date,value;

        public TiterViewHolder(@NonNull View itemView) {
            super(itemView);
            typ = itemView.findViewById(R.id.tvTyp);
            labor = itemView.findViewById(R.id.tvLabor);
            date = itemView.findViewById(R.id.tvDateTiter);
            value = itemView.findViewById(R.id.tvTiterValue);
        }
    }
}
