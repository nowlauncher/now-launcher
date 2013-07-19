package com.nowlauncher.nowlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

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
	
	
	/*
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
        return mInfo.loadLabel(mPm);
	}
	public final Drawable getIcon() {

        return mInfo.loadIcon(mPm);
	}
	
	public final Intent getIntent() {
        return mPm.getLaunchIntentForPackage(mInfo.activityInfo.packageName);
	}
	
}