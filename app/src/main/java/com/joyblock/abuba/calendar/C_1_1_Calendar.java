package com.joyblock.abuba.calendar;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.FragmentCalendar;
import com.joyblock.abuba.FragmentCarter;
import com.joyblock.abuba.MainDawerSelectActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.document.FragmentAbsence;


/**
 * Created by BLUE on 2018-01-26.
 */

public class C_1_1_Calendar extends BaseActivity implements View.OnClickListener {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private ConstraintLayout bt_tab1, bt_tab2;
    TextView textView126;
    TextView textView129;
    String seq_user;

//    FragmentCalendar fragment1;
    FragmentAbsence fragment2;

    FragmentCalendar fragmentCalendar;
    FragmentCarter fragmentCarter;
    TextDialog mCustomDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.layout_c_1_1_calendar);

        bt_tab1 = (ConstraintLayout) findViewById(R.id.bt_tab110);
        bt_tab2 = (ConstraintLayout) findViewById(R.id.bt_tab220);
        textView126 = (TextView) findViewById(R.id.textView126);

        textView129 = (TextView) findViewById(R.id.textView129);



        bt_tab2.setBackgroundColor(Color.parseColor("#FF9900"));
        bt_tab1.setBackgroundColor(Color.parseColor("#FF7300"));


        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);


        //액션바 및 상태바 커스텀
        actionbarCustom();
        callFragment(getIntent().getIntExtra("fragment_num", 1));
        seq_user = pref.getString("seq_user", "sss");
//        Log.d("seq : " , seq_user);


    }

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        switch (frament_no) {
            case 1:
                // '일정표 fragment' 호출
                fragmentCalendar = new FragmentCalendar();
                fragmentCalendar.app = app;
                fragmentCalendar.setPref(pref);
                transaction.replace(R.id.fragment_container1, fragmentCalendar);
                transaction.commit();
                break;

            case 2:
                fragmentCarter = new FragmentCarter();
                fragmentCarter.app = app;
                fragmentCarter.setPref(pref);
                transaction.replace(R.id.fragment_container1, fragmentCarter);
                transaction.commit();
                break;
        }


    }


    public void actionbarCustom() {

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        final TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("일정관리");
        title.setVisibility(View.VISIBLE);

        ImageView editorImage = (ImageView) findViewById(R.id.editorImage);
        editorImage.setVisibility(View.VISIBLE);
        editorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCustomDialog = new TextDialog(C_1_1_Calendar.this, R.layout.dialog_one_check);
                mCustomDialog.setTexts(new String[]{"작성하실 메뉴를 선택하세요.", "일정표", "식단표"});
                mCustomDialog.show();
            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(C_1_1_Calendar.this, MainDawerSelectActivity.class);
                C_1_1_Calendar.this.startActivity(loginIntent);
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FF9900"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tab110:
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#FF7300"));
                textView126.setTypeface(NanumSquareExtraBold);
                bt_tab2.setBackgroundColor(Color.parseColor("#FF9900"));
                textView129.setTypeface(NanumSquareRegular);
                callFragment(FRAGMENT1);
                break;
            case R.id.bt_tab220:
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#FF9900"));
                textView126.setTypeface(NanumSquareRegular);
                bt_tab2.setBackgroundColor(Color.parseColor("#FF7300"));
                textView129.setTypeface(NanumSquareExtraBold);
                callFragment(FRAGMENT2);
                break;
        }
    }



    public void clickTextView2(View v) {
        mCustomDialog.dismiss();
        Intent intent = new Intent(C_1_1_Calendar.this, C_3_1_CalendarEditor.class);
        C_1_1_Calendar.this.startActivity(intent);
    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        Intent intent = new Intent(C_1_1_Calendar.this, C_2_2_CarteEditor.class);
        C_1_1_Calendar.this.startActivity(intent);
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }



}
