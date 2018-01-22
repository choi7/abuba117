package com.joyblock.abuba.document;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.notice.FragmentNotice;
import com.joyblock.abuba.notice.FragmentSurvey;


public class A5_1_Attendance extends BaseActivity implements View.OnClickListener {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private Button bt_tab1, bt_tab2;
    String seq_user;

    @Override
    protected void onResume() {
        super.onResume();
        callFragment(getIntent().getIntExtra("fragment_num",1));
        seq_user = pref.getString("seq_user", "sss");
        Log.d("seq : " , seq_user);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a5_1_attendance);

        bt_tab1 = (Button)findViewById(R.id.bt_tab110);
        bt_tab2 = (Button)findViewById(R.id.bt_tab220);

        bt_tab1.setBackgroundColor(Color.parseColor("#9966FF"));
        bt_tab2.setBackgroundColor(Color.parseColor("#9966FF"));
        bt_tab1.setText("출석");
        bt_tab2.setText("결석");
        bt_tab1.setBackgroundColor(Color.parseColor("#7C48F9"));


        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);



        //액션바 및 상태바 커스텀
        actionbarCustom();



    }


    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /*
        switch (frament_no) {
            case 1:
                // '공지사항 리스트 fragment' 호출
                FragmentNotice fragment1 = new FragmentNotice();
//                fragment1.setPref(pref);
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '설문지 fragment' 호출
                FragmentSurvey fragment2 = new FragmentSurvey();
//                fragment2.setPref(pref);
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;
        }
        */


    }

    public void actionbarCustom() {

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
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
        } if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tab110 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#7C48F9"));
                bt_tab2.setBackgroundColor(Color.parseColor("#9966FF"));
                callFragment(FRAGMENT1);
                break;

            case R.id.bt_tab220 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#9966FF"));
                bt_tab2.setBackgroundColor(Color.parseColor("#7C48F9"));
                callFragment(FRAGMENT2);
                break;
        }
    }
}
