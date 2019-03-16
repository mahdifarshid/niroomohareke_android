package com.application.mahabad.niroomohareke.Adapter.ViewPager;


import android.support.v4.view.ViewPager;
import android.view.View;

import com.application.mahabad.niroomohareke.R;

public class PageTransformer implements ViewPager.PageTransformer {
    private View firstImage, secondImage, thirdImage;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        if(position >= -1 && position <= 1){
//            page.findViewById(R.id.image).setTranslationX(-position * page.getWidth() / 2);
//            page.setRotationY(position * -20);
            firstImage = page.findViewById(R.id.pager_iv);
            firstImage.setTranslationX(-position * (pageWidth / 2));
        } else {
            page.setAlpha(1);
        }


           /*   if ((int) page.getTag() == 0) {
            firstImage = page.findViewById(R.id.image_one);
            firstImage.setTranslationX(-position * (pageWidth / 2));
        }
        if ((int) page.getTag() == 1) {
            secondImage = page.findViewById(R.id.image_two);
            secondImage.setTranslationX(-position * (pageWidth / 2));
        }
        if ((int) page.getTag() == 2) {
            thirdImage = page.findViewById(R.id.image_three);
            thirdImage.setTranslationX(-position * (pageWidth / 2));
        }*/
    }
}