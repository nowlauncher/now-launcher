package com.nowlauncher.nowlauncher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.YahooWeather.tools.ImageUtils;
import com.YahooWeather.utils.WeatherInfo;
import com.YahooWeather.utils.YahooWeatherInfoListener;
import com.YahooWeather.utils.YahooWeatherUtils;
import com.nowlauncher.musicplayer.SongsManager;
import com.nowlauncher.musicplayer.Utilities;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity implements YahooWeatherInfoListener, OnCompletionListener, SeekBar.OnSeekBarChangeListener{


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

    private ImageView ivWeather0;
    private ImageView ivWeather1;
    private ImageView ivWeather2;
    private TextView tvWeather0;
    private TextView tvWeather1;
    private TextView tvWeather2;

    private SlidingDrawer sldrawer;
    RelativeLayout forecastCard;
    public int padding;

    HeightWrappingViewPager cardPager;

    /**
     * MusicPlayer
     */
    private ImageView sliderMusic;
    private RelativeLayout listLayout;
    private boolean musiccheck=false;

    private ImageView btnPlay;
    private ImageView btnForward;
    private ImageView btnBackward;
    private ImageView btnNext;
    private ImageView btnPrevious;
    private ImageView btnPlaylist;
    private ImageView btnRepeat;
    private ImageView btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songArtistLabel;
    private TextView songAlbumNameLabel;
    private ImageView songAlbumArtImageView;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    // Media Player
    private MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();;
    private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<SongsManager> songsList = new ArrayList<SongsManager>();
    private boolean isFinishedLoadSongList = false;



    public static int convertDip2Pixels(Context context, int dip) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
    private boolean isRuntimePostGingerbread() {
        return Build.VERSION.SDK_INT  >= Build.VERSION_CODES.HONEYCOMB;
    }

    class GetSongList extends AsyncTask<String, Void, ArrayList<SongsManager>> {

        @Override
        protected ArrayList<SongsManager> doInBackground(String... params) {
            // TODO Auto-generated method stub
            ArrayList<SongsManager> songsList;
            SongsManager songManager = new SongsManager(getApplicationContext());
            songsList=songManager.getPlayList();
            return songsList;
        }

        @Override
        protected void onPostExecute(ArrayList<SongsManager> results) {
            // TODO Auto-generated method stub
            super.onPostExecute(results);
            Log.d("MediaPlayer", "Finished to execute GetSongList");
            songsList=results;
            isFinishedLoadSongList=true;
            loadMusicLists();

        }

    }
    private void loadMusicLists(){
        ListView listArtist=(ListView)findViewById(R.id.listViewArtist);
        ListView listAlbum=(ListView)findViewById(R.id.listViewAlbum);
        ListView listTitle=(ListView)findViewById(R.id.listViewTitle);
        ListView listPlaylist=(ListView)findViewById(R.id.listViewPlaylist);

        TabHost songsTabHost=(TabHost)findViewById(R.id.songsTabHost);
        songsTabHost.setup();

        TabHost.TabSpec specArtist = songsTabHost.newTabSpec("tag1");

        specArtist.setContent(listArtist.getId());
        specArtist.setIndicator("Artist");

        songsTabHost.addTab(specArtist);

        TabHost.TabSpec specAlbum = songsTabHost.newTabSpec("tag2");
        specAlbum.setContent(listAlbum.getId());
        specAlbum.setIndicator("Album");

        songsTabHost.addTab(specAlbum);

        TabHost.TabSpec specTitle = songsTabHost.newTabSpec("tag3");
        specTitle.setContent(listTitle.getId());
        specTitle.setIndicator("Title");

        songsTabHost.addTab(specTitle);

        TabHost.TabSpec specPlaylist = songsTabHost.newTabSpec("tag4");
        specPlaylist.setContent(listPlaylist.getId());
        specPlaylist.setIndicator("Playlist");

        songsTabHost.addTab(specPlaylist);

        MusicListViewAdapter playlistadapter=new MusicListViewAdapter(getApplicationContext(),songsList);

        listPlaylist.setAdapter(playlistadapter);
        listPlaylist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                playSong(position);
                currentSongIndex=position;
            }
        });

        MusicListViewAdapter titleadapter=new MusicListViewAdapter(getApplicationContext(),songsList);
        ArrayList<SongsManager> songsListTilte=songsList;
        //Collections.sort(songsListTilte, new MusicComparator());
        listTitle.setAdapter(titleadapter);
        listTitle.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                playSong(position);
                currentSongIndex=position;
            }
        });
    }
    public class MusicComparator implements Comparator<SongsManager>
    {
        @Override
        public int compare(SongsManager songsManager, SongsManager songsManager2) {
            Log.d("MusicComparator",songsManager.getTitle());
            return songsManager.getTitle().compareToIgnoreCase(songsManager2.getTitle());
        }
    }
    private void loadMusicPlayer(){
        btnPlay = (ImageView) findViewById(R.id.musicplaybtn);
        //btnForward = (ImageView) findViewById(R.id.btnForward);
        //btnBackward = (ImageView) findViewById(R.id.btnBackward);
        btnNext = (ImageView) findViewById(R.id.musicnextbtn);
        btnPrevious = (ImageView) findViewById(R.id.musicbackbtn);
        btnRepeat = (ImageView) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageView) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songArtistLabel = (TextView) findViewById(R.id.songArtist);
        songAlbumNameLabel = (TextView) findViewById(R.id.songAlbum);
        songAlbumArtImageView = (ImageView) findViewById(R.id.songAlbumArt);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

        final Toast musiclistNotReadyToast = Toast.makeText(getApplicationContext(), "Music list isn't ready yet.", Toast.LENGTH_SHORT);

        // Mediaplayer
        Log.d("MediaPlayer","Starting new MediaPlayer()");
        mp = new MediaPlayer();
        Log.d("MediaPlayer","Finished new MediaPlayer()");
        Log.d("MediaPlayer","Starting new SongsManager()");
        songManager = new SongsManager(getApplicationContext());
        Log.d("MediaPlayer","Finished new SongsManager()");
        Log.d("MediaPlayer","Starting new Utilities()");
        utils = new Utilities();
        Log.d("MediaPlayer","Finished new Utilities()");

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        Log.d("MediaPlayer","Starting  getSongList.execute();");
        // Getting all songs list
        //songsList = songManager.getPlayList();
        GetSongList getSongList=new GetSongList();
        if (isRuntimePostGingerbread())getSongList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else getSongList.execute();
        Log.d("MediaPlayer","Finished getSongList.execute();");
        // By default play first song
        //playSong(0);

        /**
         * Play button click event
         * plays a song and changes button to pause image
         * pauses a song and changes button to play image
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isFinishedLoadSongList){

                    // check for already playing
                    if(mp.isPlaying()){
                        if(mp!=null){
                            mp.pause();
                            // Changing button image to play button
                            btnPlay.setImageResource(R.drawable.music_play);
                        }
                    }else{
                        // Resume song
                        if(mp!=null){
                            mp.start();
                            // Changing button image to pause button
                            btnPlay.setImageResource(R.drawable.music_pause);
                        }
                    }

                }
                else
                {
                    musiclistNotReadyToast.show();
                }
            }
        });

        /**
         * Forward button click event
         * Forwards song specified seconds
         * *//*
        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mp.getDuration()){
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
            }
        });
*/
        /**
         * Backward button click event
         * Backward song to specified seconds
         * *//*
        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if(currentPosition - seekBackwardTime >= 0){
                    // forward song
                    mp.seekTo(currentPosition - seekBackwardTime);
                }else{
                    // backward to starting position
                    mp.seekTo(0);
                }

            }
        });
*/
        /**
         * Next button click event
         * Plays next song by taking currentSongIndex + 1
         * */
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isFinishedLoadSongList){
                    if(isShuffle){
                        // shuffle is on - play a random song
                        Random rand = new Random();
                        currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
                        playSong(currentSongIndex);
                    } else{
                        // no shuffle ON - play next song
                        if(currentSongIndex < (songsList.size() - 1)){
                            playSong(currentSongIndex + 1);
                            currentSongIndex = currentSongIndex + 1;
                        }else{
                            // play first song
                            playSong(0);
                            currentSongIndex = 0;
                        }
                    }
                }
                else
                {
                    musiclistNotReadyToast.show();
                }
            }
        });

        /**
         * Back button click event
         * Plays previous song by currentSongIndex - 1
         * */
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isFinishedLoadSongList){
                    if(isShuffle){
                        // shuffle is on - play a random song
                        Random rand = new Random();
                        currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
                        playSong(currentSongIndex);
                    } else{
                        // no shuffle ON - play previous song
                        if(currentSongIndex > 0){
                            playSong(currentSongIndex - 1);
                            currentSongIndex = currentSongIndex - 1;
                        }else{
                            // play last song
                            playSong(songsList.size() - 1);
                            currentSongIndex = songsList.size() - 1;
                        }
                    }
                }
                else
                {
                    musiclistNotReadyToast.show();
                }

            }
        });


        /**
         * Button Click event for Repeat button
         * Enables repeat flag to true
         * */
        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(isRepeat){
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }else{
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }
            }
        });

        /**
         * Button Click event for Shuffle button
         * Enables shuffle flag to true
         * */
        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(isShuffle){
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });

        /**
         * Button Click event for Play list click event
         * Launches list activity which displays list of songs
         * *//*
        btnPlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
                startActivityForResult(i, 100);
            }
        });*/


        /***
         * Setting TabHost and populate it
         *
         */



    }

    public void  playSong(int songIndex){
        // Play song
        try {
            mp.reset();
            mp.setDataSource(songsList.get(songIndex).getPath());
            mp.prepare();
            mp.start();
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.music_pause));
            // Displaying Song title and other tags
            String songTitle = songsList.get(songIndex).getTitle();
            songTitleLabel.setText(songTitle);
            String songArtist = songsList.get(songIndex).getArtist();
            songArtistLabel.setText(songArtist);
            String songAlbumName = songsList.get(songIndex).getAlbumName();
            songAlbumNameLabel.setText(songAlbumName);
            Bitmap songAlbumArt=songsList.get(songIndex).getAlbumArt();
            songAlbumArtImageView.setImageDrawable(new BitmapDrawable(getResources(),songAlbumArt));

            // Changing Button Image to pause image
            //btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getAlbumart(Long album_id)
    {
        Bitmap bm = null;
        try
        {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = getApplicationContext().getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
        } catch (Exception e) {
            bm=BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.unknownalbum);
        }
        return bm;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }
    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    /**
     * When user starts moving the progress handler
     * */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     * */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     * */
    @Override
    public void onCompletion(MediaPlayer arg0) {

    // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else{
            // no repeat or shuffle ON - play next song
            if(currentSongIndex < (songsList.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

        cardPager=(HeightWrappingViewPager)findViewById(R.id.cardPager);
        CardAdapter cardAdapter=new CardAdapter(cardPager);
        cardPager.setAdapter(cardAdapter);
        cardPager.setCurrentItem(0);
        cardPager.setPageMargin(convertDip2Pixels(getApplicationContext(),10));
        cardPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels>0)cardPager.setPageToWrap(position+1);
                else cardPager.setPageToWrap(position);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        sldrawer = (SlidingDrawer)findViewById(R.id.sldrawer);

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

        forecastCard = (RelativeLayout)findViewById(R.id.forecastLayout);

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

        sliderMusic=(ImageView)findViewById(R.id.musicslider);
        listLayout=(RelativeLayout)findViewById(R.id.listLayout);
        sliderMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!musiccheck) {
                    expand(listLayout);
                    RelativeLayout musicLists=(RelativeLayout)findViewById(R.id.include_music_lists);
                    musicLists.setVisibility(View.VISIBLE);
                    musiccheck=true;
                }
                else{
                    collapse(listLayout);
                    RelativeLayout musicLists=(RelativeLayout)findViewById(R.id.include_music_lists);
                    musicLists.setVisibility(View.GONE);
                    musiccheck=false;
                }
            }
        });

        // Carica il layout del drawer conforme alle impostazioni e carica la lista delle applicazioni

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPrefs.getString("drawer_orientation", "1").equals("1")) {
            findViewById(R.id.drawerlist).setVisibility(View.VISIBLE);
            findViewById(R.id.pager).setVisibility(View.GONE);
            if (isRuntimePostGingerbread()) new ListDrawer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else new ListDrawer().execute();

        }
        else if (sharedPrefs.getString("drawer_orientation", "1").equals("2"))
        {
            if (isRuntimePostGingerbread()) new ListDrawerscroll().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else new ListDrawerscroll().execute();
        }


        loadMusicPlayer();
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
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
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
            if (isRuntimePostGingerbread()) new GetPosition().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else new GetPosition().execute();
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

//Gesture per chiusura drawer
    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            float sensitvity = 50;
            if((e1.getY() - e2.getY()) > sensitvity){
                Log.d("ON TOUCH VIEWPAGER","swipe up");

                sldrawer.animateOpen();
            }
            /*
            else if ((e2.getY()-e1.getY()>sensitvity)){
                Log.d("ON TOUCH VIEWPAGER","swipe down");

                sldrawer.animateClose();
            }
*/
            return true;
        }

    };
    GestureDetector gestureDetector = new GestureDetector(simpleOnGestureListener);

    View addImageView(int resId)
    {
        ImageView iv = new ImageView(this);
        iv.setImageResource(resId);

        return iv;
    }
	public void CreateViews() {
		GridView gridview = (GridView) findViewById(R.id.drawerlist);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int drawerrows=Integer.parseInt(sharedPrefs.getString("drawer_rows", "4"));
        int drawercolumns=Integer.parseInt(sharedPrefs.getString("drawer_coloums", "4"));
        gridview.setNumColumns(drawercolumns);
        final ApplicationsAdapter applicationsAdapter=new ApplicationsAdapter(mApplications,gridview, getApplicationContext(),drawerrows);
		gridview.setAdapter(applicationsAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = applicationsAdapter.getIntent(position);
                if (intent != null) {
                    intent = Intent.makeMainActivity(intent.getComponent());
                }
                startActivity(intent);
            }

        });
		}

    public void CreateViewsscroll() {

        //Divide la lista delle applicazioni in altri array, creando la lista delle app delle varie pagine

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        double numappxpage;
        int drawerrows=Integer.parseInt(sharedPrefs.getString("drawer_rows", "4"));
        int drawercolumns=Integer.parseInt(sharedPrefs.getString("drawer_coloums", "4"));
        numappxpage=drawerrows*drawercolumns;
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

        /**
         * Crea le varie GridView, setta l'adapter e assegna varie proprietà
         */

        findViewById(R.id.drawerlist).setVisibility(View.GONE);
        findViewById(R.id.pager).setVisibility(View.VISIBLE);

        GridView[] gridViewsApps = new GridView[npage];
        for (int app=0; app<gridViewsApps.length; app++){
            gridViewsApps[app]=new GridView(this);
            gridViewsApps[app].setNumColumns(drawercolumns);
            gridViewsApps[app].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            int height = gridViewsApps[app].getHeight();
            final ApplicationsAdapter applicationsAdapter=new ApplicationsAdapter(splittedarray[app], gridViewsApps[app], this, drawerrows);
            gridViewsApps[app].setAdapter(applicationsAdapter);
            gridViewsApps[app].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = applicationsAdapter.getIntent(position);
                    startActivity(intent);
                }
            });
        }

        /**
         * Setta l'adapter al ViewPager
         */

        MyPagerAdapter adapter = new MyPagerAdapter(this, gridViewsApps);
        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);

        /******************************************************************************************************
         * Carica completamente le pagine del ViewPager ma è eseguito sul thread principale quindi freeza l'app
         *****************************************************************************************************/
        //Log.d("myPager","setOffscreenPageLimit(npage)");
        //myPager.setOffscreenPageLimit(npage);

        UnderlinePageIndicator titleIndicator = (UnderlinePageIndicator)findViewById(R.id.titles);
        titleIndicator.setViewPager(myPager);
        //API <11 .setPageTransformer ignorato quindi niente animazioni custom.
        if (sharedPrefs.getString("drawer_animation", "1").equals("2")) {
            myPager.setPageTransformer(true, new DepthPageTransformer());
        }
        else if (sharedPrefs.getString("drawer_animation", "1").equals("3")) {
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

       return gestureDetector.onTouchEvent(event);
		//return false;

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
            if (mApplications == null) {
                mApplications = new ArrayList<AppInfo>();
            }

            mApplications.clear();

            Intent queryIntent= new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
            PackageManager mPackageManager = getPackageManager();
            List<ResolveInfo> resolveInfos = mPackageManager.queryIntentActivities(queryIntent,0);
            for (ResolveInfo ri : resolveInfos) {
                AppInfo ai = new AppInfo();
                ai.icon = ri.loadIcon(mPackageManager);
                ai.label = ri.loadLabel(mPackageManager);
                ai.componentName = new ComponentName(ri.activityInfo.packageName,ri.activityInfo.name);Intent i=new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(ai.componentName);
                ai.intent=i;
                mApplications.add(ai);
            }

            Collections.sort(mApplications, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo activityInfo, AppInfo activityInfo2) {
                    return activityInfo.label.toString().compareTo(
                            activityInfo2.label.toString());
                }
            });
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
            Log.d("MainActivity","Started ListDrawerscroll");
            if (mApplications == null) {
                mApplications = new ArrayList<AppInfo>();
            }

            mApplications.clear();

            Intent queryIntent= new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
            PackageManager mPackageManager = getPackageManager();
            List<ResolveInfo> resolveInfos = mPackageManager.queryIntentActivities(queryIntent, 0);
            for (ResolveInfo ri : resolveInfos) {
                AppInfo ai = new AppInfo();
                ai.icon = ri.loadIcon(mPackageManager);
                ai.label = ri.loadLabel(mPackageManager);
                ai.componentName = new ComponentName(ri.activityInfo.applicationInfo.packageName,ri.activityInfo.name);
                Intent i=new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(ai.componentName);
                ai.intent=i;
                mApplications.add(ai);
            }

            Collections.sort(mApplications, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo activityInfo, AppInfo activityInfo2) {
                    return activityInfo.label.toString().compareTo(
                            activityInfo2.label.toString());
                }
            });
            return null;
        }

        protected void onPostExecute(String result) {
            CreateViewsscroll();
            pgbar.setVisibility(View.GONE);
        }
    }

    public void gotWeatherInfo(final WeatherInfo weatherInfo) {
        // TODO Auto-generated method stub
        if(weatherInfo != null) {

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


            //Imposta il link all'immagine di Yahoo!Weather
            ImageView yahoologo=(ImageView)findViewById(R.id.yahooLogo);
            yahoologo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(weatherInfo.getWeatherurl()));
                    startActivity(browserIntent);
                }
            });

            LoadWebImagesTask task = new LoadWebImagesTask();
            if (isRuntimePostGingerbread()) task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                    weatherInfo.getCurrentConditionIconURL(),
                    weatherInfo.getForecast1ConditionIconURL(),
                    weatherInfo.getForecast2ConditionIconURL(),
                    weatherInfo.getForecast3ConditionIconURL(),
                    weatherInfo.getForecast4ConditionIconURL(),
                    weatherInfo.getForecast5ConditionIconURL()
            );
            else task.execute(
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
            citylb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(),results[0]));
            todayweath.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(getResources(),results[1]));
            forecast1.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(getResources(),results[2]));
            forecast2.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(getResources(),results[3]));
            forecast3.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(getResources(),results[4]));
            forecast4.setCompoundDrawablesWithIntrinsicBounds(null,null,null, new BitmapDrawable(getResources(),results[5]));

        }

    }
    public class CardAdapter extends PagerAdapter {
        ViewPager viewPager;
        public int getCount() {
            return 2;
        }
        public CardAdapter(ViewPager viewPager){
            this.viewPager=viewPager;
        }
        public Object instantiateItem(ViewGroup collection, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.weatherPage;
                    break;
                case 1:
                    resId = R.id.musicPage;
                    break;
            }

            return findViewById(resId);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }
    }

    /**
     * Metodo chiamato quando l'activity viene distrutta (uscita app)
     */
    @Override
    protected void onDestroy() {
        //Rimuove il thread Runnable in background
        mHandler.removeCallbacks(mUpdateTimeTask);
        //Resetta il mediaplayer
        mp.reset();
        super.onDestroy();
    }
}
