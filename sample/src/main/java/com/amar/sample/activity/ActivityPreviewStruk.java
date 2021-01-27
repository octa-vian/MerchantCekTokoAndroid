package com.amar.sample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.sample.MainActivityNavigation;
import com.amar.sample.R;
import com.amar.sample.adapter.AdapterDaftarBarangRecomed;
import com.amar.sample.adapter.AdapterDaftarBarangRecomendPreview;
import com.amar.sample.adapter.AdapterDaftarBarangStruk;
import com.amar.sample.adapter.AdapterDaftarBarangStrukPreview;
import com.amar.sample.adapter.AdapterProdukTambah;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ActivityPreviewStruk extends AppCompatActivity {
    private AdapterDaftarBarangStrukPreview adapterDaftarBarangStruk;
    private AdapterDaftarBarangRecomendPreview adapterDaftarBarangRecomend;
    private ArrayList<ModelDaftarProduk> listBarang = new ArrayList<>();
    private ArrayList<ModelDaftarProduk> listRecomend = new ArrayList<>();
    private Activity activity;
    private ModelDaftarProduk modelDaftarProduk;
    private String Id;
    private static TextView txt_total;
    private ImageView img_back;
    private Button btn_kirim;
    private EditText edt_pesan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_struk);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();

        activity = this;

        RecyclerView rc_barang = findViewById(R.id.rc_daftar_barang);
        rc_barang.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rc_barang.setLayoutManager(layoutManager);
        adapterDaftarBarangStruk = new AdapterDaftarBarangStrukPreview(activity, listBarang);
        rc_barang.setAdapter(adapterDaftarBarangStruk);

        RecyclerView rc_recomend = findViewById(R.id.rc_rekomendasi);
        rc_recomend.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(activity);
        rc_recomend.setLayoutManager(lm);
        adapterDaftarBarangRecomend = new AdapterDaftarBarangRecomendPreview(activity, listRecomend);
        rc_recomend.setAdapter(adapterDaftarBarangRecomend);

        txt_total = findViewById(R.id.txt_total);
        img_back = findViewById(R.id.img_back);
        btn_kirim = findViewById(R.id.btn_oke);
        edt_pesan = findViewById(R.id.txt_deskripsi_produk);

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KirimData();
                Intent intent = new Intent(activity, MainActivityNavigation.class);
                intent.putExtra(Server.EXTRA_BARANG, "sukses");
                startActivity(intent);
                finish();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Extra
        Bundle extra = getIntent().getExtras();
        Id = extra.getString(Server.EXTRA_BARANG);

        LoadBarang();
        LoadBarangRecomend();

    }

    private void KirimData() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", Id);
            body.put("catatan", edt_pesan.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIvolley(activity, body, "POST", Server.URL_KIRIM, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("response").getString("message");
                    String status = object.getJSONObject("response").getString("status");

                    if (Integer.parseInt(status)==200){
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String result) {

            }
        });
    }

    public static void totalBRG(String s){
        Integer harga = Integer.parseInt(s);
        txt_total.setText(NumberFormat.getCurrencyInstance().format(harga));
    }

    private void LoadBarang() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(activity, body, "POST", Server.URL_DETAIL_BARANG_PESANAN, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listBarang.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if(Integer.parseInt(status)==200){
                        JSONArray obj = object.getJSONArray("response");
                        for (int i = 0; i < obj.length(); i++){
                            JSONObject objt = obj.getJSONObject(i);

                            listBarang.add(new ModelDaftarProduk(
                                    objt.getString("id"),
                                    objt.getString("id_product"),
                                    objt.getString("nama_produk"),
                                    objt.getString("qty"),
                                    objt.getString("harga"),
                                    objt.getString("satuan"),
                                    objt.getString("keterangan"),
                                    objt.getString("status"),
                                    objt.getString("keterangan_status"),
                                    objt.getString("total"))

                            );
                            /*"id": "2",
                                    "id_product": "10",
                                    "nama_produk": "Testing Produk",
                                    "qty": "3",
                                    "harga": "200000",
                                    "satuan": "BOX",
                                    "keterangan": "",
                                    "status": "1",
                                    "keterangan_status": "Proses",
                                    "total": "600000"*/
                        }
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterDaftarBarangStruk.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void LoadBarangRecomend() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(activity, body, "POST", Server.URL_BARANG_RECOMENDASI, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listRecomend.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if(Integer.parseInt(status)==200){
                        JSONArray obj = object.getJSONArray("response");
                        for (int i = 0; i < obj.length(); i++){
                            JSONObject objt = obj.getJSONObject(i);

                            listRecomend.add(new ModelDaftarProduk(
                                    objt.getString("id"),
                                    objt.getString("id_product"),
                                    objt.getString("nama_produk"),
                                    objt.getString("qty"),
                                    objt.getString("harga"),
                                    objt.getString("satuan"),
                                    objt.getString("keterangan"),
                                    objt.getString("status"),
                                    objt.getString("keterangan_status"),
                                    objt.getString("total"))

                            );

                        }
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterDaftarBarangRecomend.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });

    }
}