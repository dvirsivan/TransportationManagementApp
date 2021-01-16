package com.example.TransportationManagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;

import com.example.TransportationManagement.R;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {
    private List<Travel> companyItems;
    private Context context;
    private CompanyTravelListener listener;


    public CompanyAdapter(List<Travel> companyItems, Context context) {
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
        if(position % 2 == 0)
            holder.linearLayout.setBackgroundColor(Color.LTGRAY);
        Travel companyItem = companyItems.get(position);
        holder.sumDays.setText("sum of\n days:"+String.valueOf(companyItem.getSumDays()));
        holder.date.setText("date:\n"+companyItem.getStartDate());
        holder.cName.setText("name:\n"+companyItem.getClientName());
        holder.numPass.setText("travelers:22");//companyItem.getAmountTravelers()
        holder.source.setText("address:\n"+companyItem.getSource().convertToString(context));
        spinerAdapter(holder.dest,UserLocation.convertToString(context,companyItem.getDestinations()));
        holder.accept.setOnClickListener(  view->
        {
            if(listener!=null)
                listener.onButtonClicked(position,view);
        });
        holder.call.setOnClickListener(view ->{
            if(listener!=null)
                listener.onButtonClicked(position,view);
        });
        holder.acceptedBox.setChecked(Travel.RequestType.getTypeInt(companyItem.getStatus()) == 1);
    }
    private void spinerAdapter(Spinner spin,List list){
        ArrayAdapter aa = new ArrayAdapter(this.context,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }


    @Override
    public int getItemCount() {
        return companyItems.size();
    }

    public static class CompanyHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
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
            linearLayout = (LinearLayout)itemView.findViewById(R.id.company_layout);
            source = (EditText) itemView.findViewById(R.id.sourceInCompany);
            numPass = (EditText) itemView.findViewById(R.id.num_of_passengers);
            cName = (EditText) itemView.findViewById(R.id.client_name);
            date = (EditText) itemView.findViewById(R.id.start_date);
            sumDays = (EditText) itemView.findViewById(R.id.sum_days);
            dest = (Spinner) itemView.findViewById(R.id.destinationsInCompany);
            accept = (Button) itemView.findViewById(R.id.accept_button);
            call = (Button) itemView.findViewById(R.id.callButton);
            acceptedBox = (CheckBox) itemView.findViewById(R.id.acceptedCheckBox);

        }
    }
    public interface CompanyTravelListener {
        void onButtonClicked(int position, View view);
    }
    public void setListener(CompanyTravelListener listener){
        this.listener=listener;
    }

}
