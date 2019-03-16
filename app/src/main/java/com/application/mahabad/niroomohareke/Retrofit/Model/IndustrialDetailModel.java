package com.application.mahabad.niroomohareke.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IndustrialDetailModel {

    @SerializedName("title")
    public String title;
    @SerializedName("price")
    public String price;
    @SerializedName("description")
    public String description;
    @SerializedName("images")
    public ArrayList<IndustrialDetailModel.images> images = new ArrayList<>();
    public static class images {
        @SerializedName("image")
        public String image;
    }

}
