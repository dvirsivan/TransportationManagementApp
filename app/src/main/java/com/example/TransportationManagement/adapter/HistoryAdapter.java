package com.example.TransportationManagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private ArrayList<Travel> travels;

    public HistoryAdapter(ArrayList<Travel> travels) {
        this.travels = travels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.company_item, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Travel travel = travels.get(position);
        String company = "";
        for (Map.Entry<String, Boolean> comp : travel.getCompany().entrySet()){
            if (comp.getValue())
                company = comp.getKey();
        }
        holder.kilometers.setText((int) travel.calcKilometers());
        holder.name.setText(company);
        holder.paidUp.setOnClickListener( v -> {
            // need to implement
        });
        holder.call.setOnClickListener(v -> {
            // need to implement
        });
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView kilometers;
        Button call;
        Button paidUp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.companyName);
            kilometers = (TextView) itemView.findViewById(R.id.kilometers);
            call = (Button) itemView.findViewById(R.id.callButtonHistory);
            paidUp = (Button) itemView.findViewById(R.id.changeStatusButton);
        }
    }
}
