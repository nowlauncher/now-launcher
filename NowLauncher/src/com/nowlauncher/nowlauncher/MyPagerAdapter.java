package com.nowlauncher.nowlauncher;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by andrea on 14/06/13.
 */
public class MyPagerAdapter extends PagerAdapter {
    Activity activity;
    ArrayList[] pagearray;
    Context acontext;
    public MyPagerAdapter(Activity act, ArrayList[] pagear, Context context) {
        pagearray = pagear;
        activity = act;
        acontext=context;
    }
    public int getCount() {
        return pagearray.length;
    }
    public Object instantiateItem(ViewGroup collection, int positionpage) {

        //Crea la gridview e assegna i vari attributi, l'adapter, l'evento Click e la aggiunge al ViewPager
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(acontext);
        GridView mygridview = new GridView(activity);
        mygridview.setNumColumns(Integer.parseInt(sharedPrefs.getString("drawer_coloums", "4")));
        mygridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mygridview.setVerticalSpacing(5);
        mygridview.setHorizontalSpacing(2);
        mygridview.setAdapter(new ApplicationsAdapter(activity, pagearray[positionpage], mygridview));
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id) {
               //intent
            }

        });

        ((ViewPager) collection).addView(mygridview, 0);
        return mygridview;
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
