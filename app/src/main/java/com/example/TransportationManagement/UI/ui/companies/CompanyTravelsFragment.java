package com.example.TransportationManagement.UI.ui.companies;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
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
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    CompanyAdapter companyAdapter;
    int currentMaxDistance = Integer.MAX_VALUE;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_company_travels, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.companyRecyclerView);
        currentUser = mAuth.getCurrentUser();
        setFilter(root.findViewById(R.id.filterButton),root.findViewById(R.id.filterSpinner));
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                currentLocation =location;
                Toast.makeText(getContext(), "found GPS", Toast.LENGTH_LONG).show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {   }

            public void onProviderEnabled(String provider) { }

            public void onProviderDisabled(String provider) { }
        };
        getLocation();
        mainViewModel.getMutableCompany().observe(this.getActivity(), travelList -> {
            travels = travelList;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            companyAdapter = new CompanyAdapter(travels, getContext());
            companyAdapter.setListener((position, view) -> action(position,view));
            recyclerView.setAdapter(companyAdapter);
        });
        return root;
    }


    private void action(Travel travel, View view){
        if(view.getId() == R.id.callButton){
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            String phone = travel.getClientPhone();
            callIntent.setData(Uri.parse("tel:" + phone));
            if(phone.isEmpty())
                Toast.makeText(getContext(), "no phone number exist", Toast.LENGTH_LONG).show();
            else if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 5);
            }
            else
                getContext().startActivity(callIntent);
        }
        if(view.getId() == R.id.accept_button){
            travel.setCompany(keyFromMail(currentUser.getEmail()),false);
            mainViewModel.getIsSuccess().observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    Toast.makeText(view.getContext(), "operation Succeeded", Toast.LENGTH_LONG).show();
                }
            });
            mainViewModel.updateTravel(travel);

        }

    }

    private void getLocation() {

        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);

        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        }

    }

    private void setFilter(Button filterButton, Spinner filterSpinner){

        filterButton.setOnClickListener(v -> {
            if(currentLocation != null){
                String maxDistance = filterSpinner.getSelectedItem().toString();
                if(maxDistance.equals("without") || Integer.parseInt(maxDistance)>currentMaxDistance)
                    companyAdapter.resetData();
                if(!maxDistance.equals("without"))
                    currentMaxDistance = Integer.parseInt(maxDistance);
                companyAdapter.setLocation(currentLocation);
                companyAdapter.getFilter().filter(filterSpinner.getSelectedItem().toString());
            }

        });
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