package com.example.TransportationManagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Model.CompanyItem;
import com.example.TransportationManagement.R;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {
    private CompanyItem[] companyItems;

    public CompanyAdapter(CompanyItem[] companyItems) {
        this.companyItems = companyItems;
    }

    @NonNull
    @Override
    public CompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.company_item, parent, false);
        CompanyHolder companyHolder = new CompanyHolder(listItem);
        return companyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyHolder holder, int position) {
        CompanyItem companyItem = companyItems[position];
        holder.sumDays.setText(companyItem.getSumDays());
        holder.date.setText(companyItem.getSumDays());
        holder.cName.setText(companyItem.getSumDays());
        holder.numPass.setText(companyItem.getSumDays());
        holder.source.setText(companyItem.getSumDays());
        holder.accept.setOnClickListener(  view->
        {
            // need to implement!!
        });
        holder.call.setOnClickListener(view ->{
            // need to implement!!
        });
        holder.acceptedBox.setChecked(companyItem.getAccepted());
    }

    @Override
    public int getItemCount() {
        return companyItems.length;
    }

    public static class CompanyHolder extends RecyclerView.ViewHolder {
        TextView source;
        TextView numPass;
        TextView cName;
        TextView date;
        TextView sumDays;
        Spinner dest;
        Button call;
        Button accept;
        CheckBox acceptedBox;
        public RelativeLayout relativeLayout;
        public CompanyHolder(View itemView) {
            super(itemView);
            source = (TextView) itemView.findViewById(R.id.sourceInCompany);
            numPass = (TextView) itemView.findViewById(R.id.num_of_passengers);
            cName = (TextView) itemView.findViewById(R.id.client_name);
            date = (TextView) itemView.findViewById(R.id.start_date);
            sumDays = (TextView) itemView.findViewById(R.id.sum_days);
            dest = (Spinner) itemView.findViewById(R.id.companySpinner);
            accept = (Button) itemView.findViewById(R.id.accept_button);
            call = (Button) itemView.findViewById(R.id.callButton);
            acceptedBox = (CheckBox) itemView.findViewById(R.id.acceptedCheckBox);

        }

    }
}
