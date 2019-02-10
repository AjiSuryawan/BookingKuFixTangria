package com.example.guru.bookingku.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.guru.bookingku.Activity.InputPhone;
import com.example.guru.bookingku.Activity.LoginActivity;
import com.example.guru.bookingku.Activity.Main.MainActivity;
import com.example.guru.bookingku.intro;

public class ControlClass extends Activity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("first_time",0)== 0){
            Intent in=new Intent(getApplicationContext(),intro.class);
            startActivity(in);
            finish();
        }
        else if (sharedPreferences.getInt("userid",0)== 0){
            Intent in=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(in);
            finish();
        } else if(!sharedPreferences.getBoolean("phone", false)){
            Intent in= new Intent(getApplicationContext(), InputPhone.class);
            startActivity(in);
            finish();
        }
        else{
            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
            finish();
        }
    }
}
