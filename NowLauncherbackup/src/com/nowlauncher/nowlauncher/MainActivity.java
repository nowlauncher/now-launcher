package com.nowlauncher.nowlauncher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	/**
	* EN
	* Tollereance on touch of drop down bar
	*
	* IT
	* Tolleranza nel touch della drop down bar.
	* significa che viene cosiderato un movimento di questa barra un tocco distante sopra e sotto il valore della tolleranza
	*/
	
	public int TOLLERANCE_TOP = 70;
	public int TOLLERANCE_BOTTOM = 70;
	
	/**
	* EN
	* Declaration of some variables
	*
	* IT
	* Dichiarazione di alcune variabili
	*/
	
	// Drop Down Bar (drawer open bar)
	View dropdownbar2;
	
	// Layout master
	public RelativeLayout rootlayout;
	
	// Value of offset of status bar
	public int statusBarOffset;
	
	// DisplayMetrics object
	public DisplayMetrics dm;
	
	// Array of all application in the device
	private ArrayList<AppInfo> mApplications;

    //Variabili per il controllo della selezione della barra e del touch
    public boolean checkbarpressed = (boolean) false;
    public int y;
    public int yiniziale=0;
    public boolean yinizialebool = (boolean) false;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  
		super.onCreate(savedInstanceState);
		
		/**
		* EN
		* Set up layout
		*
		* IT
		* Carica il layout
		*/
		
		setContentView(R.layout.main_activity);
		
		/**
		* EN
		* Istance some variables
		*
		* IT
		* Istanzia alcune variabili
		*/
		
		dropdownbar2 = (ImageView) findViewById(R.id.DropDownBar2);
		rootlayout = (RelativeLayout) findViewById(R.id.rootLayout);
        dropdownbar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //imposta il booleano su true quando la barra è premuta
                    checkbarpressed=true;
                }
                return false;
            }
        });


                /**
                 * EN
                 * Load array of applications and set up drawer
                 *
                 * IT
                 * Carica la lista delle applicazioni e carica il drawer
                 */

                LoadApplication();
		CreateViews();
	          
	}
    
	public void CreateViews() {
	
		/**
		* EN
		* Assign the adapter to grid view and set on click listener
		*
		* IT
		* Assegna l'adapter alla grid view e dichiara la funzione all'evento onItemClick
		*/
	
		GridView gridview = (GridView) findViewById(R.id.drawer);
		gridview.setAdapter(new ApplicationsAdapter(this, mApplications));
        
		gridview.setOnItemClickListener(new OnItemClickListener() {
		
		public void onItemClick(AdapterView parent, View v, int position, long id) {
			
			AppInfo app = mApplications.get(position);
			startActivity(app.intent);
		}
		
		});
      
	}
    
	public void LoadApplication() {
	
		/**
		* EN
		* Get the list of applications by searching with intent category
		*
		* @param apps List of ResolveInfo, useful objects for getting applications informations 
		*
		* IT
		* Ottiene la lista delle applicazioni cercando mediante la categoria dell'intent
		*
		* @param apps Lista degli oggetti ResolveInfo, oggetti utili per ottenere delle informazioni sulle applicazioni 
		*/
	
      
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		PackageManager manager = getPackageManager();
	
		final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
		Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
      
		if (apps != null) {
			//final int count = apps.size();

			if (mApplications == null) {
				mApplications = new ArrayList<AppInfo>();				
			}
				
			mApplications.clear();
      
			ListIterator<ResolveInfo> i=apps.listIterator();
			while(i.hasNext()) {
				AppInfo application = new AppInfo();
				ResolveInfo info = i.next();

				application.title = info.loadLabel(manager);
				application.setIntent(new ComponentName(
				info.activityInfo.applicationInfo.packageName,
				info.activityInfo.name),
				Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                        
				Bitmap d = ((BitmapDrawable)info.activityInfo.loadIcon(manager)).getBitmap();
		
				Bitmap bitmapOrig;
		
				dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
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
		
				application.icon = new BitmapDrawable(bitmapOrig);
               
				mApplications.add(application);
        	 
			}      
		}
	
	}
	
      
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		/**
		* EN
		* Get y value, calculate if touch is in tollerance zone of drop down bar
		*
		* IT
		* Ottiene il valore di y e calcola se il touch è nella zona di tolleranza della drop down bar
		*/
		
	    y = (int) event.getY();

        if (checkbarpressed==true) {
            if (yinizialebool==false) {
                //imposta la coordinata y iniziale per il successivo controllo dello "spostamento" del dito
                yiniziale=y;
                yinizialebool=true;
            }

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

			statusBarOffset = dm.heightPixels - rootlayout.getHeight();


			/**
			* EN
			* If the touch is over the minimal bar position before compression set it in the minimal position
			*
			* IT
			* Se il touch è oltre la posizione minima della barra prima di ridursi di altezza la barra viene posizionata nel punto minimo
			*/

            //Qui non ho capito molto bene l'algoritmo ma funziona... quindi! :D (dema121)

			if ((rootlayout.getHeight() - (y - statusBarOffset)) <= dropdownbar2.getHeight())
				params.topMargin = rootlayout.getHeight() - dropdownbar2.getHeight();
			else if ((y - statusBarOffset) <= dropdownbar2.getHeight())            
				params.topMargin = 0;
			else params.topMargin = y - statusBarOffset - dropdownbar2.getHeight()/2;
            
			dropdownbar2.setLayoutParams(params);

		}
        if(event.getAction() == MotionEvent.ACTION_UP) {
            yinizialebool=false;
            if (checkbarpressed==true) {

                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                if (yiniziale>y){
                    params.topMargin = 0;
                }
                else {
                    params.topMargin = rootlayout.getHeight()-dropdownbar2.getHeight();
                }


                int originalPos[] = new int[2];
                dropdownbar2.getLocationOnScreen( originalPos );

                Animation animation = new TranslateAnimation(0, 0, 0, 0-(dropdownbar2.getTop()-params.topMargin));

                // il costruttore prevede 4 int, x iniziale, x finale, y iniziale e y finale. Tutte le coordinate sono relative al punto in cui si trova la view prima dell'animazione (es. 0,0,0,10 sposterà la view di 10dp in alto)

                // A better form is:
                // TraslateAnimation animation = new TranslateAnimation(0, 0, 0, -originalPos[1]);
                // or
                // TraslateAnimation animation = new TranslateAnimation(0, 0, 0, -(dropdownbar2.geTop()));

                animation.setDuration(300);
                animation.setFillAfter(false); // <-- fa in modo che a fine animazione la view rimanga nel posto e non ritorni al posto iniziale
                dropdownbar2.startAnimation(animation);
                animation.setInterpolator(new AccelerateInterpolator(1));

                animation.setAnimationListener(new Animation.AnimationListener(){



                    @Override
                    public void onAnimationStart(Animation anim) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation anim) {

                    }

                    @Override
                    public void onAnimationEnd(Animation anim) {

                        dropdownbar2.setLayoutParams(params);

                    }
                });
            checkbarpressed=false;
            }
		}
        return false;
    }
        
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	  
    
}
