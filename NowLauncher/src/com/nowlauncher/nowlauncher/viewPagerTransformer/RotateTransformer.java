package com.nowlauncher.nowlauncher.viewPagerTransformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.nowlauncher.nowlauncher.ViewPagerAnim;

/**
 * Created by andrea on 08/09/13.
 */
public class RotateTransformer implements ViewPagerAnim.PageTransformer {

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        float FACTOR=0.5f;

        if (position < -1) { // [-Infinity,-1)

        }
        else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            ViewHelper.setPivotX(view, pageWidth*FACTOR);
            ViewHelper.setPivotY(view, pageHeight*FACTOR);
            ViewHelper.setRotation(view, 180*position);
        }
        else if (position <= 1) { // (0,1]
            // Fade the page out.
            ViewHelper.setPivotX(view, pageWidth*FACTOR);
            ViewHelper.setPivotY(view, pageHeight*FACTOR);
            ViewHelper.setRotation(view, 180*position);
        } else { // (1,+Infinity]
        }
    }
}