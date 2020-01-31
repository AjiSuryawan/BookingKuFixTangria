package com.tangria.spa.bookingku.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import org.json.JSONObject;

public class MedicalQuestion1 extends AppCompatActivity {

    Button btnNext;
    String rematik = "";
    String jantung = "";
    String tekananDarah = "";
    String tulangBelakang = "";
    String asamUrat = "";
    String asma = "";
    String hamil = "";
    String datangBulan = "";
    String alatBantu = "";
    String operasi = "";
    String makan = "";
    String menghindariBagian = "";
    int orderid;
    String order_nama = "";
    String name = "";
    String address = "";
    String phone = "";
    String job = "";
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_question1);

        Toolbar toolbar = findViewById(R.id.toolbarMQ1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Medical Questioner Tangria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderid = bundle.getInt("orderid");
            order_nama = bundle.getString("order_nama");
            name = bundle.getString("name", "");
            address = bundle.getString("address", "");
            phone = bundle.getString("phone", "");
            job = bundle.getString("job", "");
            isEdit = bundle.getBoolean("isEdit", false);
        }

        if (isEdit) {
            final ProgressDialog loading = new ProgressDialog(this);
            loading.setMessage("Harap tunggu...");
            loading.show();
            AndroidNetworking.post(BookingClient.BASE_URL + "api/user")
                    .addBodyParameter("user_id", String.valueOf(getSharedPreferences("login", MODE_PRIVATE).getInt("userid", 0)))
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            loading.dismiss();
                            rematik = response.optString("mq_rematik");
                            if (rematik.equalsIgnoreCase("true")) {
                                ((RadioButton) findViewById(R.id.rbRematikYes)).setChecked(true);
                            } else if (rematik.equalsIgnoreCase("false")) {
                                ((RadioButton) findViewById(R.id.rbRematikNo)).setChecked(true);
                            }
                            jantung = response.optString("mq_jantung");
                            if (jantung.equalsIgnoreCase("true")) {
                                ((RadioButton) findViewById(R.id.rbJantungYes)).setChecked(true);
                            } else if (jantung.equalsIgnoreCase("false")) {
                                ((RadioButton) findViewById(R.id.rbJantungNo)).setChecked(true);
                            }
                            tekananDarah = response.optString("mq_tekanan_darah");
                            if (tekananDarah.equalsIgnoreCase("true")) {
                                ((RadioButton) findViewById(R.id.rbDarahYes)).setChecked(true);
                            } else if (tekananDarah.equalsIgnoreCase("false")) {
                                ((RadioButton) findViewById(R.id.rbDarahNo)).setChecked(true);
                            }
                            tulangBelakang = response.optString("mq_tulang_belakang");
                            if (tulangBelakang.equalsIgnoreCase("true")) {
                                ((RadioButton) findViewById(R.id.rbTulangYes)).setChecked(true);
                            } else if (tulangBelakang.equalsIgnoreCase("false")) {
                                ((RadioButton) findViewById(R.id.rbTulangNo)).setChecked(true);
                            }
                            asamUrat = response.optString("mq_asamurat");
                            if (asamUrat.equalsIgnoreCase("true")) {
                                ((RadioButton) findViewById(R.id.rbAsamuratYes)).setChecked(true);
                            } else if (asamUrat.equalsIgnoreCase("false")) {
                                ((RadioButton) findViewById(R.id.rbAsamuratNo)).setChecked(true);
                            }
                            asma = response.optString("mq_asma");
                            if (asma.equalsIgnoreCase("true")) {
                                ((RadioButton) findViewById(R.id.rbAsmaYes)).setChecked(true);
                            } else if (asma.equalsIgnoreCase("false")) {
                                ((RadioButton) findViewById(R.id.rbAsmaNo)).setChecked(true);
                            }
                            hamil = response.optString("mq_hamil");
                            datangBulan = response.optString("mq_datang_bulan");
                            alatBantu = response.optString("mq_alat_bantu");
                            operasi = response.optString("mq_operasi");
                            makan = response.optString("mq_makan");
                            menghindariBagian = response.optString("mq_menghindari_bagian");
                        }

                        @Override
                        public void onError(ANError anError) {
                            loading.dismiss();
                            Toast.makeText(MedicalQuestion1.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rematik.isEmpty() || jantung.isEmpty() || tekananDarah.isEmpty() || asamUrat.isEmpty() || asma.isEmpty()) {
                    Toast.makeText(MedicalQuestion1.this, "Isi semua form terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MedicalQuestion1.this, MedicalQuestion2.class);
                intent.putExtra("rematik", rematik);
                intent.putExtra("jantung", jantung);
                intent.putExtra("tekananDarah", tekananDarah);
                intent.putExtra("tulangBelakang", tulangBelakang);
                intent.putExtra("asamUrat", asamUrat);
                intent.putExtra("asma", asma);
                intent.putExtra("hamil", hamil);
                intent.putExtra("datangBulan", datangBulan);
                intent.putExtra("alatBantu", alatBantu);
                intent.putExtra("operasi", operasi);
                intent.putExtra("makan", makan);
                intent.putExtra("menghindariBagian", menghindariBagian);
                intent.putExtra("orderid", orderid);
                intent.putExtra("order_nama", order_nama);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);
                intent.putExtra("job", job);
                intent.putExtra("isEdit", isEdit);
                startActivity(intent);
            }
        });

    }

    public void onRbRematik(View view) {
        Boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbRematikYes:
                if (check) {
                    rematik = "true";
                }
                break;
            case R.id.rbRematikNo:
                if (check) {
                    rematik = "false";
                }
                break;
        }

    }

    public void onRbJantung(View view) {
        Boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbJantungYes:
                if (check) {
                    jantung = "true";
                }
                break;
            case R.id.rbJantungNo:
                if (check) {
                    jantung = "false";
                }
                break;
        }
    }

    public void onRbDarah(View view) {
        Boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbDarahYes:
                if (check) {
                    tekananDarah = "true";
                }
                break;
            case R.id.rbDarahNo:
                if (check) {
                    tekananDarah = "false";
                }
                break;
        }
    }

    public void onRbTulang(View view) {
        Boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbTulangYes:
                if (check) {
                    tulangBelakang = "true";
                }

                break;
            case R.id.rbTulangNo:
                if (check) {
                    tulangBelakang = "false";
                }
                break;
        }
    }

    public void onRbAsamurat(View view) {
        Boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbAsamuratYes:
                if (check) {
                    asamUrat = "true";
                }
                break;
            case R.id.rbAsamuratNo:
                if (check) {
                    asamUrat = "false";
                }
                break;
        }
    }

    public void onRbAsma(View view) {
        Boolean check = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rbAsmaYes:
                if (check) {
                    asma = "true";
                }
                break;
            case R.id.rbAsmaNo:
                if (check) {
                    asma = "false";
                }
                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
