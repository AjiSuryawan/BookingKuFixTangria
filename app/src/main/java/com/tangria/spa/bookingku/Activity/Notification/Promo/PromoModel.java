package com.tangria.spa.bookingku.Activity.Notification.Promo;

import android.os.Parcel;
import android.os.Parcelable;

public class PromoModel implements Parcelable {
    String title;
    String content;
    String image;
    String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PromoModel(String title, String content, String image, String created_at) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.created_at = created_at;
    }

    public PromoModel() {
        this.title = title;
        this.content = content;
        this.image = image;
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(image);
        dest.writeString(created_at);
    }

    protected PromoModel(Parcel in) {
        title = in.readString();
        content = in.readString();
        image = in.readString();
        created_at = in.readString();
    }

    public static final Creator<PromoModel> CREATOR = new Creator<PromoModel>() {
        @Override
        public PromoModel createFromParcel(Parcel in) {
            return new PromoModel(in);
        }

        @Override
        public PromoModel[] newArray(int size) {
            return new PromoModel[size];
        }
    };
}
