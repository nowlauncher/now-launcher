package com.nowlauncher.nowlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * EN
 * Represents a launchable application. An application is made of a name (or title), an intent
 * and an icon.
 **/
 
 /** 
 * IT
 * Rappresenta un applicazione lanciabile. Un applicazione è composta da un nome (o titolo) un intent e un icona
 **/
 
public class AppInfo {
	
	/**
	* EN
	* Application title
	*
	* IT
	* Nome dell'applicazione
	**/
	private CharSequence mTitle;
	
	/**
	* EN
	* The intent used to start the application
	*
	* IT
	* L'intent usato per lanciare l'applicazione
	*/
	private Intent mIntent;

	/**
	* EN
	* The application icon
	*
	* IT
	* L'icona dell'applicazione
	*/
	private Drawable mIcon;
	private ResolveInfo mInfo;
	private PackageManager mPm;
	private Context mContext;
	
	
	/**
	* EN
	* Creates the application intent based on a component name and various launch flags
	*
	* @param className the class name of the component representing the intent
	* @param launchFlags the launch flags
	*
	* IT
	* Crea l'intent dell'applicazione basandoti nel ComponentName e con alcuni flag
	*
	* @param className la classe che rappresenta il component dell'intent
	* @param launchFlags i flag(parametri) di lancio
	*/	
	
	public AppInfo(Context context, ResolveInfo info, PackageManager pm) {
	  
		mInfo = info;
		mPm = pm;
		mContext = context;
	  
	}
	
	public final CharSequence getTitle() {
	
		if (mTitle == null) mTitle = mInfo.loadLabel(mPm);
		return mTitle;
	  
	}
	
	public final Drawable getIcon() {
	  
		if (mIcon == null) {
		 
			
			Bitmap d = ((BitmapDrawable)mInfo.loadIcon(mPm)).getBitmap();

			Bitmap bitmapOrig;
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
			// Display density chooser
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW)
			bitmapOrig = Bitmap.createScaledBitmap(d, 36, 36, false);
			else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM)
			bitmapOrig = Bitmap.createScaledBitmap(d, 48, 48, false);
			else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH)
			bitmapOrig = Bitmap.createScaledBitmap(d, 72, 72, false);
			else if (dm.densityDpi == DisplayMetrics.DENSITY_XHIGH)
			bitmapOrig = Bitmap.createScaledBitmap(d, 96, 96, false);
			else bitmapOrig = Bitmap.createScaledBitmap(d, 72, 72, false);
			mIcon = new BitmapDrawable(bitmapOrig);
		}
		
		return mIcon;
	  
	}
	
	public final Intent getIntent() {
	  
		if (mIntent == null) mIntent = mPm.getLaunchIntentForPackage(mInfo.activityInfo.packageName);
		
		return mIntent;
	  
	}
	
	
	public final void loadTitle() {
	  
		if (mTitle == null) mTitle = mInfo.loadLabel(mPm);
	
	}
	
	
	  
	public final void loadIcon() {
	  
		if (mIcon == null) {
		 
			
			Bitmap d = ((BitmapDrawable)mInfo.loadIcon(mPm)).getBitmap();

			Bitmap bitmapOrig;
			DisplayMetrics dm = new DisplayMetrics();
			WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
			// Display density chooser
			if (dm.densityDpi == DisplayMetrics.DENSITY_LOW)
			bitmapOrig = Bitmap.createScaledBitmap(d, 36, 36, false);
			else if (dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM)
			bitmapOrig = Bitmap.createScaledBitmap(d, 48, 48, false);
			else if (dm.densityDpi == DisplayMetrics.DENSITY_HIGH)
			bitmapOrig = Bitmap.createScaledBitmap(d, 72, 72, false);
			else if (dm.densityDpi == DisplayMetrics.DENSITY_XHIGH)
			bitmapOrig = Bitmap.createScaledBitmap(d, 96, 96, false);
			else bitmapOrig = Bitmap.createScaledBitmap(d, 72, 72, false);
			mIcon = new BitmapDrawable(bitmapOrig);
		}
	  
	}
	
	public final void loadIntent() {
	  
		if (mIntent == null) mIntent = mPm.getLaunchIntentForPackage(mInfo.activityInfo.packageName);
	  
	}


	
}