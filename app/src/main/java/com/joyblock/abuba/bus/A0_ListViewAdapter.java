package com.joyblock.abuba.bus;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NSER-00 on 2017-07-25.
 */

public class A0_ListViewAdapter extends BaseAdapter implements Serializable {

    private ArrayList<Item> listViewItems = new ArrayList<Item>();
    BaseActivity activity;
    Typeface font;
    ListViewHolder holder;

    public A0_ListViewAdapter(Typeface font){
        this.font=font;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Item getItem(int position) {
        return listViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();
        ImageView iconImageView,iconImageView1;
        TextView titleTextView;

        Item listViewItem = listViewItems.get(position);
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(listViewItem.int_layout, parent, false);
            holder=new ListViewHolder();
//            R.layout.park_fragment_document_listview_item
            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            iconImageView = (ImageView) convertView.findViewById(R.id.imageView_item);
            holder.iconImageView=iconImageView;
            titleTextView = (TextView) convertView.findViewById(R.id.textView_item);
            holder.titleTextView=titleTextView;
            iconImageView1 = (ImageView) convertView.findViewById(R.id.imageView_item1);
            holder.iconImageView1=iconImageView1;
        }else{
            try {
                holder = (ListViewHolder) convertView.getTag();
                iconImageView = holder.iconImageView;
                titleTextView = holder.titleTextView;
                iconImageView1 = holder.iconImageView1;
            }catch(NullPointerException e){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(listViewItem.int_layout, parent, false);
                holder=new ListViewHolder();
                iconImageView = (ImageView) convertView.findViewById(R.id.imageView_item);
                holder.iconImageView=iconImageView;
                titleTextView = (TextView) convertView.findViewById(R.id.textView_item);
                holder.titleTextView=titleTextView;
                iconImageView1 = (ImageView) convertView.findViewById(R.id.imageView_item1);
                holder.iconImageView1=iconImageView1;
            }
        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득


        titleTextView.setTypeface(font);
        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);
        }else{
            titleTextView.setTextColor(Color.BLUE);
        }
//        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        iconImageView1.setImageDrawable(listViewItem.getIcon1());

        //리스트뷰 안에 홀수마다 백그라운드 컬러 지정
        if (!(position % 2 == 0)) {
            convertView.setBackgroundColor(Color.parseColor("#F7F7F7"));
        }

        return convertView;
    }

    public void removeItem(int index){
        listViewItems.remove(index);
    }


    public void addItem(int int_layout,Drawable icon, String title, Drawable icon1,int index) {
        Item item = new Item();

        item.setIcon(icon);
        item.setTitle(title);
        item.setIcon1(icon1);
        item.setLayout(int_layout);

        listViewItems.add(index,item);
    }

    public void addItem(int int_layout,Drawable icon, String title, Drawable icon1) {
        Item item = new Item();

        item.setIcon(icon);
        item.setTitle(title);
        item.setIcon1(icon1);
        item.setLayout(int_layout);
        listViewItems.add(item);
    }
    private class ListViewHolder {
        ImageView iconImageView,iconImageView1;
        TextView titleTextView;
    }

    public class Item {

        int int_layout;

        private Drawable iconDrawable ;
        private String titleStr ;
        private Drawable iconDrawable1 ;
        boolean a=false;

        public void setIcon(Drawable icon) {
            iconDrawable = icon ;
        }
        public void setTitle(String title) {
            titleStr = title ;
        }
        public void setLayout(int int_layout){this.int_layout=int_layout;}

        public void setIcon1(Drawable icon) {
            iconDrawable1 = icon ;
        }

        public Drawable getIcon() {
            return this.iconDrawable ;
        }
        public String getTitle() {
            return this.titleStr ;
        }
        public Drawable getIcon1() {
            return this.iconDrawable1 ;
        }
    }
}
