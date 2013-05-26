package com.nowlauncher.nowlauncher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import android.view.ViewGroup;
//import android.view.animation.TranslateAnimation;
//import android.widget.Button;
//import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {
	
	public int TOLLERANCE_TOP = 70;
	public int TOLLERANCE_BOTTOM = 70;
	
	public RelativeLayout rootlayout;
	public ImageView dropdownbar2;
	public int x;
	public int y;
	public int statusBarOffset;
	public DisplayMetrics dm;
	public TextView right;
	public TextView below;
	public TextView first;
	public List<ApplicationInfo> packages;
	public LinearLayout row;
	
	private ArrayList<AppInfo> mApplications;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        rootlayout = (RelativeLayout) findViewById(R.id.rootLayout);

        dropdownbar2 = (ImageView) findViewById(R.id.DropDownBar2);
        
        dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        
	LoadApplication();
	CreateViews();
	          
	}
    
	public void CreateViews() {
      
		GridView gridview = (GridView) findViewById(R.id.drawer);
		gridview.setAdapter(new ApplicationsAdapter(this, mApplications));
        
		gridview.setOnItemClickListener(new OnItemClickListener() {
			
		@SuppressWarnings("rawtypes")
		public void onItemClick(AdapterView parent, View v, int position, long id) {
			
			AppInfo app = mApplications.get(position);
			startActivity(app.intent);
		}
		
		});
      
      }
    
    public void LoadApplication() {
      
       PackageManager manager = getPackageManager();
      
       Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
       mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

       final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
       Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
      
       if (apps != null) {
           final int count = apps.size();

           if (mApplications == null)
               mApplications = new ArrayList<AppInfo>(count);
           mApplications.clear();      
      
           for (int i = 0; i < count; i++) {
        	   AppInfo application = new AppInfo();
        	   ResolveInfo info = apps.get(i);

        	   application.title = info.loadLabel(manager);
        	   application.setIntent(new ComponentName(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        
                           
        	   Bitmap d = ((BitmapDrawable)info.activityInfo.loadIcon(manager)).getBitmap();
		
        	   Bitmap bitmapOrig;
		
        	   DisplayMetrics metrics = new DisplayMetrics();
        	   getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
        	   // Display density chooser
        	   if (metrics.densityDpi == DisplayMetrics.DENSITY_LOW)		  
        		   bitmapOrig = Bitmap.createScaledBitmap(d, 36, 36, false);
        	   else if (metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM)			
        		   bitmapOrig = Bitmap.createScaledBitmap(d, 48, 48, false);			
        	   else if (metrics.densityDpi == DisplayMetrics.DENSITY_HIGH) 			
        		   bitmapOrig = Bitmap.createScaledBitmap(d, 72, 72, false);
        	   else if (metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH)
        		   bitmapOrig = Bitmap.createScaledBitmap(d, 96, 96, false);
        	   else bitmapOrig = Bitmap.createScaledBitmap(d, 72, 72, false);
		
        	   application.icon = new BitmapDrawable(bitmapOrig);
               
        	   mApplications.add(application);
        	 
           	}       
       	}	      
    }	
      
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	       	   	
            int y = (int) event.getY();
            
            if ((y >= (dropdownbar2.getTop() - TOLLERANCE_TOP ) ) & ( y <= (dropdownbar2.getBottom() + TOLLERANCE_BOTTOM ))) {
		  
            	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            
            	statusBarOffset = dm.heightPixels - rootlayout.getHeight();
            

            	if ((rootlayout.getHeight() - (y - statusBarOffset)) <= dropdownbar2.getHeight())
            		params.topMargin = rootlayout.getHeight() - dropdownbar2.getHeight();
            	else if ((y - statusBarOffset) <= dropdownbar2.getHeight())            
            		params.topMargin = 0;
            	else params.topMargin = y - statusBarOffset - dropdownbar2.getHeight()/2;
            
            	dropdownbar2.setLayoutParams(params);
        
            }
        
        return false;
   }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    	
    public void Launch(Intent intent) { startActivity(intent); }
    
    
}
