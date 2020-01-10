package com.tangria.spa.bookingku.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Activity.Booking.BookingActivity;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Guest_Comment extends AppCompatActivity {

    String staff;
    String suasana;
    String kebersihan;
    String teknik;
    String pelayanan;
    String mungkin;
    String adakah;
    String komen;

    Button btnSubmitGC;


    private SharedPreferences sharedPreferences;

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

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int id = sharedPreferences.getInt("userid", 0);


        btnSubmitGC = findViewById(R.id.btnSubmitGC);
        btnSubmitGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AndroidNetworking.post(BookingClient.BASE_URL+"api/guest-comment")
                        .addBodyParameter("id" ,String.valueOf(id))
                        .addBodyParameter("gc_staff_pelayanan" , staff)
                        .addBodyParameter("gc_suasana_spa" , suasana)
                        .addBodyParameter("gc_kebersihan_kenyamanan" , kebersihan)
                        .addBodyParameter("gc_teknik_perawatan" , teknik)
                        .addBodyParameter("gc_pelayanan_terapis" , pelayanan)
                        .addBodyParameter("gc_at_brosur" , brosur)
                        .addBodyParameter("gc_at_rekomendasi" , rekomendasi)
                        .addBodyParameter("gc_at_spanduk" , spanduk)
                        .addBodyParameter("gc_at_media_sosial" , medsos)
                        .addBodyParameter("gc_at_lain" , lainnya)
                        .addBodyParameter("gc_mungkinkah_kembali" , mungkin)
                        .addBodyParameter("gc_ada_perlu_diperbaiki" , adakah)
                        .addBodyParameter("gc_komen_lain" , komen)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String status =response.getString("STATUS");
                                    finish();
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(Guest_Comment.this, "eror", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


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

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.cbbrosur:
                if (checked) {
                    brosur = "true";
                }else

                break;
            case R.id.cbrekomendasi:
                if (checked){
                    rekomendasi = "true";
                }else

                break;

            case R.id.cbspanduk:
                if (checked){
                    spanduk = "true";
                }else

                    break;
            case R.id.cbmedsos:
                if (checked){
                    medsos = "true";
                }else

                    break;
                case R.id.cblainnya:
                if (checked){
                    lainnya = "true";
                }else
                    break;
        }
    }
}