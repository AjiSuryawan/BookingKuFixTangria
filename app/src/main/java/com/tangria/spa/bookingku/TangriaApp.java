package com.tangria.spa.bookingku;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class TangriaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        AndroidNetworking.initialize(getApplicationContext(), builder.build());
    }

}
