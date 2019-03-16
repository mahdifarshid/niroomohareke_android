package com.application.mahabad.niroomohareke.Activities.NavigationActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.PdfsAdapter;
import com.application.mahabad.niroomohareke.ConnectivityReceiver;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.PdfsModel;
import com.application.mahabad.niroomohareke.Utils.PdfInterface;
import com.application.mahabad.niroomohareke.Utils.PermissionHandel;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class ActivtyPdfs extends BaseToolBarActivity implements PdfInterface {

    private String PdfDirectory;
    PdfsAdapter pdfsAdapter;
    private ArrayList<PdfsModel> pdfsModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private SwipeRefreshLayout pullToRefresh;
    private String adobe="";
    File directory;

    public String getWithoutExtension(String fileFullPath) {
        return fileFullPath.substring(0, fileFullPath.lastIndexOf('.'));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pullToRefresh = findViewById(R.id.pullToRefresh);

        Intent intent=getIntent();
        if(intent.hasExtra("adobe_reader")){
            adobe= getIntent().getExtras().getString("adobe_reader");
        }


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Grant();
                getFiles();
            }
        });
        PermissionHandel permissionHandel = new PermissionHandel(this);
        permissionHandel.checkPermision();
        if (permissionHandel.is_havepermistion()) {
            Grant();
            getFiles();
        }
    }


    void Grant(){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+ConnectivityReceiver.NIROO_DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        PdfDirectory = Environment.getExternalStorageDirectory().getAbsolutePath()  + "/"+ConnectivityReceiver.NIROO_DIRECTORY;
        String path = PdfDirectory;
        directory = new File(path);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Grant();
                    getFiles();

                } else {
                    finish();
                }
                return;
            }

        }
    }

    private void getFiles() {
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        pdfsModels.clear();
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "FileName:" + files[i].getName());
            PdfsModel pdfsModel = new PdfsModel();
            pdfsModel.title = getWithoutExtension(files[i].getName());
            pdfsModel.filename = files[i].getName();
            pdfsModels.add(pdfsModel);

        }
        pdfsAdapter = new PdfsAdapter(this, pdfsModels,adobe);
        recyclerView.setAdapter(pdfsAdapter);
        pullToRefresh.setRefreshing(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdfs;
    }

    @Override
    public void refresh() {
        getFiles();
    }
}
