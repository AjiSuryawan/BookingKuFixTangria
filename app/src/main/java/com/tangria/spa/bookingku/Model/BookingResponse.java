package com.tangria.spa.bookingku.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("result_available_time")
    @Expose
    private List<AvailableTime> availableTimeList = null;
    @SerializedName("result_booking_history")
    @Expose
    private List<HistoryBooking> historyBookingList = null;

    public List<HistoryBooking> getHistoryBookingList() {
        return historyBookingList;
    }

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

    public boolean getPhoneStatus(){
        if(phone == null)
            return true;
        else
            return false;
    }
}
