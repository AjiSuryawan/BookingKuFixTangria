package com.tangria.spa.bookingku.Activity.Jenisproduk;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tangria.spa.bookingku.Fragment.Home.adapter_list_item_spa;
import com.tangria.spa.bookingku.Fragment.Home.data_item_spa;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.Network.BookingService;
import com.tangria.spa.bookingku.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Massage extends AppCompatActivity {
    public List<data_item_spa> arrayList = new ArrayList<>();
    String data;
    Call<List<data_item_spa>> call;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private adapter_list_item_spa adapter;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private String title;
    RelativeLayout layoutkosong;


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
        setContentView(R.layout.fragment_home);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getString("category");
        }
layoutkosong=(RelativeLayout)findViewById(R.id.layoutkosong);
        toolbar = findViewById(R.id.toolbar);
        tvTitleToolbar = findViewById(R.id.tv_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mShimmerViewContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycle_view_list);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load_data();
            }
        });

        load_data();
    }

    private void load_data() {
        recyclerView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        BookingService bookingService = BookingClient.getRetrofit().create(BookingService.class);
        Log.e("Massage", "load_data: " + data);
        if (data.equalsIgnoreCase("package_treatment")) {
            call = bookingService.getPackageTreatment();
            title = "Package Treatment";
            tvTitleToolbar.setText(title);
        } else if (data.equalsIgnoreCase("ala_carte_treatment")) {
            call = bookingService.getAlaCarteTreatment();
            title = "Ala Carte Treatment";
            tvTitleToolbar.setText(title);
        }
        call.enqueue(new Callback<List<data_item_spa>>() {
            @Override
            public void onResponse(Call<List<data_item_spa>> call, Response<List<data_item_spa>> response) {

                try {
                    arrayList.clear();
                    arrayList.addAll(response.body());
                    if (arrayList.size()==0 || arrayList.isEmpty() || arrayList ==null){
                        Log.d("masuk1", "onResponse: ");

                        layoutkosong.setVisibility(View.VISIBLE);
                        layoutkosong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                layoutkosong.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"makan",Toast.LENGTH_LONG).show();
                                load_data();
                            }
                        });
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }else{
                        recyclerView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new adapter_list_item_spa(getApplicationContext(), arrayList);
                        recyclerView.setAdapter(adapter);
                        Log.e("hasilnya", "onResponse: " + arrayList);
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }


                    

                } catch (Exception e) {
                    Log.d("masuk1", "onResponse: ");
                    layoutkosong.setVisibility(View.VISIBLE);
                    layoutkosong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"makan",Toast.LENGTH_LONG).show();
                            layoutkosong.setVisibility(View.GONE);
                            load_data();
                        }
                    });
                    mShimmerViewContainer.setVisibility(View.GONE);
                    Log.d("masuk2", "onResponse: ");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<data_item_spa>> call, Throwable t) {
                Log.d("masuk3", "onResponse: ");
                t.printStackTrace();
                Log.e("TAG", "onFailure: " + t.getMessage());
                Toast.makeText(Massage.this, t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }
}
