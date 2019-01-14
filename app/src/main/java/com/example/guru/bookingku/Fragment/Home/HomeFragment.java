package com.example.guru.bookingku.Fragment.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.example.guru.bookingku.Activity.Jenisproduk.Massage;
import com.example.guru.bookingku.Fragment.Base.BaseFragment;
import com.example.guru.bookingku.Network.BookingClient;
import com.example.guru.bookingku.Network.BookingService;
import com.example.guru.bookingku.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment{
//    public List<data_item_spa> arrayList = new ArrayList<>();
//    private RecyclerView recyclerView;
//    private ProgressBar pg;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private adapter_list_item_spa adapter;


    @Override
    protected int getLayout() {
        return R.layout.activity_homedashboard;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        CardView cardkuspa1=(CardView)view.findViewById(R.id.cardkuspa1);
        cardkuspa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(view.getContext(), Massage.class);
                in.putExtra("category","package_treatment");
                startActivity(in);
            }
        });

        CardView cardku2=(CardView)view.findViewById(R.id.cardku2);
        cardku2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(view.getContext(), Massage.class);
                in.putExtra("category","ala_carte_treatment");
                startActivity(in);
            }
        });

    }

}