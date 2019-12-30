package com.tangria.spa.bookingku.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MedicalQuestion2 extends AppCompatActivity {

    Button mSubmitbtn;

    String rematik;
    String jantung;
    String tekananDarah;
    String tulangBelakang;
    String asamUrat;
    String asma;
    String hamil;
    String datangBulan;
    String alatBantu;
    String operasi;
    String makan;
    String menghindariBagian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_question2);

        final int id = 634;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            rematik = bundle.getString("rematik");
            jantung = bundle.getString("jantung");
            tekananDarah = bundle.getString("tekananDarah");
            tulangBelakang = bundle.getString("tulangBelakang");
            asamUrat = bundle.getString("asamUrat");
            asma = bundle.getString("asma");
        }


        mSubmitbtn = findViewById(R.id.btnSubmit);
        mSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AndroidNetworking.post(BookingClient.BASE_URL+"api/user/update-profile")
                        .addBodyParameter("id" ,String.valueOf(id))
                        .addBodyParameter("mq_rematik" , rematik)
                        .addBodyParameter("mq_jantung" , jantung)
                        .addBodyParameter("mq_tekanan_darah" , tekananDarah)
                        .addBodyParameter("mq_tulang_belakang" , tulangBelakang)
                        .addBodyParameter("mq_asamurat" , asamUrat)
                        .addBodyParameter("mq_asma" , asma)
                        .addBodyParameter("mq_hamil" , hamil)
                        .addBodyParameter("mq_datang_bulan" , datangBulan)
                        .addBodyParameter("mq_alat_bantu" , alatBantu)
                        .addBodyParameter("mq_operasi" , operasi)
                        .addBodyParameter("mq_makan" , makan)
                        .addBodyParameter("mq_menghindari_bagian" , menghindariBagian)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String status =response.getString("success");
                                    if(status.equalsIgnoreCase("true")){
                                        String message = response.getString("message");
                                        Toast.makeText(MedicalQuestion2.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(MedicalQuestion2.this, "Ntar lah", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        });

    }
}
