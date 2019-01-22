package com.example.guru.bookingku.Activity.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.example.guru.bookingku.Activity.Booking.BookingActivity;
import com.example.guru.bookingku.Activity.Jenisproduk.Price;
import com.example.guru.bookingku.Fragment.Home.data_item_spa;
import com.example.guru.bookingku.R;

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
    @BindView(R.id.tv_title) TextView tvTitleToolbar;

    Bundle extras;
    private boolean getAvailable;
    int idbarang=0;
    private data_item_spa product;

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

        Intent intent = getIntent();
        extras = intent.getExtras();
        if (extras != null) {
            product = extras.getParcelable("product");
            idbarang = product.getId();
            Price price = product.getPrice();
            Glide.with(getApplicationContext())
                    .load(product.getImage())
                    .into(imageview_product);
            name_product.setText(product.getName());
            description_product.setText(product.getDescription());
            price_product.setText("Rp " + price.getHarga());
            if(product.getPrice().getDiskon() != null) {
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
            if(!getAvailable){
               bookNowBtn.setEnabled(false);
                bookNowBtn.setText("NOT AVAILABLE");
                bookNowBtn.setTextColor(getResources().getColor(android.R.color.black));
                bookNowBtn.setBackgroundColor(getResources().getColor(R.color.not_available_color));
            }
        }
        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(getApplicationContext(), BookingActivity.class);
                in.putExtra("orderid",idbarang);
                in.putExtra("order_nama", product.getName());
                startActivity(in);
            }
        });
    }
}
