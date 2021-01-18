package com.example.TransportationManagement.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.TransportationManagement.Entities.Company;
import com.example.TransportationManagement.Model.ITravelDataSource;
import com.example.TransportationManagement.Entities.Travel;

import java.util.List;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    MutableLiveData<Boolean> getIsSuccess();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    List<Company> getCompanies();
    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}
