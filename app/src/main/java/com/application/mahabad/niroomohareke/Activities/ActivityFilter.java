package com.application.mahabad.niroomohareke.Activities;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.CategoryAdapter;
import com.application.mahabad.niroomohareke.Adapter.Recyclerview.FilterParentAdapter;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.AttributesModel;
import com.application.mahabad.niroomohareke.Utils.DummyContent;
import com.application.mahabad.niroomohareke.Retrofit.Model.FilterModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.application.mahabad.niroomohareke.Utils.DummyContent.Mymap;
import static com.application.mahabad.niroomohareke.Utils.DummyContent.checkMap;

public class ActivityFilter extends AppCompatActivity {
    private APIService apiService;
    private Toolbar toolbar;
    private Button btn_filter;
    TextView delfilter;
    ArrayList<AttributesModel> ATTRIBUTES_MODELS = new ArrayList<>();
    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        apiService = ApiUtils.getAPIService();
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_filter = findViewById(R.id.btn_filter);
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/vazir.ttf");
        btn_filter.setTypeface(font);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Back();
            }
        });
        findViewById(R.id.delfilter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMap.clear();
                Back();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("filter_array")&&intent.hasExtra("cat_id")) {
            String myjson=intent.getExtras().getString("filter_array");
            
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<AttributesModel>>(){}.getType();

            ATTRIBUTES_MODELS.clear();
            if(gson.fromJson(myjson, type)!=null){
            ATTRIBUTES_MODELS = gson.fromJson(myjson, type);

            Mymap.clear();

            for (int i = 0; i < ATTRIBUTES_MODELS.size(); i++) {
                for (int j = 0; j < ATTRIBUTES_MODELS.get(i).children.size(); j++) {
                    Mymap.put(ATTRIBUTES_MODELS.get(i).children.get(j).title
                            , ATTRIBUTES_MODELS.get(i).children.get(j).attribute_values);
                    for (int k = 0; k < ATTRIBUTES_MODELS.get(i).children.get(j).attribute_values.size(); k++) {
                        int id = ATTRIBUTES_MODELS.get(i).children.get(j).attribute_values.get(k).id;
                        int attr_id = ATTRIBUTES_MODELS.get(i).children.get(j).attribute_values.get(k).Attr_id;
                        String attr_value = ATTRIBUTES_MODELS.get(i).children.get(j).attribute_values.get(k).Attr_val;
                        if (checkMap.get(id) != null) {
                            if (checkMap.get(id).flag) {
                                FilterModel filter = new FilterModel();
                                filter.attr_id = attr_id;
                                filter.attr_value = attr_value;
                                filter.flag = true;
                                checkMap.put(id, filter);
                            }
                        } else {
                            FilterModel filter = new FilterModel();
                            filter.attr_id = attr_id;
                            filter.attr_value = attr_value;
                            filter.flag = false;
                            checkMap.put(id, filter);
                        }
                    }
                }
            }

            View recyclerView = findViewById(R.id.item_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
            }
        }
    }
    void Back() {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("filterJson", new Gson().toJson(checkMap));
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityFilter.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new FilterParentAdapter(this, ATTRIBUTES_MODELS));
    }
}
