package com.example.a16011034_notelook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterNot extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private ArrayList<Not> notlar;
    Context context;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewBaslik;
        public TextView textViewTarih;
        public View layout;
        public ViewHolder(View v){
            super(v);
            layout=v;
            textViewBaslik=(TextView) v.findViewById(R.id.TextViewBaslik);
            textViewTarih=(TextView) v.findViewById(R.id.TextViewTarih);
        }
    }
    public AdapterNot(ArrayList<Not> listNotlar,Context context){
        notlar=listNotlar;
        this.context=context;
    }
    public AdapterNot.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_recyler_view,parent,false);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int renk=notlar.get(i).renkAl();
        Date tarih=notlar.get(i).tarihAl();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String strTarih=dateFormat.format(tarih);
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.textViewBaslik.setText(notlar.get(i).baslikAl());
        vh.textViewBaslik.setTextColor(renk);
        vh.textViewTarih.setText(strTarih);
        vh.textViewTarih.setTextColor(renk);
        ((ViewHolder) viewHolder).textViewBaslik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNotGoruntule = new Intent(context,ActivityNotGoruntule.class);
                intentNotGoruntule.putExtra("not",notlar.get(i));
                context.startActivity(intentNotGoruntule);
            }
        });
        ((ViewHolder) viewHolder).textViewTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNotGoruntule = new Intent(context,ActivityNotGoruntule.class);
                intentNotGoruntule.putExtra("not",notlar.get(i));
                context.startActivity(intentNotGoruntule);
            }
        });
    }
    @Override
    public int getItemCount() {
        return notlar.size();
    }
}
