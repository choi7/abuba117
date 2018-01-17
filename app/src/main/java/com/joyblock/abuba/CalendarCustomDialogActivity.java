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


public class CalendarCustomDialogActivity extends DatePickerDialog {

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
    DatePicker datePicker;
    ImageView imageView;

    QuestionnaireActivity qa;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CalendarCustomDialogActivity(@NonNull Context context) {
        super(context);
        this.qa = (QuestionnaireActivity) context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        //화면 외부 백그라운드 색상 투명
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //뷰 외부 터치 가능하게 함
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        //뷰 외부 터치시 종료
        this.setCanceledOnTouchOutside(true);
//        System.gc();
        setContentView(R.layout.activity_calendar_custom_dialog);

        Button trueButton = (Button) findViewById(R.id.calendarTrueButton);
        Button falseButton = (Button) findViewById(R.id.calendarFalseButton);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
//        datePicker.setDrawingCacheBackgroundColor(Color.parseColor("#009688"));
        imageView = qa.questTimeSettingImageView;


        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.ch_off);
                CalendarCustomDialogActivity.this.cancel();
            }
        });

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qa.day = datePicker.getDayOfMonth();
                qa.month = datePicker.getMonth() + 1;
                qa.year = datePicker.getYear();
                imageView.setImageResource(R.drawable.ch_on);
                System.out.println(qa.day + "" + qa.month + "" + qa.year);
                CalendarCustomDialogActivity.this.dismiss();
            }
        });
    }
}




