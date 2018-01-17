package com.joyblock.abuba.notice;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.joyblock.abuba.R;

/**
 * Created by hyoshinchoi on 2018. 1. 4..
 */

public class NoticeEditBottom2 extends ConstraintLayout {
    Context context;

    public NoticeEditBottom2(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.noticeeditbottom2, this, true);
    }
}
