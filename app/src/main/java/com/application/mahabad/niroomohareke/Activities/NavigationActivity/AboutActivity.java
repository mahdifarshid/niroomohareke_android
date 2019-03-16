package com.application.mahabad.niroomohareke.Activities.NavigationActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.application.mahabad.niroomohareke.R;


public class AboutActivity extends BaseToolBarActivity {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = findViewById(R.id.about);
        Intent intent = getIntent();
        if (intent.hasExtra("about")) {
            textView.setText(intent.getExtras().getString("about"));
        }

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_aboutus;
    }
}
