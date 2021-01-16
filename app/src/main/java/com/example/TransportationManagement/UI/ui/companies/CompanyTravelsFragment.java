package com.example.TransportationManagement.UI.ui.companies;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class CompanyTravelsFragment extends Fragment {

    private MainViewModel mainViewModel;
    List<Travel> travels = new ArrayList<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_travels, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.companyRecyclerView);
        CompanyAdapter companyAdapter = new CompanyAdapter(travels,getContext());
        currentUser=mAuth.getCurrentUser();
       // updateListView(travels,recyclerView);
        //CompanyAdapter companyAdapter = new CompanyAdapter();

        mainViewModel.getMutableCompany().observe(this.getActivity(), travelList -> {
            travels = travelList;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            CompanyAdapter adapter = new CompanyAdapter(travels, getContext());
            adapter.setListener((position, view) -> action(position,view));
            recyclerView.setAdapter(adapter);
        });

        companyAdapter.setListener((position, view) -> action(position,view));

        return root;
    }
    private void action(int position, View view){
        Travel travel = travels.get(position);
        if(view.getId()==R.id.callButton){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String phone = travel.getClientPhone();
            callIntent.setData(Uri.parse("tel:" + phone));
            if(phone.isEmpty())
                Toast.makeText(getContext(), "no phone number exist", Toast.LENGTH_LONG).show();
            else if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                Toast.makeText(getContext(), "please approve phone call", Toast.LENGTH_LONG).show();
            }
            else
                getContext().startActivity(callIntent);
        }
        if(view.getId()==R.id.accept_button){
            travel.setCompany(keyFromMail(currentUser.getEmail()),true);//לשנות לcurrentuser.getmail
            mainViewModel.updateTravel(travel);
            mainViewModel.getIsSuccess().observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    Toast.makeText(getContext(), "operation Succeeded", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
 private String keyFromMail(String mail){
        int i = 0;
        String res="";
        while (mail.charAt(i)!='@'){
            res += mail.charAt(i);
            i++;
        }
        return res;
 }
}