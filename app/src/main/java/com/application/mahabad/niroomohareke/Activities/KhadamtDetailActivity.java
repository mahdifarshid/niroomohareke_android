package com.application.mahabad.niroomohareke.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatDetailModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhadamtDetailActivity extends AppCompatActivity {
    private APIService apiService;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.product_title)
    TextView product_title;
    @BindView(R.id.child)
    RelativeLayout relative_child;
    @BindView(R.id.loading_progress)
    ProgressBar progressBar;
    @BindView(R.id.img_back)
    ImageView img_back;

    void init() {
        ButterKnife.bind(this);
        apiService = ApiUtils.getAPIService();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khadamt_detail);
        init();

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
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

        Intent intent = getIntent();
        if (intent.hasExtra("khadamat_id")) {

            apiService.getKhadamatDetail(intent.getExtras().getInt("khadamat_id")).enqueue(new Callback<ArrayList<KhadamatDetailModel>>() {
                @Override
                public void onResponse(Call<ArrayList<KhadamatDetailModel>> call, Response<ArrayList<KhadamatDetailModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().size() > 0) {
                            final String mimeType = "text/html";
                            final String encoding = "UTF-8";
                            String html = response.body().get(0).html;
                            product_title.setText(response.body().get(0).title);
                            webView.loadDataWithBaseURL("", html, mimeType, encoding, "");
                            webView.setWebViewClient(new WebViewClient() {
                                public void onPageFinished(WebView view, String url) {
                                    progressBar.setVisibility(View.GONE);
                                    relative_child.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<KhadamatDetailModel>> call, Throwable t) {
                    Toast.makeText(KhadamtDetailActivity.this, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });


        }

    }
}
