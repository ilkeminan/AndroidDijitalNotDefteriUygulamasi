package com.example.a16011034_notelook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VeritabaniIslemleri {
    public void notEkle(Veritabani vt, Not not) {
        Date tarih=not.tarihAl();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String strTarih=dateFormat.format(tarih);
        Boolean hatirlatma=not.hatirlatmaAl();
        int intHatirlatma;
        if(hatirlatma==true){
            intHatirlatma=1;
        }
        else{
            intHatirlatma=0;
        }
        Boolean oncelik=not.oncelikAl();
        int intOncelik;
        if(oncelik==true){
            intOncelik=1;
        }
        else{
            intOncelik=0;
        }
        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put("baslik", not.baslikAl());
        degerler.put("tarih", strTarih);
        degerler.put("icerik",not.icerikAl());
        degerler.put("hatirlatma",intHatirlatma);
        degerler.put("oncelik",intOncelik);
        degerler.put("renk",not.renkAl());
        degerler.put("dosyaYolu",not.dosyaYoluAl());
        degerler.put("dosyaTipi",not.dosyaTipiAl());
        dbx.insertOrThrow("Notlar", null, degerler);
        dbx.close();
    }
    public void notGuncelle(Veritabani vt,String baslik,String yeniBaslik,Date tarih,String icerik,boolean hatirlatma,boolean oncelik,int renk,String dosyaYolu,String dosyaTipi){
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String strTarih=dateFormat.format(tarih);
        int intHatirlatma;
        if(hatirlatma==true){
            intHatirlatma=1;
        }
        else{
            intHatirlatma=0;
        }
        int intOncelik;
        if(oncelik==true){
            intOncelik=1;
        }
        else{
            intOncelik=0;
        }
        SQLiteDatabase dbx= vt.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put("baslik",yeniBaslik);
        degerler.put("tarih",strTarih);
        degerler.put("icerik",icerik);
        degerler.put("hatirlatma",intHatirlatma);
        degerler.put("oncelik",intOncelik);
        degerler.put("renk",renk);
        degerler.put("dosyaYolu",dosyaYolu);
        degerler.put("dosyaTipi",dosyaTipi);
        dbx.update("Notlar",degerler,"baslik=?",new String[]{baslik});
        dbx.close();
    }
    public void notSil(Veritabani vt,String baslik){
        SQLiteDatabase dbx = vt.getWritableDatabase();
        dbx.delete("Notlar","baslik=?",new String[]{baslik});
        dbx.close();
    }
    public ArrayList<Not> tumNotlariCek(Veritabani vt) {
        ArrayList<Not> notlarArrayList = new ArrayList<>();
        SQLiteDatabase dbx = vt.getWritableDatabase();
        Cursor c = dbx.rawQuery("SELECT * FROM Notlar", null);
        while (c.moveToNext()) {
            String baslik=c.getString(c.getColumnIndex("baslik"));
            String strTarih=c.getString(c.getColumnIndex("tarih"));
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            Date tarih=null;
            try {
                tarih=dateFormat.parse(strTarih);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            String icerik=c.getString(c.getColumnIndex("icerik"));
            int intHatirlatma=c.getInt(c.getColumnIndex("hatirlatma"));
            Boolean hatirlatma;
            if(intHatirlatma==1){
                hatirlatma=true;
            }
            else{
                hatirlatma=false;
            }
            int intOncelik=c.getInt(c.getColumnIndex("oncelik"));
            Boolean oncelik;
            if(intOncelik==1){
                oncelik=true;
            }
            else{
                oncelik=false;
            }
            int renk=c.getInt(c.getColumnIndex("renk"));
            String dosyaYolu=c.getString(c.getColumnIndex("dosyaYolu"));
            String dosyaTipi=c.getString(c.getColumnIndex("dosyaTipi"));
            Not not = new Not(baslik,tarih,icerik,hatirlatma,oncelik,renk,dosyaYolu,dosyaTipi);
            notlarArrayList.add(not);
        }
        return notlarArrayList;
    }
}
