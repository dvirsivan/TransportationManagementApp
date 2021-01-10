package com.example.TransportationManagement.Model;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Entities.UserLocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegisteredItem {
    private String source;
    private LinkedList<String> destinations = new LinkedList<>();
    private String date;
    private Enum<Travel.RequestType> status;
    private LinkedList<String> company = new LinkedList<>();

    public RegisteredItem(UserLocation source, List<UserLocation> destinations, String date, Enum<Travel.RequestType> status, HashMap<String, Boolean> company) {
        this.source = source.toString();
        this.date = date;
        this.status = status;
        for(Map.Entry<String,Boolean> entry:company.entrySet()){
            this.company.add(entry.getKey());
        }

        for (UserLocation userLocation : destinations){
            this.destinations.add(userLocation.toString());
        }
    }

    public String getSource() {
        return source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public String getDate() {
        return date;
    }

    public Enum<Travel.RequestType> getStatus() {
        return status;
    }

    public List<String> getCompany() {
        return company;
    }
}
