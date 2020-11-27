package com.tangria.spa.bookingku.Activity.Promo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tangria.spa.bookingku.Activity.Notification.Promo.PromoModel;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvCreartAt)
    TextView tvCreartAt;

    @BindView(R.id.tvTitlePromo)
    TextView tvTitlePromo;

    @BindView(R.id.tvDescPromo)
    TextView tvDescPromo;

    @BindView(R.id.ivPromo)
    ImageView ivPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final PromoModel dataCatatan = getIntent().getExtras().getParcelable("EXTRA_PROMO");
        if(dataCatatan != null){
            tvTitlePromo.setText(dataCatatan.getTitle());
            tvDescPromo.setText(dataCatatan.getContent());
            tvCreartAt.setText(""+dataCatatan.getCreated_at());
            Glide.with(PromoDetailActivity.this).load(BookingClient.BASE_URL +dataCatatan.getImage()).into(ivPromo);
        }

//        Glide.with(this)
//                .load(this.getResources().getDrawable(R.drawable.ic_medal_platinum))
//                .into(ivPromo);

        //detailPromo
//        tvDescPromo.setText(getResources().getString(R.string.member_start) + getResources().getString(R.string.member_5) + getResources().getString(R.string.member_master));
    }
}