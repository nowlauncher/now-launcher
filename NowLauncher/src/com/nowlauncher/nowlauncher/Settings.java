package com.nowlauncher.nowlauncher;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Settings extends PreferenceActivity {
    Preference restartDrawer;
    Preference appspackPreference;
    Preference setWallpaper;
    CheckBoxPreference wallpapercheck;
    Bitmap wallpaperSelected;
    WallpaperManager wallpaperManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        restartDrawer=findPreference("drawer_restart");
        final Intent i = getIntent();
        restartDrawer.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                i.putExtra("restartdrawer", true);
                setResult(RESULT_OK, i);
                finish();
                return false;
            }
        });
        String[] ss=new String[]{"Not already implemented", "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbrad", "Honeycomb", "Ice Cream Sandwich", "JellyBean", "KitKat" };
        ListView listAppWithIconPack=new ListView(this);
        listAppWithIconPack.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ss));
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(listAppWithIconPack);
        builder.setTitle(R.string.theme_icon_title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                //action on dialog close
            }

        });
        appspackPreference=findPreference("theme_icon_replace");
        appspackPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                builder.show();
                return false;
            }
        });
        wallpapercheck=(CheckBoxPreference)findPreference("home_wallpaper_use_check");
        wallpapercheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue instanceof Boolean){
                    Boolean boolVal = (Boolean)newValue;
                    wallpapercheck.setChecked(boolVal);
                }
                i.putExtra("setwallpaper", true);
                setResult(RESULT_OK, i);
                return false;
            }
        });

        setWallpaper = findPreference("home_setwallpaper");
        setWallpaper.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                pickImage();
                i.putExtra("setwallpaper", true);
                setResult(RESULT_OK, i);
                return false;
            }
        });
    }
    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK){
            try {
                // We need to recyle unused bitmaps
                if (wallpaperSelected != null) {
                    wallpaperSelected.recycle();
                }
                Uri selectedImage = data.getData();
                wallpaperSelected = decodeUri(selectedImage);
                wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
                wallpaperManager.setBitmap(wallpaperSelected);

            }
            catch (Exception e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(), "An error occured while trying to set the wallpaper! Sorry...", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 900;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
    }
}