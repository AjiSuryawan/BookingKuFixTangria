package com.tangria.spa.bookingku.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.tangria.spa.bookingku.Activity.Booking.BookingActivity;
import com.tangria.spa.bookingku.Activity.Main.MainActivity;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;
import com.tangria.spa.bookingku.Util.AlarmConfig;

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
    String hamil = "";
    String datangBulan = "";
    String alatBantu = "";
    String operasi = "";
    String makan = "";
    String menghindariBagian = "";
    int orderid;
    String order_nama;
    String name;
    String address;
    String phone;
    String job;
    boolean isEdit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_question2);

        Toolbar toolbar = findViewById(R.id.toolbarMQ2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Medical Questioner Tangria");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int id = sharedPreferences.getInt("userid", 0);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isEdit = bundle.getBoolean("isEdit", false);
            rematik = bundle.getString("rematik");
            jantung = bundle.getString("jantung");
            tekananDarah = bundle.getString("tekananDarah");
            tulangBelakang = bundle.getString("tulangBelakang");
            asamUrat = bundle.getString("asamUrat");
            asma = bundle.getString("asma");
            asamUrat = bundle.getString("asamUrat");
            hamil = bundle.getString("hamil", "");
            if (hamil.equalsIgnoreCase("true")) {
                ((RadioButton) findViewById(R.id.rbHamilYes)).setChecked(true);
            } else if (hamil.equalsIgnoreCase("false")) {
                ((RadioButton) findViewById(R.id.rbHamilNo)).setChecked(true);
            }
            datangBulan = bundle.getString("datangBulan", "");
            if (datangBulan.equalsIgnoreCase("true")) {
                ((RadioButton) findViewById(R.id.rbDatangBulanYes)).setChecked(true);
            } else if (datangBulan.equalsIgnoreCase("false")) {
                ((RadioButton) findViewById(R.id.rbDatangBulanNo)).setChecked(true);
            }
            alatBantu = bundle.getString("alatBantu", "");
            if (alatBantu.equalsIgnoreCase("true")) {
                ((RadioButton) findViewById(R.id.rbAlatYes)).setChecked(true);
            } else if (alatBantu.equalsIgnoreCase("false")) {
                ((RadioButton) findViewById(R.id.rbAlatNo)).setChecked(true);
            }
            operasi = bundle.getString("operasi", "");
            if (operasi.equalsIgnoreCase("true")) {
                ((RadioButton) findViewById(R.id.rbOperasiYes)).setChecked(true);
            } else if (operasi.equalsIgnoreCase("false")) {
                ((RadioButton) findViewById(R.id.rbOperasiNo)).setChecked(true);
            }
            makan = bundle.getString("makan", "");
            if (makan.equalsIgnoreCase("true")) {
                ((RadioButton) findViewById(R.id.rbMakanYes)).setChecked(true);
            } else if (makan.equalsIgnoreCase("false")) {
                ((RadioButton) findViewById(R.id.rbMakanNo)).setChecked(true);
            }
            menghindariBagian = bundle.getString("menghindariBagian", "");
            if (menghindariBagian.equalsIgnoreCase("true")) {
                ((RadioButton) findViewById(R.id.rbTherapisYes)).setChecked(true);
            } else if (menghindariBagian.equalsIgnoreCase("false")) {
                ((RadioButton) findViewById(R.id.rbTherapisNo)).setChecked(true);
            }
            orderid = bundle.getInt("orderid");
            order_nama = bundle.getString("order_nama");
            name = bundle.getString("name", "");
            address = bundle.getString("address", "");
            phone = bundle.getString("phone", "");
            job = bundle.getString("job", "");
        }


        mSubmitbtn = findViewById(R.id.btnSubmit);
        mSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (makan.isEmpty() || alatBantu.isEmpty() || hamil.isEmpty() || datangBulan.isEmpty() || operasi.isEmpty() || menghindariBagian.isEmpty()) {
                    Toast.makeText(MedicalQuestion2.this, "Isi semua form terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(MedicalQuestion2.this);
                progressDialog.setMessage("Harap tunggu...");

//                Log.e("", String.valueOf(id) );
                progressDialog.setCancelable(false);


                final int role = sharedPreferences.getInt("role", 0);
                if (role == 2) {
                    Intent bookingIntent = new Intent(MedicalQuestion2.this, BookingActivity.class);
                    bookingIntent
                            .putExtra("mq_rematik", rematik)
                            .putExtra("mq_jantung", jantung)
                            .putExtra("mq_tekanan_darah", tekananDarah)
                            .putExtra("mq_tulang_belakang", tulangBelakang)
                            .putExtra("mq_asamurat", asamUrat)
                            .putExtra("mq_asma", asma)
                            .putExtra("mq_hamil", hamil)
                            .putExtra("mq_datang_bulan", datangBulan)
                            .putExtra("mq_alat_bantu", alatBantu)
                            .putExtra("mq_operasi", operasi)
                            .putExtra("mq_makan", makan)
                            .putExtra("mq_menghindari_bagian", menghindariBagian)
                            .putExtra("cst_name", name)
                            .putExtra("cst_address", address)
                            .putExtra("cst_phone", phone)
                            .putExtra("cst_job", job)
                            .putExtra("orderid", orderid)
                            .putExtra("order_nama", order_nama);
                    bookingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(bookingIntent);
                } else {
                    progressDialog.show();
                    AndroidNetworking.post(BookingClient.BASE_URL + "api/user/update-profile")
                            .addBodyParameter("id", String.valueOf(id))
                            .addBodyParameter("mq_rematik", rematik)
                            .addBodyParameter("mq_jantung", jantung)
                            .addBodyParameter("mq_tekanan_darah", tekananDarah)
                            .addBodyParameter("mq_tulang_belakang", tulangBelakang)
                            .addBodyParameter("mq_asamurat", asamUrat)
                            .addBodyParameter("mq_asma", asma)
                            .addBodyParameter("mq_hamil", hamil)
                            .addBodyParameter("mq_datang_bulan", datangBulan)
                            .addBodyParameter("mq_alat_bantu", alatBantu)
                            .addBodyParameter("mq_operasi", operasi)
                            .addBodyParameter("mq_makan", makan)
                            .addBodyParameter("mq_menghindari_bagian", menghindariBagian)
                            .addBodyParameter("cst_name", name)
                            .addBodyParameter("cst_address", address)
                            .addBodyParameter("cst_phone", phone)
                            .addBodyParameter("cst_job", job)
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        String status = response.getString("STATUS");
                                        String message = response.getString("MESSAGE");
                                        if (status.equalsIgnoreCase("SUCCESS")) {
                                            AlarmConfig alarmConfig = new AlarmConfig(MedicalQuestion2.this);
                                            alarmConfig.setMonthNotification();
                                            Toast.makeText(MedicalQuestion2.this, "Medical question berhasil dikirim", Toast.LENGTH_SHORT).show();
                                            if (isEdit) {
                                                Intent intent = new Intent(MedicalQuestion2.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(MedicalQuestion2.this, BookingActivity.class);
                                                intent.putExtra("orderid", orderid);
                                                intent.putExtra("order_nama", order_nama);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }

                                        } else {
                                            Toast.makeText(MedicalQuestion2.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        e.getLocalizedMessage();
                                    } finally {
                                        progressDialog.dismiss();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    progressDialog.dismiss();
                                    Toast.makeText(MedicalQuestion2.this, "eror", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
    }

    public void onRbHamil(View view) {

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rbHamilYes:
                if (check) {
                    hamil = "true";
                }

                break;

            case R.id.rbHamilNo:
                if (check) {
                    hamil = "false";
                }

                break;

        }

    }

    public void onRbDatangBulan(View view) {

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rbDatangBulanYes:
                if (check) {
                    datangBulan = "true";
                }

                break;

            case R.id.rbDatangBulanNo:
                if (check) {
                    datangBulan = "false";
                }

                break;

        }

    }

    public void onRbAlat(View view) {

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rbAlatYes:
                if (check) {
                    alatBantu = "true";
                }

                break;

            case R.id.rbAlatNo:
                if (check) {
                    alatBantu = "false";
                }

                break;

        }

    }

    public void onRbOperasi(View view) {

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rbOperasiYes:
                if (check) {
                    operasi = "true";
                }

                break;

            case R.id.rbOperasiNo:
                if (check) {
                    operasi = "false";
                }

                break;

        }

    }

    public void onRbMakan(View view) {

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rbMakanYes:
                if (check) {
                    makan = "true";
                }

                break;

            case R.id.rbMakanNo:
                if (check) {
                    makan = "false";
                }

                break;

        }

    }

    public void onRbTherapis(View view) {

        Boolean check = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.rbTherapisYes:
                if (check) {
                    menghindariBagian = "true";
                }

                break;

            case R.id.rbTherapisNo:
                if (check) {
                    menghindariBagian = "false";
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
