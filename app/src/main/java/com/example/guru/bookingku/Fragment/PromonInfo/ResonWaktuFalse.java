package com.example.guru.bookingku.Fragment.PromonInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResonWaktuFalse {
    @SerializedName("time")
    @Expose
    private ArrayList<Time> time = null;

    public ArrayList<Time> getTime() {
        return time;
    }

}
