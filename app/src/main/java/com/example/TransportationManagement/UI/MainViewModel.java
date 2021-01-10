package com.example.TransportationManagement.UI;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Model.CompanyItem;
import com.example.TransportationManagement.Model.RegisteredItem;
import com.example.TransportationManagement.Repository.ITravelRepository;
import com.example.TransportationManagement.Repository.TravelRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    ITravelRepository repository;
    MutableLiveData<List<Travel>> mutableTravels = new MutableLiveData<>();
    MutableLiveData<List<Travel>> mutableCompany = new MutableLiveData<>();
    MutableLiveData<List<Travel>> mutableRegistered = new MutableLiveData<>();

    public MutableLiveData<List<Travel>> getMutableLiveData() {
        return mutableTravels;
    }


    public MainViewModel(Application p){//צריך לבדוק אם הרשימות נטענות
        super(p);
        repository = TravelRepository.getInstance(p);
        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener=new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.getAllTravels();
                mutableTravels.setValue(travelList);
                updateCompany(travelList);
                updateRegistered(travelList);
            }
        };
        repository.setNotifyToTravelListListener(notifyToTravelListListener);


    }



    private void updateCompany(List<Travel> travelList){
        ArrayList<Travel> companyTravels = new ArrayList<>();
        for (Travel travel:travelList){
            if(Travel.RequestType.getTypeInt(travel.getStatus())<2){
                companyTravels.add(travel);
            }
        }
        mutableCompany.setValue(companyTravels);
    }

    private void updateRegistered(List<Travel> travelList){
        ArrayList<Travel> registeredTravel = new ArrayList<>();
        for(Travel travel:travelList){
            if(Travel.RequestType.getTypeInt(travel.getStatus())<1){
                registeredTravel.add(travel);
            }
        }
        mutableRegistered.setValue(registeredTravel);
    }

    public MutableLiveData<List<Travel>> getMutableTravels() {
        return mutableTravels;
    }

    public MutableLiveData<List<Travel>> getMutableCompany() {
        return mutableCompany;
    }

    public MutableLiveData<List<Travel>> getMutableRegistered() {
        return mutableRegistered;
    }

}
