package com.tangria.spa.bookingku.Fragment.Home;

import android.os.Parcel;
import android.os.Parcelable;
import com.tangria.spa.bookingku.Activity.Jenisproduk.Price;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class data_item_spa implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private Price price;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("available")
    @Expose
    private String available;

    protected data_item_spa(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        image = in.readString();
        category = in.readString();
        description = in.readString();
        note = in.readString();
        available = in.readString();
        price = (Price) in.readSerializable();
    }

    public static final Creator<data_item_spa> CREATOR = new Creator<data_item_spa>() {
        @Override
        public data_item_spa createFromParcel(Parcel in) {
            return new data_item_spa(in);
        }

        @Override
        public data_item_spa[] newArray(int size) {
            return new data_item_spa[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Price getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return BookingClient.BASE_URL + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public boolean getAvailable() {
        if (available.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(note);
        dest.writeString(available);
        dest.writeSerializable(price);
    }
}
