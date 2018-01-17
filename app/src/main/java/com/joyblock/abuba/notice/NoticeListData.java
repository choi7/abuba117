package com.joyblock.abuba.notice;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.ListData;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by hyoshinchoi on 2018. 1. 3..
 */

public class NoticeListData {

    //아이콘
    public Drawable mIcon;
    //제목
    public String mTitle;
    //부제목
    public String mNickname;
    public String mTime;
    public String mCount;




    //알파벳이름으로 정렬

    public static final Comparator<ListData> ALPHA_COMPARTOR = new Comparator<ListData>() {

        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData o1, ListData o2) {
            return sCollator.compare(o1.mTitle,o2.mTitle);
        }
    };
}
