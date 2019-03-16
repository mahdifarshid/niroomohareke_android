package com.application.mahabad.niroomohareke.Activities.NavigationActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.DocumentAdapter;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocumentModel;
import com.application.mahabad.niroomohareke.Utils.PermissionHandel;
import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDocuments extends BaseToolBarActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private APIService apiService;
    void Progress(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(this,R.color.colorPrimary));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar=findViewById(R.id.progress);
        recyclerView=findViewById(R.id.recyclerview);
        apiService= ApiUtils.getAPIService();

        Progress();


        Intent intent=getIntent();
        if(intent.hasExtra("cat_id")){
           int cat_id= intent.getExtras().getInt("cat_id");
            getDocuments(cat_id);
        }


    }

    void getDocuments(int cat_id){
        apiService.getDocuments(cat_id).enqueue(new Callback<ArrayList<DocumentModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DocumentModel>> call, Response<ArrayList<DocumentModel>> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        Log.i("myresponse",new Gson().toJson(response.body()));
                        DocumentAdapter documentAdapter=new DocumentAdapter(ActivityDocuments.this,response.body());
                        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityDocuments.this));
                        recyclerView.setAdapter(documentAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DocumentModel>> call, Throwable t) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_documents;
    }
}
