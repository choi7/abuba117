package com.joyblock.abuba.document;

import android.graphics.drawable.Drawable;

public class NoticeItem {

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