package com.joyblock.abuba.notice;

import android.graphics.drawable.Drawable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeListViewItem {

    private Drawable photo ;
    private String title,name,time;
    boolean a=false;

    public NoticeListViewItem(Drawable photo,String title,String name,String time){
        this.photo=photo;
        this.title=title;
        this.name=name;
        this.time=time;
    }

    public Drawable getPhoto(){
        return photo;
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