package com.application.mahabad.niroomohareke.Activities.NavigationActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.application.mahabad.niroomohareke.R;


public abstract class BaseToolBarActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setSupportActionBar(toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected abstract int getLayoutId();

}
