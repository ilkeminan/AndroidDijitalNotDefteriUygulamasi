package com.example.a16011034_notelook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityOncelikliNotlariListele extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context;
    ArrayList<Not> notlar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oncelikli_notlari_listele);
        context=this;
        Intent intentOncelikliNotlar=getIntent();
        notlar=(ArrayList<Not>) intentOncelikliNotlar.getSerializableExtra("oncelikliNotlar");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_oncelikli_notlar);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        AdapterNot mAdapter = new AdapterNot(notlar,context);
        recyclerView.setAdapter(mAdapter);
    }
}
