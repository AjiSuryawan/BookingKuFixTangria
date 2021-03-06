package com.tangria.spa.bookingku.Activity.Detail;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.tangria.spa.bookingku.Activity.Booking.BookingActivity;
import com.tangria.spa.bookingku.Activity.Jenisproduk.Price;
import com.tangria.spa.bookingku.Activity.MedicalQuestion1;
import com.tangria.spa.bookingku.Fragment.Home.data_item_spa;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.scrolldown)
    ScrollView scrollView;
    @BindView(R.id.imageview_product)
    ImageView imageview_product;
    @BindView(R.id.bookNowBtn)
    Button bookNowBtn;
    @BindView(R.id.name_product)
    TextView name_product;
    @BindView(R.id.description_product)
    TextView description_product;
    @BindView(R.id.price_product)
    TextView price_product;
    @BindView(R.id.available_product)
    TextView available_product;
    @BindView(R.id.txtnote)
    TextView tvNote;
    @BindView(R.id.calculated_price_product)
    TextView tvCalculatedPriceProduct;
    @BindView(R.id.diskon_product)
    TextView tvDiskonProduct;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitleToolbar;

    Bundle extras;
    private boolean getAvailable;
    int idbarang = 0;
    private data_item_spa product;
    private SharedPreferences sharedPreferences;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTitleToolbar.setText("PRODUCT DETAIL");

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int role = sharedPreferences.getInt("role", 0);

        final Intent intent = getIntent();
        extras = intent.getExtras();
        if (extras != null) {
            product = extras.getParcelable("product");
            idbarang = product.getId();
            Price price = product.getPrice();
            Glide.with(getApplicationContext())
                    .load(product.getImage())
                    .into(imageview_product);
            name_product.setText(product.getName());
            Log.d("ordernamenya", "onClick: " + product.getName());
            description_product.setText(product.getDescription());
            price_product.setText("Rp " + price.getHarga());
            if (product.getPrice().getDiskon() != null) {
                float calculatedPrice = price.getHarga() - (price.getHarga() * price.getDiskon() / 100);
                tvCalculatedPriceProduct.setText("Rp " + calculatedPrice);
//                tvCalculatedPriceProduct.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                tvDiskonProduct.setText(String.valueOf(price.getDiskon()) + "%");
            } else {
                tvDiskonProduct.setVisibility(View.GONE);
                tvCalculatedPriceProduct.setVisibility(View.GONE);
            }
            tvNote.setText(product.getNote());
            getAvailable = product.getAvailable();
            available_product.setText(String.valueOf(getAvailable));
            if (!getAvailable) {
                bookNowBtn.setEnabled(false);
                bookNowBtn.setText("NOT AVAILABLE");
                bookNowBtn.setTextColor(getResources().getColor(android.R.color.holo_red_light));
//                bookNowBtn.setBackgroundColor(getResources().getColor(R.color.not_available_color));
            }
        }
        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role == 2) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.cs_mode, null);
                    final EditText mNama = (EditText) mView.findViewById(R.id.etNama);
                    final EditText mAlamat = (EditText) mView.findViewById(R.id.etAlamat);
                    final EditText mNoTelp = (EditText) mView.findViewById(R.id.etNoTelp);
                    final EditText mPekerjaan = (EditText) mView.findViewById(R.id.etJob);
                    Button mLanjut = mView.findViewById(R.id.btnLanjut);

                    mLanjut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mNama.getText().toString().isEmpty() || mAlamat.getText().toString().isEmpty() || mNoTelp.getText().toString().isEmpty() || mPekerjaan.getText().toString().isEmpty()) {
                                Toast.makeText(DetailActivity.this, "Isi semua form terlebih dahulu", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent in = new Intent(getApplicationContext(), MedicalQuestion1.class);
                            in.putExtra("orderid", idbarang);
                            in.putExtra("order_nama", product.getName());
                            in.putExtra("name", mNama.getText().toString());
                            in.putExtra("address", mAlamat.getText().toString());
                            in.putExtra("phone", mNoTelp.getText().toString());
                            in.putExtra("job", mPekerjaan.getText().toString());
                            Log.d("ordernamenya", "onClick: " + product.getName());
                            startActivity(in);
                        }
                    });

                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();
                } else {
                    int userId = sharedPreferences.getInt("userid", 0);
                    final ProgressDialog loading = new ProgressDialog(DetailActivity.this);
                    loading.setMessage("Harap tunggu...");
                    loading.show();
                    AndroidNetworking.get(BookingClient.BASE_URL + "api/user/type")
                            .addQueryParameter("user_id", String.valueOf(userId))
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    String status = response.optString("STATUS", "");
                                    String message = response.optString("MESSAGE", "");
                                    if (!status.equalsIgnoreCase("SUCCESS")) {
                                        Toast.makeText(DetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                    } else {
                                        JSONObject payload = response.optJSONObject("PAYLOAD");
                                        String type = payload.optString("type", "");
                                        if (type.equalsIgnoreCase("baru")) {
                                            Intent in = new Intent(getApplicationContext(), MedicalQuestion1.class);
                                            in.putExtra("orderid", idbarang);
                                            in.putExtra("order_nama", product.getName());
                                            Log.d("ordernamenya", "onClick: " + product.getName());
                                            startActivity(in);
                                        } else if (type.equalsIgnoreCase("berulang")) {
                                            Intent bookingIntent = new Intent(DetailActivity.this, BookingActivity.class);
                                            bookingIntent.putExtra("orderid", idbarang);
                                            bookingIntent.putExtra("order_nama", product.getName());
                                            startActivity(bookingIntent);
                                        }
                                    }
                                    loading.dismiss();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    loading.dismiss();
                                    Toast.makeText(DetailActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
