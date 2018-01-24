package com.joyblock.abuba.test_park;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

public class A2_1_boading_management extends BaseActivity{


    String boarding_str;
    SharedPreferences.Editor editor;
    boolean flag;

    BoardingListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag=true;// 승/하차 플래그
        setContentView(R.layout.layout_a2_1_boading_management);


        editor=pref.edit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String seq_user = pref.getString("seq_user","없음");
        String seq_kindergarden = pref.getString("seq_kindergarden","없음");
        String notice_title="긴급공지";
        String content="내용없음";
        String files="no files";




        actionbarCustom();

        boarding_str="m";
        callFragment(boarding_str);





    }

    private void callFragment(String str) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        A2_1_boarding_management_fragment fragment = new A2_1_boarding_management_fragment();
        fragment.setFlag(boarding_str);
        fragment.setPref(pref);
        transaction.replace(R.id.education_plan_fragment_container, fragment);
        transaction.commit();

//        if(month) {
//            // '공지사항 리스트 fragment' 호출
//            FragmentEducationPlan1 fragment1 = new 1FragmentEducationPlan();
//            fragment1.setPref(pref);
//            transaction.replace(R.id.education_plan_fragment_container, fragment1);
//            transaction.commit();
//        }else{
//            // '설문지 fragment' 호출
//            FragmentEducationPlanWeek fragment2 = new FragmentEducationPlanWeek();
//            fragment2.setPref(pref);
//            transaction.replace(R.id.education_plan_fragment_container, fragment2);
//            transaction.commit();
//        }
    }

    public void actionbarCustom() {

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("승차관리");
        title.setVisibility(View.VISIBLE);

        String str_class="호랑이";
        TextView textview_class_name= (TextView) findViewById(R.id.a3_1_Class_TextView);
        textview_class_name.setText(str_class+"반");
        textview_class_name.setVisibility(View.VISIBLE);

//
        ImageView imageView = (ImageView) findViewById(R.id.editorImage);
        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(A2_1_EducationPlan.this, A2_2_EducationPlanEditor.class);
//                intent.putExtra("plan_flag",plan_flag);
//                A2_1_EducationPlan.this.startActivity(intent);
//                finish();
//            }
//        });
//
//        ImageView backImage = (ImageView) findViewById(R.id.backImage);
//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent loginIntent = new Intent(A2_1_EducationPlan.this, A1_DocumentSelect.class);
//                A2_1_EducationPlan.this.startActivity(loginIntent);
//                finish();
//            }
//        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        } if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }


    public void clickLeft(View v){
        boarding_str="m";
        callFragment(boarding_str);
    }

    public void clickRight(View v){
        boarding_str="w";
        callFragment(boarding_str);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A2_1_EducationPlan.this, A1_DocumentSelect.class);
//        A2_1_EducationPlan.this.startActivity(intent);
//        finish();
    }
}
