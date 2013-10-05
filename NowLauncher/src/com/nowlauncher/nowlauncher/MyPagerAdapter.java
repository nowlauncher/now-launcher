package com.nowlauncher.nowlauncher;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by andrea on 14/06/13.
 */
public class MyPagerAdapter extends PagerAdapter {
    GridView[] mGridViewsArray;
    public MyPagerAdapter(GridView[] gridViewsArray) {
        mGridViewsArray=gridViewsArray;
    }
    public int getCount() {
        return mGridViewsArray.length;
    }
    public Object instantiateItem(ViewGroup collection, int positionpage) {
        ((ViewPagerAnim) collection).addView(mGridViewsArray[positionpage], 0);
        return mGridViewsArray[positionpage];
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPagerAnim) arg0).removeView((View) arg2);
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
}
