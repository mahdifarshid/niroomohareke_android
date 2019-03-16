package com.application.mahabad.niroomohareke.Activities;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.Activities.NavigationActivity.AboutActivity;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.ActivityDocCats;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.ActivityDocuments;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.ActivtyPdfs;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.GalleryActivity;
import com.application.mahabad.niroomohareke.Activities.NavigationActivity.KhadamatActivity;
import com.application.mahabad.niroomohareke.Adapter.Recyclerview.CategoryAdapter;
import com.application.mahabad.niroomohareke.LoadingDialog;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.CategoriesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.GalleryModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.MoreModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// implements NavigationView.OnNavigationItemSelectedListener
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CategoryAdapter categoryAdapter;
    private APIService apiService;
    LoadingDialog connnectionDialog;
    private Dialog dialog_loading;
    private ArrayList<CategoriesModel> categoriesModels;
    @BindView(R.id.linear_gallery)
    LinearLayout linear_gallery;
    @BindView(R.id.linear_khadamat)
    LinearLayout linear_khadamat;
    @BindView(R.id.linear_channel)
    LinearLayout linear_channel;
    @BindView(R.id.linear_address)
    LinearLayout linear_address;

    @BindView(R.id.linear_about)
    LinearLayout linearaboutus;
    @BindView(R.id.linear_home)
    LinearLayout linear_home;
    @BindView(R.id.linear_document)
    LinearLayout linear_document;
    @BindView(R.id.linear_download)
    LinearLayout linear_downloads;
    @BindView(R.id.header)
    RelativeLayout header;


    private CategoriesModel cat;
    private KhadamatModel khadamatModel;
    private GalleryModel galleryModel;
    private DocCatModel docCatModel;
    private MoreModel moreModel;
    private String about;
    private String telegram,address;
    private String adobe_reader;
    private DrawerLayout drawer;
    private ImageView img_drawer;
    private Toolbar toolbar;
    private int Time_Between_Two_Back = 2000;
    private long TimeBackPressed;
    private SwipeRefreshLayout swipe;

    public int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        header.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    void init() {
        ButterKnife.bind(this);
        swipe = findViewById(R.id.swipe);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        apiService = ApiUtils.getAPIService();
        gridLayoutManager = new GridLayoutManager(MainActivity.this, calculateNoOfColumns(MainActivity.this));
        recyclerView.setLayoutManager(gridLayoutManager);
        img_drawer = findViewById(R.id.img_drawer);
        linear_gallery.setOnClickListener(this);
        linear_khadamat.setOnClickListener(this);
        linear_channel.setOnClickListener(this);
        linear_address.setOnClickListener(this);
        linearaboutus.setOnClickListener(this);
        img_drawer.setOnClickListener(this);
        linear_home.setOnClickListener(this);
        linear_document.setOnClickListener(this);
        linear_downloads.setOnClickListener(this);

    }

    private void resetCategory() {
        apiService.getCategories().enqueue(new Callback<ArrayList<CategoriesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoriesModel>> call, Response<ArrayList<CategoriesModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        categoryAdapter = new CategoryAdapter(MainActivity.this,response.body());
                        recyclerView.setAdapter(categoryAdapter);
                        swipe.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoriesModel>> call, Throwable t) {
                swipe.setRefreshing(false);
                Error();
            }
        });
    }
    void Error() {
        Intent intent = new Intent(MainActivity.this, ActivityNoConnection.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resetCategory();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("category")) {
            recyclerView.setHasFixedSize(false);
            cat = getIntent().getParcelableExtra("category");
            categoryAdapter = new CategoryAdapter(MainActivity.this, cat.models);
            recyclerView.setAdapter(categoryAdapter);
        }
        if (intent.hasExtra("gallery")) {
            galleryModel = getIntent().getParcelableExtra("gallery");
        }

        if (intent.hasExtra("khadamat")) {
            khadamatModel = getIntent().getParcelableExtra("khadamat");
        }
        if (intent.hasExtra("about")) {
            about = getIntent().getExtras().getString("about");
        }

        if (intent.hasExtra("telegram")) {
            telegram = getIntent().getExtras().getString("telegram");
        }
        if (intent.hasExtra("address")) {
            address = getIntent().getExtras().getString("address");
        }

        if (intent.hasExtra("adobe_reader")) {
            adobe_reader = getIntent().getExtras().getString("adobe_reader");
        }
        if (intent.hasExtra("doccat")) {
            docCatModel = getIntent().getParcelableExtra("doccat");
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            if (TimeBackPressed + Time_Between_Two_Back > System.currentTimeMillis()) {
//                super.onBackPressed();
//                finish();
                ActivityCompat.finishAffinity(MainActivity.this);
                return;
            } else {
                Snackbar snackbar = Snackbar
                        .make(drawer, getResources().getString(R.string.exit), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            TimeBackPressed = System.currentTimeMillis();
        }


    }

    private void closeDrawer() {
        if (drawer.isDrawerVisible(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_about:
                Intent intentabout = new Intent(MainActivity.this, AboutActivity.class);
                if (about != null) {
                    intentabout.putExtra("about", about);
                    startActivity(intentabout);
                    closeDrawer();
                }
                break;
            case R.id.linear_channel:
                if (telegram != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegram));
                    try
                    {
                        startActivity(intent);
                        closeDrawer();
                    }
                    catch (ActivityNotFoundException e){
                        Toast.makeText(this, R.string.channelnotfound, Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.linear_address:
                if (address != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                    try
                    {
                        startActivity(intent);
                        closeDrawer();
                    }
                    catch (ActivityNotFoundException e){
                        Toast.makeText(this, R.string.channelnotfound, Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.linear_khadamat:
                Intent intent2 = new Intent(MainActivity.this, KhadamatActivity.class);
                if (khadamatModel != null) {
                    intent2.putExtra("khadamat", khadamatModel);
                }
                startActivity(intent2);
                closeDrawer();
                break;
            case R.id.linear_gallery:
                Intent intent3 = new Intent(MainActivity.this, GalleryActivity.class);
                if (galleryModel != null) {
                    intent3.putExtra("gallery", galleryModel);
                }
                startActivity(intent3);
                closeDrawer();
                break;
            case R.id.img_drawer:
                if (drawer.isDrawerVisible(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.linear_home:
                closeDrawer();
                break;
            case R.id.linear_document:
                Intent doccat = new Intent(MainActivity.this, ActivityDocCats.class);
                if (docCatModel != null) {
                    doccat.putExtra("doccat", docCatModel);
                }
                startActivity(doccat);
                closeDrawer();
                break;
            case R.id.linear_download:
                Intent downloads = new Intent(MainActivity.this, ActivtyPdfs.class);
                if (adobe_reader != null) {
                    downloads.putExtra("adobe_reader", adobe_reader);
                }
                startActivity(downloads);
                closeDrawer();
                break;
        }
    }
}
