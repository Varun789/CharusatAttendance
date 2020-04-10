package com.example.charusatattendance.classes;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.charusatattendance.activities.MainActivity;
import com.example.charusatattendance.activities.R;
import com.example.charusatattendance.activities.admin_main_page;

public class  NotificationHelper {

    public static void displayNotification(Context context, String title, String body) {

        Intent intent=new Intent(context,admin_main_page.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,100,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                //to go to pending intent
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        Log.d("notifiy",title);
        notificationManagerCompat.notify(1, mBuilder.build());
    }
}
