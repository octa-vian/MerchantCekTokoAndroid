package com.amar.sample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amar.sample.MainActivityNavigation;
import com.amar.sample.R;
import com.amar.sample.util.APIvolley;
import com.amar.sample.util.AppSharedPreferences;
import com.amar.sample.util.Server;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private EditText Username, Password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AppSharedPreferences.isLoggedIn(this)){
            startActivity(new Intent(this, MainActivityNew.class));
            finish();
        }

        Username = findViewById(R.id.username);
        Password =findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogin();
            }
        });



    }

    private void getLogin() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",Username.getText().toString());
            jsonObject.put("password",Password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(LoginActivity.this, jsonObject, "POST", Server.URL_LOGIN, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject objek = new JSONObject(result);

                    String message = objek.getJSONObject("metadata").getString("message");
                    String status = objek.getJSONObject("metadata").getString("status");

                    if (Integer.parseInt(status)==200){
                        AppSharedPreferences.Login(
                                LoginActivity.this,
                                        objek.getJSONObject("response").getString("uid"),
                                        objek.getJSONObject("response").getString("token"),
                                        objek.getJSONObject("response").getString("nama_pemilik"));
                        Intent intent = new Intent(LoginActivity.this, MainActivityNew.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else{
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
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
}