package com.joyblock.abuba.album;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by BLUE on 2018-01-24.
 */

public class Album_View_Adapter extends BaseAdapter implements Serializable {

    public ArrayList<BanListViewItem> list = new ArrayList<BanListViewItem>();
    BaseActivity activity;
    ListViewHolder holder;

    public Album_View_Adapter(){

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        ImageView name;
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_cell_album_image_list, parent, false);
            name = (ImageView) convertView.findViewById(R.id.custom_cell_Album_Image);
            holder=new ListViewHolder();
            holder.name=name;
            convertView.setTag(holder);
            name.setVisibility(View.VISIBLE);
        }else {
            holder=(ListViewHolder)convertView.getTag();
            name=holder.name;
        }
//        name.setText(list.get(pos).getName());
        Picasso.with(context).load(list.get(position).name).into(name);


        return convertView;
    }



    public class BanListViewItem {
        private Uri name;
        public BanListViewItem(Uri name){
            this.name=name;
        }
        public Uri getName(){
            return name;
        }
    }

    public void addItem(Uri name) {
        list.add(new BanListViewItem(name));
    }
    private class ListViewHolder {
        ImageView name;
    }

}
