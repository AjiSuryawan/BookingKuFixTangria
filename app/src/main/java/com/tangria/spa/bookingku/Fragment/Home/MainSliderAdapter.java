package com.tangria.spa.bookingku.Fragment.Home;

import com.tangria.spa.bookingku.Fragment.PromonInfo.Gambar;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

import java.util.ArrayList;

/**
 * @author S.Shahini
 * @since 2/12/18
 */

public class MainSliderAdapter extends SliderAdapter {

    ArrayList<Gambar> arrayku;

    public MainSliderAdapter(ArrayList<Gambar> arrayku) {
        this.arrayku=arrayku;
    }

    @Override
    public int getItemCount() {
        return arrayku.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        for (int i = 0; i <arrayku.size() ; i++) {
            viewHolder.bindImageSlide(arrayku.get(position).getGambar());
        }
    }

}
