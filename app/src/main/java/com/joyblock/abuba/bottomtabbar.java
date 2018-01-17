package com.joyblock.abuba;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;

/**
 * Created by hyoshinchoi on 2018. 1. 3..
 */

public class bottomtabbar extends ConstraintLayout{
    Context context;

    public bottomtabbar(Context context, AttributeSet attrs) {
        super(context, attrs);

            this.context = context;
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            inflater.inflate(R.layout.bottomactionbar, this, true);

        }
    }

