package com.nowlauncher.nowlauncher;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nowlauncher.musicplayer.SongsManager;

import java.util.ArrayList;

/**
 * Created by andrea on 11/08/13.
 */
public class MusicListViewAdapter extends ArrayAdapter {
    ArrayList<SongsManager> objects;

    Context mcontext;
    public MusicListViewAdapter(Context context, ArrayList<SongsManager> objects) {
        super(context,R.layout.musicrow,objects);
        this.mcontext=context;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.musicrow, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.musicart);
            viewHolder.text = (TextView)convertView.findViewById(R.id.musictext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //Bitmap songAlbumArt=getAlbumart(Long.valueOf(objects.get(position).getAlbumId()));
        viewHolder.image.setImageDrawable(new BitmapDrawable(mcontext.getResources(),objects.get(position).getAlbumArt()));
        viewHolder.text.setText(objects.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder {
        public ImageView image;
        public TextView text;
    }

}
