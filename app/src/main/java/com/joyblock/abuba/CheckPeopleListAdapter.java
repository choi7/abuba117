package com.joyblock.abuba;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hyoshinchoi on 2018. 1. 19..
 */

public class CheckPeopleListAdapter extends BaseAdapter implements Serializable {


    ArrayList<NoticeListViewItem> listViewItems = new ArrayList<NoticeListViewItem>();
    ListViewHolder holder;
    Context context;

    public CheckPeopleListAdapter(Context context){
        this.context=context;

    }


    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public NoticeListViewItem getItem(int position) {
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


        ImageView photo;
        TextView peopleClass,kidsName,parentName;
        //        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.checkpeoplelistviewcellcustom, parent, false);
            photo = (ImageView) convertView.findViewById(R.id.checkPeopleImageView);
            peopleClass = (TextView) convertView.findViewById(R.id.checkPeopleClassText);
            kidsName = (TextView) convertView.findViewById(R.id.checkPeopleKidsNameText);
            parentName = (TextView) convertView.findViewById(R.id.checkPeopleParentNameText);
            holder=new ListViewHolder();
            holder.photo=photo;
            holder.peopleClass=peopleClass;
            holder.kidsName=kidsName;
            holder.parentName=parentName;
            convertView.setTag(holder);
        }else{
            holder=(ListViewHolder)convertView.getTag();
            photo=holder.photo;
            peopleClass=holder.peopleClass;
            kidsName=holder.kidsName;
            parentName=holder.parentName;
        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        NoticeListViewItem listViewItem = listViewItems.get(position);

/*
        if(!listViewItem.getFile_path().equals(""))
            Picasso.with(context).load(listViewItem.getFile_path()).resize(180,180).error(R.drawable.ch_on).centerCrop().into(photo);
*/
        photo.setImageResource(R.drawable.check_1_no);

//        photo.setImageDrawable(listViewItem.getPhoto());
        peopleClass.setText(listViewItem.getTitle());
        parentName.setText(listViewItem.getName());
        kidsName.setText(listViewItem.getTime());
        return convertView;
    }

    public void addItem(String file_path, String title,String time,String name) {
        listViewItems.add(new NoticeListViewItem(file_path,title,name,time));
    }

    public class ListViewHolder {
        ImageView photo;
        TextView peopleClass,parentName,kidsName;
    }

    public class NoticeListViewItem {

        private String file_path, peopleClass,parentName,kidsName;
        boolean a=false;

        public NoticeListViewItem(String file_path,String peopleClass,String parentName,String kidsName){
//            this.file_path=file_path;
            this.peopleClass=peopleClass;
            this.parentName=parentName;
            this.kidsName=kidsName;
        }

        public String getFile_path(){
            return file_path;
        }
        public String getTitle(){
            return peopleClass;
        }
        public String getName(){
            return parentName;
        }
        public String getTime(){
            return kidsName;
        }


//    public Drawable getIcon() {
//        return this.iconDrawable ;
//    }
//    public String getTitle() {
//        return this.titleStr ;
//    }
//    public Drawable getIcon1() {
//        return this.iconDrawable1 ;
//    }

    }

}
