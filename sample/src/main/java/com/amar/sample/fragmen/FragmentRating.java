package com.amar.sample.fragmen;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amar.sample.R;
import com.amar.sample.adapter.AdapterProdukTambah;
import com.amar.sample.adapter.AdapterRating;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelRaringPesanan;

import java.util.ArrayList;


public class FragmentRating extends Fragment {

    private View view;
    private AdapterRating adapterRating;
    private ArrayList<ModelRaringPesanan> listReting;
    private Activity context;
    private RecyclerView recyclerView;


    public FragmentRating() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view =  inflater.inflate(R.layout.fragment_rating, container, false);

        addData();

        recyclerView = (RecyclerView) view.findViewById(R.id.rc_Rating);
        adapterRating = new AdapterRating(listReting);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRating);

        return view;
    }

    private void addData(){
        listReting = new ArrayList<>();
        listReting.add(new ModelRaringPesanan("001", "Budi S", "Mei 06 2020"));
        listReting.add(new ModelRaringPesanan("002", "Vikiy Nisasi", "Januari 08 2020"));
        listReting.add(new ModelRaringPesanan("003", "Lucinta Luna", "Februari 02 2020"));
        listReting.add(new ModelRaringPesanan("004", "Black Mamba", "April 09 2020"));
        listReting.add(new ModelRaringPesanan("005", "Ronal", "September 05 2020"));
        listReting.add(new ModelRaringPesanan("006", "Agus", "Maret 09 2020"));
        listReting.add(new ModelRaringPesanan("007", "Septian Nur", "Juni 05 2020"));
        listReting.add(new ModelRaringPesanan("008", "Sopik", "Januari 09 2020"));
        listReting.add(new ModelRaringPesanan("009", "Vivi Arum", "Agustus 09 2020"));
        listReting.add(new ModelRaringPesanan("010", "Yoga S", "September 08 2020"));
    }
}