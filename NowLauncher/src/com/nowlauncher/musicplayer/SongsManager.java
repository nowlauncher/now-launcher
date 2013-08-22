package com.nowlauncher.musicplayer;


import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import com.nowlauncher.nowlauncher.R;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
    private Context mcontext;
	private ArrayList<SongsManager> songsList = new ArrayList<SongsManager>();
    private String fullpath;
    private String album_id;
    private String song_name;
    private String album_name;
    private String artist_name;
    private Bitmap album_art;
    private int song_index;
	// Constructor
	public SongsManager(Context context){
		mcontext=context;
	}
	
	/**
	 * Function to read all mp3 files from sdcard
	 * and store the details in ArrayList
	 * */
	public ArrayList<SongsManager> getPlayList(){
        String[] STAR = { "*" };
        Uri allaudiosong = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String audioselection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor;
        cursor = mcontext.getContentResolver().query(allaudiosong, STAR, audioselection, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SongsManager song = new SongsManager(mcontext);
                    song.fullpath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
                    song.album_id = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(song.fullpath);
                    song.song_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                    song.album_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                    song.artist_name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                    song.song_index=cursor.getPosition();
                    songsList.add(song);
                } while (cursor.moveToNext());
            }
        }
		// return songs list array
        cursor.close();
		return songsList;
	}
    public final String getPath() {
        return fullpath;
    }
    public final String getAlbumId() {
        return album_id;
    }
    public final String getTitle() {
        return song_name;
    }
    public final String getAlbumName() {
        return album_name;
    }
    public final String getArtist() {
        return artist_name;
    }
    public final Bitmap getAlbumArt() {
        return getAlbumart(Long.valueOf(getAlbumId()));
    }
    public Bitmap getAlbumart(Long album_id)
    {
        Bitmap bm = null;
        try
        {
            final Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = mcontext.getContentResolver()
                    .openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
        } catch (Exception e) {
            bm=BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.unknownalbum);
        }
        return bm;
    }

}
