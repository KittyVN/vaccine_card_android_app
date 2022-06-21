package com.example.MOCO;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TiterAdapter extends RecyclerView.Adapter<TiterAdapter.TiterViewHolder> {

    private List<TiterDto> allTiter;

    private Context ctx;

    public TiterAdapter(Context ctx, List<TiterDto> allTiter) {
        this.ctx = ctx;
        this.allTiter = allTiter;
    }


    @NonNull
    @Override
    public TiterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.titer_recyclerview_row, viewGroup, false);
        return new TiterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiterViewHolder titerViewHolder, int i) {
        titerViewHolder.typ.setText(allTiter.get(i).getTiterTyp());
        titerViewHolder.labor.setText(allTiter.get(i).getTiterLabor());
        titerViewHolder.date.setText(allTiter.get(i).getTiterDate());
        titerViewHolder.value.setText(allTiter.get(i).getTiterValue());
    }

    @Override
    public int getItemCount() {
        if (allTiter == null) {
            return 0;
        } else {
            return allTiter.size();
        }
    }

    public class TiterViewHolder extends RecyclerView.ViewHolder {
        TextView typ, labor, date, value;

        public TiterViewHolder(@NonNull View itemView) {
            super(itemView);
            typ = itemView.findViewById(R.id.tvTyp);
            labor = itemView.findViewById(R.id.tvLabor);
            date = itemView.findViewById(R.id.tvDateTiter);
            value = itemView.findViewById(R.id.tvTiterValue);
        }
    }
}
