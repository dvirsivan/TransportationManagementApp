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

import java.util.LinkedList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    ITravelRepository repository;
    private MutableLiveData<List<Travel>> mutableLiveData=new MutableLiveData<>();



    private MutableLiveData<List<CompanyItem>> mutableCompanyItemLiveData=new MutableLiveData<>();
    private MutableLiveData<List<RegisteredItem>> mutableRegisteredItemLiveData=new MutableLiveData<>();
    public MainViewModel(Application p){
        super(p);
        repository = TravelRepository.getInstance(p);
        ITravelRepository.NotifyToTravelListListener notifyToTravelListListener=new ITravelRepository.NotifyToTravelListListener() {
            @Override
            public void onTravelsChanged() {
                List<Travel> travelList = repository.getAllTravels();
                mutableLiveData.setValue(travelList);
                updateCompanyItem(travelList);

            }
        };


    }
    private void updateCompanyItem(List<Travel> travelList){
        LinkedList<CompanyItem> companyItemList = new LinkedList<>();

        for (Travel travel:travelList){
            companyItemList.add(new CompanyItem(travel.getSource(),travel.getAmountTravelers(),travel.getClientName(),travel.getStartDate(),
                    "3",travel.getClientPhone(),travel.getDestinations(),travel.getStatus()==Travel.RequestType.accepted));
        }
        mutableCompanyItemLiveData.setValue(companyItemList);
    }
    private void updateRegisteredItem(List<Travel> travelList){
        LinkedList<RegisteredItem> registeredItemList = new LinkedList<>();
        for (Travel travel:travelList){
            registeredItemList.add(new RegisteredItem(travel.getSource(),travel.getDestinations(),travel.getStartDate(),travel.getStatus(),travel.getCompany()));
        }
        mutableRegisteredItemLiveData.setValue(registeredItemList);
    }

    public MutableLiveData<List<CompanyItem>> getAllCompanyItem() {
        return mutableCompanyItemLiveData;
    }

    public MutableLiveData<List<RegisteredItem>> getAllRegisteredItem() {
        return mutableRegisteredItemLiveData;
    }

}
