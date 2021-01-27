package com.amar.sample.activityrating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amar.sample.R;
import com.amar.sample.adapter.AdapterDaftarRatingToko;
import com.amar.sample.adapter.AdapterRating;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelRaringPesanan;

import java.util.ArrayList;

public class ActivityPreviewRatingToko extends AppCompatActivity {

    private ArrayList<ModelDaftarProduk> listReting;
    private Activity activity;
    private AdapterDaftarRatingToko adapterDaftarRatingToko;
    private RecyclerView recyclerView;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_rating_toko);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();

        activity = this;
        addData();

        img_back = findViewById(R.id.img_back);

        recyclerView = (RecyclerView) findViewById(R.id.rc_rating_toko);
        adapterDaftarRatingToko = new AdapterDaftarRatingToko(activity, listReting);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDaftarRatingToko);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void addData(){
        listReting = new ArrayList<>();
        listReting.add(new ModelDaftarProduk("001", "Budi S", "Mei 06 2020", ""));
        listReting.add(new ModelDaftarProduk("002", "Vikiy Nisasi", "Januari 08 2020",""));
        listReting.add(new ModelDaftarProduk("003", "Lucinta Luna", "Februari 02 2020", ""));
        listReting.add(new ModelDaftarProduk("004", "Black Mamba", "April 09 2020", ""));
        listReting.add(new ModelDaftarProduk("005", "Ronal", "September 05 2020", ""));
        listReting.add(new ModelDaftarProduk("006", "Agus", "Maret 09 2020", ""));
        listReting.add(new ModelDaftarProduk("007", "Septian Nur", "Juni 05 2020",""));
        listReting.add(new ModelDaftarProduk("008", "Sopik", "Januari 09 2020", ""));
        listReting.add(new ModelDaftarProduk("009", "Vivi Arum", "Agustus 09 2020", ""));
        listReting.add(new ModelDaftarProduk("010", "Yoga S", "September 08 2020", ""));
    }
}