package com.example.followupapp.Utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.R;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;



import java.util.Calendar;
import java.util.List;

public class GeofenceRegistrationService extends IntentService {

    private static final String TAG = "GeoIntentService";

    public GeofenceRegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Log.e("geofence", geofences.size() + "");
            Geofence geofence = geofences.get(0);
          /*  if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_STAN_UNI)) {
                Log.d(TAG, "You are inside Ecstasy Business Park Mulund");
              showNotification("Entered","You are inside Ecstasy Business Park Mulund");

            } else {
                Log.d(TAG, "You are outside Ecstasy Business Park Mulund");
                showNotification("Exited", "You exited Ecstasy Business Park Mulund");
            }*/


            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_MURAL1)) {
                Log.e("geo1", geofence.getRequestId());
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());

                String date = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);
                String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);

                System.out.println(date + " " + time);
                showNotification("Entered", "Enter first space" + time + " " + date);
            } else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_MURAL2)) {
                Log.e("geo2", geofence.getRequestId());
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());

                String date = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH);
                String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);

                System.out.println(date + " " + time);
                showNotification("Entered", "Enter second space" + time + " " + date);
            } else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_MURAL3)) {
                Log.e("geo3", geofence.getRequestId());

                showNotification("Entered", "Enter third space");
            } else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_MURAL4)) {
                Log.e("geo4", geofence.getRequestId());
                showNotification("Entered", "Enter fourth space");
            }
//           else
//           if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_MURAL5))
//           {
//               Log.e("geo1",geofence.getRequestId());
//               showNotification("Entered","MURAL_5");
//           }else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_LAKE1))
//           {
//               Log.e("geo2",geofence.getRequestId());
//               showNotification("Entered","LAKE_1 ");
//           }else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_LASTMURAL))
//           {
//               Log.e("geo3",geofence.getRequestId());
//               showNotification("Entered","LAST_MURAL");
//           }else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_WELCOMEHALL))
//           {
//               Log.e("geo4",geofence.getRequestId());
//               showNotification("Entered","WELCOME_HALL");
//           }else if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_MURAL))
//           {
//               Log.e("geo4",geofence.getRequestId());
//               showNotification("Entered","MURAL");
//           }
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

    }

    public void showNotification(String text, String bigText) {
//        long timemills=System.currentTimeMillis();
//        Log.e("time",String.valueOf(timemills));
//        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timemills), TimeUnit.MILLISECONDS.toMinutes(timemills) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timemills)), TimeUnit.MILLISECONDS.toSeconds(timemills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timemills)));
//        Log.e("time in hr",hms);
//
//        String hms1 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timemills),
//                TimeUnit.MILLISECONDS.toMinutes(timemills) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timemills)),
//                TimeUnit.MILLISECONDS.toSeconds(timemills) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timemills)));
//
//        Log.e("time in hour",hms1);
        // 1. Create a NotificationManager


        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        // 2. Create a PendingIntent for AllGeofencesActivity
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(text)
                .setContentText(bigText)
                .setContentIntent(pendingNotificationIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(0, notification);
        // 3. Create and send a notification
//        Notification notification = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(text)
//                .setContentText(bigText)
//                .setContentIntent(pendingNotificationIntent)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setAutoCancel(true)
//                .build();
//        notificationManager.notify(0, notification);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name ="chan";
//            String description = "des";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("12", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//           // NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//            notificationManager.notify(0, notification);
//        }
//        else {
//
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String CHANNEL_ID = "my_channel_pocketbits_mobileapp2018_";// The id of the channel.
            CharSequence name = "chardescription";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    /*Notification icon image*/
                    .setChannelId(CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(text)
                    .setContentText(bigText)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))/*Notification with Image*/
                    .setAutoCancel(true)
                    .setColor(getResources().getColor(R.color.colorAccent))
                    .setContentIntent(pendingNotificationIntent);

//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
            notificationManager.notify(0, notificationBuilder.build());

        }
//        else {
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
//                    .setAutoCancel(true)
//                    .setContentTitle(text)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentText(bigText)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))/*Notification with Big Text*/
//                    .setAutoCancel(true)
//
//                    .setContentIntent(pendingNotificationIntent);
//
////            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////                mBuilder.setSmallIcon(R.mipmap.noti_icon_two);
////                mBuilder.setColor(getResources().getColor(R.color.colorAccent));
////            } else {
////                mBuilder.setSmallIcon(R.mipmap.noti_icon_two);
////            }
//
//          //  mBuilder.setContentIntent(pendingNotificationIntent);
//           // NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0, mBuilder.build());
//        }


    }
}





