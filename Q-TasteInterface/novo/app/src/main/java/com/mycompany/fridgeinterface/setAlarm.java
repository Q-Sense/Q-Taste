package com.mycompany.fridgeinterface;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;


/**
 * Created by Carolina on 12/05/2015.
 */
public class setAlarm extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent){

        String NOME = intent.getStringExtra("NOME");

        createNotification(context, NOME + " is runnig out of time ", "QtasteANDROID", "Notification Q-taste");

    }


    public void createNotification(Context context, String msg, String msgText, String msgAlert){

        // Define an Intent and an action to perform with it by another application
        PendingIntent notificIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        // Builds a notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle(msg)
                        .setTicker(msgAlert)
                        .setContentText(msgText);

        // Defines the Intent to fire when the notification is clicked
        mBuilder.setContentIntent(notificIntent);

        // Set the default notification option
        // DEFAULT_SOUND : Make sound
        // DEFAULT_VIBRATE : Vibrate
        // DEFAULT_LIGHTS : Use the default light notification
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        // Auto cancels the notification when clicked on in the task bar
        mBuilder.setAutoCancel(true);

        // Gets a NotificationManager which is used to notify the user of the background event
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Post the notification
        mNotificationManager.notify(1, mBuilder.build());

    }


}



/* NotificationCompat.Builder notificacion = new NotificationCompat.Builder(setAlarm.this);

notificacion.setSmallIcon(R.drawable.notification_icon);
        notificacion.setTicker("Notification Q-taste");
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle("Somethig is runnig out of time");
        notificacion.setContentText("QtasteANDROID");
        notificacion.setContentInfo("QT");

        Uri sonido = RingtoneManager.getDefaultUri(Notification.DEFAULT_SOUND);
        notificacion.setSound(sonido);

        Bitmap icono = BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon);
        notificacion.setLargeIcon(icono);

        PendingIntent myPendingIntent;
        Intent myIntent = new Intent();
        Context myContext = getApplicationContext();

        myIntent.setClass(myContext, Activity2.class);
        myIntent.putExtra("ID", 1);

        myPendingIntent = PendingIntent.getActivity(myContext, 0, myIntent, 0);
        notificacion.setContentIntent(myPendingIntent);

        Notification n = notificacion.build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        nm.notify(1, n); */