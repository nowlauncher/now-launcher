package com.nowlauncher.nowlauncher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrea on 10/09/13.
 */
public class ApplicationsHomeAdapter extends BaseAdapter {

    ArrayList<AppInfo> mInfos;
    Context mContext;

    ApplicationsHomeAdapter(ArrayList<AppInfo> mApplications, Context context) {
        mContext=context;
        mInfos=mApplications;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfos.get(position);
    }

    public Intent getIntent(int position) {
        return mInfos.get(position).intent;
    }

    @Override
    public long getItemId(int position) {
        return mInfos.get(position).componentName.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_model_home, null);
            viewHolder = new ViewHolder();
            viewHolder.appname=(TextView)convertView.findViewById(R.id.label);
            viewHolder.appicon=(ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        AppInfo ai = mInfos.get(position);
        viewHolder.appname.setText(ai.label);
        viewHolder.appicon.setImageDrawable(ai.icon);
        return convertView;
    }

    public String getPackageName(int position) {
        return mInfos.get(position).packagename;
    }
    public String getPackageClass(int position) {
        return mInfos.get(position).packageclass;
    }

    public class ViewHolder {
        public TextView appname;
        private ImageView appicon;

    }
}

