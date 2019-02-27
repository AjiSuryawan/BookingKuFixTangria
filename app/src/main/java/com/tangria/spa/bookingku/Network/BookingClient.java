package com.tangria.spa.bookingku.Network;

import com.tangria.spa.bookingku.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingClient {

    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://admin.tangriaspa.com/";
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            if(BuildConfig.DEBUG){
                okHttpClient = okHttpClient.addInterceptor(logging);
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }
}
