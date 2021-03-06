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

public class A2_3_1_route_registration_point_list extends BaseActivity {

    String str_boarding;
    boolean flag;


    int int_selected_class;


    CustomListViewDialog dialog;

    ConstraintLayout class_ConstraintLayout;

    TextView classText;

    TextView btn_left,btn_right;
    TextListViewAdapter adapter;
    BanListViewAdapter class_adapter;
    ListView listview,class_listview;
    //    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity=this;
        flag=true;// 승/하차 플래그
        setContentView(R.layout.layout_a2_3_1_route_registration_point_list);
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

//        editor=pref.edit();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String seq_user = app.seq_user;
        String seq_kindergarden = app.my_kindergarden_list.get(0).seq_kindergarden;
        String notice_title="긴급공지";
        String content="내용없음";
        String files="no files";

        classText = (TextView) findViewById(R.id.a3_1_Class_TextView);

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("노선등록");
        title.setVisibility(View.VISIBLE);

        TextView textview_class_name= (TextView) findViewById(R.id.a3_1_Class_TextView);
        textview_class_name.setText("곰돌이 1호차");
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

        btn_left=(TextView)findViewById(R.id.btn_left);
        btn_right=(TextView)findViewById(R.id.btn_right);


        str_boarding="m";


        class_ConstraintLayout = (ConstraintLayout) findViewById(R.id.a3_1ClassConstraintLayout);

        //반 검색 부부 클릭 다이얼로그 생성 이벤트
        class_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //어댑터 생성 후 customListViewDialog에 반 리스트를 보여준다.
                class_adapter = new BanListViewAdapter();
                dialog = new CustomListViewDialog(A2_3_1_route_registration_point_list.this,class_adapter);
//                class_adapter.addItem("전체");
                class_adapter.addItem("곰돌이 1호차");
                class_adapter.addItem("곰돌이 2호차");
                class_adapter.addItem("곰돌이 3호차");
                class_adapter.addItem("호랑이 1호차");
                class_adapter.addItem("호랑이 2호차");
                class_adapter.addItem("호랑이 3호차");

//                for (R6_SelectKindergardenClassList list : app.kindergarden_class_list) {
//                    class_adapter.addItem(list.kindergarden_class_name);
//                }
                class_adapter.notifyDataSetChanged();
                dialog.show();

                //반 다이얼로그 이벤트 처리
                class_listview=dialog.getListView();
                class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BanListViewItem item = class_adapter.list.get(position);
                        int_selected_class=position;
                        //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
                        classText.setText(item.getName());
                        Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });


        listview = findViewById(R.id.listview_point);
        adapter = new TextListViewAdapter(3,R.layout.row_point );
        listview.setAdapter(adapter);
//        for(String str:list)
        adapter.addItem("1지점","편의점 앞","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("2지점","진선여고 앞","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("3지점","역삼아이파크 1단지 정문","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("4지점","역삼아이파크 1단지 후문","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("5지점","갤러리아포레 정문","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("6지점","평생체험학습관","경남 창원시 마산회원구 구암동 123-12번지");


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextListViewAdapter.Item item = adapter.list.get(position);
                int_selected_class=position;
                Intent intent = new Intent( A2_3_1_route_registration_point_list.this, A2_3_2_route_registration_point_student_list.class);
//                intent.putExtra("name",item.getStrName());
                A2_3_1_route_registration_point_list.this.startActivity(intent);
                //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
//                classText.setText(item.getName());
//                Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
//                dialog.dismiss();
            }
        });





    }
    public void clickLeft(View v){
        btn_left.setBackgroundColor(Color.parseColor("#2baf7f"));
        btn_right.setBackgroundColor(Color.parseColor("#33cc99"));
        btn_left.setTypeface(NanumSquareExtraBold);
        btn_right.setTypeface(NanumSquareRegular);
    }

    public void clickRight(View v){
        btn_right.setBackgroundColor(Color.parseColor("#2baf7f"));
        btn_left.setBackgroundColor(Color.parseColor("#33cc99"));
        btn_right.setTypeface(NanumSquareExtraBold);
        btn_left.setTypeface(NanumSquareRegular);
    }
}
