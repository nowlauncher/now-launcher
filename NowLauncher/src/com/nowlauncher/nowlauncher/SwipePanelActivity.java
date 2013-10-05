package com.nowlauncher.nowlauncher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by andrea on 15/09/13.
 */
public class SwipePanelActivity extends Activity {
    private SharedPreferences preferences;
    ArrayList<AppInfo> favouriteapp_list;
    private ApplicationListViewAdapter applicationListViewAdapter;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_swipe_panel);
        preferences=getSharedPreferences("favourite_preference",0);
        listView=(ListView)findViewById(R.id.favourite_apps_listeview_panel);
        loadFavourites();
    }

    private void loadFavourites(){
        favouriteapp_list=new ArrayList<AppInfo>();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String favouriteListString = preferences.getString("favouriteapp_list", "");
        if (!favouriteListString.equals("")) {
            String[] arraystringComponentName = favouriteListString.split("\\|");
            final PackageManager pm = getPackageManager();

            for (String anArraystringComponentName : arraystringComponentName) {
                Intent intent = new Intent();
                String[] arraypackageclass = anArraystringComponentName.split("&");
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
                    favouriteapp_list.add(ai);
                }
            }
        }


        applicationListViewAdapter = new ApplicationListViewAdapter(favouriteapp_list, this);
        listView.setAdapter(applicationListViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = applicationListViewAdapter.getIntent(position);
                startActivity(intent);
                //TODO: close activity when clicking item or in an external zone
            }
        });

    }
}
