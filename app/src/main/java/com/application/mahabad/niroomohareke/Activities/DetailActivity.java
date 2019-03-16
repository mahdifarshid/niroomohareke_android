package com.application.mahabad.niroomohareke.Activities;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.ProductDetailAdapter;
import com.application.mahabad.niroomohareke.Adapter.ToPersianNumber;
import com.application.mahabad.niroomohareke.Adapter.ViewPager.PageTransformer;
import com.application.mahabad.niroomohareke.Adapter.ViewPager.ViewpagerImagesAdapter;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductDetailModel;
import com.google.gson.Gson;
import com.rd.PageIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private APIService apiService;
    private ArrayList<ProductDetailModel.attributes> attributesArray = new ArrayList<>();
    private ArrayList<ProductDetailModel.images> imagesArray = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.parallax_scroll)
//    ParallaxScrollView scroll;
            ScrollView scroll;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.product_title)
    TextView product_title;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_code_kala)
    TextView tv_code_kala;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.child)
    RelativeLayout child_relative;
    @BindView(R.id.loading_progress)
    ProgressBar progressBar;
    @BindView(R.id.title_seperator)
    View title_seperator;


    private Dialog dialog_loading;

    void init() {
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this);
        apiService = ApiUtils.getAPIService();

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = viewPager.getWidth();
                viewPager.setLayoutParams(params);

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(this, R.color.colorPrimary));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        Intent intent = getIntent();
        if (intent.hasExtra("product_id")) {

            apiService.getProductDetails(intent.getExtras().getInt("product_id")).enqueue(new Callback<ProductDetailModel>() {
                @Override
                public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().title == null || response.body().title.equals("")) {
                            Toast.makeText(DetailActivity.this, "این محصول پیدا نشد", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            attributesArray.addAll(response.body().attributes);

                            Log.i("fsdkfds",new Gson().toJson(attributesArray));

                            imagesArray.addAll(response.body().images);
                            ProductDetailModel detailModel = response.body();


                            String code = getResources().getString(R.string.codeDastgah) + detailModel.title;
                            if (detailModel.is_code_dastgah == 1) {
                                tv_title.setText(code);
                                product_title.setText(code);
                            } else {
                                tv_title.setText(detailModel.title);
                                product_title.setText(detailModel.title);
                            }

                            String price = new ToPersianNumber().toPersianNumber(detailModel.price);
                            String date =detailModel.created;

                            if (!price.equals("")) {
                                tv_price.setText(price);
                            } else {
                                tv_price.setVisibility(View.GONE);
                            }
                            if (!price.equals("")) {
                                tv_date.setText("تاریخ: "+date);
                            } else {
                                tv_date.setVisibility(View.GONE);
                            }



                            if (detailModel.code_kala != null) {
                                if (!detailModel.code_kala.equals("")) {
                                    tv_code_kala.setText(new ToPersianNumber().toPersianNumberCode(detailModel.code_kala));
                                } else {
                                    tv_code_kala.setVisibility(View.GONE);
                                }
                            } else {
                                tv_code_kala.setVisibility(View.GONE);
                            }


                            if (attributesArray.size() > 0) {
                                ProductDetailAdapter detailAdapter = new ProductDetailAdapter(DetailActivity.this, attributesArray);
                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                        linearLayoutManager.getOrientation());
                                recyclerView.addItemDecoration(dividerItemDecoration);
                                recyclerView.setAdapter(detailAdapter);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                Log.i("respbvsdbdsbsdonse", new Gson().toJson(attributesArray));
                            }

                            if (imagesArray.size() > 0) {
                                viewPager.setAdapter(new ViewpagerImagesAdapter(DetailActivity.this, imagesArray));
                                viewPager.setPageTransformer(false, new PageTransformer());
                                viewPager.setOffscreenPageLimit(imagesArray.size());
                                pageIndicatorView.setViewPager(viewPager);

                            } else {
                                viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @Override
                                    public void onGlobalLayout() {
                                        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
                                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                        params.height = toolbar.getHeight();
                                        toolbar.getBackground().setAlpha(250);
                                        viewPager.setLayoutParams(params);
                                        pageIndicatorView.setVisibility(View.GONE);
                                    }
                                });
                            }
                            child_relative.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    Log.i("response", new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<ProductDetailModel> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

        toolbar.getBackground().setAlpha(0);
        scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                double percent = getscreentPercent();
                if (percent < 1) {
                    toolbar.getBackground().setAlpha((int) (percent * 255));
                    product_title.setVisibility(View.GONE);
                } else if (percent > 1) {
                    toolbar.getBackground().setAlpha(255);
                    product_title.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private double getscreentPercent() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        height = toolbar.getHeight();
        int scrollY = scroll.getScrollY();
        int scrollContentHeight = scroll.getChildAt(0).getHeight();
        int screenHeight = height;
        int statusBarHeight = getStatusBarHeight();
//        double percent = ((((float) scrollY) / ((float) ((scrollContentHeight - screenHeight) + statusBarHeight))));
        double percent = ((((float) scrollY) / (viewPager.getHeight())));
        return percent;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
