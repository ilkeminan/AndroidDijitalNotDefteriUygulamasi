package com.example.a16011034_notelook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Veritabani extends SQLiteOpenHelper {
    public Veritabani(Context context){
        super(context,"Notlar",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Notlar (baslik TEXT,tarih TEXT,icerik TEXT,hatirlatma INT,oncelik INT,renk INT,dosyaYolu TEXT,dosyaTipi TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Notlar");
        onCreate(db);
    }
}
