package com.application.mahabad.niroomohareke.Retrofit.Model;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by farshid on 7/18/18.
 */

public class AttributesModel {

    ArrayList<AttributesModel>attributesModels=new ArrayList<>();

    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("children")
    public ArrayList<AttributesModel.children> children = new ArrayList<>();
    public static class children {
        @SerializedName("id")
        public int id;
        @SerializedName("title")
        public String title;
        @SerializedName("color")
        public int color;
        @SerializedName("attribute_values")
        public ArrayList<AttributesModel.children.attribute_values> attribute_values = new ArrayList<>();
        public static class attribute_values{
            @SerializedName("id")
            public int id;
            @SerializedName("Attr_id")
            public int Attr_id;
            @SerializedName("Attr_val")
            public String Attr_val;
            @SerializedName("total")
            public int total;
            @SerializedName("is_checked")
            public boolean is_checked;
        }
    }
}
