package com.example.guru.bookingku;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.guru.bookingku.Util.ControlClass;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int SPLASH_TIME_OUT = 3500;
        new Handler().postDelayed(new Runnable() {

            /*
             * showing splash screen with a timer. This will be useful when you
             * want to show case your app logo/company
             */
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this.getApplicationContext(), ControlClass.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
