package com.nowlauncher.nowlauncher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.GridView;
import android.graphics.drawable.Drawable;
import android.graphics.Bitmap;
import android.widget.BaseAdapter;  
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.lang.String;
import java.lang.Class;

 /**
 * EN
 * GridView adapter to show the list of all installed applications
 */
 
 /**
 * IT
 * GridView adapter per mostrare la lista delle applicazioni installate
 */

public class ApplicationsAdapter extends BaseAdapter {
	
	/**
	* EN
	* Context of activity
	*
	* IT
	* Context dell'activity
	*/
	private Context mContext;
	
	/**
	* EN
	* App info array
	*
	* IT
	* App info array
	*/
	private ArrayList<AppInfo> mApplications;
    private GridView mgridView;

	private AppInfo info;
  
	/**
	 * EN
	 * getItem and getItemId and required by structure but are useless
	 * 
	 * IT
	 * le funzioni getItem e getItemId sono richieste dalla struttura ma sono inutilizzate
	 */
	
	public Object getItem(int position) {  
		return null;  
	}
	
	public long getItemId(int position) {  
		return position;  
	}  
	
	/**
	* EN
	* Costructor, get context and Applications array
	*
	* @param context the context of activity
	* @param Applications the array of applications
	*
	* IT
	* Costruttore, permette di ottenere il context e l'array delle applicazioni
	*
	* @param context il context dell'activity
	* @param Applications l'arrat delle applicazioni
	*/
	public ApplicationsAdapter(Context context, ArrayList<AppInfo> Applications, GridView gridView) {
	    mContext = context;  
	    mApplications = Applications;
        mgridView=gridView;
	} 

	/**
	* EN
	* Get the number of views
	*
	* IT
	* Ottiene il numero di views
	*/
	public int getCount() {  
	    return mApplications.size();
	}

     private boolean isRuntimePostapi16() {
         return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
     }
	/**
	* EN
	* Create the view
	*
	* IT
	* Crea la view
	*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		info = mApplications.get(position);
            
		BitmapDrawable icon = (BitmapDrawable)info.getIcon();
        Bitmap bitmapOrig;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        // Display density chooser
        if (dm.densityDpi == DisplayMetrics.DENSITY_LOW)
            bitmapOrig = Bitmap.createScaledBitmap(icon.getBitmap(), 36, 36, false);
        else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM)
            bitmapOrig = Bitmap.createScaledBitmap(icon.getBitmap(), 48, 48, false);
        else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH)
            bitmapOrig = Bitmap.createScaledBitmap(icon.getBitmap(), 72, 72, false);
        else if (dm.densityDpi == DisplayMetrics.DENSITY_XHIGH)
            bitmapOrig = Bitmap.createScaledBitmap(icon.getBitmap(), 96, 96, false);
        else bitmapOrig = Bitmap.createScaledBitmap(icon.getBitmap(), 72, 72, false);
        icon = new BitmapDrawable(bitmapOrig);

		final TextView textview = new TextView(mContext);
		textview.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
		textview.setText(info.getTitle());
		textview.setGravity(17);
		//textview.setPadding(0, 0, 10, 20);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        int numrow=Integer.parseInt(sharedPrefs.getString("drawer_rows", "4"));
        if (isRuntimePostapi16()) textview.setHeight((mgridView.getHeight()-(numrow+1)*mgridView.getVerticalSpacing())/numrow);
        else textview.setHeight(mgridView.getHeight()/numrow);
        final Intent appintent=info.getIntent();
        textview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(appintent);

            }
        });

		return textview;
                        
	}
}