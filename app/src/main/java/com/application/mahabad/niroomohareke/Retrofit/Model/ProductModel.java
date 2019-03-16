package com.application.mahabad.niroomohareke.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductModel {
    @SerializedName("current_page")
    public int current_page;
    @SerializedName("data")
    public ArrayList<data> data = new ArrayList<>();

    public static class data {
        @SerializedName("id")
        public int id;
        @SerializedName("code_kala")
        public String code_kala;
        @SerializedName("is_code_dastgah")
        public int is_code_dastgah;
        @SerializedName("title")
        public String title;
        @SerializedName("price")
        public String price;
        @SerializedName("thumb")
        public String thumb;
    }
    @SerializedName("total")
    public int total;
    @SerializedName("prev_page_url")
    public String prev_page_url;
    @SerializedName("next_page_url")
    public String next_page_url;
    @SerializedName("per_page")
    public int per_page;
}
