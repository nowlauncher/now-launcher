package com.nowlauncher.nowlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

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
     CharSequence label;
     Drawable icon;
     ComponentName componentName;
     Intent intent;
 }