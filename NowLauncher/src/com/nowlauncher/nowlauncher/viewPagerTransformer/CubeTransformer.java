package com.nowlauncher.nowlauncher.viewPagerTransformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.nowlauncher.nowlauncher.ViewPagerAnim;

/**
 * Created by andrea on 07/09/13.
 */
public class CubeTransformer implements ViewPagerAnim.PageTransformer {
    private float ROTATION_FACTOR;

    public CubeTransformer(boolean isCubeOut){
        ROTATION_FACTOR= isCubeOut ? 90.0f : -90.0f;
    }

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            ViewHelper.setPivotX(view, pageWidth);
            ViewHelper.setPivotY(view, pageWidth*0.5f);
            ViewHelper.setRotationY(view, ROTATION_FACTOR*position);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            ViewHelper.setPivotX(view, 0);
            ViewHelper.setPivotY(view, pageWidth*0.5f);
            ViewHelper.setRotationY(view, ROTATION_FACTOR*position);
        } else { // (1,+Infinity]
        }
    }
}