package com.tangria.spa.bookingku.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.tangria.spa.bookingku.R;

public class MedicalQuestion1 extends AppCompatActivity {

    Button btnNext;
    String rematik;
    String jantung;
    String tekananDarah;
    String tulangBelakang;
    String asamUrat;
    String asma;
    String orderid;
    String order_nama;
    String name;
    String address;
    String phone;
    String job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_question1);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderid = String.valueOf(bundle.getInt("orderid"));
            order_nama = bundle.getString("order_nama");
            name = bundle.getString("name", "");
            address = bundle.getString("address", "");
            phone = bundle.getString("phone", "");
            job = bundle.getString("job", "");
        }

        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MedicalQuestion1.this, MedicalQuestion2.class);
                intent.putExtra("rematik",rematik);
                intent.putExtra("jantung",jantung);
                intent.putExtra("tekananDarah",tekananDarah);
                intent.putExtra("tulangBelakang",tulangBelakang);
                intent.putExtra("asamUrat",asamUrat);
                intent.putExtra("asma",asma);
                intent.putExtra("orderid", orderid);
                intent.putExtra("order_nama", order_nama);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);
                intent.putExtra("job", job);
                startActivity(intent);
            }
        });

    }

    public void onRbRematik(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbRematikYes :

                if (check){
                    rematik = "true";
                }

                break;

            case R.id.rbRematikNo :

                if (check){
                    rematik = "false";
                }

                break;

        }

    }

    public void onRbJantung(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbJantungYes :
                if (check){
                    jantung = "true";
                }

                break;

            case R.id.rbJantungNo :
                if (check){
                    jantung = "false";
                }

                break;

        }

    }

    public void onRbDarah(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbDarahYes :
                if (check){
                    tekananDarah = "true";
                }

                break;

            case R.id.rbDarahNo :
                if (check){
                    tekananDarah = "false";
               }

                break;

        }

    }

    public void onRbTulang(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbTulangYes :
                if (check){
                    tulangBelakang = "true";
               }

                break;

            case R.id.rbTulangNo :
                if (check){
                    tulangBelakang = "false";
          }

                break;

        }

    }

    public void onRbAsamurat(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbAsamuratYes :
                if (check){
                    asamUrat = "true";
             }

                break;

            case R.id.rbAsamuratNo :
                if (check){
                    asamUrat = "false";
                }

                break;

        }

    }

    public void onRbAsma(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbAsmaYes :
                if (check){
                    asma = "true";
                }

                break;

            case R.id.rbAsmaNo :
                if (check){
                    asma = "false";
              }

                break;

        }

    }
}
