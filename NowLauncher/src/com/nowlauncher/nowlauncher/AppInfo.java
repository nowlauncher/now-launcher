package com.nowlauncher.nowlauncher;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

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
     public Map<String,Integer>applicationIconPacks=new HashMap<String, Integer>();
     public AppInfo(){
         applicationIconPacks.put("com.android.calculator2",R.drawable.calculator);
         applicationIconPacks.put("com.google.android.calendar",R.drawable.calendar);
         applicationIconPacks.put("com.android.calendar",R.drawable.calendar);
         applicationIconPacks.put("com.android.camera",R.drawable.camera);
         applicationIconPacks.put("com.android.chrome",R.drawable.chrome);
         applicationIconPacks.put("com.google.android.deskclock",R.drawable.clock);
         applicationIconPacks.put("com.android.deskclock",R.drawable.clock);
         applicationIconPacks.put("com.android.contacts",R.drawable.contacts);
         applicationIconPacks.put("com.google.android.gm",R.drawable.gmail);
         applicationIconPacks.put("com.google.android.apps.plus",R.drawable.googleplus);
         applicationIconPacks.put("com.google.android.googlequicksearchbox",R.drawable.google);
         applicationIconPacks.put("com.instagram.android",R.drawable.instagram);
         applicationIconPacks.put("com.google.android.apps.maps",R.drawable.maps);
         applicationIconPacks.put("com.android.mms",R.drawable.messages);
         applicationIconPacks.put("com.android.vending",R.drawable.playstore);
         applicationIconPacks.put("com.android.settings",R.drawable.settings);
         applicationIconPacks.put("com.android.browser",R.drawable.stockbrowser);
         applicationIconPacks.put("packagename_uguale_a_contacts",R.drawable.telephone);
         applicationIconPacks.put("com.twitter.android",R.drawable.twitter);
         applicationIconPacks.put("packagename_video",R.drawable.video);
     }
 }