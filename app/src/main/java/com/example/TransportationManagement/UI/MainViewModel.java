package com.example.TransportationManagement.UI;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Repository.ITravelRepository;
import com.example.TransportationManagement.Repository.TravelRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainViewModel extends AndroidViewModel {
    ITravelRepository repository;
    MutableLiveData<List<Travel>> mutableHistoryTravels = new MutableLiveData<>();
    MutableLiveData<List<Travel>> mutableCompany = new MutableLiveData<>();
    MutableLiveData<List<Travel>> mutableRegistered = new MutableLiveData<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    private SharedPreferences sharedPreferences;


    public MainViewModel(Application p){//צריך לבדוק אם הרשימות נטענות
        super(p);

        repository = TravelRepository.getInstance(p);
        currentUser = mAuth.getCurrentUser();
        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener=new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.getAllTravels();
                mutableHistoryTravels.setValue(travelList);
                updateCompany(travelList);
                updateRegistered(travelList);
                updateHistoryTravels(travelList);
            }
        };
        repository.setNotifyToTravelListListener(notifyToTravelListListener);


    }



    private void updateCompany(List<Travel> travelList){
        ArrayList<Travel> companyTravels = new ArrayList<>();
        for (Travel travel:travelList){
            if(Travel.RequestType.getTypeInt(travel.getStatus()) < 2){
                companyTravels.add(travel);
            }
        }
        mutableCompany.setValue(companyTravels);
    }

    private void updateRegistered(List<Travel> travelList){
        ArrayList<Travel> registeredTravel = new ArrayList<>();
        for(Travel travel:travelList){
            if(Travel.RequestType.getTypeInt(travel.getStatus()) < 1 )//&& travel.getClientEmail().equals(currentUser.getEmail())
            {
                registeredTravel.add(travel);
            }
        }
        mutableRegistered.setValue(registeredTravel);
    }
    private void updateHistoryTravels(List<Travel> travelList) {
        ArrayList<Travel> travels = new ArrayList<>();
        for(Travel travel:travelList) {
            if(Travel.RequestType.getTypeInt(travel.getStatus()) == 3)
            {
                travels.add(travel);
            }
        }
        mutableHistoryTravels.setValue(travels);
    }
    public void updateTravel(Travel toUpdate){
        repository.updateTravel(toUpdate);
    }

    public MutableLiveData<List<Travel>> getMutableHistoryTravels() {
        return mutableHistoryTravels;
    }

    public MutableLiveData<List<Travel>> getMutableCompany() {
        return mutableCompany;
    }

    public MutableLiveData<List<Travel>> getMutableRegistered() {
        return mutableRegistered;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return repository.getIsSuccess();
    }
}
