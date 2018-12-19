package com.example.guru.bookingku.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableTime {
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("available")
    @Expose
    private Boolean available;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
