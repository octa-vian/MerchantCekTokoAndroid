package com.amar.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivityFromTambahProduk extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tambah Produk");
        setContentView(R.layout.activity_from_tambah_produk);
    }
}