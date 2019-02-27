package com.tangria.spa.bookingku.Activity.Jenisproduk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Price implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("product_id")
    @Expose
    private Integer product_id;

    @SerializedName("diskon")
    @Expose
    private Integer diskon;

    @SerializedName("harga")
    @Expose
    private Integer harga;

    public Integer getId() {
        return id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public Integer getDiskon() {
        return diskon;
    }

    public Integer getHarga() {
        return harga;
    }
}
