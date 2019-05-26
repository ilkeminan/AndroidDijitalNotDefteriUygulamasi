package com.example.a16011034_notelook;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import yuku.ambilwarna.AmbilWarnaDialog;

public class ActivityNotGoruntule extends AppCompatActivity {
    Veritabani vt;
    EditText editTextBaslik,editTextTarih,editTextSaat,editTextNot;
    ImageButton imageButtonNotGuncelle,imageButtonNotSil,imageButtonTakvim,imageButtonSaat,imageButtonRenkSec,imageButtonDosyaAc;
    Switch switchHatirlatma,switchOncelik;
    Not not;
    Boolean hatirlatma,oncelik;
    Date tarih;
    String strTarih,saat,icerik,dosyaYolu;
    static String dosyaTipi;
    int renk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_goruntule);
        vt = new Veritabani(this);
        editTextBaslik=(EditText) this.findViewById(R.id.editTextBaslik);
        editTextTarih=(EditText) this.findViewById(R.id.editTextTarih);
        editTextSaat=(EditText) this.findViewById(R.id.editTextSaatDuzenle);
        editTextNot=(EditText) this.findViewById(R.id.editTextNotDuzenle);
        editTextTarih.setKeyListener(null);
        editTextSaat.setKeyListener(null);
        switchHatirlatma=(Switch) this.findViewById(R.id.switchHatirlatmaNotGoruntule);
        switchOncelik=(Switch) this.findViewById(R.id.switchOncelikNotGoruntule);
        Intent intentNotGoruntule=getIntent();
        not=(Not)intentNotGoruntule.getSerializableExtra("not");
        String baslik=not.baslikAl();
        icerik=not.icerikAl();
        hatirlatma=not.hatirlatmaAl();
        if(hatirlatma==true){
            switchHatirlatma.setChecked(true);
        }
        else{
            switchHatirlatma.setChecked(false);
        }
        oncelik=not.oncelikAl();
        if(oncelik==true){
            switchOncelik.setChecked(true);
        }
        else{
            switchOncelik.setChecked(false);
        }
        renk=not.renkAl();
        dosyaYolu=not.dosyaYoluAl();
        dosyaTipi=not.dosyaTipiAl();
        tarih=not.tarihAl();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
        strTarih=dateFormat.format(not.tarihAl());
        saat=timeFormat.format(not.tarihAl());
        editTextBaslik.setText(baslik);
        editTextBaslik.setTextColor(renk);
        editTextTarih.setText(strTarih);
        editTextTarih.setTextColor(renk);
        editTextSaat.setText(saat);
        editTextSaat.setTextColor(renk);
        if(icerik!=null){
            editTextNot.setText(icerik);
            editTextNot.setTextColor(renk);
        }
        final Context context=this;
        imageButtonTakvim=(ImageButton) findViewById(R.id.imageButtonTakvim);
        imageButtonTakvim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                final int yil = takvim.get(Calendar.YEAR);
                final int ay = takvim.get(Calendar.MONTH);
                final int gun = takvim.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                tarih.setDate(dayOfMonth);
                                tarih.setMonth(month);
                                tarih.setYear(year-1900);
                                month = month+1;
                                editTextTarih.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, yil, ay, gun);
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Tamam", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();
            }
        });
        imageButtonSaat=(ImageButton) this.findViewById(R.id.imageButtonSaatDuzenle);
        imageButtonSaat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                final int saat = takvim.get(Calendar.HOUR_OF_DAY);
                final int dakika = takvim.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tarih.setHours(hourOfDay);
                                tarih.setMinutes(minute);
                                editTextSaat.setText(hourOfDay+":"+minute);
                            }
                        }, saat,dakika,true);
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE,"Tamam",tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",tpd);
                tpd.show();
            }
        });
        imageButtonRenkSec=(ImageButton) this.findViewById(R.id.imageButtonRenkSecNotGoruntule);
        imageButtonRenkSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });
        imageButtonDosyaAc=(ImageButton) this.findViewById(R.id.imageButtonDosyaAcNotGoruntule);
        if(dosyaYolu==null){
            imageButtonDosyaAc.setImageResource(R.drawable.bos_resim);
            imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bos_resim));
        }
        else{
            if(dosyaTipi.equals("resim")){
                imageButtonDosyaAc.setImageResource(R.drawable.resim);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.resim));
            }
            else if(dosyaTipi.equals("video")){
                imageButtonDosyaAc.setImageResource(R.drawable.video);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.video));
            }
            else if(dosyaTipi.equals("ses")){
                imageButtonDosyaAc.setImageResource(R.drawable.ses);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.ses));
            }
        }
        imageButtonDosyaAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dosyaYolu!=null){
                    openFile(dosyaYolu);
                }
            }
        });
        imageButtonNotGuncelle=(ImageButton) this.findViewById(R.id.imageButtonNotGuncelle);
        imageButtonNotGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchHatirlatma.isChecked()==true){
                    hatirlatma=true;
                }
                else{
                    hatirlatma=false;
                }
                if(switchOncelik.isChecked()==true){
                    oncelik=true;
                }
                else{
                    oncelik=false;
                }
                if(editTextNot.getText().toString().equals("")){
                    icerik=null;
                }
                else{
                    icerik=editTextNot.getText().toString();
                }
                if(editTextBaslik.getText().toString().equals("") && editTextNot.getText().toString().equals("") && dosyaYolu==null){
                    Toast.makeText(getApplicationContext(),"Boş not kaydedilemez.",Toast.LENGTH_SHORT).show();
                }
                else{
                    new VeritabaniIslemleri().notGuncelle(vt,not.baslikAl(),editTextBaslik.getText().toString(),tarih,icerik,hatirlatma,oncelik,renk,dosyaYolu,dosyaTipi);
                    Toast.makeText(getApplicationContext(),"Not güncellendi.",Toast.LENGTH_LONG).show();
                    Intent intentMain=new Intent(ActivityNotGoruntule.this,MainActivity.class);
                    startActivity(intentMain);
                }
            }
        });
        imageButtonNotSil=(ImageButton) this.findViewById(R.id.imageButtonNotSil);
        imageButtonNotSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VeritabaniIslemleri().notSil(vt,not.baslikAl());
                Toast.makeText(getApplicationContext(),"Not silindi.",Toast.LENGTH_LONG).show();
                Intent intentAnaSayfa=new Intent(ActivityNotGoruntule.this,MainActivity.class);
                startActivity(intentAnaSayfa);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data!=null){
            Uri uploadfileuri=data.getData();
            dosyaYolu=getRealPathFromURI(this,uploadfileuri);
            if(dosyaTipi.equals("resim")){
                imageButtonDosyaAc.setImageResource(R.drawable.resim);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.resim));
            }
            else if(dosyaTipi.equals("video")){
                imageButtonDosyaAc.setImageResource(R.drawable.video);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.video));
            }
            else if(dosyaTipi.equals("ses")){
                imageButtonDosyaAc.setImageResource(R.drawable.ses);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.ses));
            }
        }
    }
    public void openFile(String filePath){
        final Uri data= FileProvider.getUriForFile(this,"com.example.a16011034_notelook",new File(filePath));
        this.grantUriPermission(this.getPackageName(),data,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        final Intent intent=new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data,"*/*")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        this.startActivity(intent);
    }
    public static String getRealPathFromURI(Context context, Uri uri){
        String filePath = "";
        String wholeID = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            wholeID = DocumentsContract.getDocumentId(uri);
        }
        String id = wholeID.split(":")[1];
        if(dosyaTipi.equals("resim")){
            String[] column = { MediaStore.Images.Media.DATA };
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        else if(dosyaTipi.equals("video")){
            String[] column = { MediaStore.Video.Media.DATA };
            String sel = MediaStore.Video.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        else if(dosyaTipi.equals("ses")){
            String[] column = { MediaStore.Audio.Media.DATA };
            String sel = MediaStore.Audio.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_for_files,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.resimSec){
            if(Build.VERSION.SDK_INT>22){
                requestPermissions(new String[] {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"}, 1);
            }
            Intent intent=new Intent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            dosyaTipi="resim";
            startActivityForResult(intent,1);
        }
        else if(item.getItemId()==R.id.videoSec){
            if(Build.VERSION.SDK_INT>22){
                requestPermissions(new String[] {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"}, 1);
            }
            Intent intent=new Intent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            dosyaTipi="video";
            startActivityForResult(intent,1);
        }
        else if(item.getItemId()==R.id.sesSec){
            if(Build.VERSION.SDK_INT>22){
                requestPermissions(new String[] {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"}, 1);
            }
            Intent intent=new Intent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("audio/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            dosyaTipi="ses";
            startActivityForResult(intent,1);
        }
        else if(item.getItemId()==R.id.dosyaSil){
            if(dosyaYolu==null){
                Toast.makeText(getApplicationContext(),"Dosya ekli değil.",Toast.LENGTH_SHORT).show();
            }
            else{
                dosyaYolu=null;
                dosyaTipi=null;
                imageButtonDosyaAc.setImageResource(R.drawable.bos_resim);
                imageButtonDosyaAc.setBackgroundDrawable(getResources().getDrawable(R.drawable.bos_resim));
                Toast.makeText(getApplicationContext(),"Dosya kaldırıldı.",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void openColorPicker(){
        AmbilWarnaDialog colorPicker=new AmbilWarnaDialog(this, renk, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                renk=color;
                editTextBaslik.setTextColor(renk);
                editTextTarih.setTextColor(renk);
                editTextSaat.setTextColor(renk);
                editTextNot.setTextColor(renk);
            }
        });
        colorPicker.show();
    }
}
