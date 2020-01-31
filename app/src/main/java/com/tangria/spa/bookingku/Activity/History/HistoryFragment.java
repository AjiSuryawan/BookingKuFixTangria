package com.tangria.spa.bookingku.Activity.History;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tangria.spa.bookingku.Activity.Detail.DetailHistory;
import com.tangria.spa.bookingku.Activity.Guest_Comment;
import com.tangria.spa.bookingku.Fragment.Base.BaseFragment;
import com.tangria.spa.bookingku.Model.BookingResponse;
import com.tangria.spa.bookingku.Model.HistoryBooking;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.Network.BookingService;
import com.tangria.spa.bookingku.R;
import com.tangria.spa.bookingku.Util.onItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends BaseFragment implements onItemClickListener {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<HistoryBooking> mDataList = new ArrayList<>();
    private SharedPreferences preferences;
    private Toolbar toolbar;
    private TextView tvTitleToolbar;
    RelativeLayout relativeLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_timeline;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getContext(), "fetching data", Toast.LENGTH_SHORT).show();
        preferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);


        mShimmerViewContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAdapter(mDataList, getActivity());
        mTimeLineAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mTimeLineAdapter);

        relativeLayout = view.findViewById(R.id.relativeLayout1);


        int userId = preferences.getInt("userid", 0);
        Log.e("userID", "onCreate: " + userId);
        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
        mDataList.clear();
        Call<BookingResponse> call = service.getHistoryBookingList(userId);
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                try {

                    List<HistoryBooking> result_booking_history = response.body().getHistoryBookingList();
                    Log.d("historysystem", "onResponse: " + result_booking_history);

                    if (result_booking_history == null || result_booking_history.equals("") || result_booking_history.isEmpty()){
                        mRecyclerView.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        Log.d("historysystem", "onResponse: masuk");
                    }else {

                        relativeLayout.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);

                        mDataList.addAll(response.body().getHistoryBookingList());
                        mTimeLineAdapter.notifyDataSetChanged();

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Can't connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private LinearLayoutManager getLinearLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

    }

    @Override
    public void onItemClick(final int position) {
        String guestComment = mDataList.get(position).getGuestComment();
        Log.d("trial", "onResponse: " + guestComment);
        if (mDataList.get(position).getStatus().equalsIgnoreCase("selesai")) {
            if (guestComment.contains("exist")) {
                Intent intent = new Intent(getActivity(), DetailHistory.class);
                intent.putExtra("history", mDataList.get(position));
                startActivity(intent);
            } else if (guestComment.contains("none")) {
                Intent intent = new Intent(getActivity(), Guest_Comment.class);
                intent.putExtra("history", mDataList.get(position));
                intent.putExtra("booking_id", mDataList.get(position).getOrderId());
                intent.putExtra("isBerulang", mDataList.get(position).getUserType());
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(getActivity(), DetailHistory.class);
            intent.putExtra("history", mDataList.get(position));
            startActivity(intent);
        }
    }

}
