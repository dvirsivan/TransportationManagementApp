package com.example.TransportationManagement.Model;

import androidx.lifecycle.MutableLiveData;

import com.example.TransportationManagement.Entities.Company;
import com.example.TransportationManagement.Entities.Travel;

import java.util.List;

public interface ITravelDataSource {
    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();

    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(NotifyToTravelListListener l);
    List<Company> getCompanies();
}
