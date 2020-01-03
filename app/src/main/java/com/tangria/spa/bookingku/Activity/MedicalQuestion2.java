package com.tangria.spa.bookingku.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Activity.Booking.BookingActivity;
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
    String orderid;
    String order_nama;
    String name;
    String address;
    String phone;
    String job;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_question2);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int id = sharedPreferences.getInt("userid", 0);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            rematik = bundle.getString("rematik");
            jantung = bundle.getString("jantung");
            tekananDarah = bundle.getString("tekananDarah");
            tulangBelakang = bundle.getString("tulangBelakang");
            asamUrat = bundle.getString("asamUrat");
            asma = bundle.getString("asma");
            orderid = bundle.getString("orderid");
            order_nama = bundle.getString("order_nama");
            name = bundle.getString("name");
            address = bundle.getString("address");
            phone = bundle.getString("phone");
            job = bundle.getString("job");
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
                        .addBodyParameter("cst_name" , name)
                        .addBodyParameter("cst_address" , address)
                        .addBodyParameter("cst_phone" , phone)
                        .addBodyParameter("cst_job" , job)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String status =response.getString("STATUS");
                                    if(status.equalsIgnoreCase("SUCCESS")){
                                        String message = response.getString("message");
                                        Toast.makeText(MedicalQuestion2.this, message, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MedicalQuestion2.this, BookingActivity.class);
                                        intent.putExtra("orderid", orderid);
                                        intent.putExtra("order_nama", order_nama);
                                        startActivity(intent);
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
                                Toast.makeText(MedicalQuestion2.this, "eror", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public void onRbHamil(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbHamilYes :
                if (check){
                    hamil = "true";
                }

                break;

            case R.id.rbHamilNo :
                if (check){
                    hamil = "false";
                }

                break;

        }

    }

    public void onRbDatangBulan(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbDatangBulanYes :
                if (check){
                    datangBulan = "true";
                }

                break;

            case R.id.rbDatangBulanNo :
                if (check){
                    datangBulan = "false";
                }

                break;

        }

    }

    public void onRbAlat(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbAlatYes :
                if (check){
                    alatBantu = "true";
                }

                break;

            case R.id.rbAlatNo :
                if (check){
                    alatBantu = "false";
                }

                break;

        }

    }

    public void onRbOperasi(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbOperasiYes :
                if (check){
                    operasi = "true";
                }

                break;

            case R.id.rbOperasiNo :
                if (check){
                    operasi = "false";
                }

                break;

        }

    }

    public void onRbMakan(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbMakanYes :
                if (check){
                    makan = "true";
                }

                break;

            case R.id.rbMakanNo :
                if (check){
                    makan = "false";
                }

                break;

        }

    }

    public void onRbTherapis(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbTherapisYes :
                if (check){
                    menghindariBagian = "true";
                }

                break;

            case R.id.rbTherapisNo :
                if (check){
                    menghindariBagian = "false";
                }

                break;

        }

    }
}
