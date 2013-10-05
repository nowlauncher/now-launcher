package com.nowlauncher.nowlauncher.viewPagerTransformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.nowlauncher.nowlauncher.ViewPagerAnim;

/**
 * Created by andrea on 08/09/13.
 */
public class RotateInTransformer implements ViewPagerAnim.PageTransformer {

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        float FACTOR=0.5f;
        float MIN_SCALE = 0.75f;

        if (position < -1) { // [-Infinity,-1)

            ViewHelper.setAlpha(view,0);
        }
        else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page

            ViewHelper.setAlpha(view,1);
            ViewHelper.setRotation(view, 360*position);
        }
        else if (position <= 1) { // (0,1]
            // Fade the page out.

            ViewHelper.setAlpha(view,1 - position);
            ViewHelper.setRotation(view, 360*position);
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            ViewHelper.setScaleX(view,scaleFactor);
            ViewHelper.setScaleY(view,scaleFactor);
        } else { // (1,+Infinity]
            ViewHelper.setAlpha(view,0);
        }
    }
}