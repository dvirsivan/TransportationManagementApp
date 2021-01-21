package com.example.TransportationManagement.Model;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.TransportationManagement.R;
import com.example.TransportationManagement.UI.MainActivity;
import com.example.TransportationManagement.UI.loginActivity;

import static android.content.ContentValues.TAG;


public class MyBroadcastReceiver extends android.content.BroadcastReceiver {
    private  Context context;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (intent.getAction().matches("com.javacodegeeks.android.NEW_TRAVEL")) {
            Log.d(TAG,"got intent!");
            addNotification();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNotification() {
        String id = "channel_1";//id of channel
        String description = "message";//Description information of channel
        int importance = NotificationManager.IMPORTANCE_HIGH;//The Importance of channel
        NotificationChannel channel = new NotificationChannel(id, description, importance);


        Intent intent = new Intent(context, loginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification.Builder mBuilder = new Notification.Builder(context, id);
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My notification")
                .setContentText("added a new travel! ")
                .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.createNotificationChannel(channel);
        mBuilder.setChannelId(id);

        mNotificationManager.notify(001, mBuilder.build());
    }
}
