package com.example.TransportationManagement.UI.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Model.RegisteredItem;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainViewModel;
import com.example.TransportationManagement.adapter.CompanyAdapter;
import com.example.TransportationManagement.adapter.RegisteredAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RegisteredFragment extends Fragment {

    private ArrayList<Travel> travels;
    MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registered, container, false);
        ListView listView = ((ListView)root.findViewById(R.id.registeredList));
        //RegisteredAdapter adapter = new RegisteredAdapter(root.getContext(), travels);
        Lifecycle.State a = this.getActivity().getLifecycle().getCurrentState();
        //listView.setAdapter(adapter);
        Activity activity = this.getActivity();
        mainViewModel.getMutableCompany().observe(this.getActivity(), new Observer<List<Travel>>() {
            @Override
            public void onChanged(List<Travel> travelList) {
                ArrayList<Travel> travels = new ArrayList<>(travelList);
                RegisteredAdapter companyAdapter = new RegisteredAdapter(getContext(), travels);
                listView.setAdapter(companyAdapter);
            }
        });



        return root;
    }
}