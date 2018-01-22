package com.joyblock.abuba;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.document.A3_1_Medicine;

/**
 * Created by hyoshinchoi on 2018. 1. 22..
 */

public class CalendarCustomDialong_document extends DatePickerDialog {

    private TextView mTitleView;
    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mTitle;
    private String mContent;
    private String myear;
    private String mmonth;
    private String mday;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public Integer year, month, day;
    public DatePicker datePicker;

    public A3_1_Medicine qas;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CalendarCustomDialong_document(@NonNull Context context) {
        super(context);
        this.qas = (A3_1_Medicine) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        //화면 외부 백그라운드 색상 투명
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //뷰 외부 터치 가능하게 함
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        setContentView(R.layout.activity_calendar_custom_dialog);


        //뷰 외부 터치시 종료
        this.setCanceledOnTouchOutside(true);
//        System.gc();

        Button trueButton = (Button) findViewById(R.id.calendarTrueButton);
        Button trueButton1 = (Button) findViewById(R.id.calendarTrueButton1);
        trueButton.setVisibility(View.GONE);
        Button falseButton = (Button) findViewById(R.id.calendarFalseButton);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        day = datePicker.getDayOfMonth();
        year = datePicker.getYear();
        month = datePicker.getMonth();


        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarCustomDialong_document.this.cancel();
            }
        });




    }
}
