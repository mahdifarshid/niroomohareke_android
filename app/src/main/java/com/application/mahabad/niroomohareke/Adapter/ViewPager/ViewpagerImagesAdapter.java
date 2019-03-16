package com.application.mahabad.niroomohareke.Adapter.ViewPager;

/**
 * Created by farshid on 4/11/18.
 */

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.application.mahabad.niroomohareke.R;
import com.application.mahabad.niroomohareke.Retrofit.Model.ProductDetailModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farshid on 3/7/18.
 */

public class ViewpagerImagesAdapter extends android.support.v4.view.PagerAdapter {



    //    private Integer[]images={R.drawable.boston,R.drawable.fort_lauderdale,R.drawable.nyc};
    private LayoutInflater layoutInflater;
    private Context context;
    private ProgressBar progressBar;


    int currentPage = 0;
    int NUM_PAGES = 0;


    private List<ProductDetailModel.images> images=new ArrayList<>();

    public ViewpagerImagesAdapter(Context c,List<ProductDetailModel.images>image){
        context=c;
        images=image;
    }


    @Override
    public int getCount() {
        if( images.get(0).resized_name.isEmpty())
            return 1;
        else
            return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.item_viewpager_images,null);

        view.setTag(position);

        ImageView imageView=view.findViewById(R.id.pager_iv);
//         progressBar=view.findViewById(R.id.progress);

//        imageView.setImageResource(images[position]);

//        if(images.get(0).Images.isEmpty())
//            Glide.with(context).load(R.drawable.piclist_icon_default).into(imageView);
//        else
            Glide.with(context).load(images.get(position).resized_name).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(imageView);

        ViewPager vp=(ViewPager) container;
        vp.addView(view,0);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Drawable drawableProgress = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
//            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(context,R.color.colorPrimary));
//            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));
//
//        } else {
//            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
//        }

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        ViewPager vp=(ViewPager) container;
        View view=(View) object;
        vp.removeView(vp);
    }
}
