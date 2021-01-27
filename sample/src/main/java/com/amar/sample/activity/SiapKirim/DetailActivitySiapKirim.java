package com.amar.sample.activity.SiapKirim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.amar.sample.activity.ActivityBuatStruk;
import com.amar.sample.adapter.AdapterDaftarBarangPesanan;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.Server;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;

public class DetailActivitySiapKirim extends AppCompatActivity {
    private TextView txt_nama, txt_noOrder, txt_alamat, txt_waktu_pengiriman, txt_total;
    private EditText edt_catatan;
    private Button btn_oke, btn_tolak;
    private AdapterDaftarBarangPesanan adapterDaftarBarangPesanan;
    private ArrayList<ModelDaftarProduk> listBarang = new ArrayList<>();
    private ImageView img_back;
    private Activity activity;
    private ModelDaftarProduk brg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_siap_kirim);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();

        activity = this;

        if(getIntent().hasExtra(Server.EXTRA_BARANG)){
            Gson gson = new Gson();
            brg = gson.fromJson(getIntent().getStringExtra(Server.EXTRA_BARANG), ModelDaftarProduk.class);
        }

        txt_nama = findViewById(R.id.txt_nama);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_noOrder = findViewById(R.id.no_pesanan);
        txt_waktu_pengiriman = findViewById(R.id.txt_waktu_pengiriman);
        txt_total = findViewById(R.id.txt_total);
        edt_catatan = findViewById(R.id.txt_deskripsi_produk);
        btn_oke = findViewById(R.id.btn_oke);
        img_back = findViewById(R.id.img_back);
       // btn_tolak = findViewById(R.id.btn_tolak);

        RecyclerView rc_barang = findViewById(R.id.rc_daftar_barang);
        rc_barang.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rc_barang.setLayoutManager(layoutManager);
        adapterDaftarBarangPesanan = new AdapterDaftarBarangPesanan(activity, listBarang);
        rc_barang.setAdapter(adapterDaftarBarangPesanan);

        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKirimPesanan();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       /* btn_tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivityNavigation.class);
                startActivity(intent);
                getTolak();
            }
        });*/

        LoadBarang();
        LoadData();
    }

    private void getKirimPesanan() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", brg.getItem2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIvolley(activity, body, "POST", Server.URL_KIRIM_PESANAN, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("response").getString("message");
                    String status = object.getJSONObject("response").getString("status");

                    if (Integer.parseInt(status)==200){
                        Dialog dialog = new Dialog(activity);
                        dialog.setContentView(R.layout.pop_up_dialog_konfirmasi);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        Button btn_oke;

                        btn_oke = dialog.findViewById(R.id.btn_ya);
                        btn_oke.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(activity, MainActivityNavigation.class);
                                intent.putExtra(Server.EXTRA_BARANG, "siap_kirim");
                                startActivity(intent);
                            }
                        });
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        dialog.show();

                    }else{
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTolak() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", brg.getItem2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIvolley(activity, body, "POST", Server.URL_TOLAK_TRANSAKSI, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadData() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", brg.getItem2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(activity, body, "POST", Server.URL_DETAIL_PESANAN, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)== 200){
                        txt_noOrder.setText(object.getJSONObject("response").getString("no_order"));
                        txt_alamat.setText(object.getJSONObject("response").getString("alamat"));
                        String Total = object.getJSONObject("response").getString("total");
                        Integer total = Integer.parseInt(Total);
                        txt_total.setText(NumberFormat.getCurrencyInstance().format(total));
                        String Tgl_dari = object.getJSONObject("response").getString("jam_dari_pengiriman");
                        String Tgl_sampai = object.getJSONObject("response").getString("jam_sampai_pengiriman");
                        txt_waktu_pengiriman.setText(object.getJSONObject("response").getString("tgl_pengiriman") + Tgl_dari + "-" + Tgl_sampai);
                        txt_nama.setText(object.getJSONObject("response").getString("nama"));
                        edt_catatan.setText(object.getJSONObject("response").getString("catatan"));
                        edt_catatan.setEnabled(false);

                    } else{
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void LoadBarang() {
        JSONObject body = new JSONObject();
        try {
            body.put("no_order", brg.getItem2());
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
                                    objt.getString("total")

                            ));

                        }
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterDaftarBarangPesanan.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });
    }
}