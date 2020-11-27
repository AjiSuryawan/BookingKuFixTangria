package com.tangria.spa.bookingku.Activity.Notification.Promo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.tangria.spa.bookingku.Activity.Notification.NotificationAdapter;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromoFragment extends Fragment {
    View view;
    private ArrayList<PromoModel> mList = new ArrayList<>();
    private PromoAdapter mAdapter;
    private RecyclerView recycler_view_promo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PromoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromoFragment newInstance(String param1, String param2) {
        PromoFragment fragment = new PromoFragment();
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
        view =inflater.inflate(R.layout.fragment_promo, container, false);
        recycler_view_promo=(RecyclerView)view.findViewById(R.id.recycler_view_promo);

        getPromoList();
        recycler_view_promo.setHasFixedSize(true);
        recycler_view_promo.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void getPromoList (){
        AndroidNetworking.get(BookingClient.BASE_URL + "api/promo")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        if (mAdapter != null) {
                            mAdapter.clearData();
                            mAdapter.notifyDataSetChanged();
                        }
                        if (mList != null)  mList.clear();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject dataPromo = response.optJSONObject(i);
                            PromoModel item = new PromoModel();
                            Log.d("RBA", "onResponse: " + dataPromo.optString("created_at"));
                            Log.d("RBA", "onResponse 2 : " + response);
                            item.setTitle(dataPromo.optString("title"));
                            item.setContent(dataPromo.optString("content"));
                            item.setImage(dataPromo.optString("image"));
                            item.setCreated_at(dataPromo.optString("created_at"));
                            mList.add(item);
                        }
                        show();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.d("RBA", "onError: " + error.getErrorBody());
                        Log.d("RBA", "onError: " + error.getLocalizedMessage());
                        Log.d("RBA", "onError: " + error.getErrorDetail());
                        Log.d("RBA", "onError: " + error.getResponse());
                        Log.d("RBA", "onError: " + error.getErrorCode());
                    }
                });
    }

    public void show(){
//        mAdapter = new CatatanAdapter(CatatanActivity.this, mList);
//        rv.setAdapter(mAdapter);
        mAdapter = new PromoAdapter(getContext(), mList);
        recycler_view_promo.setAdapter(mAdapter);
    }
}