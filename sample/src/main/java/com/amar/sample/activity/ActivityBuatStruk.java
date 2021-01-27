package com.amar.sample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.Touch;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.sample.R;
import com.amar.sample.adapter.AdapterDaftarBarangPesanan;
import com.amar.sample.adapter.AdapterDaftarBarangRecomed;
import com.amar.sample.adapter.AdapterDaftarBarangStruk;
import com.amar.sample.adapter.AdapterProdukTambah;
import com.amar.sample.model.ModelDaftarProduk;
import com.amar.sample.model.ModelSpinner;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.IDN;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

import static com.amar.sample.activity.MainActivityNew.SHARED_PREFF;
import static com.amar.sample.activity.MainActivityNew.SWITCH1;

public class ActivityBuatStruk extends AppCompatActivity {
    private AdapterDaftarBarangStruk adapterDaftarBarangStruk;
    private AdapterProdukTambah adapterDaftarBarangRecomend;
    private ArrayList<ModelDaftarProduk> listBarang = new ArrayList<>();
    private ArrayList<ModelDaftarProduk> listBarangRecomend = new ArrayList<>();
    private Activity activity;
    private ModelDaftarProduk modelDaftarProduk;
    private static TextView txt_total;
    private String Id;
    private CardView ln_recomend;
    private EditText edt_nama, edt_qtty, edt_harga;
    private Spinner edt_satuan;
    private ArrayAdapter adapterSP;
    private List<ModelSpinner> listSpinner = new ArrayList<>();
    private Integer satuan;
    private ModelSpinner modelSpinner;
    private JSONArray jsSpinner;
    private Button btn_lanjutkan;
    private ImageView img_back;
    private TourGuide tourGuide;

    private String TokenPrivite = Server.getRandomString(30);
    private boolean switchOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_struk);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();

        activity = this;

        RecyclerView rc_barang = findViewById(R.id.rc_struk);
        rc_barang.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        rc_barang.setLayoutManager(layoutManager);
        adapterDaftarBarangStruk = new AdapterDaftarBarangStruk(activity, listBarang);
        rc_barang.setAdapter(adapterDaftarBarangStruk);

        RecyclerView rc_barang_recomed = findViewById(R.id.rc_rekomendasi);
        rc_barang_recomed.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(activity);
        rc_barang_recomed.setLayoutManager(lm);
        adapterDaftarBarangRecomend = new AdapterProdukTambah(activity, listBarangRecomend);
        rc_barang_recomed.setAdapter(adapterDaftarBarangRecomend);

        //View
        txt_total = findViewById(R.id.txt_total);
        ln_recomend = findViewById(R.id.ln_recomend);
        btn_lanjutkan = findViewById(R.id.btn_lanjutkan);
        img_back = findViewById(R.id.img_back);

        /*Integer harga = Integer.parseInt(modelDaftarProduk.getItem10());
        txt_total.setText(modelDaftarProduk.getItem10());*/

        //Extra
        Bundle extra = getIntent().getExtras();
        Id = extra.getString(Server.EXTRA_BARANG);

        btn_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityPreviewStruk.class);
                intent.putExtra(Server.EXTRA_BARANG, Id);
                startActivity(intent);
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //getRandomString(30);
            }
        });

        LoadBarang();
        LoadData();

    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFF, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    protected void onFinishFragment() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH1, true);
        editor.apply();
    }



    public void CliketTourGuide1(LinearLayout tv_btn){
        tourGuide = TourGuide.init(activity).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(
                new ToolTip().setTitle("...PANDUAN...")
                .setDescription("1. Click Item untuk Mengubah Jumlah")
                        .setBackgroundColor(Color.parseColor("#EB2843"))
                        .setGravity(Gravity.TOP | Gravity.CENTER)
                        .setShadow(true))
                .setOverlay(new Overlay())
                .playOn(tv_btn);
        onFinishFragment();
    }

    public void GetBtn(ImageView img){
        CliketTourGuide2(img);
    }

    public void CliketTourGuide2(ImageView img){

        tourGuide = TourGuide.init(activity).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(
                        new ToolTip()
                .setDescription("2. Click Icon X untuk Menghapus Item")
                .setBackgroundColor(Color.parseColor("#EB2843"))
                .setGravity(Gravity.BOTTOM | Gravity.LEFT)
                .setShadow(true))
                .setOverlay(new Overlay())
                .playOn(img);

    }

    public void UbahItem(String flag, String nama, String qtty, String satuan, String harga, final String id_barang){

        if (flag.equals("1")){
            final Dialog dialog = new Dialog(activity);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.pop_up_dialog_tambah_recomendasi);

            edt_nama = dialog.findViewById(R.id.edt_nama);
            edt_nama.setText(nama);
            edt_nama.setEnabled(false);
            edt_qtty = dialog.findViewById(R.id.edt_qtty);
            edt_qtty.setText(qtty);
            edt_satuan = dialog.findViewById(R.id.spn_satuan);

            edt_satuan.setEnabled(false);
            edt_harga = dialog.findViewById(R.id.edt_harga);
            edt_harga.setText(harga);
            edt_harga.setEnabled(false);
            Button btn_oke;
            btn_oke = dialog.findViewById(R.id.btn_simpan);

            btn_oke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateBarangTransaksi(id_barang);
                    dialog.dismiss();
                }
            });
            InitSpinner();
            dialog.show();

        } else{

            final Dialog dialog = new Dialog(activity);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.pop_up_dialog_tambah_recomendasi);

            edt_nama = dialog.findViewById(R.id.edt_nama);
            edt_nama.setText(nama);
            edt_nama.setEnabled(false);
            edt_qtty = dialog.findViewById(R.id.edt_qtty);
            edt_qtty.setText(qtty);
            edt_qtty.setEnabled(false);
            edt_satuan = dialog.findViewById(R.id.spn_satuan);

            edt_satuan.setEnabled(false);
            edt_harga = dialog.findViewById(R.id.edt_harga);
            Button btn_oke;
            btn_oke = dialog.findViewById(R.id.btn_simpan);

            btn_oke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateBarangTransaksi(id_barang);
                    dialog.dismiss();
                }
            });
            InitSpinner();
            dialog.show();
        }
        tourGuide.cleanUp();


    }

    private void UpdateBarangTransaksi(String id_brg) {
        JSONObject body = new JSONObject();

        try {
            body.put("id", id_brg);
            body.put("qty", edt_qtty.getText().toString());
            body.put("harga", edt_harga.getText().toString());
            body.put("token", TokenPrivite);

            Log.d("body", String.valueOf(body));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(activity, body, "POST", Server.URL_UPDATE_PRODUCT_TRANSAKSi, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String message = obj.getJSONObject("metadata").getString("message");
                    String status = obj.getJSONObject("metadata").getString("status");


                    if(Integer.parseInt(status)==200){

                     Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                     LoadBarang();

                    } else{

                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void HapusBarang(final String id, final int flag){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.pop_up_dialog_confirm);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_tdk, btn_ya;

        btn_tdk = dialog.findViewById(R.id.btn_tidak);
        btn_ya = dialog.findViewById(R.id.btn_ya);



        btn_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1){
                    RekomendasiBrg(id, flag);
                }else{
                    ConfirmasiHapus(id, flag);
                }
                dialog.dismiss();
            }
        });

        btn_tdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void InputBarangRecomend(){
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.pop_up_dialog_tambah_recomendasi);

        edt_nama = dialog.findViewById(R.id.edt_nama);
        edt_qtty = dialog.findViewById(R.id.edt_qtty);
        edt_satuan = dialog.findViewById(R.id.spn_satuan);
        edt_harga = dialog.findViewById(R.id.edt_harga);
        Button btn_oke;
        btn_oke = dialog.findViewById(R.id.btn_simpan);

        btn_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahBarangRecomend();
                dialog.dismiss();
            }
        });
        InitSpinner();
        dialog.show();

    }

    private void InitSpinner() {
        InitDropdown();
        adapterSP = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, listSpinner);
        edt_satuan.setAdapter(adapterSP);
        edt_satuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String item = adapterView.getItemAtPosition(i).toString();
               //satuan = item.toString();
                satuan = LoadSpinner(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private int LoadSpinner(int i) {
        int id = 0;
        try {
            id = jsSpinner.getJSONObject(i).getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void InitDropdown() {

        new APIvolley(activity, new JSONObject(), "GET", Server.URL_SATUAN_SPINNER, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                listSpinner.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){

                        jsSpinner = object.getJSONArray("response");
                        for (int i = 0; i < jsSpinner.length(); i++) {
                            JSONObject objt = jsSpinner.getJSONObject(i);

                            listSpinner.add( new ModelSpinner(objt.getString("id"),
                                    objt.getString("satuan"),
                                    objt.getString("status"),
                                    objt.getString("user_insert"),
                                    objt.getString("insert_at"),
                                    objt.getString("user_update"),
                                    objt.getString("update_at")));

                        }
                    }
                 } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterSP.notifyDataSetChanged();

            }

            @Override
            public void onError(String result) {

            }
        });

    }

    private void TambahBarangRecomend() {
        JSONObject body = new JSONObject();
        try {

            body.put("no_order", Id);
            body.put("nama_product", edt_nama.getText().toString());
            body.put("qty", edt_qtty.getText().toString());
            body.put("satuan", satuan);
            body.put("harga", edt_harga.getText().toString());
            body.put("token", TokenPrivite);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIvolley(activity, body, "POST", Server.URL_TAMBAH_PRODUK_RECOMEND, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                        LoadBarang();
                        tourGuide.cleanUp();
                    } else {
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

    private void RekomendasiBrg(final String s, final int flag) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.pop_up_dialog_recomedasi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_tdk, btn_ya;

        btn_tdk = dialog.findViewById(R.id.btn_tidak);
        btn_ya = dialog.findViewById(R.id.btn_ya);

        btn_ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1){
                    ConfirmasiHapus(s, flag);
                    InputBarangRecomend();

                }else{
                    ConfirmasiHapus(s, flag);
                }
                dialog.dismiss();
            }
        });

        btn_tdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void ConfirmasiHapus(String s, final int flag) {
        JSONObject body = new JSONObject();

        try {
            body.put("id", s);
            body.put("token", TokenPrivite);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(activity, body, "POST", Server.URL_HAPUS_TRANSAKSI, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){
                        if (flag == 1){
                            LoadBarang();
                        } else if(flag == 2) {
                            LoadBarang();
                        }

                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
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
                body.put("token", TokenPrivite);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new APIvolley(activity, body, "POST", Server.URL_DETAIL_BARANG_BUAT_STRUK, new APIvolley.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    listBarang.clear();
                    listBarangRecomend.clear();
                    try {
                        JSONObject object = new JSONObject(result);
                        String message = object.getJSONObject("metadata").getString("message");
                        String status = object.getJSONObject("metadata").getString("status");

                        if(Boolean.parseBoolean(status)==true){

                            String on_order = object.getJSONObject("response").getString("no_order");
                            String token = object.getJSONObject("response").getString("token");

                            JSONArray obj = object.getJSONObject("response").getJSONArray("produk_konfirmasi");
                            Log.d("status", String.valueOf(obj));
                            for (int i = 0; i < obj.length(); i++){
                                JSONObject objt = obj.getJSONObject(i);

                                modelDaftarProduk = new ModelDaftarProduk(
                                        objt.getString("id"),
                                        objt.getString("id_product"),
                                        objt.getString("nama_product"),
                                        objt.getString("qty"),
                                        objt.getString("harga"),
                                        objt.getString("satuan"),
                                        objt.getString("keterangan"),
                                        objt.getString("status"),
                                        objt.getString("total"),
                                        objt.getString("flag")

                                );
                                listBarang.add(modelDaftarProduk);
                            }

                            JSONArray objk = object.getJSONObject("response").getJSONArray("produk_rekomendasi");
                            for (int i = 0; i < objk.length(); i++){
                                JSONObject objt = objk.getJSONObject(i);

                                modelDaftarProduk = new ModelDaftarProduk(
                                        objt.getString("id"),
                                        objt.getString("id_product"),
                                        objt.getString("nama_product"),
                                        objt.getString("qty"),
                                        objt.getString("harga"),
                                        objt.getString("satuan"),
                                        objt.getString("keterangan"),
                                        objt.getString("status"),
                                        objt.getString("total")

                                );
                                listBarangRecomend.add(modelDaftarProduk);
                                ln_recomend.setVisibility(View.VISIBLE);
                            }


                        } else {
                            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapterDaftarBarangStruk.notifyDataSetChanged();
                    adapterDaftarBarangRecomend.notifyDataSetChanged();
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
                listBarang.clear();
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if(Integer.parseInt(status)==200){
                        ln_recomend.setVisibility(View.VISIBLE);
                        JSONArray obj = object.getJSONArray("response");
                        for (int i = 0; i < obj.length(); i++){
                            JSONObject objt = obj.getJSONObject(i);

                            modelDaftarProduk = new ModelDaftarProduk(
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

                            );
                            listBarang.add(modelDaftarProduk);
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
                adapterDaftarBarangRecomend.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {
                Toast.makeText(activity, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }
        });

    }



    }
