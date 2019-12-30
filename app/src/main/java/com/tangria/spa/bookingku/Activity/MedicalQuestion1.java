package com.tangria.spa.bookingku.Activity;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_question1);

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
                startActivity(intent);
            }
        });

    }

    public void onRbRematik(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbRematikYes :

                if (check){
                    rematik = "Yes";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("rematik", "Yes");
//                    editor.commit();

                }

                break;

            case R.id.rbRematikNo :

                if (check){
                    rematik = "No";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("rematik", "No");
//                    editor.commit();

                }

                break;

        }

    }

    public void onRbJantung(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbJantungYes :
                if (check){
                    jantung = "Yes";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("jantung", "Yes");
//                    editor.commit();

                }

                break;

            case R.id.rbJantungNo :
                if (check){
                    jantung = "No";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("jantung", "No");
//                    editor.commit();

                }

                break;

        }

    }

    public void onRbDarah(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbDarahYes :
                if (check){
                    tekananDarah = "Yes";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("darah", "Yes");
//                    editor.commit();

                }

                break;

            case R.id.rbDarahNo :
                if (check){
                    tekananDarah = "No";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("darah", "No");
//                    editor.commit();

                }

                break;

        }

    }

    public void onRbTulang(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbTulangYes :
                if (check){
                    tulangBelakang = "Yes";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("tulang", "Yes");
//                    editor.commit();

                }

                break;

            case R.id.rbTulangNo :
                if (check){
                    tulangBelakang = "No";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("tulang", "No");
//                    editor.commit();

                }

                break;

        }

    }

    public void onRbAsamurat(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbAsamuratYes :
                if (check){
                    asamUrat = "Yes";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("asamurat", "Yes");
//                    editor.commit();

                }

                break;

            case R.id.rbAsamuratNo :
                if (check){
                    asamUrat = "No";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("asamurat", "No");
//                    editor.commit();

                }

                break;

        }

    }

    public void onRbAsma(View view){

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()){

            case R.id.rbAsmaYes :
                if (check){
                    asma = "Yes";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("asma", "Yes");
//                    editor.commit();

                }

                break;

            case R.id.rbAsmaNo :
                if (check){
                    asma = "No";
//                    SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//                    editor.putString("asma", "No");
//                    editor.commit();

                }

                break;

        }

    }
}
