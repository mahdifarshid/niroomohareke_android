package com.application.mahabad.niroomohareke.Activities.NavigationActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.application.mahabad.niroomohareke.Adapter.Recyclerview.GalleryAdapter;
import com.application.mahabad.niroomohareke.Fragments.SlideshowDialogFragment;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.GalleryModel;

public class GalleryActivity extends BaseToolBarActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private GalleryModel gallery;
    private GalleryAdapter adapter;
    private Toolbar toolbar;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar=findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        grid=new GridLayoutManager(this,calculateNoOfColumns(this));
        Intent intent = getIntent();
        if (intent.hasExtra("gallery")) {
            gallery = getIntent().getParcelableExtra("gallery");
            grid=new GridLayoutManager(this,calculateNoOfColumns(this));
            recyclerView.setLayoutManager(grid);
            adapter=new GalleryAdapter(GalleryActivity.this,gallery.galleryModels);
            recyclerView.setAdapter(adapter);
            recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("images", gallery.galleryModels);
                    bundle.putInt("position", position);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gallery;
    }
}
