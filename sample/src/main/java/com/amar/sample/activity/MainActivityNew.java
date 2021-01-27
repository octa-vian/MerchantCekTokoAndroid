package com.amar.sample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.sample.MainActivityNavigation;
import com.amar.sample.R;
import com.amar.sample.activityrating.ActivityPreviewRatingToko;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.AppSharedPreferences;
import com.amar.sample.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

public class MainActivityNew extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageView img_setting;
    private TextView txt_nama, txt_alamat;
    private ImageView img_profile;
    private Activity activity;
    private Switch TextSwich;
    private CardView cr_daftar_pesanan, btn_rating_toko;
    private  Vibrator vibrator;
    public static final String SHARED_PREFF = "Sharedpreff";
    public static final String SWITCH1 = "Switch1";
    private LinearLayout ln_btn_profile;
    private Boolean switchOnOff;
    private TourGuide tourGuide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_nav);

        activity = this;

        img_setting = findViewById(R.id.id_set);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_nama = findViewById(R.id.txt_nama);
        img_profile = findViewById(R.id.img_profile);
        cr_daftar_pesanan = findViewById(R.id.btn_daftar_pesanan);
        ln_btn_profile = findViewById(R.id.ln_btn_profile);
        btn_rating_toko = findViewById(R.id.btn_rating_toko);

        vibrator = (Vibrator)getSystemService(activity.VIBRATOR_SERVICE);

        TextSwich = (Switch) findViewById(R.id.switch_action);
        TextSwich.setOnCheckedChangeListener(this);

        /*tourGuide = TourGuide.init(activity).with(TourGuide.Technique.Click)
                .setPointer(new Pointer())
                .setToolTip(
                        new ToolTip().setTitle("Welcome Cek Toko")
                .setDescription("Click Daftar Pesanan untuk melihat teransaksi"))
                .setOverlay(new Overlay())
                .playOn(cr_daftar_pesanan);*/


        cr_daftar_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivityNavigation.class);
                startActivity(intent);
            }
        });

        ln_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityChangeProfile.class);
                startActivity(intent);
            }
        });

        btn_rating_toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityPreviewRatingToko.class);
                startActivity(intent);
            }
        });

        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.pop_up_setting);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btn_logout;
                btn_logout = dialog.findViewById(R.id.btn_logout);

                btn_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppSharedPreferences.Logout(activity);
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        });

        getProfile();
        LoadData();
        //ViewSwitch();
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
                        Picasso.get().load(object.getJSONObject("response").getString("logo_merchant")).into(img_profile);

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

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH1, TextSwich.isChecked());
        editor.apply();
    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFF, MODE_PRIVATE);
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);
    }

    public void ViewSwitch(){
        TextSwich.setChecked(switchOnOff);
        vibrator.cancel();
    }


    /*if (Filter.equals(terlaris)) {
        group.check(btn_terlaris.getId());
    } else if (Filter.equals(termurah)) {
        group.check(btn_termurah.getId());
    } else if (Filter.equals(termahal)) {
        group.check(btn_termahal.getId());
    }*/

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            TextSwich.setText("Buka");
            vibrator.vibrate(1000);
            Toast.makeText(activity, "Toko Buka", Toast.LENGTH_SHORT).show();
        } else {
            TextSwich.setText("Tutup");
            vibrator.vibrate(1000);
            Toast.makeText(activity, "Toko Tutup", Toast.LENGTH_SHORT).show();
        }
        if (isChecked){
            saveData();
        } else {
            saveData();
        }
    }

    /*@Override
    protected void onResume() {
        ViewSwitch();
        super.onResume();
    }*/
}