package com.tangria.spa.bookingku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tangria.spa.bookingku.Activity.LoginActivity;

public class intro extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private slide_adapter slide_adapter;
    private TextView[] textViews;
    private Button button_back, button_next;
    private int state_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        viewPager = (ViewPager) findViewById(R.id.view_pager_intro);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout_intro);
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setText("");
        button_next = (Button) findViewById(R.id.button_next);
        button_next.setText("Next");
        slide_adapter = new slide_adapter(this);
        viewPager.setAdapter(slide_adapter);
        indicator_dot(0);
        viewPager.addOnPageChangeListener(listener);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state_page = getItem(+1);
                if (state_page < textViews.length) {
                    viewPager.setCurrentItem(state_page);
                }else {
                    goto_activiy_login();
                }
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(state_page - 1);
            }
        });
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void goto_activiy_login() {
        Intent intent =new Intent(intro.this,LoginActivity.class);
        SharedPreferences sharedPreferences= getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("first_time",1);
        editor.apply();
        startActivity(intent);
        finish();
    }

    private void indicator_dot(int position) {
        textViews = new TextView[3];
        linearLayout.removeAllViews();
        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = new TextView(this);
            textViews[i].setText(Html.fromHtml("&#8226;"));
            textViews[i].setTextSize(35);
            textViews[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            linearLayout.addView(textViews[i]);
        }
        if (textViews.length > 0) {
            textViews[position].setTextColor(getResources().getColor(R.color.yello));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            indicator_dot(i);
            state_page = i;
            if (i == 0) {
                button_back.setEnabled(false);
                button_back.setVisibility(View.INVISIBLE);
                button_back.setText("");
                button_next.setEnabled(true);
                button_next.setText("Next");
            } else if (i == textViews.length - 1) {
                button_back.setEnabled(true);
                button_back.setVisibility(View.VISIBLE);
                button_back.setText("Back");
                button_next.setEnabled(true);
                button_next.setText("Finish");
            } else if (i%2 == 0){
                button_back.setEnabled(true);
                button_back.setVisibility(View.VISIBLE);
                button_back.setText("Back");
                button_next.setEnabled(true);
                button_next.setText("Next");
            }
            else if (i%2 == 1){
                button_back.setEnabled(true);
                button_back.setVisibility(View.VISIBLE);
                button_back.setText("Back");
                button_next.setEnabled(true);
                button_next.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
