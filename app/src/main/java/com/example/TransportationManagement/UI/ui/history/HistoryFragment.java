package com.example.TransportationManagement.UI.ui.history;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.TransportationManagement.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {

    private MainViewModel mainViewModel;
    List<Travel> travels = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.historyRecyclerView);
        HistoryAdapter historyAdapter = new HistoryAdapter(travels,getContext());

        mainViewModel.getMutableHistoryTravels().observe(this.getActivity(), travelList -> {
            travels = travelList;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            HistoryAdapter adapter = new HistoryAdapter(travels, getContext());
            adapter.setListener((position, view) -> action(position,view));
            recyclerView.setAdapter(adapter);
        });
        historyAdapter.setListener((position,view) -> action(position,view));


        return root;
    }

    private void action(int position,View view) {
        if(view.getId() == R.id.changeStatusButton) {
            Travel travel = travels.get(position);
            travel.setStatus(Travel.RequestType.paidUp);
            mainViewModel.updateTravel(travel);
        }

        if(view.getId() == R.id.mailButtonHistory){
            String mail="";
            for(Map.Entry<String,Boolean> company:travels.get(position).getCompany().entrySet()){
                if(company.getValue()){
                    mail = company.getKey() + "@gmail.com";
                }

            }
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            if(!mail.equals("")) {
                intent.setData(Uri.parse("mailto:" + mail));
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}