package com.nowlauncher.nowlauncher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
//import android.support.v4.content.

import android.util.Log;

import com.viewpagerindicator.UnderlinePageIndicator;

public class MainActivity extends Activity {


	// Drop Down Bar (drawer open bar)
	ImageView drawerbar;

	// Layout master
	public RelativeLayout rootlayout;

	// Value of offset of status bar
	public int statusBarOffset;

	// DisplayMetrics object
	public DisplayMetrics dm;

	// Array of all application in the device
	public ArrayList<AppInfo> mApplications;

	//Variabili per il controllo della selezione della barra e del touch
	public boolean checkbarpressed = (boolean) false;
	public int y;
	public int yiniziale=0;
	public boolean yinizialebool = (boolean) false;
	public RelativeLayout rootlayoutdrawer;

	//Variabili per il controllo della selezione delle icone sulla dock
	public ImageView dockapp1;
	public ImageView dockapp2;
	public ImageView dockapp3;
	public ImageView dockapp4;
	public boolean checkdockapp1 = (boolean) false;
	public boolean checkdockapp2 = (boolean) false;
	public boolean checkdockapp3 = (boolean) false;
	public boolean checkdockapp4 = (boolean) false;
	public int x;


    ArrayList[]splittedarray;

	public MainService mService;
	
	/** Defines callbacks for service binding, passed to bindService() */
	public ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
		    // We've bound to MainService, cast the IBinder and get MainService instance
		    MainService.MainBinder binder = (MainService.MainBinder) service;
		    mService = binder.getService();
		    Log.d("com.nowlauncher.nowlauncher", "Server Connected");
		}

		@Override
		public void onServiceDisconnected(ComponentName compname) {
		    Log.d("com.nowlauncher.nowlauncher", "Server Disconnected");
		
		}
	
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		//Imposta gli eventi OnTouch della barra del drawer

		drawerbar = (ImageView) findViewById(R.id.drawer_bar);
		rootlayout = (RelativeLayout) findViewById(R.id.rootLayout);
		rootlayoutdrawer = (RelativeLayout) findViewById(R.id.drawer_rootlayout);
		drawerbar.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					//imposta il booleano su true quando la barra è premuta
					checkbarpressed=true;
				}

			return false;

			}

		});

        // Carica il layout del drawer conforme alle impostazioni e carica la lista delle applicazioni

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPrefs.getString("drawer_orientation", "NULL").equals("1")) {
            findViewById(R.id.drawerlist).setVisibility(View.VISIBLE);
            findViewById(R.id.pager).setVisibility(View.GONE);
            new ListDrawer().execute("");
            //Toast toast = Toast.makeText(getApplicationContext(), "Vertical", Toast.LENGTH_SHORT);
            //toast.show();

        }
        else if (sharedPrefs.getString("drawer_orientation", "NULL").equals("2"))
        {
            //Toast toast = Toast.makeText(getApplicationContext(), "Horizontal", Toast.LENGTH_SHORT);
            //toast.show();
            new ListDrawerscroll().execute("");
        }


        //Tasto impostazioni nel drawer

        ImageView settings_button = (ImageView) findViewById(R.id.settingsbtn);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });



		//Imposta gli eventi OnTouch delle icone della dock

		dockapp1 = (ImageView) findViewById(R.id.dockapp1);
		dockapp2 = (ImageView) findViewById(R.id.dockapp2);
		dockapp3 = (ImageView) findViewById(R.id.dockapp3);
		dockapp4 = (ImageView) findViewById(R.id.dockapp4);
		dockapp1.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				checkdockapp1=true;
				return false;
			}
		});

		
		// Bind to MainService
		Intent intent = new Intent(this, MainService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

	}

	public void CreateViews() {

		/**
		* EN
		* Assign the adapter to grid view and set on click listener
		*
		* IT
		* Assegna l'adapter alla grid view e dichiara la funzione all'evento onItemClick
		*/

		GridView gridview = (GridView) findViewById(R.id.drawerlist);
		gridview.setAdapter(new ApplicationsAdapter(this, mApplications));

		gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                AppInfo app = mApplications.get(position);
                startActivity(app.getIntent());
            }

        });
		
		}

    public void CreateViewsscroll() {

        //Divide la lista delle applicazioni in altri array, creando la lista delle app delle varie pagine

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        double numappxpage;
        numappxpage=Integer.parseInt(sharedPrefs.getString("drawer_rows", "4"))*Integer.parseInt(sharedPrefs.getString("drawer_coloums", "4"));
        int npage = (int) StrictMath.ceil(mApplications.size()/numappxpage);


        splittedarray = new ArrayList[npage];
        for (int i=0; i<npage; i++) {
            splittedarray[i] = new ArrayList();
        }
        for (int i=0; i<npage; i++) {
            for (int app=0; app<numappxpage; app++) {
                if (i==0) splittedarray[i].add(mApplications.get(app));
                else if (app+i*numappxpage==mApplications.size()) break;
                else  splittedarray[i].add(mApplications.get(app+i*(int)numappxpage));

            }
        }

        //Controlla le impsotazioni e carica l'animazione corrispondente
        //Problema con API 10 - è come se non settasse nessuna animazione (da controllare uso libreria nineoldandroids)


        findViewById(R.id.drawerlist).setVisibility(View.GONE);
        findViewById(R.id.pager).setVisibility(View.VISIBLE);
        MyPagerAdapter adapter = new MyPagerAdapter(this, splittedarray, getApplicationContext());
        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
        UnderlinePageIndicator titleIndicator = (UnderlinePageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(myPager);
        if (sharedPrefs.getString("drawer_animation", "NULL").equals("2")) {
            myPager.setPageTransformer(true, new DepthPageTransformer());
        }
        else if (sharedPrefs.getString("drawer_animation", "NULL").equals("3")) {
            myPager.setPageTransformer(true, new ZoomOutPageTransformer());
        }

    }


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		x = (int) event.getX();
		y = (int) event.getY();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//Gestione barra drawer

		if (checkbarpressed==true) {
            drawerbar= (ImageView) findViewById(R.id.drawer_bar);
            drawerbar.setImageResource(R.drawable.bar_pressed);

			if (yinizialebool==false) {
				//imposta la coordinata y iniziale per il successivo controllo dello "spostamento" del dito
				yiniziale=y;
				yinizialebool=true;
			}
			// Questa variabile (statusBarOffset) serve per passare dai valori di y assoluti a quelli relativi (per relativi intendo rispetto al rootlayout, quindi una view può essere per esempio in assoluto a 10y mentre relativamente a 5y perchè c'è la barra di stato cioè dista 10 dp dall' Top dello scermo e 5 dp dal Top del root layout)
			int statusBarOffset = dm.heightPixels - rootlayout.getMeasuredHeight();
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

			if (y-(drawerbar.getHeight()/2)-statusBarOffset <= 0) params.topMargin=0;
			else if (y-statusBarOffset >= rootlayout.getHeight()-(drawerbar.getHeight()/2)) params.topMargin=rootlayout.getHeight()-drawerbar.getHeight();
			else params.topMargin=y-statusBarOffset-(drawerbar.getHeight()/2); // oltre a sotrarre l'offset ho sotratto anche meta della grandezza della barra per fare in modo che la barra si imposti non con il Top sul dito ma con la meta sul dito come è più naturale quindi

			//params.topMargin=y-drawerbar.getHeight();
			rootlayoutdrawer.setLayoutParams(params);
		}

		//Gestione icona dock 1

		if (checkdockapp1==true) {
			RelativeLayout.LayoutParams dock1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			dock1.width=x;
			dockapp1.setLayoutParams(dock1);
			/*if (x>=centreapp1.getLeft()) {
				//AZIONE
			}*/
		}

		if(event.getAction() == MotionEvent.ACTION_UP) {
			yinizialebool=false;

			if (checkbarpressed==true) {
                drawerbar.setImageResource(R.drawable.bar_normal);
				checkbarpressed=false;
				final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
				if (yiniziale>y) params.topMargin = 0;
				else params.topMargin = rootlayout.getHeight()-drawerbar.getHeight();


				int originalPos[] = new int[2];
				rootlayoutdrawer.getLocationOnScreen( originalPos );

				Animation animation = new TranslateAnimation(0, 0, 0, 0-(rootlayoutdrawer.getTop()-params.topMargin));
				// il costruttore prevede 4 int, x iniziale, x finale, y iniziale e y finale. Tutte le coordinate sono relative al punto in cui si trova la view prima dell'animazione (es. 0,0,0,10 sposterà la view di 10dp in alto)

				// A better form is:
				// TraslateAnimation animation = new TranslateAnimation(0, 0, 0, -originalPos[1]);
				// or
				// TraslateAnimation animation = new TranslateAnimation(0, 0, 0, -(dropdownbar2.geTop()));
				animation.setDuration(300);
				animation.setFillAfter(false); // <-- fa in modo che a fine animazione la view rimanga nel posto e non ritorni al posto iniziale
				rootlayoutdrawer.startAnimation(animation);
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

						rootlayoutdrawer.setLayoutParams(params);

					}
				});
				
			}
		}
		
		//checkbarpressed=false;
		checkdockapp1=false;
		checkdockapp2=false;
		checkdockapp3=false;
		checkdockapp4=false;
		

		return false;
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case R.id.menu_settings:
				Intent i = new Intent(this, Settings.class);
				startActivity(i);
				break;

		}

		return true;

	}
	
	protected class ListDrawer extends AsyncTask<String, Void, String> {
	  
		ProgressBar pgbar = (ProgressBar) findViewById(R.id.drawerProgressBar);

		protected String doInBackground(String... params) {

			Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
			mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

			PackageManager manager = getPackageManager();

			final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
			Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));

			if (apps != null) {

				if (mApplications == null) {
					mApplications = new ArrayList<AppInfo>();
				}

				mApplications.clear();

				ListIterator<ResolveInfo> i=apps.listIterator();
				while(i.hasNext()) {
				  
					ResolveInfo info = i.next();
					AppInfo application = new AppInfo(getApplicationContext(), info, manager);
					
					application.loadTitle();
					application.loadIcon();
					application.loadIntent();
					
					mApplications.add(application);

				}
			}
			
			return null;
		}

		protected void onPostExecute(String result) {
			CreateViews();
			pgbar.setVisibility(View.GONE);
		}
	}
    protected class ListDrawerscroll extends AsyncTask<String, Void, String> {

        ProgressBar pgbar = (ProgressBar) findViewById(R.id.drawerProgressBar);

        protected String doInBackground(String... params) {

            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            PackageManager manager = getPackageManager();

            final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
            Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));

            if (apps != null) {

                if (mApplications == null) {
                    mApplications = new ArrayList<AppInfo>();
                }

                mApplications.clear();

                ListIterator<ResolveInfo> i=apps.listIterator();
                while(i.hasNext()) {

                    ResolveInfo info = i.next();
                    AppInfo application = new AppInfo(getApplicationContext(), info, manager);

                    application.loadTitle();
                    application.loadIcon();
                    application.loadIntent();
                    mApplications.add(application);

                }
            }

            return null;
        }

        protected void onPostExecute(String result) {
            CreateViewsscroll();
            pgbar.setVisibility(View.GONE);
        }
    }
	
	/*

	Funzione di esempio per ottenere i valori nelle impostazioni:

	private void getSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		String string = sharedPrefs.getString("title", "string returned if empty"); // ottiene la stringa dell'edit_text_preference con title="title"

		boolean checkBox = sharedPrefs.getBoolean("title", false); // ottiene il valore del checkbox di title="title"


	}

	*/




}

