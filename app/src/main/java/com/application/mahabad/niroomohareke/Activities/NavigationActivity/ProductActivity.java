package com.application.mahabad.niroomohareke.Activities.NavigationActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.Activities.ActivityNoConnection;
import com.application.mahabad.niroomohareke.Activities.SplashScreen;
import com.application.mahabad.niroomohareke.LoadingDialog;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.FilterModel;
import com.application.mahabad.niroomohareke.Retrofit.Model.GalleryModel;
import com.application.mahabad.niroomohareke.Utils.DummyContent;
import com.application.mahabad.niroomohareke.Utils.PaginationScrollListener;
import com.bumptech.glide.Glide;
import com.application.mahabad.niroomohareke.Activities.ActivityFilter;
import com.application.mahabad.niroomohareke.Activities.MainActivity;
import com.application.mahabad.niroomohareke.Adapter.Recyclerview.ProductsAdapter;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductModel;
import com.application.mahabad.niroomohareke.Utils.PaginationAdapterCallback;
import com.google.gson.Gson;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.application.mahabad.niroomohareke.Utils.DummyContent.Mymap;
import static com.application.mahabad.niroomohareke.Utils.DummyContent.checkMap;

public class ProductActivity extends AppCompatActivity implements PaginationAdapterCallback {
    private APIService apiService;
    private String filter_json = "", cat_name = "", search = "";
    boolean isLoading;
    private int cat_id = 0, FirstVisibleItemPosition = 0;
    @BindView(R.id.catname)
    TextView txt_catname;
    @BindView(R.id.img_viewtype)
    ImageView img_viewtype;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private ProductsAdapter industrialAdapter;
    private RecyclerView.OnScrollListener onScrollListener;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.relative_filter)
    RelativeLayout relative_filter;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_searc)
    ImageView img_searc;
    @BindView(R.id.child)
    RelativeLayout child_relative;
    @BindView(R.id.loading_progress)
    ProgressBar progressBar;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    private AttributesModel attributeModel;
    private ArrayList<AttributesModel> attributeslist = new ArrayList<>();;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        init();
        initBundle();


        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (industrialAdapter.getItemViewType(position)) {
                    case ProductsAdapter.LOADING:
                        return calculateNoOfColumns(ProductActivity.this);
                    default:
                        return 1;
                }
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              reset();
            }
        });
    }


    private void LoadAttributes(int cat_id) {
        apiService.getAttributes(cat_id).enqueue(new Callback<ArrayList<AttributesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AttributesModel>> call, Response<ArrayList<AttributesModel>> response) {
                Log.i("response", new Gson().toJson(response.body()));
                attributeslist = new ArrayList<>();
                attributeslist.addAll(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<AttributesModel>> call, Throwable t) {

            }
        });
    }

    public int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }

    void init() {
        ButterKnife.bind(this);
        img_searc = findViewById(R.id.img_searc);
        relative_filter = findViewById(R.id.relative_filter);
        relative_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductActivity.this, ActivityFilter.class);
                Intent intent1 = getIntent();
                if (intent1 != null && intent1.hasExtra("category_id")) {
                    intent.putExtra("cat_id", intent1.getExtras().getInt("category_id"));
                    intent.putExtra("filter_array", new Gson().toJson(attributeslist));
                    if (attributeslist.size() == 0) {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.root), "این دسته بندی فیلتر ندارد", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        startActivityForResult(intent, 0);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        apiService = ApiUtils.getAPIService();
        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        img_viewtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setNextViewtype();
                } catch (Exception e) {
                }
            }
        });
        setSupportActionBar(toolbar);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        industrialAdapter = new ProductsAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(industrialAdapter);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(this,R.color.colorPrimary));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
    }

    void initBundle() {
        img_searc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, SearchActivity.class);
                startActivityForResult(intent, 1);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("filterJson") && intent.hasExtra("category_id")) {
            filter_json = intent.getExtras().getString("filterJson");
            cat_id = intent.getExtras().getInt("category_id");
            loadFirstPage();
        } else if (intent != null && intent.hasExtra("filterJson") && intent.hasExtra("category_id")) {
            filter_json = intent.getExtras().getString("filterJson");
            cat_id = intent.getExtras().getInt("category_id");
            txt_catname.setText(intent.getExtras().getString("category_name"));
            loadFirstPage();
        } else if (intent != null && intent.hasExtra("category_id")) {
            cat_id = intent.getExtras().getInt("category_id");
            loadFirstPage();
        }

        if (intent != null && intent.hasExtra("category_name")) {
            txt_catname.setText(intent.getExtras().getString("category_name"));
            cat_name = intent.getExtras().getString("category_name");
        }

        LoadAttributes(cat_id);
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPage = 1;
    }

    void SetOnScroll() {
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager, gridLayoutManager) {
            @Override
            protected void loadMoreItems() {

                if (industrialAdapter.viewtype == 1 || industrialAdapter.viewtype == 3) {
                    FirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                } else if (industrialAdapter.viewtype == 2) {
                    FirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                }
                isLoading = true;
                currentPage += 1;
                Log.i("nextpage", "next " + currentPage);

                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadFirstPage() {
        Log.i("check1", filter_json);
        Log.i("check2", cat_id + " ");
        Log.i("check3", search);


        apiService.getFilterProducts(filter_json, cat_id, search, 1).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if (response.isSuccessful()) {

                    TOTAL_PAGES = (int) Math.ceil((float) response.body().total / response.body().per_page);
                    currentPage = 1;
                    isLoading = false;
                    SetOnScroll();
                    ArrayList<ProductModel.data> results = response.body().data;
                    industrialAdapter.addAll(results);
                    if (currentPage < TOTAL_PAGES) {
                        industrialAdapter.addLoadingFooter();
                    } else isLastPage = true;
                    Log.i("response", new Gson().toJson(response.body()));
                    setCurrentViewType();
                    child_relative.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    swipe.setRefreshing(false);
                } else {
                    Log.i("response", "not success");
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
//                Intent intent = new Intent(ProductActivity.this, ActivityNoConnection.class);
//                startActivity(intent);
//                finish();
                Toast.makeText(ProductActivity.this, "خطا در اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void loadNextPage() {
        apiService.getFilterProducts(filter_json, cat_id, search, currentPage).enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                industrialAdapter.removeLoadingFooter();
                isLoading = false;
                ArrayList<ProductModel.data> results = response.body().data;
                industrialAdapter.addAll(results);
                Log.i("hntr4mntym", currentPage + "   " + TOTAL_PAGES);
                if (currentPage != TOTAL_PAGES) industrialAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                t.printStackTrace();
                industrialAdapter.showRetry(true, "شما به اینترنت متصل نیستید");
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void retryPageLoad() {
        loadNextPage();
    }


    void storeSP(int typeid) {
        SharedPreferences.Editor editor = getSharedPreferences("viewtype", MODE_PRIVATE).edit();
        editor.putInt("layout_type", typeid);
        editor.apply();
        editor.commit();
    }

    int getSP() {
        SharedPreferences prefs = getSharedPreferences("viewtype", MODE_PRIVATE);
        int typeid = prefs.getInt("layout_type", 2);
        Log.i("rtggds", typeid + "");
        return typeid;
    }

    void setNextViewtype() {
//                Toast.makeText(IndustrialsActivity.this, ""+value, Toast.LENGTH_SHORT).show();
        switch (getSP()) {
            case 1:
                Log.i("mylog", "1");
                industrialAdapter.viewtype = 2;
                storeSP(2);
                Glide.with(ProductActivity.this).load(R.mipmap.ic_catalog_grid).into(img_viewtype);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(industrialAdapter);
                recyclerView.scrollToPosition(FirstVisibleItemPosition);
                break;
            case 2:
                Log.i("mylog", "3");
                industrialAdapter.viewtype = 3;
                storeSP(3);
                Glide.with(ProductActivity.this).load(R.mipmap.ic_menu).into(img_viewtype);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(industrialAdapter);
                recyclerView.scrollToPosition(FirstVisibleItemPosition);
                break;
            case 3:
                Log.i("mylog", "3");
                industrialAdapter.viewtype = 1;
                storeSP(1);
                Glide.with(ProductActivity.this).load(R.mipmap.ic_catalog_single).into(img_viewtype);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(industrialAdapter);
                recyclerView.scrollToPosition(FirstVisibleItemPosition);
                break;
        }
    }

    void setCurrentViewType() {
        switch (getSP()) {
            case 1:
                Log.i("mylog", "1");
                industrialAdapter.viewtype = 1;
                Glide.with(ProductActivity.this).load(R.mipmap.ic_catalog_single).into(img_viewtype);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.scrollToPosition(FirstVisibleItemPosition);
                break;
            case 2:
                Log.i("mylog", "2");
                industrialAdapter.viewtype = 2;
                Glide.with(ProductActivity.this).load(R.mipmap.ic_catalog_grid).into(img_viewtype);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.scrollToPosition(FirstVisibleItemPosition);
                break;
            case 3:
                Log.i("mylog", "3");
                industrialAdapter.viewtype = 3;
                Glide.with(ProductActivity.this).load(R.mipmap.ic_menu).into(img_viewtype);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.scrollToPosition(FirstVisibleItemPosition);
                break;
        }
    }

    void reset() {
//        industrialAdapter.removeall();
        industrialAdapter.clear();
        currentPage = 1;
        isLastPage = false;
        recyclerView.setAdapter(industrialAdapter);
        loadFirstPage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                filter_json = data.getStringExtra("filterJson");
                progressBar.setVisibility(View.VISIBLE);
                child_relative.setVisibility(View.GONE);
                Log.i("check", filter_json + " ");
                reset();

            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                search = data.getStringExtra("search");
                child_relative.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                reset();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (!search.equals("")) {
            search = "";
            industrialAdapter.clear();
            loadFirstPage();
        } else
            super.onBackPressed();
    }
}



