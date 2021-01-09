package com.example.TransportationManagement.UI.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainViewModel;
import com.example.TransportationManagement.adapter.CompanyAdapter;

public class CompanyTravelsFragment extends Fragment {

    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_travels, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.companyRecyclerView);
        //CompanyAdapter companyAdapter = new CompanyAdapter();

        return root;
    }
}