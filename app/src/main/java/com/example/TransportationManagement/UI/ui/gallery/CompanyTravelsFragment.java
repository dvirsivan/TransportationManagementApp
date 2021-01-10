package com.example.TransportationManagement.UI.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Model.CompanyItem;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainViewModel;
import com.example.TransportationManagement.adapter.CompanyAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CompanyTravelsFragment extends Fragment {

    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_travels, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.companyRecyclerView);
        //CompanyAdapter companyAdapter = new CompanyAdapter();
        mainViewModel = new ViewModelProvider(getActivity()).get(mainViewModel.getClass());
        mainViewModel.getAllCompanyItem().observe(this.getActivity(), new Observer<List<CompanyItem>>() {
            @Override
            public void onChanged(List<CompanyItem> companyItems) {
                ArrayList<CompanyItem> companyItemList = new ArrayList<>(companyItems);
                CompanyAdapter companyAdapter = new CompanyAdapter(companyItemList);

            }
        });

        return root;
    }
}