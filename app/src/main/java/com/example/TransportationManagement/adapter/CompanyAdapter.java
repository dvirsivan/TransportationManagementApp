package com.example.TransportationManagement.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;
import com.example.TransportationManagement.Model.CompanyItem;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainActivity;
import com.example.TransportationManagement.UI.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> {
    private ArrayList<Travel> companyItems;
    private Context context;
    MainViewModel mainViewModel;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;

    public CompanyAdapter(ArrayList<Travel> companyItems, Context context, MainViewModel mainViewModel) {

        this.companyItems = companyItems;
        this.context = context;
        this.mainViewModel = mainViewModel;
        currentUser=mAuth.getCurrentUser();
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
            companyItem.setCompany(currentUser.getEmail(),true);
            mainViewModel.updateTravel(companyItem);
        });
        holder.call.setOnClickListener(view ->{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + companyItem.getClientPhone()));

            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "please approve phone call", Toast.LENGTH_LONG).show();
            }
            else
                context.startActivity(callIntent);

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
