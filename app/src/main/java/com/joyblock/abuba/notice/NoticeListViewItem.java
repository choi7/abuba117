package com.joyblock.abuba.notice;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeListViewItem {

    private Drawable photo ;
    private String title,count,name,time;
    boolean a=false;

    public NoticeListViewItem(Drawable photo,String title,String count,String name,String time){
        this.photo=photo;
        this.title=title;
        this.count=count;
        this.name=name;
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.time=dateFormat.format(new Date(Long.parseLong(time))).toString();
    }

    public Drawable getPhoto(){
        return photo;
    }
    public String getTitle(){
        return title;
    }
    public String getCount(){
        return count;
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