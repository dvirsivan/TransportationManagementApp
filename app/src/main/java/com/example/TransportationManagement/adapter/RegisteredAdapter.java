package com.example.TransportationManagement.adapter;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;
import com.example.TransportationManagement.Model.RegisteredItem;
import com.example.TransportationManagement.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RegisteredAdapter extends BaseAdapter {
    private Context context;
    private List<Travel> items;
    Travel.UserLocationConverter userLocationConverter = new Travel.UserLocationConverter();
    private final Travel.ArrayListConverter arrayListConverter = new Travel.ArrayListConverter();
    UserLocation userLocation;

    public RegisteredAdapter(Context context, List<Travel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.regisreted_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Travel.RequestType[] enumL;
        enumL = new Travel.RequestType[]{Travel.RequestType.sent, Travel.RequestType.accepted, Travel.RequestType.run,
                Travel.RequestType.close};
        Travel currentItem = (Travel) getItem(position);
        viewHolder.source.setText(currentItem.getSource().convertToString(context));
        viewHolder.date.setText(currentItem.getStartDate());
        spinerAdapter(viewHolder.destinations,UserLocation.convertToString(context,currentItem.getDestinations()));
        spinerAdapter(viewHolder.company,List.of(currentItem.getCompany().keySet()));
        spinerAdapter(viewHolder.statuses,List.of(enumL));
        return convertView;
    }
    private void spinerAdapter(Spinner spin,List list){
        ArrayAdapter aa = new ArrayAdapter(this.context,android.R.layout.simple_spinner_item,list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    private class ViewHolder {
        TextView source;
        TextView date;
        Spinner destinations;
        Spinner statuses;
        Spinner company;

        public ViewHolder(View view) {
            this.source = (TextView)view.findViewById(R.id.sourceText);
            this.date = (TextView)view.findViewById(R.id.dateText);
            this.destinations = (Spinner) view.findViewById(R.id.destinationsSpinner);
            this.statuses = (Spinner) view.findViewById(R.id.statuSpinner);
            this.company = (Spinner) view.findViewById(R.id.companySpinner);
        }
    }
}
