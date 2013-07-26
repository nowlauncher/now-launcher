package com.nowlauncher.nowlauncher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v4.content.

import android.util.Log;
import android.widget.ViewFlipper;

import com.YahooWeather.tools.ImageUtils;
import com.YahooWeather.utils.WeatherInfo;
import com.YahooWeather.utils.YahooWeatherInfoListener;
import com.YahooWeather.utils.YahooWeatherUtils;
import com.nineoldandroids.view.ViewHelper;
import com.viewpagerindicator.UnderlinePageIndicator;

public class MainActivity extends Activity implements YahooWeatherInfoListener{


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

    /***
     * Metodo sostituito da SlidingDrawer
     */
	//Variabili per il controllo della selezione della barra e del touch
	public boolean checkbarpressed = false;
	public int y;
	public int yiniziale=0;
	public boolean yinizialebool = false;
	public RelativeLayout rootlayoutdrawer;


    //Variabili per il meteo
    public ImageView refreshwea;
    public TextView datelb;
    public TextView temperaturenow;

    private ImageView sliderWeather;
    private boolean sliderpressed = false;
    private boolean tempcheck=false;

    TextView todayweath;
    TextView forecast1;
    TextView forecast2;
    TextView forecast3;
    TextView forecast4;

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


    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private ViewFlipper vf;
    private Context mContext;
    private GestureDetector detector;
    private TextView citylb;
    ArrayList[]splittedarray;

	public MainService mService;

    private ImageView ivWeather0;
    private ImageView ivWeather1;
    private ImageView ivWeather2;
    private TextView tvWeather0;
    private TextView tvWeather1;
    private TextView tvWeather2;

	
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
    private boolean isRuntimePostGingerbread() {
        return Build.VERSION.SDK_INT  >= Build.VERSION_CODES.HONEYCOMB;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		//Imposta gli eventi OnTouch della barra del drawer


		//drawerbar = (ImageView) findViewById(R.id.drawer_bar);
		rootlayout = (RelativeLayout) findViewById(R.id.rootLayout);
		rootlayoutdrawer = (RelativeLayout) findViewById(R.id.drawer_rootlayout);

		/*drawerbar.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					//imposta il booleano su true quando la barra è premuta
					checkbarpressed=true;
				}

			return false;

			}

		});
*/
        RelativeLayout weatherCard=(RelativeLayout)findViewById(R.id.weatherCard);
/*
        RelativeLayout dockLayout=(RelativeLayout)findViewById(R.id.docklayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) (weatherCard.getHeight()+ TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics())), 0, 0);
        dockLayout.setLayoutParams(params);
*/
        sliderWeather=(ImageView)findViewById(R.id.weatherslider);
        final HorizontalScrollView forecastCard=(HorizontalScrollView)findViewById(R.id.forecastLayout);

        sliderWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //weathercard.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                if (!tempcheck) {
                    expand(forecastCard);
                    tempcheck=true;
                }
                else{
                    collapse(forecastCard);
                    tempcheck=false;
                }
            }
        });
        sliderWeather.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        // Carica il layout del drawer conforme alle impostazioni e carica la lista delle applicazioni

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPrefs.getString("drawer_orientation", "NULL").equals("1")) {
            findViewById(R.id.drawerlist).setVisibility(View.VISIBLE);
            findViewById(R.id.pager).setVisibility(View.GONE);
            new ListDrawer().execute("");

        }
        else if (sharedPrefs.getString("drawer_orientation", "NULL").equals("2"))
        {
            new ListDrawerscroll().execute("");
        }



        loadWeathercard();

        refreshwea=(ImageView)findViewById(R.id.weatherrefresh);
        refreshwea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadWeathercard();
            }
        });

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

	}
    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(300);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        //a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(300);
        v.startAnimation(a);
    }
    public void loadWeathercard()
    {
        //Imposta la data
        getDate();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        citylb=(TextView)findViewById(R.id.citybox);
        //Controlla se è selezionata l'opzione del luogo personalizzato
        if (!sharedPrefs.getBoolean("weather_customlocationcheck", false))
        {
            citylb.setText("Acquisizione posizione...");
            //Ricava la città in cui si è e aggiorna l'etichetta
            new GetPosition().execute("");
        }
        else
        {
            //Ricava la città personale e ricava il meteo
            citylb.setText(sharedPrefs.getString("weather_customlocation", getString(R.string.location_null)));
            getWeather((String) citylb.getText());
        }
    }
    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    private void getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM y");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        datelb=(TextView)findViewById(R.id.dateView);
        datelb.setText(currentDate);
    }
    private void getWeather(String location) {
        if (isOnline()==true){
            Log.d("YWeatherGetter4a", "onCreate");

            YahooWeatherUtils yahooWeatherUtils = YahooWeatherUtils.getInstance();
            yahooWeatherUtils.queryYahooWeather(getApplicationContext(), location, this);
        }
        else Toast.makeText(getApplicationContext(), "Sorry, no connection available", Toast.LENGTH_SHORT).show();

    }
    protected class GetPosition extends AsyncTask<String, Void,String> {
        protected String doInBackground(String... params) {

            List<Address> addresses = null;
            try {
                LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                addresses = gcd.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) return addresses.get(0).getLocality();
            } catch (Exception e) {
                return "Nessuna posizione disponibile";
            }
            return addresses.get(0).getLocality();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            citylb.setText(s);
            getWeather((String)citylb.getText());
        }
    }
    private void SwipeRight(){
        vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
        vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
        vf.showPrevious();
    }

    private void SwipeLeft(){
        vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
        vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
        vf.showNext();
    }
    /*

    //Test per ViewFlipper

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if((e1.getX() - e2.getX()) > sensitvity){
                SwipeLeft();
            }else if((e2.getX() - e1.getX()) > sensitvity){
                SwipeRight();
            }

            return true;
        }

    };
    GestureDetector gestureDetector = new GestureDetector(simpleOnGestureListener);
*/
    View addImageView(int resId)
    {
        ImageView iv = new ImageView(this);
        iv.setImageResource(resId);

        return iv;
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
		gridview.setAdapter(new ApplicationsAdapter(this, mApplications, gridview));
/*
		gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                AppInfo app = mApplications.get(position);
                startActivity(app.getIntent());
            }

        });
		*/
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

        //Controlla le impostazioni e carica l'animazione corrispondente
        //API <11 .setPageTransformer ignorato quindi niente animazioni custom.


        findViewById(R.id.drawerlist).setVisibility(View.GONE);
        findViewById(R.id.pager).setVisibility(View.VISIBLE);
        MyPagerAdapter adapter = new MyPagerAdapter(this, splittedarray, getApplicationContext());
        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);

        /******************************************************************************************************
         * Carica completamente le pagine del ViewPager ma è eseguito sul thread principale quindi freeza l'app
         *****************************************************************************************************/
        //myPager.setOffscreenPageLimit(npage);


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

       // return gestureDetector.onTouchEvent(event);
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
					/*
					application.loadTitle();
					application.loadIcon();
					application.loadIntent();
					*/
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
/*
                    application.loadTitle();
                    application.loadIcon();
                    application.loadIntent();
                    */
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

    public void gotWeatherInfo(WeatherInfo weatherInfo) {
        // TODO Auto-generated method stub
        if(weatherInfo != null) {
            /*
            TextView tv = (TextView) findViewById(R.id.textview_title);
            tv.setText(weatherInfo.getTitle() + "\n"
                    + weatherInfo.getLocationCity() + ", "
                    + weatherInfo.getLocationCountry());
            tvWeather0 = (TextView) findViewById(R.id.textview_weather_info_0);
            tvWeather0.setText("====== CURRENT ======" + "\n" +
                    "date: " + weatherInfo.getCurrentConditionDate() + "\n" +
                    "weather: " + weatherInfo.getCurrentText() + "\n" +
                    "temperature in ºC: " + weatherInfo.getCurrentTempC() + "\n" +
                    "temperature in ºF: " + weatherInfo.getCurrentTempF() + "\n" +
                    "wind chill in ºF: " + weatherInfo.getWindChill() + "\n" +
                    "wind direction: " + weatherInfo.getWindDirection() + "\n" +
                    "wind speed: " + weatherInfo.getWindSpeed() + "\n" +
                    "Humidity: " + weatherInfo.getAtmosphereHumidity() + "\n" +
                    "Pressure: " + weatherInfo.getAtmospherePressure() + "\n" +
                    "Visibility: " + weatherInfo.getAtmosphereVisibility()
            );
            */
            /*
            tvWeather1 = (TextView) findViewById(R.id.textview_weather_info_1);
            tvWeather1.setText("====== FORECAST 1 ======" + "\n" +
                    "date: " + weatherInfo.getForecast1Date() + "\n" +
                    "weather: " + weatherInfo.getForecast1Text() + "\n" +
                    "low  temperature in ºC: " + weatherInfo.getForecast1TempLowC() + "\n" +
                    "high temperature in ºC: " + weatherInfo.getForecast1TempHighC() + "\n" +
                    "low  temperature in ºF: " + weatherInfo.getForecast1TempLowF() + "\n" +
                    "high temperature in ºF: " + weatherInfo.getForecast1TempHighF() + "\n"
            );
            */
            /*
            tvWeather2 = (TextView) findViewById(R.id.textview_weather_info_2);
            tvWeather2.setText("====== FORECAST 2 ======" + "\n" +
                    "date: " + weatherInfo.getForecast1Date() + "\n" +
                    "weather: " + weatherInfo.getForecast2Text() + "\n" +
                    "low  temperature in ºC: " + weatherInfo.getForecast2TempLowC() + "\n" +
                    "high temperature in ºC: " + weatherInfo.getForecast2TempHighC() + "\n" +
                    "low  temperature in ºF: " + weatherInfo.getForecast2TempLowF() + "\n" +
                    "high temperature in ºF: " + weatherInfo.getForecast2TempHighF() + "\n"
            );
*/


            /**
             * Meteo con descrizione
             *
             */
            /*todayweath = (TextView)findViewById(R.id.todayweather);
            todayweath.setText("Today"+"\n"
                                +weatherInfo.getForecast1Text());

            forecast1 = (TextView)findViewById(R.id.forecast1);
            forecast1.setText(weatherInfo.getForecast2Day() +"\n"
                    +weatherInfo.getForecast2Text());

            forecast2 = (TextView)findViewById(R.id.forecast2);
            forecast2.setText(weatherInfo.getForecast3Day()+"\n"
                    +weatherInfo.getForecast3Text());

            forecast3 = (TextView)findViewById(R.id.forecast3);
            forecast3.setText(weatherInfo.getForecast4Day()+"\n"
                    +weatherInfo.getForecast4Text());

            forecast4 = (TextView)findViewById(R.id.forecast4);
            forecast4.setText(weatherInfo.getForecast5Day()+"\n"
                    +weatherInfo.getForecast5Text());*/

            /**
             * Meteo senza descrizione
             */
            todayweath = (TextView)findViewById(R.id.todayweather);
            todayweath.setText("Today");

            forecast1 = (TextView)findViewById(R.id.forecast1);
            forecast1.setText(weatherInfo.getForecast2Day());

            forecast2 = (TextView)findViewById(R.id.forecast2);
            forecast2.setText(weatherInfo.getForecast3Day());

            forecast3 = (TextView)findViewById(R.id.forecast3);
            forecast3.setText(weatherInfo.getForecast4Day());

            forecast4 = (TextView)findViewById(R.id.forecast4);
            forecast4.setText(weatherInfo.getForecast5Day());


            temperaturenow = (TextView)findViewById(R.id.temperaturenow);
            temperaturenow.setText(weatherInfo.getCurrentTempC()+"°C, "+weatherInfo.getAtmosphereHumidity()+"%");

            LoadWebImagesTask task = new LoadWebImagesTask();
            task.execute(
                    weatherInfo.getCurrentConditionIconURL(),
                    weatherInfo.getForecast1ConditionIconURL(),
                    weatherInfo.getForecast2ConditionIconURL(),
                    weatherInfo.getForecast3ConditionIconURL(),
                    weatherInfo.getForecast4ConditionIconURL(),
                    weatherInfo.getForecast5ConditionIconURL()
            );
        } else {
            Toast.makeText(getApplicationContext(), "Sorry, no result returned", Toast.LENGTH_SHORT).show();
        }
    }

    class LoadWebImagesTask extends AsyncTask<String, Void, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(String... params) {
            // TODO Auto-generated method stub
            Bitmap[] res = new Bitmap[6];
            res[0] = ImageUtils.getBitmapFromWeb(params[0]);
            res[1] = ImageUtils.getBitmapFromWeb(params[1]);
            res[2] = ImageUtils.getBitmapFromWeb(params[2]);
            res[3] = ImageUtils.getBitmapFromWeb(params[3]);
            res[4] = ImageUtils.getBitmapFromWeb(params[4]);
            res[5] = ImageUtils.getBitmapFromWeb(params[5]);
            return res;
        }

        @Override
        protected void onPostExecute(Bitmap[] results) {
            // TODO Auto-generated method stub
            super.onPostExecute(results);
            citylb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(results[0]));
            todayweath.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(results[1]));
            forecast1.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(results[2]));
            forecast2.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(results[3]));
            forecast3.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(results[4]));
            forecast4.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(results[5]));

        }

    }

}
