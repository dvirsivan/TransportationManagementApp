package com.example.TransportationManagement.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;
import com.example.TransportationManagement.R;

import java.util.List;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Travel> travels;
    Context context;
    HistoryTravelListener listener;
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    public HistoryAdapter(List<Travel> travels , Context context) {
        this.travels = travels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.history_item, parent, false);
        HistoryAdapter.ViewHolder viewHolder = new HistoryAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position % 2 == 0)
            holder.linearLayout.setBackgroundColor(Color.LTGRAY);
        else
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        Travel travel = travels.get(position);
        String company = "";
        for (Map.Entry<String, Boolean> comp : travel.getCompany().entrySet()){
            if (comp.getValue())
                company = comp.getKey();
        }
        if (travel != null) {
            holder.kilometers.setText(String.valueOf(calcTotalDistance(travel)));
            holder.name.setText(company);
            holder.paidUp.setOnClickListener(v -> {
                if (listener != null)
                    listener.onButtonClicked(position,v);
            });
            holder.sendMail.setOnClickListener(v -> {
                if (listener != null)
                    listener.onButtonClicked(position,v);

            });
        }
    }

    private float calcTotalDistance(Travel travel){
        List<UserLocation> destinations = travel.getDestinations();
        UserLocation firstPoint = travel.getSource();
        UserLocation secondPoint = destinations.get(0);
        float totalDistance = calculateDistance(firstPoint.getLat(),firstPoint.getLon(),secondPoint.getLat(),secondPoint.getLon());
        for(int i = 1; i < destinations.size(); i++){
            firstPoint = destinations.get(i-1);
            secondPoint = destinations.get(i);
            totalDistance += calculateDistance(firstPoint.getLat(),firstPoint.getLon(),secondPoint.getLat(),secondPoint.getLon());
        }
        totalDistance += calculateDistance(destinations.get(destinations.size()-1).getLat(),destinations.get(destinations.size()-1).getLon(),
                travel.getSource().getLat(),travel.getSource().getLon());
        return totalDistance;
    }

    public static float calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));

    }

    @Override
    public int getItemCount() {
        return travels == null? 0: travels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView kilometers;
        Button sendMail;
        Button paidUp;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.companyName);
            kilometers = (TextView) itemView.findViewById(R.id.kilometers);
            sendMail = (Button) itemView.findViewById(R.id.mailButtonHistory);
            paidUp = (Button) itemView.findViewById(R.id.changeStatusButton);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.historyItemLayout);
        }
    }
    public interface HistoryTravelListener {
        void onButtonClicked(int position,View view);
    }
    public void setListener(HistoryAdapter.HistoryTravelListener listener){
        this.listener = listener;
    }

}
