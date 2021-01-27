package com.amar.sample.fragmen;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.amar.sample.R;
import com.amar.sample.adapter.AdapterProduk;
import com.amar.sample.adapter.AdapterProdukTambah;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelProduk;

import java.util.ArrayList;


public class FragmentHome extends Fragment {
    private Activity context;
    private View view;
    private AdapterProdukTambah adapterProduk;
    private ArrayList<ModelDaftarProduk> modelProduks;
    private RecyclerView recyclerView;
    private Spinner spinnerUrut, spinnerEtalase;
    private String[] germanFeminine = {
            "Filter",
            "Abjad",
            "Terlaris",
            "Terjual",
            "Termurah"
    };
    private String[] Etal = {
            "Etalase",
            "Produk Terjual",
            "Produk Habis"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home2, container, false);
        context = getActivity();

        spinnerUrut = (Spinner) view.findViewById(R.id.urutkan);
        spinnerEtalase = (Spinner) view.findViewById(R.id.etalase);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, germanFeminine);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, Etal);

        spinnerUrut.setAdapter(adapter);
        spinnerEtalase.setAdapter(adapter2);

        addData();

        recyclerView = (RecyclerView) view.findViewById(R.id.rc_produk);
        adapterProduk = new AdapterProdukTambah(context, modelProduks);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterProduk);



        return view;
    }

    private void addData(){
        modelProduks = new ArrayList<>();
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 8.000.000", "Ready",""));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 200.000", "Ready",""));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 6.000.000", "Ready"," R.drawable.sepatu1"));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 3.000.000", "Ready", "R.drawable.sepatu7"));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 12.000.000", "Ready", "R.drawable.sepatu5"));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 12.000.000", "Ready", "R.drawable.sepatu4"));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 12.000.000", "Ready", "R.drawable.sepatu3"));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 12.000.000", "Ready", "R.drawable.sepatu_biru"));
        modelProduks.add(new ModelDaftarProduk("Sepatu Semprot", "Rp. 12.000.000", "Ready", "R.drawable.sepatu1"));
    }

}