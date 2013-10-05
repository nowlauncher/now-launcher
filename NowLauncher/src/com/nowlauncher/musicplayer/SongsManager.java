package com.nowlauncher.musicplayer;


import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
    public Context mcontext;
    public String fullpath;
    public String album_id;
    public String song_name;
    public String album_name;
    public Drawable album_art;
    public String artist_name;
    public int song_index;
	// Constructor
	public SongsManager(Context context){
		mcontext=context;
	}

    public String getPath() {
        return fullpath;
    }
    public String getAlbumId() {
        return album_id;
    }
    public String getTitle() {
        return song_name;
    }
    public String getAlbumName() {
        return album_name;
    }
    public String getArtist() {
        return artist_name;
    }
    public Drawable getAlbumArt(){
        return album_art;
    }
    public Bitmap setAlbumArt(Long album_id, int dimensions)
    {
        Bitmap bm = null;
        try
        {
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
            ParcelFileDescriptor pfd = mcontext.getContentResolver().openFileDescriptor(uri, "r");

            if (pfd != null)
            {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm=Bitmap.createScaledBitmap(BitmapFactory.decodeFileDescriptor(fd), dimensions,dimensions, true);
            }
        } catch (Exception e) {
            bm=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.unknownalbum), dimensions,dimensions, true);
        }
        return bm;
    }

}
