package com.amar.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.sample.activity.LoginActivity;
import com.amar.sample.activity.MainActivityNew;
import com.amar.sample.fragmen.FragmentDaftarPesanan;
import com.amar.sample.fragmen.FragmentDalamPengiriman;
import com.amar.sample.fragmen.FragmentHome;
import com.amar.sample.fragmen.FragmentMenungguKonfirmasi;
import com.amar.sample.fragmen.FragmentPesananSelesai;
import com.amar.sample.fragmen.FragmentRating;
import com.amar.sample.fragmen.FragmentSiapDikirim;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.AppSharedPreferences;
import com.amar.sample.util.Server;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivityNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Activity activity;
    private Fragment fragment;
    private ImageView img_setting;
    private TextView txt_nama, txt_alamat;
    private ImageView img_profile, img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_navigation);


        activity = this;
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle !=null){
            String extra = bundle.getString(Server.EXTRA_BARANG);
            if (extra.equals("sukses")){
                loadfragment(new FragmentMenungguKonfirmasi());
                navView.setSelectedItemId(R.id.navigation_dashboard);
            } else if (extra.equals("siap_kirim")){
                loadfragment(new FragmentDalamPengiriman());
                navView.setSelectedItemId(R.id.navigation_dalam_pengiriman);
            }
        } else{
            loadfragment(new FragmentDaftarPesanan());
        }

        img_back = findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //first load
        clearFragmentBackStack();
    }

    private void getProfile() {
        new APIvolley(activity, new JSONObject(), "GET", Server.URL_VIEW_PROFILE, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String message = object.getJSONObject("metadata").getString("message");
                    String status = object.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){
                        txt_nama.setText(object.getJSONObject("response").getString("nama_merchant"));
                        txt_alamat.setText(object.getJSONObject("response").getString("alamat_merchant"));
                        Picasso.get().load(object.getJSONObject("response").getString("logo")).into(img_profile);

                    } else {
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

    public void clearFragmentBackStack() {
        FragmentManager fh = getSupportFragmentManager();
        for (int i = 0; i < fh.getBackStackEntryCount() - 1; i++) {
            fh.popBackStack();
        }
    }

    private boolean loadfragment(Fragment fragment) {
        {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.nav_host_fragment, fragment);
            trans.commitAllowingStateLoss();
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new FragmentDaftarPesanan();
                loadfragment(fragment);
                break;
            case R.id.navigation_dashboard:
                fragment = new FragmentMenungguKonfirmasi();
                loadfragment(fragment);
                break;
            case R.id.navigation_siap_dikirim:
                fragment = new FragmentSiapDikirim();
                loadfragment(fragment);
                break;
            case R.id.navigation_dalam_pengiriman:
                fragment = new FragmentDalamPengiriman();
                loadfragment(fragment);
                break;
            case R.id.navigation_pesanan_selesai:
                fragment = new FragmentPesananSelesai();
                loadfragment(fragment);
                break;

        }

        return true;
    }


}