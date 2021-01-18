package com.example.TransportationManagement.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.TransportationManagement.Model.ITravelDataSource;
import com.example.TransportationManagement.Entities.Travel;

import java.util.List;

public interface ITravelRepository {

    void addTravel(Travel travel);
    void updateTravel(Travel travel);
    List<Travel> getAllTravels();
    List<Travel> getAllHistoryTravels();
    MutableLiveData<Boolean> getIsSuccess();
    interface NotifyToTravelListListener {
        void onTravelsChanged();
    }
    interface NotifyToHistoryTravelListListener {
        void onHistoryTravelsChanged();
    }
    void setNotifyToHistoryTravelListListener(ITravelRepository.NotifyToHistoryTravelListListener l);

    void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l);
}
