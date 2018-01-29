package com.joyblock.abuba.bus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.util.StringArray;

public class A3_2_student_guidance_select_point extends BaseActivity {
    String authority;
    public static A3_2_student_guidance_select_point activity;
    TextListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.layout_a3_2_student_guidance_select_point);
        Log.d("BAN LIST",app.kindergarden_class_list.get(0).kindergarden_class_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cc99));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        TextView title=((TextView)findViewById(R.id.titleName));
        title.setText("차량선택");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority","");
//
//
        listView = findViewById(R.id.listview);
        adapter = new TextListViewAdapter(4,R.layout.row_point);
        listView.setAdapter(adapter);
//        for(String str:list)
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem("1지점","편의점 앞","경남 창원시 마산회원구 구암동 123-12번지","2명");
        adapter.addItem("2지점","진선여고 앞","경남 창원시 마산회원구 구암동 123-12번지","2명");
        adapter.addItem("3지점","역삼아이파크 1단지 정문","경남 창원시 마산회원구 구암동 123-12번지","1명");
        adapter.addItem("4지점","역삼아이파크 1단지 후문","경남 창원시 마산회원구 구암동 123-12번지","3명");
        adapter.addItem("5지점","갤러리아포레 정문","경남 창원시 마산회원구 구암동 123-12번지","2명");

        mCustomDialog = new TextDialog(A3_2_student_guidance_select_point.this,R.layout.dialog_ok_cancle);

        mCustomDialog.setTexts(new String[]{"곰돌이 1호차\n차량지도를 시작하시겠습니까?","취소","확인"});
        mCustomDialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(A3_2_student_guidance_select_point.this,A3_3_student_guidance_point_detail.class);
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_LONG).show();
//
//                switch (adapter.getItem(position).getTitle()) {
//                    case "실시간버스위치" :
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A0_bus.this.startActivity(new Intent(A0_bus.this, A1_1_bus_location.class));
//                        break;
//                    case "노선관리":
//                        toggleItems();
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
////                        A0_bus.this.startActivity(new Intent(A0_bus.this, A1_1_bus_location.class));
//                        break;
//                    case "승차관리" :
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A0_bus.this.startActivity(new Intent(A0_bus.this, A2_1_boading_management.class));
//                        break;
//                    case "노선변경" :
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A0_bus.this.startActivity(new Intent(A0_bus.this, A2_2_1_route_change_list.class));
//                        break;
//                    case "노선등록" :
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A0_bus.this.startActivity(new Intent(A0_bus.this, A2_3_1_route_registration_point_list.class));
//                        break;
//                    case "등원지도" :
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A0_bus.this.startActivity(new Intent(A0_bus.this, A3_1_student_guidance_bus_list.class));
//                        break;
//
//                }
//
//            }
//        });


    }

    TextDialog mCustomDialog;
    public void clickRed(View v){
        mCustomDialog = new TextDialog(A3_2_student_guidance_select_point.this,R.layout.dialog_ok_cancle);

        mCustomDialog.setTexts(new String[]{"곰돌이 1호차\n차량지도를 시작하시겠습니까?","취소","확인"});
        mCustomDialog.show();


//        Intent intent = new Intent(A3_1_student_guidance_bus_list.this, A3_2_student_guidance_select_point.class);
//        A3_1_student_guidance_bus_list.this.startActivity(intent);
    }

    public void clickTextView2(View v){
        mCustomDialog.dismiss();
        finish();

    }
    public void clickTextView3(View v){
        mCustomDialog.dismiss();
        Toast.makeText(A3_2_student_guidance_select_point.this,"해당 차량의 학부모 모두에게 알림 전송",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(A3_2_student_guidance_select_point.this, A3_3_student_guidance_point_detail.class);
//        A3_2_student_guidance_select_point.this.startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A1_DocumentSelect.this, MainDawerSelectActivity.class);
//        A1_DocumentSelect.this.startActivity(intent);
//        finish();
    }
}
