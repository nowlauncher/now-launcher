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
	private ArrayList<AppInfo> mApplications;

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
		
		/**
		* EN
		* Load array of applications and set up drawer
		*
		* IT
		* Carica la lista delle applicazioni e carica il drawer
		*/

		//showUserSettings();
		new ListDrawer().execute("");

<<<<<<< HEAD
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

    public ImageView settingsImage;


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



        /**
         * EN
         * Load array of applications and set up drawer
         *
         * IT
         * Carica la lista delle applicazioni e carica il drawer
         */


        //showUserSettings();
        new ListDrawer().execute("");

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
                startActivity(app.intent);
            }

        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        x = (int) event.getX();
        y = (int) event.getY();
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //Gestione barra drawer

        if (checkbarpressed==true) {
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
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                if (yiniziale>y){
                    params.topMargin = 0;
                }

                else {
                    params.topMargin = rootlayout.getHeight()-drawerbar.getHeight();
                }


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
            checkbarpressed=false;
            checkdockapp1=false;
            checkdockapp2=false;
            checkdockapp3=false;
            checkdockapp4=false;
        }

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
            pgbar.setVisibility(View.VISIBLE);
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
=======
	}
>>>>>>> 163a4ba517ca6358ec58f1b729cd96ca56d7c5fd

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


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		x = (int) event.getX();
		y = (int) event.getY();
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//Gestione barra drawer

		if (checkbarpressed==true) {
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
		  
			//pgbar.setVisibility(View.VISIBLE);
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
	
	/*

	Funzione di esempio per ottenere i valori nelle impostazioni:

	private void getSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		String string = sharedPrefs.getString("title", "string returned if empty"); // ottiene la stringa dell'edit_text_preference con title="title"

		boolean checkBox = sharedPrefs.getBoolean("title", false); // ottiene il valore del checkbox di title="title"


	}

	*/




}
