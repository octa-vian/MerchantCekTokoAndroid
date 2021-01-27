package com.amar.sample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amar.sample.R;

import java.text.DateFormat;
import java.util.Calendar;

public class ActivityChangeProfile extends AppCompatActivity {

    private Activity activity;
    private TimePickerDialog timePickerDialog;
    private LinearLayout ln_btn_tutup, ln_btn_buka;
    private TextView txt_hours, txt_menit, getTxt_hours_tutup, txt_menit_tutup;
    private ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 1);
        getSupportActionBar().hide();

        activity = this;

        ln_btn_buka = findViewById(R.id.ln_btn_picker_buka);
        ln_btn_tutup = findViewById(R.id.ln_btn_picker_tutup);
        txt_hours = findViewById(R.id.txt_hours);
        txt_menit = findViewById(R.id.txt_menit);
        getTxt_hours_tutup = findViewById(R.id.txt_hours_tutup);
        txt_menit_tutup = findViewById(R.id.txt_menit_tutup);
        btn_back = findViewById(R.id.img_back);

        ln_btn_tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String jam = "";
                        String menit = "";
                        if (selectedHour < 10){
                            jam = "0"+String.valueOf(selectedHour);
                        }
                        else {
                            jam = String.valueOf(selectedHour);
                            menit = String.valueOf(selectedMinute);
                        }

                        if (selectedMinute < 10){
                            menit = "0" + String.valueOf(selectedMinute);
                        } else {
                            menit = String.valueOf(selectedMinute);
                        }
                        getTxt_hours_tutup.setText(jam);
                        txt_menit_tutup.setText(menit);

                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        ln_btn_buka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String jam = "";
                        String menit = "";
                        if (selectedHour < 10){
                            jam = "0"+ String.valueOf(selectedHour);
                        }
                        else {
                            jam = String.valueOf(selectedHour);
                        }

                        if (selectedMinute < 10){
                            menit = "0" + String.valueOf(selectedMinute);
                        } else {
                            menit = String.valueOf(selectedMinute);
                        }

                        txt_hours.setText(String.valueOf(jam));
                        txt_menit.setText(String.valueOf(menit));

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }


}