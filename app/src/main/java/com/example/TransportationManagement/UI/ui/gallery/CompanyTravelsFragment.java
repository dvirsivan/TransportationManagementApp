package com.example.TransportationManagement.UI.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainViewModel;
import com.example.TransportationManagement.adapter.CompanyAdapter;
import com.example.TransportationManagement.adapter.RegisteredAdapter;

import java.util.ArrayList;
import java.util.List;

public class CompanyTravelsFragment extends Fragment {

    private MainViewModel mainViewModel;
    List<Travel> travels;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_travels, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.companyRecyclerView);
        travels = mainViewModel.getMutableCompany().getValue();
       // updateListView(travels,recyclerView);
        //CompanyAdapter companyAdapter = new CompanyAdapter();

        mainViewModel.getMutableCompany().observe(this.getActivity(), travelList -> {
            updateListView(travelList,recyclerView);
        });


        return root;
    }
    private void updateListView(List<Travel> travelList, RecyclerView recyclerView){
        travels = travelList;
        Context c = getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        CompanyAdapter companyAdapter = new CompanyAdapter(travels,getContext(),mainViewModel);
        recyclerView.setAdapter(companyAdapter);
    }
}