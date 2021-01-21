package com.example.TransportationManagement.UI;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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

        repository.setNotifyToTravelListListener(() -> {
            List<Travel> travelList = repository.getAllTravels();
            updateCompany(travelList);
            updateRegistered(travelList);
        });


    }



    private void updateCompany(List<Travel> travelList){
        ArrayList<Travel> companyTravels = new ArrayList<>();
        for (Travel travel:travelList){
            if(Travel.RequestType.getTypeInt(travel.getStatus()) < 2){//אם זה accepted זה צריך להיות פה?
                companyTravels.add(travel);
            }
        }
        mutableCompany.setValue(companyTravels);
    }

    private void updateRegistered(List<Travel> travelList){
        ArrayList<Travel> registeredTravel = new ArrayList<>();
        for(Travel travel:travelList){
            if(Travel.RequestType.getTypeInt(travel.getStatus()) < 3 )//&& travel.getClientEmail().equals(currentUser.getEmail())
            {
                registeredTravel.add(travel);
            }
        }
        mutableRegistered.setValue(registeredTravel);
    }

    public void updateTravel(Travel toUpdate){
        repository.updateTravel(toUpdate);
    }

    public LiveData<List<Travel>> getMutableHistoryTravels() {
        return repository.getAllHistoryTravels();
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
