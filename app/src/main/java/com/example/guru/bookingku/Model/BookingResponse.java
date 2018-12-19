package com.example.guru.bookingku.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("result")
    @Expose
    private List<AvailableTime> availableTimeList = null;

    public List<AvailableTime> getAvailableTime() {
        return availableTimeList;
    }

    public void setResult(List<AvailableTime> availableTimeList) {
        this.availableTimeList = availableTimeList;
    }

    public Boolean getSuccess() {
        if(success.equals("true")){
            return true;
        } else
            return false;
    }

    public Integer getUserId() {
        return userId;
    }

}
