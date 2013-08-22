package com.nowlauncher.nowlauncher;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by andrea on 14/06/13.
 */
public class MyPagerAdapter extends PagerAdapter {
    Context mContext;
    GridView[] mGridViewsArray;
    boolean[] mGridViewsArrayAlreadyAdded;
    public MyPagerAdapter(Context context, GridView[] gridViewsArray) {
        mContext=context;
        mGridViewsArray=gridViewsArray;
        mGridViewsArrayAlreadyAdded=new boolean[mGridViewsArray.length];
    }
    public int getCount() {
        return mGridViewsArray.length;
    }
    public Object instantiateItem(ViewGroup collection, int positionpage) {

        Log.d("pager","instantiateItem");
        ((ViewPager) collection).addView(mGridViewsArray[positionpage], 0);
        return mGridViewsArray[positionpage];
    }
    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }
}
