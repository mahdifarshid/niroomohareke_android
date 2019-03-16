package com.application.mahabad.niroomohareke.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocCatModel implements Parcelable{
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("documents_count")
    public int documents_count;


    public ArrayList<DocCatModel> docCatModels=new ArrayList<>();


    public DocCatModel(ArrayList<DocCatModel>docCatModels){
        this.docCatModels=docCatModels;
    }


    protected DocCatModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        documents_count = in.readInt();
        docCatModels = in.createTypedArrayList(DocCatModel.CREATOR);
    }

    public static final Creator<DocCatModel> CREATOR = new Creator<DocCatModel>() {
        @Override
        public DocCatModel createFromParcel(Parcel in) {
            return new DocCatModel(in);
        }

        @Override
        public DocCatModel[] newArray(int size) {
            return new DocCatModel[size];
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
        parcel.writeInt(documents_count);
        parcel.writeTypedList(docCatModels);
    }
}
