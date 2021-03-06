package com.example.TransportationManagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;
import com.example.TransportationManagement.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisteredAdapter extends BaseAdapter {
    private Context context;
    private List<Travel> items;
    RegisteredTravelListener listener;

    /**
     * constructor
     * @param context
     * @param items
     */
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

    /**
     * get item for all item in listView
     * @param position - index's item in list
     * @param convertView the view
     * @param parent the list
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {  // if it first time of this convertView
            convertView = LayoutInflater.from(context).inflate(R.layout.regisreted_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position % 2 == 0)
            viewHolder.linearLayout.setBackgroundColor(Color.LTGRAY);
        else
            viewHolder.linearLayout.setBackgroundColor(Color.WHITE);
        List<String> Renum = Arrays.asList("Sent", "Accepted", "Run", "close"); // list of the enums
        // set all values in item
        Travel currentItem = (Travel) getItem(position);
        viewHolder.source.setText(currentItem.getSource().convertToString(context));
        viewHolder.date.setText(currentItem.getStartDate());
        spinnerAdapter(viewHolder.destinations, UserLocation.convertToString(context,currentItem.getDestinations()));
        spinnerAdapter(viewHolder.company,new ArrayList(currentItem.getCompany().keySet()));
        spinnerAdapter(viewHolder.statuses,Renum);
        viewHolder.submit.setEnabled(!currentItem.getCompany().isEmpty()); // if no company
        viewHolder.submit.setOnClickListener(v -> {
            if(viewHolder.company.getSelectedItem()!=null){
                String comp = viewHolder.company.getSelectedItem().toString();
                int status = viewHolder.statuses.getSelectedItemPosition();
                if (listener != null)
                    listener.onButtonClicked(position,status,comp);
            }
        });

        return convertView;
    }

    /**
     * help function for insert list to spinner
     * @param spin insert for him
     * @param list for insert
     */
    private void spinnerAdapter(Spinner spin, List list){
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
        Button submit;
        LinearLayout linearLayout;
        public ViewHolder(View view) {
            this.source = (TextView)view.findViewById(R.id.sourceText);
            this.date = (TextView)view.findViewById(R.id.dateText);
            this.destinations = (Spinner) view.findViewById(R.id.destinationsSpinner);
            this.statuses = (Spinner) view.findViewById(R.id.statuSpinner);
            this.company = (Spinner) view.findViewById(R.id.companySpinner);
            this.submit = (Button) view.findViewById(R.id.submit);
            this.linearLayout = (LinearLayout)view.findViewById(R.id.registeredItemLayout);
        }
    }
    public interface RegisteredTravelListener {
        void onButtonClicked(int position, int status, String company);
    }
    public void setListener(RegisteredAdapter.RegisteredTravelListener listener){
        this.listener = listener;
    }



}
