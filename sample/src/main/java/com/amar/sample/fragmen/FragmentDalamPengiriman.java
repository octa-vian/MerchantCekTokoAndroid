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
import android.widget.Toast;

import com.amar.sample.R;
import com.amar.sample.adapter.AdapterDalamPengiriman;
import com.amar.sample.adapter.AdapterPesananKonfrm;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentDalamPengiriman extends Fragment {

    private View view;
    private Activity activity;
    private RecyclerView recyclerView;
    private AdapterDalamPengiriman adapterDaftarPesanan;
    private ArrayList<ModelDaftarProduk> ListDaftarProduk = new ArrayList<>();

    public FragmentDalamPengiriman() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = getActivity();
        view = inflater.inflate(R.layout.fragment_dalam_pengiriman, container, false);

        recyclerView = view.findViewById(R.id.rc_daftar_pesanan);
        adapterDaftarPesanan = new AdapterDalamPengiriman(activity, ListDaftarProduk);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDaftarPesanan);

        addData();

        return view;
    }

    private void addData(){
        new APIvolley(activity, new JSONObject(), "GET", Server.URL_LIST_DALAM_PENGIRIMAN, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    ListDaftarProduk.clear();
                    JSONObject order = new JSONObject(result);
                    String message = order.getJSONObject("metadata").getString("message");
                    String status = order.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){

                        JSONArray obj = order.getJSONArray("response");
                        for (int i = 0; i < obj.length(); i++){
                            JSONObject objt = obj.getJSONObject(i);

                            ListDaftarProduk.add(new ModelDaftarProduk(
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
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterDaftarPesanan.notifyDataSetChanged();

            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }

}