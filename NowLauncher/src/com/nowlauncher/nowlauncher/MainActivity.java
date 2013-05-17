package com.nowlauncher.nowlauncher;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
//import android.widget.Button;
//import android.widget.RelativeLayout.LayoutParams;
import android.util.DisplayMetrics;
//import android.util.Log;

public class MainActivity extends Activity {
	
	public RelativeLayout rl;
	public ImageView iv;
	public int x;
	public int y;
	public DisplayMetrics dm;
	public int statusBarOffset;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rl = (RelativeLayout) findViewById(R.id.rootLayout);

        iv = (ImageView) findViewById(R.id.DropDownBar2);
        
        dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics( dm );
        
	}
        
        @Override
    public boolean onTouchEvent(MotionEvent event) {
            int y = (int) event.getY();
            
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            
            statusBarOffset = dm.heightPixels - rl.getHeight();
            
            params.topMargin = y - statusBarOffset;
 
            if ((rl.getHeight() - (y - statusBarOffset)) <= iv.getHeight()) {
            	return false;
            }
            
            if ((y - statusBarOffset) <= 0) {
            	
            	return false;
            }
            
            rl.removeView(iv);
            rl.addView(iv, params);	
            
        return false;
        }
        
	         
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
}