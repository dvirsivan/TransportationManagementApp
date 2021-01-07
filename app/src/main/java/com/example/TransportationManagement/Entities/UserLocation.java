package com.example.TransportationManagement.Entities;


import android.location.Location;

import androidx.annotation.NonNull;

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
}

