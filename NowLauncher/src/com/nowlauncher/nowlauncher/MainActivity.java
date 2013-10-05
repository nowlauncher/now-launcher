package com.nowlauncher.nowlauncher;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.YahooWeather.tools.ImageUtils;
import com.YahooWeather.utils.WeatherInfo;
import com.YahooWeather.utils.YahooWeatherInfoListener;
import com.YahooWeather.utils.YahooWeatherUtils;
import com.dragSortListView.DragSortListView;
import com.nowlauncher.musicplayer.SongsManager;
import com.nowlauncher.musicplayer.Utilities;
import com.nowlauncher.nowlauncher.viewPagerTransformer.CubeTransformer;
import com.nowlauncher.nowlauncher.viewPagerTransformer.DepthPageTransformer;
import com.nowlauncher.nowlauncher.viewPagerTransformer.RotateInTransformer;
import com.nowlauncher.nowlauncher.viewPagerTransformer.RotateTransformer;
import com.nowlauncher.nowlauncher.viewPagerTransformer.ZoomOutPageTransformer;
import com.viewpagerindicator.UnderlinePageIndicator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity implements YahooWeatherInfoListener, OnCompletionListener, SeekBar.OnSeekBarChangeListener {


    // Layout master
    public RelativeLayout rootlayout;

    public ImageView wallpaperImageView;

    // Array of all application in the device
    public ArrayList<AppInfo> mApplications;
    ArrayList[] splittedarray;

    public RelativeLayout rootlayoutdrawer;
    private RelativeLayout allAppsLayout;
    private RelativeLayout favouriteAppsLayout;
    private TextView allAppsbtn;
    private TextView favouriteAppsbtn;


    //View for the weather
    public ImageView refreshwea;
    public TextView datelb;
    public TextView temperaturenow;
    private ImageView sliderWeather;
    private boolean tempcheck = false;

    private TextView citylb;
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

    private SlidingDrawer sldrawer;
    RelativeLayout forecastCard;

    HeightWrappingViewPager cardPager;

    /**
     * MusicPlayer
     */
    private ImageView sliderMusic;
    private RelativeLayout listLayout;
    private boolean musiccheck = false;

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
    private Handler mHandler = new Handler();
    ;
    private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<SongsManager> songsList = new ArrayList<SongsManager>();
    private ArrayList<SongsManager> currentsonglist;
    private boolean isFinishedLoadSongList = false;
    RelativeLayout musicLists;


    private ListDrawer listDrawer;
    private ListDrawerscroll listDrawerscroll;
    private boolean listdrawersStop = false;
    GridView verticalGridView;

    private ApplicationsAdapter applicationsAdapter;

    //Views and variable for the search in the app drawer
    private ImageView drawerSearch;
    private LinearLayout drawerSearchPanel;
    private ListView drawerSearchListView;
    private EditText drawerSearchEditText;
    private boolean drawerSearchIsOpened = false;
    Animation translateAnimationDown;
    Animation translateAnimationUp;
    boolean keyboardhideshowBoolean = false;

    InputMethodManager mgr;

    PackageReceiver receiver = new PackageReceiver();

    ViewPagerAnim myPager;

    //Home variables and controls
    public static final String PREFS_HOME_NAME = "home_preference";
    SharedPreferences home_preference;
    GridView home_app1;
    ArrayList<AppInfo> home_app1_list;
    ApplicationsHomeAdapter applicationsHomeAdapter;
    String currentAdapterString = "";
    int currentPageHorizontal;
    ArrayList<ApplicationsAdapter> applicationsAdaptersArray;

    //Favourite panel variables
    public static final String PREFS_FAVOURITE_NAME = "favourite_preference";
    SharedPreferences favourite_preference;
    ArrayList<AppInfo> favourite_list;

    //Favourite tab
    ApplicationListViewAdapter favouriteListViewAdapter;
    DragSortListView favouriteSmartListView;

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    private boolean isRuntimePostGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    class GetSongList extends AsyncTask<String, Void, ArrayList<SongsManager>> {

        @Override
        protected ArrayList<SongsManager> doInBackground(String... params) {
            return getSongsList();
        }

        @Override
        protected void onPostExecute(ArrayList<SongsManager> results) {
            super.onPostExecute(results);
            Log.d("MediaPlayer", "Finished to execute GetSongList");
            songsList = results;
            isFinishedLoadSongList = true;
            loadMusicLists();

        }

    }

    /**
     * Function to read all mp3 files from sdcard
     * and store the details in ArrayList
     */
    public ArrayList<SongsManager> getSongsList() {
        try {
            String[] STAR = {"*"};
            Uri allaudiosong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String audioselection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
            Cursor cursor;
            cursor = getApplicationContext().getContentResolver().query(allaudiosong, STAR, audioselection, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        SongsManager song = new SongsManager(getApplicationContext());
                        song.fullpath = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DATA));
                        song.album_id = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(song.fullpath);
                        song.song_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                        if (song.song_name == null) song.song_name = song.fullpath;
                        song.album_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                        if (song.album_name == null) song.album_name = "Unknown";
                        song.artist_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                        if (song.artist_name == null) song.artist_name = "Unknown";
                        song.album_art = new BitmapDrawable(getResources(), song.setAlbumArt(Long.valueOf(song.getAlbumId()), convertDip2Pixels(getApplicationContext(), 100)));
                        song.song_index = cursor.getPosition();
                        songsList.add(song);
                    } while (cursor.moveToNext());
                }
            }
            // return songs list array

            cursor.close();
        } catch (Exception expection) {
            Log.d("getSongList", "Expection in getSongList");
        }
        return songsList;
    }

    private void loadMusicLists() {
        ListView listArtist = (ListView) findViewById(R.id.listViewArtist);
        ListView listAlbum = (ListView) findViewById(R.id.listViewAlbum);
        ListView listTitle = (ListView) findViewById(R.id.listViewTitle);
        ListView listPlaylist = (ListView) findViewById(R.id.listViewPlaylist);

        TabHost songsTabHost = (TabHost) findViewById(R.id.songsTabHost);
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
        MusicListViewAdapter playlistadapter = new MusicListViewAdapter(getApplicationContext(), songsList);

        listPlaylist.setAdapter(playlistadapter);
        listPlaylist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentsonglist = songsList;
                playSong(position);
                currentSongIndex = position;
            }
        });

        final ArrayList<SongsManager> songsListTitle = new ArrayList<SongsManager>(songsList);
        Collections.sort(songsListTitle, new Comparator<SongsManager>() {
            @Override
            public int compare(SongsManager song1, SongsManager song2) {
                return song1.getTitle().toUpperCase().compareTo(song2.getTitle().toUpperCase());
            }
        });
        MusicListViewAdapter titleadapter = new MusicListViewAdapter(getApplicationContext(), songsListTitle);
        listTitle.setAdapter(titleadapter);
        listTitle.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                currentsonglist = songsListTitle;
                playSong(position);
                currentSongIndex = position;
            }
        });
        // Default song list is the one sorted by title
        currentsonglist = songsListTitle;
    }

    private void loadMusicPlayer() {
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
        Log.d("MediaPlayer", "Starting new MediaPlayer()");
        mp = new MediaPlayer();
        Log.d("MediaPlayer", "Finished new MediaPlayer()");
        Log.d("MediaPlayer", "Starting new SongsManager()");
        songManager = new SongsManager(getApplicationContext());
        Log.d("MediaPlayer", "Finished new SongsManager()");
        Log.d("MediaPlayer", "Starting new Utilities()");
        utils = new Utilities();
        Log.d("MediaPlayer", "Finished new Utilities()");

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        Log.d("MediaPlayer", "Starting  getSongList.execute();");
        // Getting all songs list
        GetSongList getSongList = new GetSongList();
        if (isRuntimePostGingerbread())
            getSongList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else getSongList.execute();
        Log.d("MediaPlayer", "Finished getSongList.execute();");

        /**
         * Play button click event
         * plays a song and changes button to pause image
         * pauses a song and changes button to play image
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (isFinishedLoadSongList) {

                    // check for already playing
                    if (mp.isPlaying()) {
                        if (mp != null) {
                            mp.pause();
                            // Changing button image to play button
                            btnPlay.setImageResource(R.drawable.music_play);
                        }
                    } else {
                        // Resume song
                        if (mp != null) {
                            mp.start();
                            // Changing button image to pause button
                            btnPlay.setImageResource(R.drawable.music_pause);
                        }
                    }

                } else {
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
                if (isFinishedLoadSongList) {
                    if (isShuffle) {
                        // shuffle is on - play a random song
                        Random rand = new Random();
                        currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
                        playSong(currentSongIndex);
                    } else {
                        // no shuffle ON - play next song
                        if (currentSongIndex < (songsList.size() - 1)) {
                            playSong(currentSongIndex + 1);
                            currentSongIndex = currentSongIndex + 1;
                        } else {
                            // play first song
                            playSong(0);
                            currentSongIndex = 0;
                        }
                    }
                } else {
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
                if (isFinishedLoadSongList) {
                    if (isShuffle) {
                        // shuffle is on - play a random song
                        Random rand = new Random();
                        currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
                        playSong(currentSongIndex);
                    } else {
                        // no shuffle ON - play previous song
                        if (currentSongIndex > 0) {
                            playSong(currentSongIndex - 1);
                            currentSongIndex = currentSongIndex - 1;
                        } else {
                            // play last song
                            playSong(songsList.size() - 1);
                            currentSongIndex = songsList.size() - 1;
                        }
                    }
                } else {
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
                if (isRepeat) {
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                } else {
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
                if (isShuffle) {
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.btn_shuffle);
                } else {
                    // make repeat to true
                    isShuffle = true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
                    btnRepeat.setImageResource(R.drawable.btn_repeat);
                }
            }
        });

    }

    public void playSong(int songIndex) {
        // Play song
        try {
            mp.reset();
            mp.setDataSource(currentsonglist.get(songIndex).getPath());
            mp.prepare();
            mp.start();
            btnPlay.setImageDrawable(getResources().getDrawable(R.drawable.music_pause));
            // Displaying Song title and other tags
            String songTitle = currentsonglist.get(songIndex).getTitle();
            songTitleLabel.setText(songTitle);
            String songArtist = currentsonglist.get(songIndex).getArtist();
            songArtistLabel.setText(songArtist);
            String songAlbumName = currentsonglist.get(songIndex).getAlbumName();
            songAlbumNameLabel.setText(songAlbumName);
            songAlbumArtImageView.setImageDrawable(currentsonglist.get(songIndex).getAlbumArt());

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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    /**
     * When user starts moving the progress handler
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     */
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
     */
    @Override
    public void onCompletion(MediaPlayer arg0) {

        // check for repeat is ON or OFF
        if (isRepeat) {
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if (isShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else {
            // no repeat or shuffle ON - play next song
            if (currentSongIndex < (currentsonglist.size() - 1)) {
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            } else {
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

        /**
         * Set the receiver to manage new installed/uninstalled applications
         */
        IntentFilter filter = new IntentFilter();
        filter.addDataScheme("package");
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);

        receiver = new PackageReceiver();
        registerReceiver(receiver, filter);

        home_app1 = (GridView) findViewById(R.id.home_app1);

        drawerSearch = (ImageView) findViewById(R.id.drawersearch);
        drawerSearchPanel = (LinearLayout) findViewById(R.id.drawersearchpanel);
        drawerSearchListView = (ListView) findViewById(R.id.listviewDrawerSearch);
        drawerSearchEditText = (EditText) findViewById(R.id.drawersearchedittext);

        translateAnimationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);
        translateAnimationDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (keyboardhideshowBoolean) {
                    mgr.hideSoftInputFromWindow(drawerSearchEditText.getWindowToken(), 0);
                    keyboardhideshowBoolean = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        translateAnimationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        translateAnimationUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (keyboardhideshowBoolean) {
                    drawerSearchEditText.requestFocus();
                    mgr.showSoftInput(drawerSearchEditText, InputMethodManager.SHOW_IMPLICIT);
                    keyboardhideshowBoolean = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        drawerSearchListView.setDivider(null);
        drawerSearchListView.setDividerHeight(0);
        drawerSearchListView.setTextFilterEnabled(true);
        drawerSearchListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mgr.hideSoftInputFromWindow(drawerSearchEditText.getWindowToken(), 0);
                return false;
            }
        });
        mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        drawerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerSearchIsOpened) {
                    keyboardhideshowBoolean = true;
                    drawerSearchPanel.setAnimation(translateAnimationDown);
                    drawerSearchPanel.getAnimation().start();
                    drawerSearchPanel.setVisibility(View.GONE);
                    drawerSearchIsOpened = false;
                } else {
                    keyboardhideshowBoolean = true;
                    drawerSearchPanel.setVisibility(View.VISIBLE);
                    drawerSearchPanel.setAnimation(translateAnimationUp);
                    drawerSearchPanel.getAnimation().start();
                    drawerSearchIsOpened = true;

                }
            }
        });

        cardPager = (HeightWrappingViewPager) findViewById(R.id.cardPager);
        CardAdapter cardAdapter = new CardAdapter();
        cardPager.setAdapter(cardAdapter);
        cardPager.setCurrentItem(0);
        cardPager.setPageMargin(convertDip2Pixels(getApplicationContext(), 10));
        cardPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                cardPager.setPageToWrap(i);
                if (musiccheck) {
                    collapse(listLayout);
                    musicLists.setAnimation(translateAnimationDown);
                    musicLists.getAnimation().start();
                    musicLists.setVisibility(View.GONE);
                    musiccheck = false;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        sldrawer = (SlidingDrawer) findViewById(R.id.sldrawer);
        rootlayout = (RelativeLayout) findViewById(R.id.rootLayout);
        rootlayoutdrawer = (RelativeLayout) findViewById(R.id.drawer_rootlayout);
        wallpaperImageView = (ImageView) findViewById(R.id.backgroundwallpaper);
        sliderWeather = (ImageView) findViewById(R.id.weatherslider);
        forecastCard = (RelativeLayout) findViewById(R.id.forecastLayout);
        sliderWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tempcheck) {
                    expand(forecastCard);
                    tempcheck = true;
                } else {
                    collapse(forecastCard);
                    tempcheck = false;
                }
            }
        });

        musicLists = (RelativeLayout) findViewById(R.id.include_music_lists);
        sliderMusic = (ImageView) findViewById(R.id.musicslider);
        listLayout = (RelativeLayout) findViewById(R.id.listLayout);
        sliderMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!musiccheck) {
                    expand(listLayout);
                    musicLists.setAnimation(translateAnimationUp);
                    musicLists.getAnimation().start();
                    musicLists.setVisibility(View.VISIBLE);
                    musiccheck = true;
                } else {
                    collapse(listLayout);
                    musicLists.setAnimation(translateAnimationDown);
                    musicLists.getAnimation().start();
                    musicLists.setVisibility(View.GONE);
                    musiccheck = false;
                }
            }
        });
        home_app1_list = new ArrayList<AppInfo>();
        favourite_preference = getSharedPreferences(PREFS_FAVOURITE_NAME, 0);
        loadWallpaper();
        loadHome();
        loadFavouritesList();
        loadDrawer();
        loadMusicPlayer();
        loadWeathercard();

        refreshwea = (ImageView) findViewById(R.id.weatherrefresh);
        refreshwea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadWeathercard();
            }
        });

        //Settings button in the drawer
        ImageView settings_button = (ImageView) findViewById(R.id.settingsbtn);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivityForResult(intent, 1);
            }
        });

        allAppsLayout = (RelativeLayout) findViewById(R.id.allAppsLayout);
        favouriteAppsLayout = (RelativeLayout) findViewById(R.id.favouritesAppsLayout);
        allAppsbtn = (TextView) findViewById(R.id.drawer_allapps_btn);
        favouriteAppsbtn = (TextView) findViewById(R.id.drawer_favourite_btn);
        allAppsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteAppsLayout.setAnimation(translateAnimationDown);
                favouriteAppsLayout.getAnimation().start();
                favouriteAppsLayout.setVisibility(View.GONE);
                allAppsLayout.setAnimation(translateAnimationUp);
                allAppsLayout.getAnimation().start();
                allAppsLayout.setVisibility(View.VISIBLE);
                allAppsbtn.setBackgroundColor(getResources().getColor(R.color.silver));
                favouriteAppsbtn.setBackgroundDrawable(null);
            }
        });
        favouriteAppsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allAppsLayout.setAnimation(translateAnimationDown);
                allAppsLayout.getAnimation().start();
                allAppsLayout.setVisibility(View.GONE);
                favouriteAppsLayout.setAnimation(translateAnimationUp);
                favouriteAppsLayout.getAnimation().start();
                favouriteAppsLayout.setVisibility(View.VISIBLE);
                favouriteAppsbtn.setBackgroundColor(getResources().getColor(R.color.silver));
                allAppsbtn.setBackgroundDrawable(null);
            }
        });

        dockapp1 = (ImageView) findViewById(R.id.dockapp1);
        dockapp2 = (ImageView) findViewById(R.id.dockapp2);
        dockapp3 = (ImageView) findViewById(R.id.dockapp3);
        dockapp4 = (ImageView) findViewById(R.id.dockapp4);
        dockapp1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                checkdockapp1 = true;
                return false;
            }
        });

    }

    private void loadHome() {
        home_preference = getSharedPreferences(PREFS_HOME_NAME, 0);


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String homeapp1listString = home_preference.getString("home_app1_list", "");
        if (!homeapp1listString.equals("")) {
            String[] arraystringComponentName = homeapp1listString.split("\\|");
            final PackageManager pm = getPackageManager();

            for (int apphome = 0; apphome < arraystringComponentName.length; apphome++) {
                Intent intent = new Intent();
                String[] arraypackageclass = arraystringComponentName[apphome].split("&");
                if (arraypackageclass[0] != null && arraypackageclass[1] != null) {
                    intent.setComponent(new ComponentName(arraypackageclass[0], arraypackageclass[1]));
                    ResolveInfo ri = pm.resolveActivity(intent, 0);
                    AppInfo ai = new AppInfo();
                    if (sharedPrefs.getBoolean("theme_iconpackcheck", false)) {
                        Integer resultIconPacks = ai.applicationIconPacks.get(ri.activityInfo.packageName);
                        if (resultIconPacks != null)
                            ai.icon = getResources().getDrawable(resultIconPacks);
                        else ai.icon = ri.loadIcon(pm);
                    } else ai.icon = ri.loadIcon(pm);
                    ai.label = ri.loadLabel(pm);
                    ai.packagename = ri.activityInfo.packageName;
                    ai.packageclass = ri.activityInfo.name;
                    ai.componentName = new ComponentName(ai.packagename, ai.packageclass);
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    i.setComponent(ai.componentName);
                    ai.intent = i;
                    home_app1_list.add(ai);
                }
            }
        }


        applicationsHomeAdapter = new ApplicationsHomeAdapter(home_app1_list, getApplicationContext());
        home_app1.setAdapter(applicationsHomeAdapter);
        home_app1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = applicationsHomeAdapter.getIntent(position);
                startActivity(intent);
                //TODO find the default launcher animation and add it to all intents
                //overridePendingTransition(android.R.anim., android.R.anim.slide_out_right);
            }
        });
        registerForContextMenu(home_app1);
    }

    private void loadDrawer() {
        listdrawersStop = true;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPrefs.getString("drawer_orientation", "1").equals("1")) {
            findViewById(R.id.drawerlist).setVisibility(View.VISIBLE);
            findViewById(R.id.pager).setVisibility(View.GONE);
            listDrawer = new ListDrawer();
            listdrawersStop = false;
            if (isRuntimePostGingerbread())
                listDrawer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else listDrawer.execute();

        } else if (sharedPrefs.getString("drawer_orientation", "1").equals("2")) {
            findViewById(R.id.drawerlist).setVisibility(View.GONE);
            findViewById(R.id.pager).setVisibility(View.VISIBLE);
            listDrawerscroll = new ListDrawerscroll();
            listdrawersStop = false;
            if (isRuntimePostGingerbread())
                listDrawerscroll.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else listDrawerscroll.execute();
        }
    }

    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
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

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
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

    public void loadWeathercard() {
        //Set the date
        getDate();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        citylb = (TextView) findViewById(R.id.citybox);
        //Check if the custom weather place preference is enabled
        if (!sharedPrefs.getBoolean("weather_customlocationcheck", false)) {
            citylb.setText("Acquisizione posizione...");
            //Get the position and refresh the city label
            if (isRuntimePostGingerbread())
                new GetPosition().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else new GetPosition().execute();
        } else {
            //Get the custom city, refresh the city label and get the weather
            citylb.setText(sharedPrefs.getString("weather_customlocation", getString(R.string.location_null)));
            getWeather((String) citylb.getText());
        }
    }

    /**
     * Function to check if the device is online
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM y");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        datelb = (TextView) findViewById(R.id.dateView);
        datelb.setText(currentDate);
    }

    private void getWeather(String location) {
        if (isOnline()) {
            Log.d("YWeatherGetter4a", "onCreate");
            YahooWeatherUtils yahooWeatherUtils = YahooWeatherUtils.getInstance();
            yahooWeatherUtils.queryYahooWeather(getApplicationContext(), location, this);
        } else
            Toast.makeText(getApplicationContext(), "Sorry, no connection available", Toast.LENGTH_SHORT).show();

    }

    protected class GetPosition extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {

            List<Address> addresses = null;
            try {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            getWeather((String) citylb.getText());
        }
    }

    //GestureDetector to manage the open/close gesture of the drawer
    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                float sensitvity = convertDip2Pixels(getApplicationContext(), 100);
                if ((e1.getY() - e2.getY()) > sensitvity) {
                    Log.d("ON TOUCH VIEWPAGER", "swipe up");
                    Log.d("ON TOUCH VIEWPAGER", "e1: " + e1.getY() + " e2: " + e2.getY());
                    if (!sldrawer.isOpened()) sldrawer.animateOpen();
                } else if (e2.getY() - e1.getY() > sensitvity) {
                    Log.d("ON TOUCH VIEWPAGER", "swipe down");
                    Log.d("ON TOUCH VIEWPAGER", "e1: " + e1.getY() + " e2: " + e2.getY());
                    if (sldrawer.isOpened()) sldrawer.animateClose();
                }
            } catch (Exception ex) {
            }
            return true;
        }

    };
    GestureDetector gestureDetector = new GestureDetector(simpleOnGestureListener);

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == verticalGridView) {
            menu.setHeaderTitle(getResources().getString(R.string.drawer_menu_title));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_uninstall));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_sendvia));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_manage));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_addhome));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_addfavourite));
            currentAdapterString = "verticalGridView";
        } else if (v == home_app1) {
            menu.setHeaderTitle(getResources().getString(R.string.drawer_menu_title));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_uninstall));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_sendvia));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_manage));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_removehome));
            currentAdapterString = "home_app1";
        } else if (v.getTag() == "HorizontalDrawerTag") {
            menu.setHeaderTitle(getResources().getString(R.string.drawer_menu_title));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_uninstall));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_sendvia));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_manage));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_addhome));
            menu.add(0, v.getId(), 0, getResources().getString(R.string.drawer_menu_addfavourite));
            currentAdapterString = "HorizontalDrawer";
            currentPageHorizontal = myPager.getCurrentItem();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == getResources().getString(R.string.drawer_menu_uninstall))
            uninstallApp(info.position);
        else if (item.getTitle() == getResources().getString(R.string.drawer_menu_manage))
            manageApp(info.position);
        else if (item.getTitle() == getResources().getString(R.string.drawer_menu_addhome))
            addHome(info.position);
        else if (item.getTitle() == getResources().getString(R.string.drawer_menu_removehome))
            removeHome(info.position);
        else if (item.getTitle() == getResources().getString(R.string.drawer_menu_addfavourite))
            addFavourite(info.position);
        else if (item.getTitle() == getResources().getString(R.string.drawer_menu_sendvia))
            sendVia(info.position);
        else {
            return false;
        }
        return true;
    }

    private void sendVia(int position) {
        Toast.makeText(getApplicationContext(), "Sorry, not already implemented!", Toast.LENGTH_SHORT).show();
    }

    private void uninstallApp(int position) {
        String packageName = "";
        if (currentAdapterString.equals("verticalGridView")) {
            packageName = applicationsAdapter.getPackageName(position);
        } else if (currentAdapterString.equals("home_app1")) {
            packageName = applicationsHomeAdapter.getPackageName(position);
        } else if (currentAdapterString.equals("HorizontalDrawer")) {
            packageName = applicationsAdaptersArray.get(currentPageHorizontal).getPackageName(position);
        }
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }

    private void manageApp(int position) {
        String packageName = "";
        if (currentAdapterString.equals("verticalGridView")) {
            packageName = applicationsAdapter.getPackageName(position);
        } else if (currentAdapterString.equals("home_app1")) {
            packageName = applicationsHomeAdapter.getPackageName(position);
        } else if (currentAdapterString.equals("HorizontalDrawer")) {
            packageName = applicationsAdaptersArray.get(currentPageHorizontal).getPackageName(position);
        }
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void addHome(int position) {
        String packageName = "";
        String packageClass = "";
        if (currentAdapterString.equals("verticalGridView")) {
            packageName = applicationsAdapter.getPackageName(position);
            packageClass = applicationsAdapter.getPackageClass(position);
        } else if (currentAdapterString.equals("HorizontalDrawer")) {
            packageName = applicationsAdaptersArray.get(currentPageHorizontal).getPackageName(position);
            packageClass = applicationsAdaptersArray.get(currentPageHorizontal).getPackageClass(position);
        }
        String currentStringPreference = home_preference.getString("home_app1_list", "");
        home_preference.edit().putString("home_app1_list", currentStringPreference + packageName + "&" + packageClass + "|").commit();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final PackageManager pm = getPackageManager();
        AppInfo ai = new AppInfo();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, packageClass));
        ResolveInfo ri = pm.resolveActivity(intent, 0);
        if (sharedPrefs.getBoolean("theme_iconpackcheck", false)) {
            Integer resultIconPacks = ai.applicationIconPacks.get(ri.activityInfo.packageName);
            if (resultIconPacks != null)
                ai.icon = getResources().getDrawable(resultIconPacks);
            else ai.icon = ri.loadIcon(pm);
        } else ai.icon = ri.loadIcon(pm);
        ai.label = ri.loadLabel(pm);
        ai.packagename = ri.activityInfo.packageName;
        ai.packageclass = ri.activityInfo.name;
        ai.componentName = new ComponentName(ai.packagename, ai.packageclass);
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(ai.componentName);
        ai.intent = i;
        home_app1_list.add(ai);
        applicationsHomeAdapter.notifyDataSetChanged();
    }

    private void removeHome(int position) {
        home_app1_list.remove(position);
        applicationsHomeAdapter.notifyDataSetChanged();
        String newStringPreference = "";
        for (int i = 0; i < home_app1_list.size(); i++) {
            AppInfo appInfo = home_app1_list.get(i);
            newStringPreference = newStringPreference + appInfo.packagename + "&" + appInfo.packageclass + "|";
        }
        home_preference.edit().putString("home_app1_list", newStringPreference).commit();
    }

    private void addFavourite(int position) {
        String packageName = "";
        String packageClass = "";
        if (currentAdapterString.equals("verticalGridView")) {
            packageName = applicationsAdapter.getPackageName(position);
            packageClass = applicationsAdapter.getPackageClass(position);
        } else if (currentAdapterString.equals("HorizontalDrawer")) {
            packageName = applicationsAdaptersArray.get(currentPageHorizontal).getPackageName(position);
            packageClass = applicationsAdaptersArray.get(currentPageHorizontal).getPackageClass(position);
        }
        String currentStringPreference = favourite_preference.getString("favouriteapp_list", "");
        favourite_preference.edit().putString("favouriteapp_list", currentStringPreference + packageName + "&" + packageClass + "|").commit();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final PackageManager pm = getPackageManager();
        AppInfo ai = new AppInfo();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, packageClass));
        ResolveInfo ri = pm.resolveActivity(intent, 0);
        if (sharedPrefs.getBoolean("theme_iconpackcheck", false)) {
            Integer resultIconPacks = ai.applicationIconPacks.get(ri.activityInfo.packageName);
            if (resultIconPacks != null)
                ai.icon = getResources().getDrawable(resultIconPacks);
            else ai.icon = ri.loadIcon(pm);
        } else ai.icon = ri.loadIcon(pm);
        ai.label = ri.loadLabel(pm);
        ai.packagename = ri.activityInfo.packageName;
        ai.packageclass = ri.activityInfo.name;
        ai.componentName = new ComponentName(ai.packagename, ai.packageclass);
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(ai.componentName);
        ai.intent = i;
        favourite_list.add(ai);
        //favouriteListViewAdapter.setNewItem(ai);
    }

    private void notifyFavouritesChanges() {
        //applicationsHomeAdapter.notifyDataSetChanged();
        String newStringPreference = "";
        for (int i = 0; i < favouriteListViewAdapter.getCount(); i++) {
            AppInfo appInfo = favouriteListViewAdapter.getItem(i);
            newStringPreference = newStringPreference + appInfo.packagename + "&" + appInfo.packageclass + "|";
        }
        favourite_preference.edit().putString("favouriteapp_list", newStringPreference).commit();
    }

    public void CreateViews() {
        verticalGridView = (GridView) findViewById(R.id.drawerlist);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int drawerrows = Integer.parseInt(sharedPrefs.getString("drawer_rows", "4"));
        int drawercolumns = Integer.parseInt(sharedPrefs.getString("drawer_coloums", "4"));
        verticalGridView.setNumColumns(drawercolumns);
        applicationsAdapter = new ApplicationsAdapter(mApplications, verticalGridView, getApplicationContext(), drawerrows);
        verticalGridView.setAdapter(applicationsAdapter);
        verticalGridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = applicationsAdapter.getIntent(position);
                startActivity(intent);
            }
        });
        registerForContextMenu(verticalGridView);
    }

    public void CreateViewsscroll() {

        //Divide la lista delle applicazioni in altri array, creando la lista delle app delle varie pagine

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        double numappxpage;
        int drawerrows = Integer.parseInt(sharedPrefs.getString("drawer_rows", "4"));
        int drawercolumns = Integer.parseInt(sharedPrefs.getString("drawer_coloums", "4"));
        numappxpage = drawerrows * drawercolumns;
        int npage = (int) StrictMath.ceil(mApplications.size() / numappxpage);

        //Split the mApplications array into others array. Each array correspond to the application array in each drawer page
        splittedarray = new ArrayList[npage];
        for (int i = 0; i < npage; i++) {
            splittedarray[i] = new ArrayList();
        }
        for (int page = 0; page < npage; page++) {
            for (int app = 0; app < numappxpage; app++) {
                if (page * numappxpage + app < mApplications.size())
                    splittedarray[page].add(mApplications.get(page * (int) numappxpage + app));
            }
        }

        findViewById(R.id.drawerlist).setVisibility(View.GONE);
        findViewById(R.id.pager).setVisibility(View.VISIBLE);

        //Create an array of GridView (drawer page) and populate it with slittedarray
        applicationsAdaptersArray = new ArrayList<ApplicationsAdapter>();
        GridView[] gridViewsApps = new GridView[npage];
        for (int app = 0; app < gridViewsApps.length; app++) {
            gridViewsApps[app] = new GridView(this);
            gridViewsApps[app].setNumColumns(drawercolumns);
            gridViewsApps[app].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            final ApplicationsAdapter applicationsAdapterHorizontal = new ApplicationsAdapter(splittedarray[app], gridViewsApps[app], this, drawerrows);
            applicationsAdaptersArray.add(applicationsAdapterHorizontal);
            gridViewsApps[app].setAdapter(applicationsAdapterHorizontal);
            gridViewsApps[app].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = applicationsAdapterHorizontal.getIntent(position);
                    startActivity(intent);
                }
            });
            gridViewsApps[app].setTag("HorizontalDrawerTag");
            registerForContextMenu(gridViewsApps[app]);
        }

        //Set the adapter to the viewPager
        MyPagerAdapter adapter = new MyPagerAdapter(gridViewsApps);
        myPager = (ViewPagerAnim) findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);

        //Set the underlinePageIndicator to the viewPager of the drawer
        UnderlinePageIndicator titleIndicator = (UnderlinePageIndicator) findViewById(R.id.titles);
        titleIndicator.setViewPager(myPager);
        //Check preferences and set the animation
        if (sharedPrefs.getString("drawer_animation", "1").equals("1")) {
            myPager.setPageTransformer(true, null);
        } else if (sharedPrefs.getString("drawer_animation", "1").equals("2")) {
            myPager.setPageTransformer(true, new DepthPageTransformer());
        } else if (sharedPrefs.getString("drawer_animation", "1").equals("3")) {
            myPager.setPageTransformer(true, new ZoomOutPageTransformer());
        } else if (sharedPrefs.getString("drawer_animation", "1").equals("4")) {
            myPager.setPageTransformer(true, new CubeTransformer(true));
        } else if (sharedPrefs.getString("drawer_animation", "1").equals("5")) {
            myPager.setPageTransformer(true, new CubeTransformer(false));
        } else if (sharedPrefs.getString("drawer_animation", "1").equals("6")) {
            myPager.setPageTransformer(true, new RotateTransformer());
        } else if (sharedPrefs.getString("drawer_animation", "1").equals("7")) {
            myPager.setPageTransformer(true, new RotateInTransformer());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                startActivityForResult(i, 1);
                break;
        }
        return true;
    }

    public void loadWallpaper() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPrefs.getBoolean("home_wallpaper_use_check", false)) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            Drawable wallpaperDrawable = wallpaperManager.getDrawable();
            wallpaperImageView.setImageDrawable(wallpaperDrawable);
        } else if (wallpaperImageView.getDrawable() != getResources().getDrawable(R.color.background)) {
            Log.d("Wallpaper", "Setting Wallpaper to background");
            wallpaperImageView.setImageResource(R.color.background);
        }
    }

    public void loadApplicationList() {
        while (!listdrawersStop) {
            if (mApplications == null) {
                mApplications = new ArrayList<AppInfo>();
            }

            mApplications.clear();
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            Intent queryIntent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER);
            PackageManager mPackageManager = getPackageManager();

            List<ResolveInfo> resolveInfos;

            try {
                /**
                 * Eccezione su queryIntentActivities. Con molte app viene superato il limite di 1 mb per le binder transaction.
                 * Io non lo riscontro al primo avvio ma dopo varie volte consecutive che riavvio il drawer.
                 * Non so come risolvere!
                 *
                 *
                 * Caused by: java.lang.RuntimeException: Package manager has died
                 * Caused by: android.os.TransactionTooLargeExceptionTransactionTooLargeException
                 */
                resolveInfos = mPackageManager.queryIntentActivities(queryIntent, 0);
                for (ResolveInfo ri : resolveInfos) {
                    AppInfo ai = new AppInfo();
                    if (sharedPrefs.getBoolean("theme_iconpackcheck", false)) {
                        Integer resultIconPacks = ai.applicationIconPacks.get(ri.activityInfo.packageName);
                        if (resultIconPacks != null)
                            ai.icon = getResources().getDrawable(resultIconPacks);
                        else ai.icon = ri.loadIcon(mPackageManager);
                    } else ai.icon = ri.loadIcon(mPackageManager);
                    ai.label = ri.loadLabel(mPackageManager);
                    ai.packagename = ri.activityInfo.packageName;
                    ai.packageclass = ri.activityInfo.name;
                    ai.componentName = new ComponentName(ai.packagename, ai.packageclass);
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    i.setComponent(ai.componentName);
                    ai.intent = i;
                    mApplications.add(ai);
                }
            } catch (Exception ex) {
                Log.d("LoadApplicationList", ex.getMessage());
            }
            //TODO: rewrite this better!
           /* 09-04 19:45:07.932  18836-18942/? E/AndroidRuntime: FATAL EXCEPTION: AsyncTask #4
            java.lang.RuntimeException: An error occured while executing doInBackground()
            at android.os.AsyncTask$3.done(AsyncTask.java:299)
            at java.util.concurrent.FutureTask.finishCompletion(FutureTask.java:352)
            at java.util.concurrent.FutureTask.setException(FutureTask.java:219)
            at java.util.concurrent.FutureTask.run(FutureTask.java:239)
            at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1080)
            at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:573)
            at java.lang.Thread.run(Thread.java:841)
            Caused by: java.lang.ArrayIndexOutOfBoundsException: length=533; index=533
            at java.util.Collections.sort(Collections.java:1896)
            at com.nowlauncher.nowlauncher.MainActivity.loadApplicationList(MainActivity.java:1310)
            at com.nowlauncher.nowlauncher.MainActivity$ListDrawerscroll.doInBackground(MainActivity.java:1347)
            at com.nowlauncher.nowlauncher.MainActivity$ListDrawerscroll.doInBackground(MainActivity.java:1341)
            at android.os.AsyncTask$2.call(AsyncTask.java:287)
            at java.util.concurrent.FutureTask.run(FutureTask.java:234)
            ... 3 more
*/
     /*-->*/
            Collections.sort(mApplications, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo activityInfo, AppInfo activityInfo2) {
                    return activityInfo.label.toString().toUpperCase().compareTo(
                            activityInfo2.label.toString().toUpperCase());
                }
            });

            listdrawersStop = true;
        }
    }

    protected class ListDrawer extends AsyncTask<String, Void, String> {

        ProgressBar pgbar = (ProgressBar) findViewById(R.id.drawerProgressBar);

        protected String doInBackground(String... params) {
            loadApplicationList();
            return null;
        }

        protected void onPostExecute(String result) {
            CreateViews();
            pgbar.setVisibility(View.GONE);

            loadSearchDrawerListView();
            drawerSearch.setVisibility(View.VISIBLE);
        }
    }

    protected class ListDrawerscroll extends AsyncTask<String, Void, String> {

        ProgressBar pgbar = (ProgressBar) findViewById(R.id.drawerProgressBar);

        protected String doInBackground(String... params) {
            Log.d("MainActivity", "Started ListDrawerscroll");
            loadApplicationList();
            return null;
        }

        protected void onPostExecute(String result) {
            CreateViewsscroll();
            pgbar.setVisibility(View.GONE);

            loadSearchDrawerListView();
            drawerSearch.setVisibility(View.VISIBLE);
        }
    }

    public void loadSearchDrawerListView() {
        final ApplicationSearchAdapter applicationSearchAdapter = new ApplicationSearchAdapter(mApplications, getApplicationContext());
        drawerSearchListView.setAdapter(applicationSearchAdapter);
        drawerSearchListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = applicationSearchAdapter.getIntent(position);
                startActivity(intent);

            }
        });

        drawerSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    applicationSearchAdapter.resetData();
                }
                applicationSearchAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        drawerSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    keyboardhideshowBoolean = true;
                    drawerSearchPanel.setAnimation(translateAnimationDown);
                    drawerSearchPanel.getAnimation().start();
                }
            }
        });
    }

    public void loadFavouritesList() {
        favourite_list = new ArrayList<AppInfo>();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences favourite_pref = getSharedPreferences("favourite_preference", 0);
        String favouriteListString = favourite_pref.getString("favouriteapp_list", "");
        if (!favouriteListString.equals("")) {
            String[] arraystringComponentName = favouriteListString.split("\\|");
            final PackageManager pm = getPackageManager();

            for (int apphome = 0; apphome < arraystringComponentName.length; apphome++) {
                Intent intent = new Intent();
                String[] arraypackageclass = arraystringComponentName[apphome].split("&");
                if (arraypackageclass[0] != null && arraypackageclass[1] != null) {
                    intent.setComponent(new ComponentName(arraypackageclass[0], arraypackageclass[1]));
                    ResolveInfo ri = pm.resolveActivity(intent, 0);
                    AppInfo ai = new AppInfo();
                    if (sharedPrefs.getBoolean("theme_iconpackcheck", false)) {
                        Integer resultIconPacks = ai.applicationIconPacks.get(ri.activityInfo.packageName);
                        if (resultIconPacks != null)
                            ai.icon = getResources().getDrawable(resultIconPacks);
                        else ai.icon = ri.loadIcon(pm);
                    } else ai.icon = ri.loadIcon(pm);
                    ai.label = ri.loadLabel(pm);
                    ai.packagename = ri.activityInfo.packageName;
                    ai.packageclass = ri.activityInfo.name;
                    ai.componentName = new ComponentName(ai.packagename, ai.packageclass);
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_LAUNCHER);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    i.setComponent(ai.componentName);
                    ai.intent = i;
                    favourite_list.add(ai);
                }
            }
        }

        favouriteSmartListView = (DragSortListView) findViewById(R.id.drawer_favourite_listview);
        favouriteListViewAdapter = new ApplicationListViewAdapter(favourite_list, getApplicationContext());
        DragSortListView.DropListener onDrop =
                new DragSortListView.DropListener() {
                    @Override
                    public void drop(int from, int to) {
                        Log.i("SmartListView", "from: " + from + "; to: " + to);
                        if (from != to) {
                            AppInfo item = favouriteListViewAdapter.getItem(from);
                            favouriteListViewAdapter.remove(item);
                            favouriteListViewAdapter.insert(item, to);
                            notifyFavouritesChanges();

                        }
                    }
                };

        DragSortListView.RemoveListener onRemove =
                new DragSortListView.RemoveListener() {
                    @Override
                    public void remove(int which) {
                        favouriteListViewAdapter.remove(favouriteListViewAdapter.getItem(which));
                        notifyFavouritesChanges();
                    }
                };
        favouriteSmartListView.setAdapter(favouriteListViewAdapter);
        favouriteSmartListView.setRemoveListener(onRemove);
        favouriteSmartListView.setDropListener(onDrop);
        favouriteSmartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = favouriteListViewAdapter.getIntent(position);
                startActivity(intent);

            }
        });
    }

    public void gotWeatherInfo(final WeatherInfo weatherInfo) {
        if (weatherInfo != null) {

            todayweath = (TextView) findViewById(R.id.todayweather);
            todayweath.setText("Today");

            forecast1 = (TextView) findViewById(R.id.forecast1);
            forecast1.setText(weatherInfo.getForecast2Day());

            forecast2 = (TextView) findViewById(R.id.forecast2);
            forecast2.setText(weatherInfo.getForecast3Day());

            forecast3 = (TextView) findViewById(R.id.forecast3);
            forecast3.setText(weatherInfo.getForecast4Day());

            forecast4 = (TextView) findViewById(R.id.forecast4);
            forecast4.setText(weatherInfo.getForecast5Day());


            temperaturenow = (TextView) findViewById(R.id.temperaturenow);
            temperaturenow.setText(weatherInfo.getCurrentTempC() + "°C, " + weatherInfo.getAtmosphereHumidity() + "%");


            ImageView yahoologo = (ImageView) findViewById(R.id.yahooLogo);
            //Set link to the Yahoo!Weather logo
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
            super.onPostExecute(results);
            citylb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(), results[0]));
            todayweath.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(), results[1]));
            forecast1.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(), results[2]));
            forecast2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(), results[3]));
            forecast3.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(), results[4]));
            forecast4.setCompoundDrawablesWithIntrinsicBounds(null, null, null, new BitmapDrawable(getResources(), results[5]));

        }

    }

    public class CardAdapter extends PagerAdapter {

        public int getCount() {
            return 2;
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
            ((ViewPager) collection).addView(findViewById(resId));
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
     * Method called when the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        //Remove the thread Runnable in background
        mHandler.removeCallbacks(mUpdateTimeTask);
        //Reset the mediaplayer
        mp.reset();
        //Remove the receiver
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * Method called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {

        if (sldrawer.isOpened()) {
            if (drawerSearchIsOpened) {
                keyboardhideshowBoolean = true;
                drawerSearchPanel.setAnimation(translateAnimationDown);
                drawerSearchPanel.getAnimation().start();
                drawerSearchPanel.setVisibility(View.GONE);
                drawerSearchIsOpened = false;
            } else sldrawer.animateClose();
        } else if (cardPager.getCurrentItem() != 0)
            cardPager.setCurrentItem(cardPager.getCurrentItem() - 1);

    }

    /**
     * Method called when a new intent of this activity is created (so when home button is pressed and you are already in the launcher)
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (sldrawer.isOpened()) sldrawer.animateClose();
        if (cardPager.getCurrentItem() != 0)
            cardPager.setCurrentItem(cardPager.getCurrentItem() - 1);
    }

    /**
     * Method called when a there is a activity result
     * We use this to check preferences results
     */
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("restartdrawer", false)) loadDrawer();
            if (data.getBooleanExtra("setanimation", false)) CreateViewsscroll();
            if (data.getBooleanExtra("setwallpaper", false)) loadWallpaper();
        }
    }


    public class PackageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("PackageReceiver", "Application installed/uninstalled");
            loadDrawer();
        }
    }
}