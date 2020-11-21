package com.tangria.spa.bookingku.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("no_hp")
    @Expose
    private String no_hp;
    @SerializedName("member_point")
    @Expose
    private String member_point;
    @SerializedName("member_status")
    @Expose
    private String member_status;
    @SerializedName("member_expired")
    @Expose
    private String member_expired;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getNoHp() {
        return no_hp;
    }

    public void setNoHp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getMember_point() {
        return member_point;
    }

    public void setMember_point(String member_point) {
        this.member_point = member_point;
    }

    public String getMember_status() {
        return member_status;
    }

    public void setMember_status(String member_status) {
        this.member_status = member_status;
    }

    public String getMember_expired() {
        return member_expired;
    }

    public void setMember_expired(String member_expired) {
        this.member_expired = member_expired;
    }
}
