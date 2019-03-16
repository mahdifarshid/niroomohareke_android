package com.application.mahabad.niroomohareke.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KhadamatModel implements Parcelable {

    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;

    public ArrayList<KhadamatModel> arrayList = new ArrayList<>();

    public KhadamatModel(ArrayList<KhadamatModel> khadamatModels) {
        arrayList = khadamatModels;

    }

    protected KhadamatModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        arrayList = in.createTypedArrayList(KhadamatModel.CREATOR);
    }

    public static final Creator<KhadamatModel> CREATOR = new Creator<KhadamatModel>() {
        @Override
        public KhadamatModel createFromParcel(Parcel in) {
            return new KhadamatModel(in);
        }

        @Override
        public KhadamatModel[] newArray(int size) {
            return new KhadamatModel[size];
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
        parcel.writeString(description);
        parcel.writeTypedList(arrayList);
    }
}
