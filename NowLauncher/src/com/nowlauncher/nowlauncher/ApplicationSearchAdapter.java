package com.nowlauncher.nowlauncher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrea on 02/09/13.
 */
public class ApplicationSearchAdapter extends BaseAdapter implements Filterable {
    ArrayList<AppInfo> mApplications;
    ArrayList<AppInfo> originalmApplications;
    private Filter appFilter;
    Context context;
    public ApplicationSearchAdapter(ArrayList<AppInfo> mApplications, Context context){
        this.context=context;
        this.mApplications=mApplications;
        this.originalmApplications=mApplications;
    }

    @Override
    public int getCount() {
        return mApplications.size();
    }

    @Override
    public Object getItem(int position) {
        return mApplications.get(position);
    }
    public Intent getIntent(int position) {
        return mApplications.get(position).intent;
    }
    @Override
    public long getItemId(int position) {
        return mApplications.get(position).hashCode();
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
    }
    public void resetData() {
        mApplications = originalmApplications;
    }
    public class ViewHolder {
        public TextView appname;
        private ImageView appicon;
    }

    @Override
    public Filter getFilter() {
        if (appFilter == null)
            appFilter = new AppFilter();
        return appFilter;
    }



    private class AppFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = originalmApplications;
                results.count = originalmApplications.size();
            }
            else {
                // We perform filtering operation
                List<AppInfo> nAppList = new ArrayList<AppInfo>();

                if (sharedPrefs.getString("drawerSearchOptions","1").equals("1")){
                    for (AppInfo p : mApplications) {
                        if (p.label.toString().toUpperCase().contains(constraint.toString().toUpperCase()))
                            nAppList.add(p);
                    }
                }
                else if (sharedPrefs.getString("drawerSearchOptions","1").equals("2")){
                    for (AppInfo p : mApplications) {
                        if (p.label.toString().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                            nAppList.add(p);
                    }
                }


                results.values = nAppList;
                results.count = nAppList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                mApplications = (ArrayList<AppInfo>) results.values;
                notifyDataSetChanged();
            }

        }

    }
}
