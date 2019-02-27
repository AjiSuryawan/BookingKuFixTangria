package com.tangria.spa.bookingku.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
    private String imagePath, name, desc;

    public Item(String imagePath, String name, String desc) {
        this.imagePath = imagePath;
        this.name = name;
        this.desc = desc;
    }

    protected Item(Parcel in) {
        imagePath = in.readString();
        name = in.readString();
        desc = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(name);
        dest.writeString(desc);
    }
}
