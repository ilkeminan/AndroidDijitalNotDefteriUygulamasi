package com.example.a16011034_notelook;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Veritabani vt;
    ArrayList<Not> notlar;
    RecyclerView recyclerView;
    Context context;
    ImageButton imageButtonNotEkle,imageButtonNotAra,imageButtonOncelikliNotlariListele;
    EditText editTextNotAra;
    ArrayList<Not> tariheGoreNotlar,oncelikliNotlar;
    int notSayisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vt = new Veritabani(this);
        notlar = new VeritabaniIslemleri().tumNotlariCek(vt);
        context=this;
        editTextNotAra=(EditText) this.findViewById(R.id.editTextNotAra);
        imageButtonNotEkle=(ImageButton) this.findViewById(R.id.imageButtonNotEkle);
        imageButtonNotEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNotEkle=new Intent(MainActivity.this,ActivityNotEkle.class);
                startActivity(intentNotEkle);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Collections.sort(notlar, new Comparator<Not>() {
            @Override
            public int compare(Not o1, Not o2) {
                return o1.tarihAl().compareTo(o2.tarihAl());
            }
        });
        AdapterNot mAdapter = new AdapterNot(notlar,context);
        recyclerView.setAdapter(mAdapter);
        notSayisi=notlar.size();
        for(int i=0;i<notSayisi;i++){
            Calendar calSet = Calendar.getInstance();
            calSet.setTime(notlar.get(i).tarihAl());
            if (calSet.after(Calendar.getInstance()) && notlar.get(i).hatirlatmaAl()==true) {
                setAlarm(calSet,i);
            }
        }
        tariheGoreNotlar=new ArrayList<>();
        imageButtonNotAra=(ImageButton) this.findViewById(R.id.imageButtonNotAra);
        imageButtonNotAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tariheGoreNotlar.removeAll(notlar);
                for(int i=0;i<notSayisi;i++){
                    SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy - HH:mm");
                    Date tarih=notlar.get(i).tarihAl();
                    String strTarih=dateFormat.format(tarih);
                    if(strTarih.contains(editTextNotAra.getText().toString())){
                        tariheGoreNotlar.add(notlar.get(i));
                    }
                }
                AdapterNot mAdapter = new AdapterNot(tariheGoreNotlar,context);
                recyclerView.setAdapter(mAdapter);
            }
        });
        oncelikliNotlar=new ArrayList<>();
        imageButtonOncelikliNotlariListele=(ImageButton) this.findViewById(R.id.imageButtonOncelikliNotlar);
        imageButtonOncelikliNotlariListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oncelikliNotlar.removeAll(notlar);
                for(int i=0;i<notSayisi;i++){
                    if(notlar.get(i).oncelikAl()==true){
                        oncelikliNotlar.add(notlar.get(i));
                    }
                }
                Intent intentOncelikliNotlar=new Intent(MainActivity.this,ActivityOncelikliNotlariListele.class);
                intentOncelikliNotlar.putExtra("oncelikliNotlar",oncelikliNotlar);
                startActivity(intentOncelikliNotlar);
            }
        });
    }
    public void setAlarm(Calendar alarmCalendar,int i){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastReceiverHatirlatici.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
        }
        else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
        }
    }
}
