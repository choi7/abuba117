package com.joyblock.abuba.bus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.notice.BanListViewAdapter;

public class A2_2_2_route_change_detail extends BaseActivity {
    String str_boarding;
    boolean flag;

    String[] str_bus={"곰돌이 1호차","곰돌이 2호차","곰돌이 3호차","호랑이 1호차","호랑이 2호차","호랑이 3호차"};
    String[] str_point={"편의점 앞","진선여고 앞","역삼아이파크 1단지 정문","역삼아이파크 1단지 후문","갤러리아포레 정문","평생체험학습관"};
    int int_selected_class;


    CustomListViewDialog dialog;

    ConstraintLayout class_ConstraintLayout;

    TextView classText;

    TextListViewAdapter adapter1;
    BanListViewAdapter class_adapter;
    ListView listview1, class_listview;

    //    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity=this;
        flag = true;// 승/하차 플래그
        setContentView(R.layout.layout_a2_2_2_route_change_detail);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cc99));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("김철수");
        title.setVisibility(View.VISIBLE);


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

        listview1 = (ListView) findViewById(R.id.listview_another_boarding);

        adapter1 = new TextListViewAdapter(1, R.layout.row_route_detail);
        listview1.setAdapter(adapter1);

        adapter1.addItem("등원");
        adapter1.addItem("월,수,금");
        adapter1.addItem("곰돌이 2호차");
        adapter1.addItem("효성아파트 후문");


//
//
//        class_ConstraintLayout = (ConstraintLayout) findViewById(R.id.a3_1ClassConstraintLayout);
//
//        //반 검색 부부 클릭 다이얼로그 생성 이벤트
//        class_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //어댑터 생성 후 customListViewDialog에 반 리스트를 보여준다.
//                class_adapter = new BanListViewAdapter();
//                dialog = new CustomListViewDialog(A3_1_student_guidance_bus_list.this, class_adapter);
//                class_adapter.addItem("전체");
//                for (R6_SelectKindergardenClassList list : app.kindergarden_class_list) {
//                    class_adapter.addItem(list.kindergarden_class_name);
//                }
//                class_adapter.notifyDataSetChanged();
//                dialog.show();
//
//                //반 다이얼로그 이벤트 처리
//                class_listview = dialog.getListView();
//                class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        BanListViewItem item = class_adapter.list.get(position);
//                        int_selected_class = position;
//                        //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
//                        classText.setText(item.getName());
//                        Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                });
//            }
//        });
    }


    public void changeBusToSchool(View v){
        startSelectBus(true);
    }
    public void changePointToSchool(View v){
        startSelectPoint(true);
    }
    public void changeBusToHome(View v){
        startSelectBus(false);
    }
    public void changePointToHome(View v){
        startSelectPoint(false);
    }

    void startSelectBus(boolean go_to_school){
        Intent intent = new Intent(A2_2_2_route_change_detail.this, A2_2_5_route_change_select_bus.class);
        intent.putExtra("go_to_school",go_to_school);
        A2_2_2_route_change_detail.this.startActivity(intent);
    }

    void startSelectPoint(boolean go_to_school){
        Intent intent = new Intent(A2_2_2_route_change_detail.this, A2_2_5_route_change_select_bus.class);
        intent.putExtra("go_to_school",go_to_school);
        A2_2_2_route_change_detail.this.startActivity(intent);
    }
}
