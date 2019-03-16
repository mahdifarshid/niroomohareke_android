package com.application.mahabad.niroomohareke.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GalleryModel implements Parcelable {

    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("image")
    public String image;
    @SerializedName("created_at")
    public String created_at;

    public ArrayList<GalleryModel> galleryModels = new ArrayList<>();

    public GalleryModel(ArrayList<GalleryModel> galleryModels) {
        this.galleryModels = galleryModels;
    }


    protected GalleryModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        created_at = in.readString();
        galleryModels = in.createTypedArrayList(GalleryModel.CREATOR);
    }

    public static final Creator<GalleryModel> CREATOR = new Creator<GalleryModel>() {
        @Override
        public GalleryModel createFromParcel(Parcel in) {
            return new GalleryModel(in);
        }

        @Override
        public GalleryModel[] newArray(int size) {
            return new GalleryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(created_at);
        parcel.writeTypedList(galleryModels);
    }
}
