package com.example.TransportationManagement.Entities;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserLocation {
    private Double lat;
    private Double lon;

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public UserLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public UserLocation() {
    }

    public UserLocation convertFromLocation(Location location){
        if (location==null)
            return null;
        return new UserLocation(location.getLatitude(),location.getLongitude());
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("" + getLat() + " " + getLon());
    }

    public String convertToString(Context context){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses!=null && addresses.size()>0)
            return addresses.get(0).getAddressLine(0);
        return this.toString();
    }
    public static List<String> convertToString(Context context, List<UserLocation> userLocationList){
        List<String> locations = new ArrayList<>();
        for(UserLocation location:userLocationList){
            locations.add(location.convertToString(context));
        }
        return locations;
    }

}

