package com.example.TransportationManagement.UI.ui.registered;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainViewModel;
import com.example.TransportationManagement.adapter.RegisteredAdapter;

import java.util.ArrayList;
import java.util.List;

public class RegisteredFragment extends Fragment {

    private ArrayList<Travel> travels;
    MainViewModel mainViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registered, container, false);
        ListView listView = ((ListView) root.findViewById(R.id.registeredList));

        RegisteredAdapter registeredAdapter = new RegisteredAdapter(getContext(), travels);


        mainViewModel.getMutableRegistered().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travelList) {
                travels = new ArrayList<>(travelList);
                RegisteredAdapter registeredAdapter = new RegisteredAdapter(getContext(), travels);
                registeredAdapter.setListener((position, status, company) -> {
                    Travel travel = travels.get(position);
                    travel.setStatus(Travel.RequestType.getType(status));
                    travel.setCompany(company,true);
                    mainViewModel.updateTravel(travel);
                });
                listView.setAdapter(registeredAdapter);
            }
        });
        /*registeredAdapter.setListener((position, status, company) -> {
            Travel travel = travels.get(position);
            travel.setStatus(Travel.RequestType.getType(status));
            travel.setCompany(company, true);
            mainViewModel.updateTravel(travel);

        });*/
        return root;
    }
}