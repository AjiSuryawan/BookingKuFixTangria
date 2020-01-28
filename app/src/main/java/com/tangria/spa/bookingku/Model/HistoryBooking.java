package com.tangria.spa.bookingku.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryBooking implements Parcelable {

    @SerializedName("order_id")
    @Expose
    private String orderId;
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
    @SerializedName("guest_comment")
    @Expose
    private String guestComment;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @Expose
    private String code;

    public HistoryBooking(String orderId, String order, String orderImg, String orderDesc, String date, String status, String guestComment, String userType, String code) {
        this.orderId = orderId;
        this.order = order;
        this.orderImg = orderImg;
        this.orderDesc = orderDesc;
        this.date = date;
        this.status = status;
        this.guestComment = guestComment;
        this.userType = userType;
        this.code = code;
    }

    protected HistoryBooking(Parcel in) {
        orderId = in.readString();
        order = in.readString();
        orderImg = in.readString();
        orderDesc = in.readString();
        date = in.readString();
        status = in.readString();
        guestComment = in.readString();
        userType = in.readString();
        code = in.readString();
    }

    public static final Creator<HistoryBooking> CREATOR = new Creator<HistoryBooking>() {
        @Override
        public HistoryBooking createFromParcel(Parcel in) {
            return new HistoryBooking(in);
        }

        @Override
        public HistoryBooking[] newArray(int size) {
            return new HistoryBooking[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public String getOrder() {
        return order;
    }

    public String getOrderImg() {
        return orderImg;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getGuestComment() {
        return guestComment;
    }

    public String getUserType() {
        return userType;
    }

    public String getCode() {
        return code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(order);
        dest.writeString(orderImg);
        dest.writeString(orderDesc);
        dest.writeString(date);
        dest.writeString(status);
        dest.writeString(guestComment);
        dest.writeString(userType);
        dest.writeString(code);
    }
}
