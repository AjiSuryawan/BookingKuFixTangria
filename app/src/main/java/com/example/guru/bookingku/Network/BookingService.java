package com.example.guru.bookingku.Network;

import com.example.guru.bookingku.Fragment.Home.data_item_spa;
import com.example.guru.bookingku.Model.BookingResponse;
import com.example.guru.bookingku.Model.Profile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface BookingService {
    @GET("api/product")
    Call<List<data_item_spa>> dataProduct();

    @POST("api/user/login")
    @FormUrlEncoded
    Call<BookingResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @POST("api/user/signup")
    @FormUrlEncoded
    Call<Void> signup(
            @Field("email") String email,
            @Field("name") String name,
            @Field("password") String password
    );

    @POST("api/user")
    @FormUrlEncoded
    Call<Profile> userprofile(
            @Field("id") int id
    );


    @POST("api/user/medsos")
    @FormUrlEncoded
    Call<BookingResponse> loginMedsos(
            @Field("name") String name,
            @Field("email") String email,
            @Field("provider") String provider,
            @Field("avatar") String avatar
    );

    @POST("api/booking")
    @FormUrlEncoded
    Call<BookingResponse> booking(
            @Field("user_id") int userId,
            @Field("order") int order,
            @Field("date") String date
    );

    @POST("api/available-time")
    @FormUrlEncoded
    Call<BookingResponse> getAvailableTimeList(
            @Field("date") String date
    );
}
