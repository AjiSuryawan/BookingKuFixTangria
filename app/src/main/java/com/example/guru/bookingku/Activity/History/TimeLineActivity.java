package com.example.guru.bookingku.Activity.History;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.guru.bookingku.Activity.Detail.DetailHistory;
import com.example.guru.bookingku.Model.BookingResponse;
import com.example.guru.bookingku.Model.HistoryBooking;
import com.example.guru.bookingku.Network.BookingClient;
import com.example.guru.bookingku.Network.BookingService;
import com.example.guru.bookingku.R;
import com.example.guru.bookingku.Util.onItemClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity implements onItemClickListener {
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<HistoryBooking> mDataList = new ArrayList<>();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("History");
        Toast.makeText(this, "fetching data", Toast.LENGTH_SHORT).show();
        preferences = getSharedPreferences("login", MODE_PRIVATE);
        setContentView(R.layout.activity_timeline);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mShimmerViewContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAdapter(mDataList,TimeLineActivity.this);
        mTimeLineAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mTimeLineAdapter);

        int userId = preferences.getInt("userid", 0);
        Log.e("userID", "onCreate: " + userId);
        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
        Call<BookingResponse> call = service.getHistoryBookingList(userId);
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                try {
                    mDataList.addAll(response.body().getHistoryBookingList());
                    mTimeLineAdapter.notifyDataSetChanged();

                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(TimeLineActivity.this, "Can't connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        HistoryBooking data = mDataList.get(position);
        String product = data.getOrder();
        String productImg = data.getOrderImg();
        String productDesc = data.getOrderDesc();
        String date = data.getDate();
        String status = data.getStatus();
        String code = data.getCode();
        Log.d("halo_kang", "onItemClick: " + code);
        HistoryBooking historyBooking = new HistoryBooking(product, productImg, productDesc, date, status, code);
        Intent intent = new Intent(this, DetailHistory.class);
        intent.putExtra("history", historyBooking);
        startActivity(intent);
    }
}
