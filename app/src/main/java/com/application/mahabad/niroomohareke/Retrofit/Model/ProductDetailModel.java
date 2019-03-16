package com.application.mahabad.niroomohareke.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductDetailModel {
    @SerializedName("code_kala")
    public String code_kala;
    @SerializedName("is_code_dastgah")
    public int is_code_dastgah;
    @SerializedName("title")
    public String title;
    @SerializedName("price")
    public String price;
    @SerializedName("created")
    public String created;

    @SerializedName("attributes")
    public ArrayList<ProductDetailModel.attributes> attributes = new ArrayList<>();
    public static class attributes {
        @SerializedName("parent_title")
        public String parent_title;
        @SerializedName("attribute_list")
        public ArrayList<attribute_list> attribute_list = new ArrayList<>();
        public static class attribute_list {
            @SerializedName("title")
            public String title;
            @SerializedName("Attr_val")
            public String Attr_val;

        }
    }

    @SerializedName("images")
    public ArrayList<ProductDetailModel.images> images = new ArrayList<>();
    public static class images {
        @SerializedName("resized_name")
        public String resized_name;
    }


}
