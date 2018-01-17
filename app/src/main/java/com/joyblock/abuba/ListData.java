package com.joyblock.abuba;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by hyoshinchoi on 2017. 12. 29..
 */

public class ListData {

    //아이콘
    public Drawable mIcon;
    //제목
    public String mTitle;
    //부제목
    public String mDate;

    public boolean a = true;

    //알파벳이름으로 정렬

    public static final Comparator<ListData> ALPHA_COMPARTOR = new Comparator<ListData>() {

        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData o1, ListData o2) {
            return sCollator.compare(o1.mTitle, o2.mTitle);
        }
    };
}
