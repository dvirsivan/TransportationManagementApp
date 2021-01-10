package com.example.TransportationManagement.adapter;

import android.content.Context;
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

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;
import com.example.TransportationManagement.Model.CompanyItem;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainActivity;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {
    private ArrayList<Travel> companyItems;
    private Context context;

    public CompanyAdapter(ArrayList<Travel> companyItems, Context context) {

        this.companyItems = companyItems;
        this.context = context;
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
        Travel companyItem = companyItems.get(position);
        holder.sumDays.setText((int)companyItem.getSumDays());
        holder.date.setText(companyItem.getStartDate());
        holder.cName.setText(companyItem.getClientName());
        holder.numPass.setText(companyItem.getAmountTravelers());
        holder.source.setText(companyItem.getSource().convertToString(context).get(0).getAddressLine(0));
        holder.accept.setOnClickListener(  view->
        {
            // need to implement!!
        });
        holder.call.setOnClickListener(view ->{
            // need to implement!!

        });
        holder.acceptedBox.setChecked(Travel.RequestType.getTypeInt(companyItem.getStatus()) == 1);
    }

    @Override
    public int getItemCount() {
        return companyItems.size();
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
