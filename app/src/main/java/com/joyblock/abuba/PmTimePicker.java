package com.joyblock.abuba;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

/**
 * Created by BLUE on 2018-01-28.
 */

public class PmTimePicker extends TimePicker {
    private final int m_iColor = 0xffffffff;

    public PmTimePicker(Context context) {
        super(context);
        Create(context, null);
    }

    public PmTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        Create(context, attrs);
    }

    public PmTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Create(context, attrs);
    }

    private void Create (Context clsContext, AttributeSet attrs) {
        try {
            Class<?> clsParent = Class.forName( "com.android.internal.R$id" );
            NumberPicker clsAmPm = (NumberPicker)findViewById( clsParent.getField( "amPm" ).getInt( null ) );
            NumberPicker clsHour = (NumberPicker)findViewById( clsParent.getField( "hour" ).getInt( null ) );
            NumberPicker clsMin = (NumberPicker)findViewById( clsParent.getField( "minute" ).getInt( null ) );
            Class<?> clsNumberPicker = Class.forName( "android.widget.NumberPicker" );
            Field clsSelectionDivider = clsNumberPicker.getDeclaredField( "mSelectionDivider" );

            clsSelectionDivider.setAccessible( true );
            ColorDrawable clsDrawable = new ColorDrawable( m_iColor );
            clsSelectionDivider.set(clsAmPm, clsDrawable);
            clsSelectionDivider.set(clsHour, clsDrawable);
            clsSelectionDivider.set(clsMin, clsDrawable);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
