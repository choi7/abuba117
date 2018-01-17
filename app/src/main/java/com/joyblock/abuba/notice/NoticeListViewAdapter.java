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
import com.squareup.picasso.RequestCreator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NSER-00 on 2017-07-25.
 */

public class NoticeListViewAdapter extends BaseAdapter implements Serializable {

    private ArrayList<NoticeListViewItem> listViewItems = new ArrayList<NoticeListViewItem>();
    BaseActivity activity;
    Typeface font;
    ListViewHolder holder;


    public NoticeListViewAdapter(){
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


        photo.setImageDrawable(listViewItem.getPhoto());
        title.setText(listViewItem.getTitle());
        name.setText(listViewItem.getName());
        time.setText(listViewItem.getTime());

        return convertView;
    }

    public void addItem(Drawable photo, String title, String time, String name) {
        listViewItems.add(new NoticeListViewItem(photo,title,name,time));
    }

    public class ListViewHolder {
        ImageView photo;
        TextView title,time,name;
    }
}
