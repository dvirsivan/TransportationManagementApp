package com.example.TransportationManagement.UI.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
            adapter.setListener(position -> action(position));
            recyclerView.setAdapter(adapter);
        });
        historyAdapter.setListener(position -> action(position));


        return root;
    }

    private void action(int position) {
        // need to implement!!!
    }
}