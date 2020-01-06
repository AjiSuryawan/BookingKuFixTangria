package com.tangria.spa.bookingku.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewAnimator;

import com.tangria.spa.bookingku.R;

public class Guest_Comment extends AppCompatActivity {

    String staff;
    String suasana;
    String kebersihan;
    String teknik;
    String pelayanan;
    String mungkin;
    String adakah;

    CheckBox cbbrosur, cbrekomendasi, cbspanduk ,cbmedsos ,cblainnya;
    String brosur, rekomendasi, spanduk , medsos , lainnya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__comment);

        cbbrosur = findViewById(R.id.cbbrosur);
        cbrekomendasi = findViewById(R.id.cbrekomendasi);
        cbspanduk = findViewById(R.id.cbspanduk);
        cbmedsos = findViewById(R.id.cbmedsos);
        cblainnya = findViewById(R.id.cblainnya);
    }

    public void rbstaff(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yastaff:
                if (checked){
                    staff = "true";
                    Log.d("staff", "rbstaff: "+staff);
                }
                    break;
            case R.id.tidakstaff:
                if (checked){
                    staff = "false";
                }
                    break;

        }
    }

        public void rbsuasana(View view) {

            boolean checked = ((RadioButton) view).isChecked();

            switch (view.getId()) {
                case R.id.yasuasana:
                    if (checked){
                        suasana = "true";
                    }
                        break;
                case R.id.tidaksuasana:
                    if (checked){
                        suasana = "false";
                    }
                        break;
            }
    }

    public void rbkebersihan(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yakebersihan:
                if (checked){
                    kebersihan = "true";
                }
                    break;
            case R.id.tidakkebersihan:
                if (checked){
                    kebersihan = "false";
                }
                    break;
        }
    }

    public void rbteknik(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yateknik:
                if (checked){
                    teknik = "true";
                }
                    break;
            case R.id.tidakteknik:
                if (checked){
                    teknik = "false";
                }
                    break;
        }
    }

    public void rbpelayanan(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yapelayanan:
                if (checked){
                    pelayanan = "true";
                }
                    break;
            case R.id.tidakpelayanan:
                if (checked){
                    pelayanan = "false";
                }
                    break;
        }
    }

    public void rbmungkin(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yamungkin:
                if (checked){
                    mungkin = "true";
                }
                break;
            case R.id.tidakmungkin:
                if (checked){
                    mungkin = "false";
                }
                break;
        }
    }

    public void rbadakah(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yaadakah:
                if (checked){
                    adakah = "true";
                }
                break;
            case R.id.tidakadakah:
                if (checked){
                    adakah = "false";
                }
                break;
        }
    }
}