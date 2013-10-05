package com.nowlauncher.nowlauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowlauncher.musicplayer.SongsManager;

import java.util.ArrayList;

/**
 * Created by andrea on 11/08/13.
 */
public class MusicListViewAdapter extends BaseAdapter {
    ArrayList<SongsManager> objects;

    Context mcontext;
    public MusicListViewAdapter(Context context, ArrayList<SongsManager> objects) {
        this.mcontext=context;
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.musicrow, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.musicart);
            viewHolder.text = (TextView)convertView.findViewById(R.id.musictext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageDrawable(objects.get(position).getAlbumArt());
        viewHolder.text.setText(objects.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView text;
    }

}
