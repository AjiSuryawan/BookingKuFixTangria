package com.tangria.spa.bookingku.Fragment.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tangria.spa.bookingku.Activity.History.TimeLineActivity;
import com.tangria.spa.bookingku.Activity.MedicalQuestion1;
import com.tangria.spa.bookingku.Fragment.Base.BaseFragment;
import com.tangria.spa.bookingku.Model.Profile;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.Network.BookingService;
import com.tangria.spa.bookingku.R;
import com.tangria.spa.bookingku.Util.ControlClass;
import com.facebook.login.LoginManager;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {
    private SharedPreferences sharedPreferences;
    CircleImageView profileimg;
    TextView profileName;
    TextView profileUsername;
    TextView tvlogout;
    TextView telpuser;
    Button btnEditMedq;
    CardView cardLogout;
    TextView tvMember, tvPointMember, tvExpiredMember;

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        profileimg = view.findViewById(R.id.profileimg);
        profileName = view.findViewById(R.id.profileName);
        cardLogout = view.findViewById(R.id.card_logout);
        tvMember = view.findViewById(R.id.tvMember);
        tvPointMember = view.findViewById(R.id.tvPointMember);
        tvExpiredMember = view.findViewById(R.id.tvExpiredMember);
        final int id = sharedPreferences.getInt("userid", 0);

        final int role = sharedPreferences.getInt("role", 0);
        btnEditMedq = view.findViewById(R.id.btn_edit_medq);
        if(role == 2){
            view.findViewById(R.id.card_edit_medq).setVisibility(View.GONE);
        }
        btnEditMedq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MedicalQuestion1.class);
                i.putExtra("isEdit", true);
                startActivity(i);
            }
        });
        tvlogout = view.findViewById(R.id.tvlogout);
        telpuser = view.findViewById(R.id.telpuser);
        profileUsername = view.findViewById(R.id.profileUsername);

        //
        BookingService bookingService = BookingClient.getRetrofit().create(BookingService.class);
        Call<Profile> call = bookingService.userprofile(sharedPreferences.getInt("userid", 0));
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                try {
                    profileName.setText("id : "+response.body().getEmail());
                    profileUsername.setText(response.body().getName());
                    Log.d("hpku", "onResponse: " + response.body().getNoHp());
                    telpuser.setText(response.body().getNoHp());
                    tvMember.setText("Member " + response.body().getMember_status());
                    tvPointMember.setText("Point : " + response.body().getMember_point());
                    tvExpiredMember.setText("Expired Member : " + response.body().getMember_expired());

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.avatar);
                    requestOptions.error(R.drawable.avatar);

                    Glide.with(view.getContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(response.body().getAvatar())
                            .into(profileimg);

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                t.printStackTrace();
            }
        });
        //
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext())
                        .setMessage("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getActivity(), ControlClass.class);
                                getActivity().finish();
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        //profileName.setText(sharedPreferences.getString("name",""));
        //profileUsername.setText(sharedPreferences.getString("email",""));

//        Glide.with(view.getContext())
//                .load(sharedPreferences.getString("avatar",""))
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .dontAnimate()
//                .into(profileimg);
        Log.e("Fragment", "onViewCreated: profile");
    }
}