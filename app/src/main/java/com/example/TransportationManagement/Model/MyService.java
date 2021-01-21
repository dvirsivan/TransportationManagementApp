package com.example.TransportationManagement.Model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.TransportationManagement.Entities.Travel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyService extends Service{
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference travels = firebaseDatabase.getReference("AllTravels");
    Thread thread;
    long count;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        count = 0;
        thread = new Thread(){
            @Override
            public void run() {
                Intent intent = new Intent("Add_travel");
                intent.setAction("com.javacodegeeks.android.NEW_TRAVEL");
                sendBroadcast(intent);
            }
        };
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG,"out of func!");
                if (count == 0){
                    count = snapshot.getChildrenCount();
                }
                if (count < snapshot.getChildrenCount()){
                    count = snapshot.getChildrenCount();
                    thread.run();
                    Log.d(TAG,"in func!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
