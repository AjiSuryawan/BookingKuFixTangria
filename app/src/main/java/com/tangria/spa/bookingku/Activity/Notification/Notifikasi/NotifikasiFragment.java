package com.tangria.spa.bookingku.Activity.Notification.Notifikasi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.tangria.spa.bookingku.Activity.Notification.NotificationActivity;
import com.tangria.spa.bookingku.Activity.Notification.NotificationAdapter;
import com.tangria.spa.bookingku.Activity.Notification.NotificationModel;
import com.tangria.spa.bookingku.Model.HistoryBooking;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.Network.BookingService;
import com.tangria.spa.bookingku.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotifikasiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifikasiFragment extends Fragment {

    View view;
    private ArrayList<NotificationModel> notificationList = new ArrayList<>();
    private NotificationAdapter adapter;
//    @BindView(R.id.recycler_view_notification)
    private SharedPreferences preferences;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;
    RelativeLayout relativeLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotifikasiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifikasiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifikasiFragment newInstance(String param1, String param2) {
        NotifikasiFragment fragment = new NotifikasiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_notifikasi, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShimmerViewContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();

        recyclerView = view.findViewById(R.id.recyclerView);
        relativeLayout = view.findViewById(R.id.relativeLayout1);

        ButterKnife.bind(getActivity());

        preferences = view.getContext().getSharedPreferences("login", MODE_PRIVATE);
        int userId = preferences.getInt("userid", 0);
        adapter = new NotificationAdapter(notificationList);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
        Call<ArrayList<NotificationModel>> call = service.getListNotification(userId);
        call.enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationModel>> call, Response<ArrayList<NotificationModel>> response) {
                try{
                    List<NotificationModel> result_booking_history = response.body();
                    if (result_booking_history == null || result_booking_history.equals("") || result_booking_history.isEmpty()){
//                        Toast.makeText(getContext(), "KAYAKNYA NULL", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }else {
                        relativeLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        notificationList.clear();
                        notificationList.addAll(response.body());
                        adapter.notifyDataSetChanged();

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }

                }
                catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}