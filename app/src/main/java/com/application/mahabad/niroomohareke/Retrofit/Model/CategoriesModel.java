package com.application.mahabad.niroomohareke.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by farshid on 7/18/18.
 */

public class CategoriesModel implements Parcelable {

    @SerializedName("id")
    public int id;
    @SerializedName("cat_name")
    public String cat_name;
    @SerializedName("cat_image")
    public String cat_image;
    @SerializedName("getposts_count")
    public int getposts_count;
    @SerializedName("parent_id")
    public int parent_id;

    public ArrayList<CategoriesModel>models=new ArrayList<>();


    public CategoriesModel(ArrayList<CategoriesModel>cat){
        models=cat;
    }

    protected CategoriesModel(Parcel in) {
        id = in.readInt();
        getposts_count=in.readInt();
        cat_name = in.readString();
        cat_image = in.readString();
        parent_id = in.readInt();
        models = in.createTypedArrayList(CategoriesModel.CREATOR);
    }

    public static final Creator<CategoriesModel> CREATOR = new Creator<CategoriesModel>() {
        @Override
        public CategoriesModel createFromParcel(Parcel in) {
            return new CategoriesModel(in);
        }

        @Override
        public CategoriesModel[] newArray(int size) {
            return new CategoriesModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(getposts_count);
        parcel.writeString(cat_name);
        parcel.writeString(cat_image);
        parcel.writeInt(parent_id);
        parcel.writeTypedList(models);
    }




  /*  public ArrayList<CategoriesModel> children = new ArrayList<>();
    public static class children {
        public int id;
        public String cat_name;
        public int parent_id;
        public ArrayList<CategoriesModel.children> children = new ArrayList<>();
    }*/
}
