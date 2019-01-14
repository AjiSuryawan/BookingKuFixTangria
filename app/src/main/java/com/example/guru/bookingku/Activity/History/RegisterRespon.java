package com.example.guru.bookingku.Activity.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterRespon {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("success")
    @Expose
    private String success;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
