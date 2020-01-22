package com.tangria.spa.bookingku.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Activity.Main.MainActivity;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Guest_Comment extends AppCompatActivity {

    String staff = "";
    String suasana = "";
    String kebersihan = "";
    String teknik = "";
    String pelayanan = "";
    String mungkin = "";
    String adakah = "";
    String komen = "";
    Button btnSubmitGC;
    private SharedPreferences sharedPreferences;
    CheckBox cbbrosur, cbrekomendasi, cbspanduk, cbmedsos, cblainnya;
    String brosur = "";
    String rekomendasi = "";
    String spanduk = "";
    String medsos = "";
    String lainnya = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__comment);

        cbbrosur = findViewById(R.id.cbbrosur);
        cbrekomendasi = findViewById(R.id.cbrekomendasi);
        cbspanduk = findViewById(R.id.cbspanduk);
        cbmedsos = findViewById(R.id.cbmedsos);
        cblainnya = findViewById(R.id.cblainnya);

        String isBerulang = getIntent().getExtras().getString("isBerulang", "");

        if(isBerulang.equalsIgnoreCase("baru")){
            findViewById(R.id.divBaru).setVisibility(View.VISIBLE);
        } else if (isBerulang.equalsIgnoreCase("berulang")) {
            findViewById(R.id.divBaru).setVisibility(View.GONE);
        }

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int id = sharedPreferences.getInt("userid", 0);

        btnSubmitGC = findViewById(R.id.btnSubmitGC);
        btnSubmitGC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog loading = new ProgressDialog(Guest_Comment.this);
                loading.setMessage("Harap tunggu...");
                loading.show();
                AndroidNetworking.post(BookingClient.BASE_URL + "api/guest-comment")
                        .addBodyParameter("booking_id", getIntent().getExtras().getString("booking_id", ""))
                        .addBodyParameter("user_id", String.valueOf(id))
                        .addBodyParameter("gc_staff_pelayanan", staff)
                        .addBodyParameter("gc_suasana_spa", suasana)
                        .addBodyParameter("gc_kebersihan_kenyamanan", kebersihan)
                        .addBodyParameter("gc_teknik_perawatan", teknik)
                        .addBodyParameter("gc_pelayanan_terapis", pelayanan)
                        .addBodyParameter("gc_at_brosur", brosur)
                        .addBodyParameter("gc_at_rekomendasi", rekomendasi)
                        .addBodyParameter("gc_at_spanduk", spanduk)
                        .addBodyParameter("gc_at_media_sosial", medsos)
                        .addBodyParameter("gc_at_lain", lainnya)
                        .addBodyParameter("gc_mungkinkah_kembali", mungkin)
                        .addBodyParameter("gc_ada_perlu_diperbaiki", adakah)
                        .addBodyParameter("gc_komen_lain", komen)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                loading.dismiss();
                                try {
                                    String status = response.getString("STATUS");
                                    String message = response.getString("MESSAGE");
                                    if (status.equalsIgnoreCase("SUCCESS")) {
                                        Toast.makeText(Guest_Comment.this, message, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Guest_Comment.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Guest_Comment.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(Guest_Comment.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                loading.dismiss();
                                Toast.makeText(Guest_Comment.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    public void rbstaff(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.yastaff:
                if (checked) {
                    staff = "Y";
                    Log.d("staff", "rbstaff: " + staff);
                }
                break;
            case R.id.tidakstaff:
                if (checked) {
                    staff = "N";
                }
                break;

        }
    }

    public void rbsuasana(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yasuasana:
                if (checked) {
                    suasana = "Y";
                }
                break;
            case R.id.tidaksuasana:
                if (checked) {
                    suasana = "N";
                }
                break;
        }
    }

    public void rbkebersihan(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yakebersihan:
                if (checked) {
                    kebersihan = "Y";
                }
                break;
            case R.id.tidakkebersihan:
                if (checked) {
                    kebersihan = "N";
                }
                break;
        }
    }

    public void rbteknik(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yateknik:
                if (checked) {
                    teknik = "Y";
                }
                break;
            case R.id.tidakteknik:
                if (checked) {
                    teknik = "N";
                }
                break;
        }
    }

    public void rbpelayanan(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yapelayanan:
                if (checked) {
                    pelayanan = "Y";
                }
                break;
            case R.id.tidakpelayanan:
                if (checked) {
                    pelayanan = "N";
                }
                break;
        }
    }

    public void rbmungkin(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yamungkin:
                if (checked) {
                    mungkin = "Y";
                }
                break;
            case R.id.tidakmungkin:
                if (checked) {
                    mungkin = "N";
                }
                break;
        }
    }

    public void rbadakah(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.yaadakah:
                if (checked) {
                    adakah = "Y";
                }
                break;
            case R.id.tidakadakah:
                if (checked) {
                    adakah = "N";
                }
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.cbbrosur:
                if (checked) {
                    brosur = "Y";
                } else

                    break;
            case R.id.cbrekomendasi:
                if (checked) {
                    rekomendasi = "Y";
                } else

                    break;

            case R.id.cbspanduk:
                if (checked) {
                    spanduk = "Y";
                } else

                    break;
            case R.id.cbmedsos:
                if (checked) {
                    medsos = "Y";
                } else

                    break;
            case R.id.cblainnya:
                if (checked) {
                    lainnya = "Y";
                } else
                    break;
        }
    }
}