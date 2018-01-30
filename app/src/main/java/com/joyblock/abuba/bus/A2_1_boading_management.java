package com.joyblock.abuba.bus;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;
//import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;

public class A2_1_boading_management extends BaseActivity{


    String str_boarding;
    boolean flag;


    int int_selected_class;


    CustomListViewDialog dialog;

    ConstraintLayout class_ConstraintLayout;

    TextView classText;
    TextView btn_left,btn_right;
    BoardingListViewAdapter adapter;
    BanListViewAdapter class_adapter;
    ListView listview,class_listview;
//    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity=this;
        flag=true;// 승/하차 플래그
        setContentView(R.layout.layout_a2_1_boading_management);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cc99));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

//        editor=pref.edit();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String seq_user = app.seq_user;
        String seq_kindergarden = app.my_kindergarden_list.get(0).seq_kindergarden;
        String notice_title="긴급공지";
        String content="내용없음";
        String files="no files";

        classText = (TextView) findViewById(R.id.a3_1_Class_TextView);



        actionbarCustom();
        addBacklistner();

        str_boarding="m";
        callFragment(str_boarding);

        btn_left=(TextView)findViewById(R.id.btn_left);
        btn_right=(TextView)findViewById(R.id.btn_right);


        class_ConstraintLayout = (ConstraintLayout) findViewById(R.id.a3_1ClassConstraintLayout);

        //반 검색 부부 클릭 다이얼로그 생성 이벤트
        class_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //어댑터 생성 후 customListViewDialog에 반 리스트를 보여준다.
                class_adapter = new BanListViewAdapter();
                dialog = new CustomListViewDialog(A2_1_boading_management.this,class_adapter);
                class_adapter.addItem("전체");
                for (R6_SelectKindergardenClassList list : app.kindergarden_class_list) {
                    class_adapter.addItem(list.kindergarden_class_name);
                }
                class_adapter.notifyDataSetChanged();
                dialog.show();
                Toast.makeText(A2_1_boading_management.this,"원장님만 반을 선택할 수 있습니다.",Toast.LENGTH_LONG).show();

                //반 다이얼로그 이벤트 처리
                class_listview=dialog.getListView();
                class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BanListViewItem item = class_adapter.list.get(position);
                        int_selected_class=position;
                        //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
                        classText.setText(item.getName());
                        Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });





    }

    private void callFragment(String str) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        A2_1_boarding_management_fragment fragment = new A2_1_boarding_management_fragment();
        fragment.setFlag(str_boarding);
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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cc99));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("승차관리");
        title.setVisibility(View.VISIBLE);

        TextView textview_class_name= (TextView) findViewById(R.id.a3_1_Class_TextView);
        textview_class_name.setText("전체");
        textview_class_name.setVisibility(View.VISIBLE);

//
        ImageView imageView = (ImageView) findViewById(R.id.editorImage);
//        imageView.setVisibility(View.VISIBLE);
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
        str_boarding="m";
        btn_left.setBackgroundColor(Color.parseColor("#2baf7f"));
        btn_right.setBackgroundColor(Color.parseColor("#33cc99"));
        btn_left.setTypeface(NanumSquareExtraBold);
        btn_right.setTypeface(NanumSquareRegular);
        callFragment(str_boarding);
    }

    public void clickRight(View v){
        str_boarding="w";
        btn_right.setBackgroundColor(Color.parseColor("#2baf7f"));
        btn_left.setBackgroundColor(Color.parseColor("#33cc99"));
        btn_right.setTypeface(NanumSquareExtraBold);
        btn_left.setTypeface(NanumSquareRegular);
        callFragment(str_boarding);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A2_1_EducationPlan.this, A1_DocumentSelect.class);
//        A2_1_EducationPlan.this.startActivity(intent);
//        finish();
    }
}
