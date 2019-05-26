package com.example.a16011034_notelook;

import android.content.BroadcastReceiver;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Date;


public class BroadcastReceiverHatirlatici extends BroadcastReceiver {
    Veritabani vt;
    private static final String channelID = "channelID";
    private static final String channelName = "Channel Name";
    ArrayList<Not> notlar;
    Not not;
    @Override
    public void onReceive(Context context, Intent intent) {
        vt = new Veritabani(context);
        notlar = new VeritabaniIslemleri().tumNotlariCek(vt);
        Date suAn=new Date();
        int notSayisi=notlar.size();
        for(int i=0;i<notSayisi;i++){
            if(notlar.get(i).tarihAl().getYear()==suAn.getYear() && notlar.get(i).tarihAl().getMonth()==suAn.getMonth() && notlar.get(i).tarihAl().getDate()==suAn.getDate() && notlar.get(i).tarihAl().getHours()==suAn.getHours() && notlar.get(i).tarihAl().getMinutes()==suAn.getMinutes()){
                not=notlar.get(i);
            }
        }
        Intent resultIntent=new Intent(context,ActivityNotGoruntule.class);
        resultIntent.putExtra("not",not);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(context,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nb=new NotificationCompat.Builder(context);
        nb.setContentTitle(not.baslikAl());
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setPriority(NotificationCompat.PRIORITY_HIGH);
        nb.setAutoCancel(true);
        nb.setContentIntent(resultPendingIntent);
        if(not.icerikAl()!=null){
            nb.setContentText(not.icerikAl());
        }
        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Description");
            mManager.createNotificationChannel(channel);
            nb.setChannelId(channelID);
        }
        mManager.notify(1,nb.build());
        Vibrator vibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }
}
