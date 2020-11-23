package com.tangria.spa.bookingku.Activity.Booking;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Activity.Main.MainActivity;
import com.tangria.spa.bookingku.Model.AvailableTime;
import com.tangria.spa.bookingku.Model.BookingResponse;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.Network.BookingService;
import com.tangria.spa.bookingku.R;
import com.tangria.spa.bookingku.Util.AlarmConfig;
import com.tangria.spa.bookingku.Util.onItemClickListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements onItemClickListener {

    private static final String NOTIFICATION_TITLE = "Notifikasi Title";
    List<AvailableTime> availableTimeList = new ArrayList<>();
    EditText txtdateku;
    Bundle bundlee;
    int orderid;
    String order_nama;
    private String selectedAvailableTime;
    private String selectedDate;
    private RecyclerView recyclerView;
    private adapter_time_booking adapter;
    private Button bookNowBtn;
    private SharedPreferences sharedPreferences;
    //    private TextView tvSelectedDateAndTime;
    private TextView txtavailable;
    private AlarmConfig alarmConfig;
    private int year, month, date, hour, minute;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    private ProgressDialog dialogku;


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
        setContentView(R.layout.activity_booking);
        dialogku = new ProgressDialog(BookingActivity.this);
        toolbar = findViewById(R.id.toolbar);
        tvTitleToolbar = findViewById(R.id.tv_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTitleToolbar.setText("BOOKING DATE");

        alarmConfig = new AlarmConfig(this);
        Intent intent = getIntent();
        bundlee = intent.getExtras();
        if (bundlee != null) {
            orderid = bundlee.getInt("orderid");
            order_nama = bundlee.getString("order_nama");
            Log.d("ordernamenya", "onClick: " + order_nama);
        }

//        tvSelectedDateAndTime = findViewById(R.id.selectedDateAndTime);
        txtavailable = findViewById(R.id.txtavailable);
        bookNowBtn = findViewById(R.id.bookNowBtn);
        bookNowBtn.setEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view_list_time);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new adapter_time_booking(this, availableTimeList);
        adapter.setOnItemClickListener(BookingActivity.this);
        recyclerView.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final int userid = sharedPreferences.getInt("userid", 0);
        Log.d("idsaya ", "onCreate: "+userid);
        txtdateku = (EditText) findViewById(R.id.txtdateku);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setTitle("Confirm booking ? ");
                builder.setMessage("Are you sure want to booking this service?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int id) {
                        dialogku.setMessage("please wait");
                        dialogku.show();
                        final String date_string = selectedDate + " " + selectedAvailableTime;
                        AndroidNetworking.post(BookingClient.BASE_URL + "api/booking")
                                .addBodyParameter("user_id", String.valueOf(userid))
                                .addBodyParameter("order", String.valueOf(orderid))
                                .addBodyParameter("date", date_string)
                                .addBodyParameter("mq_rematik", bundlee.getString("mq_rematik", ""))
                                .addBodyParameter("mq_jantung", bundlee.getString("mq_jantung", ""))
                                .addBodyParameter("mq_tekanan_darah", bundlee.getString("mq_tekanan_darah", ""))
                                .addBodyParameter("mq_tulang_belakang", bundlee.getString("mq_tulang_belakang", ""))
                                .addBodyParameter("mq_asamurat", bundlee.getString("mq_asamurat", ""))
                                .addBodyParameter("mq_asma", bundlee.getString("mq_asma", ""))
                                .addBodyParameter("mq_hamil", bundlee.getString("mq_hamil", ""))
                                .addBodyParameter("mq_datang_bulan", bundlee.getString("mq_datang_bulan", ""))
                                .addBodyParameter("mq_alat_bantu", bundlee.getString("mq_alat_bantu", ""))
                                .addBodyParameter("mq_operasi", bundlee.getString("mq_operasi", ""))
                                .addBodyParameter("mq_makan", bundlee.getString("mq_makan", ""))
                                .addBodyParameter("mq_menghindari_bagian", bundlee.getString("mq_menghindari_bagian", ""))
                                .addBodyParameter("cst_name", bundlee.getString("cst_name", ""))
                                .addBodyParameter("cst_address", bundlee.getString("cst_address", ""))
                                .addBodyParameter("cst_phone", bundlee.getString("cst_phone", ""))
                                .addBodyParameter("cst_job", bundlee.getString("cst_job", ""))
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        String status = response.optString("STATUS", "");
                                        String message = response.optString("MESSAGE", "");
                                        if (status.equalsIgnoreCase("SUCCESS")) {
                                            alarmConfig.setAlarm(year, month, date, hour, minute);
                                            Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                            Toast.makeText(BookingActivity.this, message, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(BookingActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                        if (dialogku.isShowing()) {
                                            dialogku.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        dialogku.dismiss();
                                        Toast.makeText(BookingActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

//                        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
//                        Log.e("date", "onClick: " + date_string);
//                        Call<BookingResponse> call = service.booking(userid, orderid, date_string);
//
//                        call.enqueue(new Callback<BookingResponse>() {
//                            @Override
//                            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
//                                boolean success = response.body().getSuccess();
//                                if (success) {
//                                    alarmConfig.setAlarm(year, month, date, hour, minute);
//                                    Intent intent = new Intent(BookingActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                    Toast.makeText(BookingActivity.this, "success", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(BookingActivity.this, "failed", Toast.LENGTH_SHORT).show();
//                                }
//                                if (dialogku.isShowing()) {
//                                    dialogku.dismiss();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<BookingResponse> call, Throwable t) {
//                                Toast.makeText(BookingActivity.this, "Can't Connect to Server", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public void processDatePickerResult(int year, int month, int day) {

        adapter.row_index = -1;
        bookNowBtn.setEnabled(false);
//        tvSelectedDateAndTime.setEnabled(false);
//        tvSelectedDateAndTime.setText("");
        availableTimeList.clear();
        adapter.notifyDataSetChanged();
//        Toast.makeText(this, "Fetching Available Time", Toast.LENGTH_SHORT).show();

        this.year = year;
        this.month = month;
        this.date = day;

        String year_string = String.valueOf(year);
        String month_string = String.valueOf(month + 1);
        String day_string = String.valueOf(day);

        if (month < 9) {
            month_string = "0" + month_string;
        }
        if (date < 10) {
            day_string = "0" + date;
        }

        selectedDate = year_string + "-" + month_string + "-" + day_string;

        String selectedDate2 = day_string + " " + month_string + " " + year_string;
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        try {
            Date formatedDate = formatter.parse(selectedDate2);
            SimpleDateFormat formatter2 = new SimpleDateFormat("E, dd MMMM yyyy");
            String datee = formatter2.format(formatedDate);
            txtdateku.setText("" + datee);
            Log.e("DATEEE", "processDatePickerResult: " + formatedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Toast.makeText(this, "Date Selected is : " + selectedDate, Toast.LENGTH_SHORT).show();
        String productName = getIntent().getExtras().getString("order_nama");
        Log.e("id", "processDatePickerResult: " + productName);
        dialogku.setMessage("please wait");
        dialogku.show();
        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
        Log.d("ordernamenya", "processDatePickerResult: " + selectedDate);
        Call<BookingResponse> call = service.getAvailableTimeList(selectedDate, order_nama);
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                try {
                    availableTimeList.addAll(response.body().getAvailableTime());
                    Log.d("ordername", "onResponse: " + response);
                    adapter.notifyDataSetChanged();
                    txtavailable.setVisibility(View.VISIBLE);
                    if (dialogku.isShowing()) {
                        dialogku.dismiss();
                    }
                } catch (Exception e) {
                    if (dialogku.isShowing()) {
                        dialogku.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                if (dialogku.isShowing()) {
                    dialogku.dismiss();
                }
                Toast.makeText(BookingActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemClick(int position) {
//        tvSelectedDateAndTime.setEnabled(true);
        selectedAvailableTime = availableTimeList.get(position).getTime();
        String[] splitTime = selectedAvailableTime.split(":");
        hour = Integer.parseInt(splitTime[0]);
        minute = Integer.parseInt(splitTime[1]);
        bookNowBtn.setEnabled(true);
//        tvSelectedDateAndTime.setText(selectedDate + " " + selectedAvailableTime);
    }
}