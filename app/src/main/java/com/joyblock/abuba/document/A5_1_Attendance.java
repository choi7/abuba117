package com.joyblock.abuba.document;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;


public class A5_1_Attendance extends BaseActivity implements View.OnClickListener {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private ConstraintLayout bt_tab1, bt_tab2;
    TextView textView126, textView127;
    TextView textView129, textView130;
    String seq_user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a5_1_attendance);

        bt_tab1 = (ConstraintLayout) findViewById(R.id.bt_tab110);
        bt_tab2 = (ConstraintLayout) findViewById(R.id.bt_tab220);
        textView126 = (TextView) findViewById(R.id.textView126);
        textView127 = (TextView) findViewById(R.id.textView127);
        textView129 = (TextView) findViewById(R.id.textView129);
        textView130 = (TextView) findViewById(R.id.textView130);


        bt_tab2.setBackgroundColor(Color.parseColor("#9966FF"));
        bt_tab1.setBackgroundColor(Color.parseColor("#7C48F9"));


        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);


        //액션바 및 상태바 커스텀
        actionbarCustom();
        callFragment(getIntent().getIntExtra("fragment_num", 1));
        seq_user = pref.getString("seq_user", "sss");
//        Log.d("seq : " , seq_user);

    }

    FragmentAttendance fragment1;
    FragmentAbsence fragment2;

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        switch (frament_no) {
            case 1:
                // '출석 리스트 fragment' 호출
                fragment1 = new FragmentAttendance();
                fragment1.app = app;
                fragment1.setPref(pref);
                transaction.replace(R.id.fragment_container0, fragment1);
                transaction.commit();
                break;

            case 2:
                // '결석 fragment' 호출
                fragment2 = new FragmentAbsence();
                fragment2.app = app;
                fragment2.setPref(pref);
                transaction.replace(R.id.fragment_container0, fragment2);
                transaction.commit();
                break;
        }


    }

    public void datepush1(View v) {
        fragment1.datepush1(v);
        fragment2.datepush1(v);
    }

    public void actionbarCustom() {

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();
        final TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("출석부");
        title.setVisibility(View.VISIBLE);

        TextView editorText = (TextView) findViewById(R.id.editorText);
        editorText.setVisibility(View.VISIBLE);
        editorText.setText("선택");
        editorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(A5_1_Attendance.this, A1_DocumentSelect.class);
                A5_1_Attendance.this.startActivity(loginIntent);
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
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
                bt_tab1.setBackgroundColor(Color.parseColor("#7C48F9"));
                textView126.setTypeface(NanumSquareExtraBold);
                textView127.setTypeface(NanumSquareExtraBold);
                bt_tab2.setBackgroundColor(Color.parseColor("#9966FF"));
                textView129.setTypeface(NanumSquareRegular);
                textView130.setTypeface(NanumSquareRegular);
                callFragment(FRAGMENT1);
                break;
            case R.id.bt_tab220:
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#9966FF"));
                textView126.setTypeface(NanumSquareRegular);
                textView127.setTypeface(NanumSquareRegular);
                bt_tab2.setBackgroundColor(Color.parseColor("#7C48F9"));
                textView129.setTypeface(NanumSquareExtraBold);
                textView130.setTypeface(NanumSquareExtraBold);
                callFragment(FRAGMENT2);
                break;
        }
    }
}
