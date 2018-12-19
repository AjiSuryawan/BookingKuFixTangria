package com.example.guru.bookingku.Activity.Booking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.guru.bookingku.Model.AvailableTime;
import com.example.guru.bookingku.Model.BookingResponse;
import com.example.guru.bookingku.Network.BookingClient;
import com.example.guru.bookingku.Network.BookingService;
import com.example.guru.bookingku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class BookingActivity extends AppCompatActivity implements adapter_time_booking.onItemClickListener{
    List<AvailableTime> availableTimeList = new ArrayList<>();
    private String selectedAvailableTime;
    private String selectedDate;
    private RecyclerView recyclerView;
    private adapter_time_booking adapter;
    private Button bookNowBtn;
    private SharedPreferences sharedPreferences;
    private TextView tvSelectedDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tvSelectedDateAndTime = findViewById(R.id.selectedDateAndTime);
        bookNowBtn = findViewById(R.id.bookNowBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list_time);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new adapter_time_booking(this, availableTimeList);
        adapter.setOnItemClickListener(BookingActivity.this);
        recyclerView.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int userid = sharedPreferences.getInt("userid", 0);
        EditText txtdateku = (EditText) findViewById(R.id.txtdateku);
        txtdateku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });
        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingService service = BookingClient.getRetrofit().create(BookingService.class);
                final String date = selectedDate + " " + selectedAvailableTime;
                Log.e("date", "onClick: " + date);
                Call<BookingResponse> call = service.booking(userid, 1, date);
                call.enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                        boolean success = response.body().getSuccess();
                        if(success){
                            Toast.makeText(BookingActivity.this, "success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookingActivity.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {
                        Toast.makeText(BookingActivity.this, "Can't Connect to Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void processDatePickerResult(int year, int month, int day) {
        tvSelectedDateAndTime.setEnabled(false);
        tvSelectedDateAndTime.setText("");
        availableTimeList.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Fetching Available Time", Toast.LENGTH_SHORT).show();

        String year_string = String.valueOf(year);
        String month_string = String.valueOf(month + 1);
        String day_string = String.valueOf(day);

        selectedDate = year_string + "-" + month_string + "-" + day_string;
        Toast.makeText(this, "Date Selected is : " + selectedDate, Toast.LENGTH_SHORT).show();
        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
        Call<BookingResponse> call = service.getAvailableTimeList(selectedDate);
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                availableTimeList.addAll(response.body().getAvailableTime());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        tvSelectedDateAndTime.setEnabled(true);
        selectedAvailableTime = availableTimeList.get(position).getTime();
        bookNowBtn.setEnabled(true);
        Toast.makeText(this, selectedAvailableTime, Toast.LENGTH_SHORT).show();
        tvSelectedDateAndTime.setText(selectedDate + " " + selectedAvailableTime);
    }
}