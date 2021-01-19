package com.example.TransportationManagement.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.TransportationManagement.Model.ITravelDataSource;
import com.example.TransportationManagement.Entities.Travel;

import java.util.List;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    LiveData<List<Travel>> getAllHistoryTravels();
    MutableLiveData<Boolean> getIsSuccess();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}
