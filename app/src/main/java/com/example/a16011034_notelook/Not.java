package com.example.a16011034_notelook;

import java.io.Serializable;
import java.util.Date;

public class Not implements Serializable {
    private String baslik,icerik,dosyaYolu,dosyaTipi;
    private boolean hatirlatma,oncelik;
    private Date tarih;
    private int renk;
    public Not(String baslik,Date tarih,String icerik,boolean hatirlatma,boolean oncelik,int renk,String dosyaYolu,String dosyaTipi){
        this.baslik=baslik;
        this.tarih=tarih;
        this.icerik=icerik;
        this.hatirlatma=hatirlatma;
        this.oncelik=oncelik;
        this.renk=renk;
        this.dosyaYolu=dosyaYolu;
        this.dosyaTipi=dosyaTipi;
    }
    public String baslikAl(){
        return baslik;
    }
    public void baslikDuzenle(String baslik){
        this.baslik=baslik;
    }
    public Date tarihAl(){
        return tarih;
    }
    public void tarihDuzenle(){
        this.tarih=tarih;
    }
    public boolean hatirlatmaAl(){
        return hatirlatma;
    }
    public void hatirlatmaDuzenle(boolean hatirlatma){
        this.hatirlatma=hatirlatma;
    }
    public void icerikAta(String icerik){
        this.icerik=icerik;
    }
    public String icerikAl(){
        return icerik;
    }
    public void oncelikAta(boolean oncelik){
        this.oncelik=oncelik;
    }
    public boolean oncelikAl(){
        return oncelik;
    }
    public void renkAta(int renk){
        this.renk=renk;
    }
    public int renkAl(){
        return renk;
    }
    public void dosyaYoluAta(String dosyaYolu){
        this.dosyaYolu=dosyaYolu;
    }
    public String dosyaYoluAl(){
        return dosyaYolu;
    }
    public void dosyaTipiAta(String dosyaTipi){
        this.dosyaTipi=dosyaTipi;
    }
    public String dosyaTipiAl(){
        return dosyaTipi;
    }
}
