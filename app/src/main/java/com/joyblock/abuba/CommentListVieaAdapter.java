package com.joyblock.abuba;

import android.content.Context;
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

public class CommentListVieaAdapter extends BaseAdapter implements Serializable {

    ArrayList<NoticeListViewItem> listViewItems = new ArrayList<NoticeListViewItem>();
    ListViewHolder holder;
    Context context;

    public CommentListVieaAdapter(Context context){
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


//        ImageView photo;
        TextView title,time,name;
        //        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.commentlistviewcellcustom, parent, false);
//            photo = (ImageView) convertView.findViewById(R.id.commentProfileImage);
            title= (TextView) convertView.findViewById(R.id.commentContentText);
            time = (TextView) convertView.findViewById(R.id.commentTimeText);
            name = (TextView) convertView.findViewById(R.id.commentNameText);
            holder=new ListViewHolder();
//            holder.photo=photo;
            holder.title=title;
            holder.time=time;
            holder.name=name;
            convertView.setTag(holder);
        }else{
            holder=(ListViewHolder)convertView.getTag();
//            photo=holder.photo;
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
//        if(!listViewItem.getFile_path().equals(""))
//            Picasso.with(context).load(listViewItem.getFile_path()).resize(180,180).centerCrop().into(photo);


//        photo.setImageDrawable(listViewItem.getPhoto());
        title.setText(listViewItem.getTitle());
        name.setText(listViewItem.getName());
        time.setText(listViewItem.getTime());
        return convertView;
    }
//String file_path,
    public void addItem( String title,String time,String name) {
//        listViewItems.add(new CommentListVieaAdapter.NoticeListViewItem(file_path,title,name,time));
        listViewItems.add(new NoticeListViewItem(title,name,time));
    }

    public class ListViewHolder {
//        ImageView photo;

        TextView title,time,name;
    }

    public class NoticeListViewItem {

        private String file_path, title,name,time;
        boolean a=false;
//String file_path,
        public NoticeListViewItem(String title,String name,String time){
//            this.file_path=file_path;
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