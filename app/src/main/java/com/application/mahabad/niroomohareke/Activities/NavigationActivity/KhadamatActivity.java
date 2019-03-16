package com.application.mahabad.niroomohareke.Activities.NavigationActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.KhadamatAdapter;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.APIService;
import com.application.mahabad.niroomohareke.Retrofit.ApiUtils;
import com.application.mahabad.niroomohareke.Retrofit.Model.KhadamatModel;

public class KhadamatActivity extends BaseToolBarActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private APIService apiService;
    private KhadamatModel khadamatModel;
    private KhadamatAdapter adapter;
    private Toolbar toolbar;

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerview);
        toolbar=findViewById(R.id.toolbar);
        apiService = ApiUtils.getAPIService();
        layoutManager = new LinearLayoutManager(this);
        Intent intent = getIntent();
        if (intent.hasExtra("khadamat")) {
            khadamatModel = getIntent().getParcelableExtra("khadamat");
            recyclerView.setLayoutManager(layoutManager);
            adapter = new KhadamatAdapter(KhadamatActivity.this, khadamatModel.arrayList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_khadamat;
    }
}
