package com.example.TransportationManagement.UI.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.TransportationManagement.Entities.Travel;
import com.example.TransportationManagement.Model.RegisteredItem;
import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainViewModel;
import com.example.TransportationManagement.adapter.RegisteredAdapter;

import java.util.Arrays;
import java.util.LinkedList;

public class RegisteredFragment extends Fragment {

    private MainViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registered, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        View v = getView();
        ListView listView = ((ListView)root.findViewById(R.id.registeredList));
        LinkedList<String> l = new LinkedList();
        l.add("asd");
        LinkedList<String> ll = new LinkedList();
        ll.add("dfgd");
        RegisteredItem[] re = new RegisteredItem[]{new RegisteredItem("asdf",l,"12-12-12",
                Travel.RequestType.accepted,ll) };
        RegisteredAdapter adapter = new RegisteredAdapter(root.getContext(), Arrays.asList(re));
        listView.setAdapter(adapter);
        return root;
    }
}