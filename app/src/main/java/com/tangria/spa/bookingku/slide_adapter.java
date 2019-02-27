package com.tangria.spa.bookingku;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class slide_adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    View view;

    public slide_adapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if(position == 0){
            view = layoutInflater.inflate(R.layout.slide1, container, false);
        } else if (position == 1){
            view = layoutInflater.inflate(R.layout.slide2, container, false);
        } else if (position == 2){
            view = layoutInflater.inflate(R.layout.slide3, container, false);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
