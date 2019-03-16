package com.application.mahabad.niroomohareke.Adapter.Recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.GalleryModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyHolder> {

    private Context context;
    private ArrayList<GalleryModel> list = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
//    public GalleryAdapter(Context context, ArrayList<GalleryModel> list,ProgressBar pb,RecyclerView rv) {
public GalleryAdapter(Context context, ArrayList<GalleryModel> list) {
//        this.progressBar=pb;
//        this.recyclerView=rv;
        this.context = context;
        this.list = list;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public GalleryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);
        return new GalleryAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.MyHolder holder, int position) {
        holder.title.setText(list.get(position).title);
//        progressBar.setVisibility(View.GONE);
//        recyclerView.setVisibility(View.VISIBLE);

        Glide.with(context).load(list.get(position).image).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.i("reebgvew","false");
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                progressBar.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.VISIBLE);
//                Log.i("reebgvew","true");
                return false;
            }
        }).placeholder(R.drawable.ic_post_image_loading).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

