package com.tangria.spa.bookingku.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryBooking implements Parcelable {

    @SerializedName("order")
    @Expose
    private String order;
    @SerializedName("order_img")
    @Expose
    private String orderImg;
    @SerializedName("order_desc")
    @Expose
    private String orderDesc;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @Expose
    private String code;

    public HistoryBooking(String order, String orderImg, String orderDesc, String date, String status, String code) {
        this.order = order;
        this.orderImg = orderImg;
        this.orderDesc = orderDesc;
        this.date = date;
        this.status = status;
        this.code = code;
    }

    public String getOrder() { return order; }

    public String getOrderImg() { return orderImg; }

    public String getOrderDesc() { return orderDesc; }

    public String getDate() { return date; }

    public String getStatus() { return status; }

    public String getCode() { return code; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order);
        dest.writeString(this.orderImg);
        dest.writeString(this.orderDesc);
        dest.writeString(this.date);
        dest.writeString(this.status);
        dest.writeString(this.code);
    }

    public HistoryBooking() {
    }

    protected HistoryBooking(Parcel in) {
        this.order = in.readString();
        this.orderImg = in.readString();
        this.orderDesc = in.readString();
        this.date = in.readString();
        this.status = in.readString();
        this.code = in.readString();
    }

    public static final Creator<HistoryBooking> CREATOR = new Creator<HistoryBooking>() {
        @Override
        public HistoryBooking createFromParcel(Parcel source) {
            return new HistoryBooking(source);
        }

        @Override
        public HistoryBooking[] newArray(int size) {
            return new HistoryBooking[size];
        }
    };
}
