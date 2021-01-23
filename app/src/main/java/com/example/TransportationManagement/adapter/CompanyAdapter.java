package com.example.TransportationManagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;

import com.example.TransportationManagement.R;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyHolder> implements Filterable {
    private List<Travel> companyItems;
    private Context context;
    private CompanyTravelListener listener;
    private DistanceFilter distanceFilter;
    private List<Travel>originalCompanyItems;

    public CompanyAdapter(List<Travel> companyItems, Context context) {
        this.companyItems = companyItems;
        this.originalCompanyItems = companyItems;
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

    /**
     * puts in each row the needed data for the company fragment such as the start date the clint name and more
     *
     * @param holder hold the views in of each row in the company fragment
     * @param position the position of the data in the list of the company items
     */
    @Override
    public void onBindViewHolder(@NonNull CompanyHolder holder, int position) {
        if(position%2==0)
            holder.linearLayout.setBackgroundColor(Color.LTGRAY);
        else
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        Travel companyItem = companyItems.get(position);
        holder.sumDays.setText("sum of\n days:"+String.valueOf(companyItem.getSumDays()));
        holder.date.setText("date:\n"+companyItem.getStartDate());
        holder.cName.setText("name:\n"+companyItem.getClientName());
        holder.numPass.setText("travelers:" + companyItem.getAmountTravelers());
        holder.source.setText("address:\n"+companyItem.getSource().convertToString(context));
        spinnerAdapter(holder.dest,UserLocation.convertToString(context,companyItem.getDestinations()));
        //listener for the accept button
        holder.accept.setOnClickListener(  view->
        {
            if(listener!=null)
                listener.onButtonClicked(companyItem,view);
        });
        //listener for the call button
        holder.call.setOnClickListener(view ->{
            if(listener!=null)
                listener.onButtonClicked(companyItem,view);
        });
        holder.acceptedBox.setChecked(Travel.RequestType.getTypeInt(companyItem.getStatus()) > 0);
    }

    /**
     * sets the spinner with data
     * @param spin the spinner that we put the data in it
     * @param list the data that go into the filter
     */
    private void spinnerAdapter(Spinner spin, List list){
        ArrayAdapter aa = new ArrayAdapter(this.context,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    /**
     * sets the location of the distanceFilter class
     * @param location the location for the filter
     */
    public void setLocation(Location location){
        if(distanceFilter!=null){
            distanceFilter.setLocation(location);
        }
    }

    @Override
    public int getItemCount() {
        return companyItems.size();
    }

    @Override
    public Filter getFilter() {
        if (distanceFilter == null)
            distanceFilter = new DistanceFilter();
        return distanceFilter;
    }

    /**
     * resets the data to the original data before the filter
     */
    public void resetData(){companyItems = originalCompanyItems;}

    /**
     * inner class that perform filtering base on the distance between the location and the source location of the Travel
     */
    private  class DistanceFilter extends Filter{
        Location location;
        public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

        /**
         *
         * @param constraint the max distance that from the location to the source of the client that we filter by
         * @return all the travels that are closer then the max distance
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            int maxDistance;
            if(constraint==null||constraint.length()==0||constraint=="without"||location==null){
                results.values=companyItems;
                results.count=companyItems.size();
            }
            else if(location!=null) {
                maxDistance = Integer.parseInt(constraint.toString());
                List<Travel> travelList = new ArrayList<>();
                for(Travel travel:companyItems){
                    if(calculateDistance(location.getLatitude(),location.getLongitude(),travel.getSource().getLat(),travel.getSource().getLon())<=maxDistance){
                        travelList.add(travel);
                    }
                }
                results.values=travelList;
                results.count=travelList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetChanged();
            else {
                companyItems = (List<Travel>) results.values;
                notifyDataSetChanged();
            }

        }

        public void setLocation(Location location) {
            this.location = location;
        }

        /**
         * calculate the distance between to points
         * @param userLat first point lat
         * @param userLng first point lng
         * @param venueLat second point lat
         * @param venueLng second point lng
         * @return the distance between the to points
         */
        public float calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

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
    }

    /**
     * holds the view of each row so we don't need to seek fir them in the R file
     */
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

    /**
     * inner interface fot the buttons
     */
    public interface CompanyTravelListener {
        void onButtonClicked(Travel travel, View view);
    }

    /**
     * set the listener to do an action when called
     * @param listener the function that is called in the company fragment when a button is clicked
     */
    public void setListener(CompanyTravelListener listener){
        this.listener=listener;
    }

}
