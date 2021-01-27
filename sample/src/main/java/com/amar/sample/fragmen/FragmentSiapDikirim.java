package com.amar.sample.fragmen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amar.sample.R;
import com.amar.sample.adapter.AdapterPesananKonfrm;
import com.amar.sample.adapter.AdapterSiapKirimPesanan;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentSiapDikirim extends Fragment {

    private Activity activity;
    private View view;
    private AdapterSiapKirimPesanan adapterSiapKirimPesanan;
    private ArrayList<ModelDaftarProduk> listPesanan = new ArrayList<>();
    private RecyclerView recyclerView;

    public FragmentSiapDikirim() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = getActivity();
        view = inflater.inflate(R.layout.fragment_siap_dikirim, container, false);

        recyclerView = view.findViewById(R.id.rc_daftar_pesanan);
        adapterSiapKirimPesanan = new AdapterSiapKirimPesanan(activity, listPesanan);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterSiapKirimPesanan);


        getData();

        return view;
    }

    private void getData() {
        new APIvolley(activity, new JSONObject(), "GET", Server.URL_SIAP_KIRIM_PESANAN, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    String message = object.getJSONObject("metadata").getString("message");
                    if (Integer.parseInt(status)==200){
                        JSONArray obj = object.getJSONArray("response");
                        for (int i = 0; i < obj.length(); i++) {
                            JSONObject objt = obj.getJSONObject(i);

                            listPesanan.add(new ModelDaftarProduk(
                                    objt.getString("id"),
                                    objt.getString("no_order"),
                                    objt.getString("tgl_pengiriman"),
                                    objt.getString("jam_dari_pengiriman"),
                                    objt.getString("jam_sampai_pengiriman"),
                                    objt.getString("total"),
                                    objt.getString("insert_at")
                            ));
                        }
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterSiapKirimPesanan.notifyDataSetChanged();


            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}