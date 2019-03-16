package com.application.mahabad.niroomohareke.Activities;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.CategoriesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.GalleryModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.MoreModel;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private static final String key = "0123456789abcdef";
    private static final String initVector = "adsdbdfbdfbdfbdb";


    private APIService apiService;
    private ArrayList<CategoriesModel> categoriesModels;
    private ArrayList<DocCatModel> docCatModels;
    private ArrayList<GalleryModel> galleryModels;
    private ArrayList<KhadamatModel> khadamatModels;
    private ProgressBar progressBar;
    private ArrayList<MoreModel> moreModels;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        apiService = ApiUtils.getAPIService();
        progressBar = findViewById(R.id.progress);

        getCategory();
        getGalleries();
        getKhadamat();
        getMore();
        getDocCats();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(this, R.color.colorPrimary));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(categoriesModels!=null){
                    Log.i("mylog1",categoriesModels.size()+" ");
                }
                if(galleryModels!=null){
                    Log.i("mylog2",galleryModels.size()+" ");
                }
                if(khadamatModels!=null){
                    Log.i("mylog3",khadamatModels.size()+" ");
                }
                if(docCatModels!=null){
                    Log.i("mylog4",docCatModels.size()+" ");
                }
                if(moreModels!=null){
                    Log.i("mylog5",moreModels.size()+" ");
                }
//                Log.i("myloggggs",categoriesModels.size()+"  "+
//                        galleryModels.size()+"  "+khadamatModels.size()+"  "+moreModels.size());
                handler.postDelayed(this, 2 * 1000);
                if (categoriesModels != null && galleryModels != null &&
                        khadamatModels != null && moreModels != null&&docCatModels!=null) {

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    CategoriesModel cat = new CategoriesModel(categoriesModels);
                    KhadamatModel khadamat = new KhadamatModel(khadamatModels);
                    GalleryModel gallery = new GalleryModel(galleryModels);
                    DocCatModel docCatModel = new DocCatModel(docCatModels);

                    intent.putExtra("category", cat);
                    intent.putExtra("doccat", docCatModel);
                    intent.putExtra("khadamat", khadamat);
                    intent.putExtra("gallery", gallery);
                    if (moreModels.get(0).telegram != null)
                        intent.putExtra("telegram", moreModels.get(0).telegram);
                    if (moreModels.get(0).address != null)
                        intent.putExtra("address", moreModels.get(0).address);
                    if (moreModels.get(0).aboutus != null)
                        intent.putExtra("about", moreModels.get(0).aboutus);
                    if (moreModels.get(0).adobe_reader != null)
                        intent.putExtra("adobe_reader", moreModels.get(0).adobe_reader);
                    handler.removeCallbacksAndMessages(null);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();


                }

            }
        }, 2 * 1000);

    }

    private void getDocCats() {
        apiService.getDocCats().enqueue(new Callback<ArrayList<DocCatModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DocCatModel>> call, Response<ArrayList<DocCatModel>> response) {
                if (response.body() != null) {
                    docCatModels=new ArrayList<>();
                    docCatModels.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DocCatModel>> call, Throwable t) {

            }
        });
    }


    private void getCategory() {
        apiService.getCategories().enqueue(new Callback<ArrayList<CategoriesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoriesModel>> call, Response<ArrayList<CategoriesModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        categoriesModels = new ArrayList<>();
                        categoriesModels.addAll(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoriesModel>> call, Throwable t) {
                Error();
            }
        });
    }

    private void getGalleries() {
        apiService.getgaleries().enqueue(new Callback<ArrayList<GalleryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GalleryModel>> call, Response<ArrayList<GalleryModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        galleryModels = new ArrayList<>();
                        ;
                        galleryModels.addAll(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GalleryModel>> call, Throwable t) {
                Error();
            }
        });
    }

    private void getKhadamat() {
        apiService.getKhadamat().enqueue(new Callback<ArrayList<KhadamatModel>>() {
            @Override
            public void onResponse(Call<ArrayList<KhadamatModel>> call, Response<ArrayList<KhadamatModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        khadamatModels = new ArrayList<>();
                        khadamatModels = response.body();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KhadamatModel>> call, Throwable t) {
                Error();
            }
        });
    }

    private void getMore() {
        apiService.getMore().enqueue(new Callback<ArrayList<MoreModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MoreModel>> call, Response<ArrayList<MoreModel>> response) {
                if (response.body() != null) {
                    moreModels = new ArrayList<>();
                    moreModels = response.body();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MoreModel>> call, Throwable t) {
                Error();
            }
        });
    }

    void Error() {
        Intent intent = new Intent(SplashScreen.this, ActivityNoConnection.class);
        startActivity(intent);
        finish();
    }
}
