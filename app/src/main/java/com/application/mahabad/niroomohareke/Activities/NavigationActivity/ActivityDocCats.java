package com.application.mahabad.niroomohareke.Activities.NavigationActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.DocCatAdapter;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.DocCatModel;

public class ActivityDocCats extends BaseToolBarActivity {

    RecyclerView recyclerView;
    DocCatModel docCatModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView=findViewById(R.id.recyclerview);

        Intent intent=getIntent();
        if(intent.hasExtra("doccat")){
            docCatModel=intent.getParcelableExtra("doccat");
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DocCatAdapter docCatAdapter=new DocCatAdapter(this,docCatModel.docCatModels);
            recyclerView.setAdapter(docCatAdapter);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doccat;
    }
}
