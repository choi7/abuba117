package com.joyblock.abuba.notice;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NSER-00 on 2017-07-25.
 */

public class NoticeListViewAdapter extends BaseAdapter implements Serializable {

    ArrayList<NoticeListViewItem> listViewItems = new ArrayList<NoticeListViewItem>();
    BaseActivity activity;
    Typeface font;
    ListViewHolder holder;
    Context context;


    public NoticeListViewAdapter(Context context){
        this.context=context;

    }



    public NoticeListViewAdapter(Typeface font){
        this.font=font;
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
        TextView title,time,name;
        //        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.noticelistviewcustom, parent, false);
            photo = (ImageView) convertView.findViewById(R.id.photo);
            title= (TextView) convertView.findViewById(R.id.titleListViewCell);
            time = (TextView) convertView.findViewById(R.id.timeViewCell);
            name = (TextView) convertView.findViewById(R.id.nickListViewCell);
            holder=new ListViewHolder();
            holder.photo=photo;
            holder.title=title;
            holder.time=time;
            holder.name=name;
            convertView.setTag(holder);
        }else{
            holder=(ListViewHolder)convertView.getTag();
            photo=holder.photo;
            title=holder.title;
            time=holder.time;
            name=holder.name;
        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        NoticeListViewItem listViewItem = listViewItems.get(position);

//        titleTextView.setTypeface(font);
//        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);0
//        }else{
//            titleTextView.setTextColor(Color.BLUE);
//        }
        // 아이템 내 각 위젯에 데이터 반영
        if(!listViewItem.getFile_path().equals(""))
            Picasso.with(context).load(listViewItem.getFile_path()).resize(180,180).centerCrop().into(photo);


//        photo.setImageDrawable(listViewItem.getPhoto());
        title.setText(listViewItem.getTitle());
        name.setText(listViewItem.getName());
        time.setText(listViewItem.getTime());

        return convertView;
    }

    public void addItem(String file_path, String title,String time,String name) {
        listViewItems.add(new NoticeListViewItem(file_path,title,name,time));
    }

    public class ListViewHolder {
        ImageView photo;
        TextView title,time,name;
    }

    public class NoticeListViewItem {

        private String file_path, title,name,time;
        boolean a=false;

        public NoticeListViewItem(String file_path,String title,String name,String time){
            this.file_path=file_path;
            this.title=title;
            this.name=name;
            this.time=time;
        }

        public String getFile_path(){
            return file_path;
        }
        public String getTitle(){
            return title;
        }
        public String getName(){
            return name;
        }
        public String getTime(){
            return time;
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
