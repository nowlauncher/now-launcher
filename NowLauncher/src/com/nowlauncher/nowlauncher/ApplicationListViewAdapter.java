package com.nowlauncher.nowlauncher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragSortListView.SimpleDragSortCursorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrea on 02/09/13.
 */
public class ApplicationListViewAdapter extends ArrayAdapter<AppInfo> {
    ArrayList<AppInfo> mApplications;
    Context context;
    public ApplicationListViewAdapter(ArrayList<AppInfo> mApplications, Context context){
        super(context,R.layout.app_model_search, mApplications);
        this.context=context;
        this.mApplications=mApplications;
    }
    public Intent getIntent(int position) {
        return mApplications.get(position).intent;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_model_search, null);
            viewHolder = new ViewHolder();
            viewHolder.appname=(TextView)convertView.findViewById(R.id.appSearchText);
            viewHolder.appicon=(ImageView)convertView.findViewById(R.id.appSearchIcon);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        AppInfo ai = mApplications.get(position);
        viewHolder.appname.setText(ai.label);
        viewHolder.appicon.setImageDrawable(ai.icon);
        return convertView;
    }/*

    public void remove(AppInfo item) {
        mApplications.remove(item);
    }
    public void remove(int which) {
        mApplications.remove(which);
    }
    public void insert(AppInfo item, int to) {
        mApplications.add(to,item);
    }

*/
    public class ViewHolder {
        public TextView appname;
        private ImageView appicon;
    }

}
