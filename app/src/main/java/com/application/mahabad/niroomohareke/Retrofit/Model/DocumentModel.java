package com.application.mahabad.niroomohareke.Retrofit.Model;

import com.google.gson.annotations.SerializedName;

public class DocumentModel {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("doc")
    public String doc;
    @SerializedName("extention")
    public String extention;
}
