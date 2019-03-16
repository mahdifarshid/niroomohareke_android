package com.application.mahabad.niroomohareke.Retrofit;


import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.CategoriesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocumentModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.GalleryModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatDetailModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.MoreModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductDetailModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

//    String url = "/laravel/industrial/";
     String url="";


    @GET(url + "/api/categories")
    Call<ArrayList<CategoriesModel>> getCategories();


    @FormUrlEncoded
    @POST(url + "/api/attributes")
    Call<ArrayList<AttributesModel>> getAttributes(@Field("cat_id") int cat_id);


//    @GET("/api/product")
//    Call<ProductModel> getIndustrials();

    @FormUrlEncoded
    @POST(url + "/api/product/filter")
    Call<ProductModel> getFilterProducts(@Field("filter") String str, @Field("category_id") int cat_id,
                                         @Field("search") String search,@Field("page")int page);


    @FormUrlEncoded
    @POST(url + "/api/product/detail")
    Call<ProductDetailModel> getProductDetails(@Field("product_id") int product_id);


    @GET(url + "/api/galleries")
    Call<ArrayList<GalleryModel>> getgaleries();

    @GET(url + "/api/khadamat")
    Call<ArrayList<KhadamatModel>> getKhadamat();

    @FormUrlEncoded
    @POST(url + "/api/khadamatDetail")
    Call<ArrayList<KhadamatDetailModel>> getKhadamatDetail(@Field("khadamat_id")int khadamat_id);


    @GET(url + "/api/more")
    Call<ArrayList<MoreModel>> getMore();

    @GET(url + "/api/doc_cats")
    Call<ArrayList<DocCatModel>> getDocCats();


    @GET(url + "/api/docs")
    Call<ArrayList<DocumentModel>> getDocuments(@Query("cat_id") int cat_id);


//
//    @FormUrlEncoded
//    @POST("/api/product/detail")
//    Call<ArrayList<IndustrialDetailModel>> getIndustrialDetails(@Field("product_id") int product_id);
//


}
