package com.joyblock.abuba;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoticeAndQuestActivity extends AppCompatActivity implements View.OnClickListener {
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private Button bt_tab1, bt_tab2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        bt_tab1 = (Button)findViewById(R.id.button6);
        bt_tab2 = (Button)findViewById(R.id.button9);

        // 탭 버튼에 대한 리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        callFragment(FRAGMENT1);


    }

    private void callFragment(int fragment1) {



        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        /*
        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
                FragmentNotice1 fragment1 = new FragmentNotice1();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                FragmentSurvey1 fragment2 = new FragmentSurvey1();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
                break;
        }
        */

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button6 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(FRAGMENT1);
                break;

            case R.id.button9 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;
        }

    }
}
