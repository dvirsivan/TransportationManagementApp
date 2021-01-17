package com.example.TransportationManagement.Repository;


import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.TransportationManagement.Model.IHistoryDataSource;
import com.example.TransportationManagement.Model.ITravelDataSource;
import com.example.TransportationManagement.Model.HistoryDataSource;
import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Model.TravelFirebaseDataSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TravelRepository implements ITravelRepository {
    ITravelDataSource travelDataSource;
    private IHistoryDataSource historyDataSource;
    private ITravelRepository.NotifyToTravelListListener notifyToTravelListListenerRepository;
    List<Travel> travelList;
    LinkedList<Travel> historyTravels;

    private static TravelRepository instance;

    public static TravelRepository getInstance(Application application) {
        if (instance == null)
            instance = new TravelRepository(application);
        return instance;
    }

    private TravelRepository(Application application) {
        travelDataSource = TravelFirebaseDataSource.getInstance();
        historyDataSource = new HistoryDataSource(application.getApplicationContext());
        sortTravelsForHistory(travelDataSource.getAllTravels());
        ITravelDataSource.NotifyToTravelListListener notifyToTravelListListener = () -> {
            travelList = travelDataSource.getAllTravels();
            checkUpdate();
            if (notifyToTravelListListenerRepository != null)
                notifyToTravelListListenerRepository.onTravelsChanged();
        };
        travelDataSource.setNotifyToTravelListListener(notifyToTravelListListener);
    }

    private void checkUpdate() {
        for (Travel travel: travelList){
            if (travel.getStatus() == Travel.RequestType.close){
                int indexTravel = historyTravels.indexOf(travel);
                if (indexTravel == -1){
                    historyTravels.add(travel);
                    historyDataSource.addTravel(travel);
                }
            }
        }
    }

    @Override
    public void addTravel(Travel travel) {
        travelDataSource.addTravel(travel);
    }

    @Override
    public void updateTravel(Travel travel) {
        travelDataSource.updateTravel(travel);
    }

    @Override
    public List<Travel> getAllTravels() {
        return travelList;
    }

    @Override
    public MutableLiveData<Boolean> getIsSuccess() {
        return travelDataSource.getIsSuccess();
    }

    public void setNotifyToTravelListListener(ITravelRepository.NotifyToTravelListListener l) {
        notifyToTravelListListenerRepository = l;
    }

    public void sortTravelsForHistory(List<Travel> trevels){
        for (Travel travel: trevels){
            if (travel.getStatus() == Travel.RequestType.close) {
                historyDataSource.addTravel(travel);
                historyTravels.add(travel);
            }
        }
    }

}
