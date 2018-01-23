package com.joyblock.abuba.notice;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.MainDawerSelectActivity;
import com.joyblock.abuba.R;

/**
 * Created by hyoshinchoi on 2018. 1. 10..
 */

public class NoticeActivity extends BaseActivity implements View.OnClickListener {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private Button bt_tab1, bt_tab2;
    String seq_user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        bt_tab1 = (Button)findViewById(R.id.bt_tab11);
        bt_tab2 = (Button)findViewById(R.id.bt_tab22);



        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);



        //액션바 및 상태바 커스텀
        actionbarCustom();




    }

    @Override
    public void onResume(){
        super.onResume();
        callFragment(getIntent().getIntExtra("fragment_num",1));
        seq_user = pref.getString("seq_user", "sss");
        Log.d("seq : " , seq_user);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_tab11 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#0099FF"));
                bt_tab2.setBackgroundColor(Color.parseColor("#0086FF"));
                callFragment(FRAGMENT1);
                break;

            case R.id.bt_tab22 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                bt_tab1.setBackgroundColor(Color.parseColor("#0086FF"));
                bt_tab2.setBackgroundColor(Color.parseColor("#0099FF"));
                callFragment(FRAGMENT2);
                break;
        }

    }



    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
            case 1:
                // '공지사항 리스트 fragment' 호출
                FragmentNotice fragment1 = new FragmentNotice();
                fragment1.setPref(pref);
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '설문지 fragment' 호출
                FragmentSurvey fragment2 = new FragmentSurvey();
                fragment2.setPref(pref);
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;
        }
    }

    public void actionbarCustom() {

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        final TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("공지사항");
        title.setVisibility(View.VISIBLE);

        ImageView imageView = (ImageView) findViewById(R.id.editorImage);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder nd = new AlertDialog.Builder(NoticeActivity.this);
                nd.setMessage("작성하실 게시판을 선택하세요")
                        .setPositiveButton("공지", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(NoticeActivity.this, NoticeEditorActivity.class);
                                NoticeActivity.this.startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("설문지", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(NoticeActivity.this, QuestionnaireActivity.class);
                                NoticeActivity.this.startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();

            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(NoticeActivity.this, MainDawerSelectActivity.class);
                NoticeActivity.this.startActivity(loginIntent);
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
        } if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }

}
