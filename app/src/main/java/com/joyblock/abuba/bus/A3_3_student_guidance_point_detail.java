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
public class A3_3_student_guidance_point_detail extends BaseActivity {
    String authority;

    BoardingListViewAdapter adapter;
    ListView listView;
    int i=1;
    TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_3_student_guidance_point_detail);
        Log.d("BAN LIST",app.kindergarden_class_list.get(0).kindergarden_class_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cc99));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        title=((TextView)findViewById(R.id.titleName));
        title.setText(i+"지점 편의점 앞");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority","");
//
//
        adapter = new BoardingListViewAdapter(R.layout.row_boarding_list_tel,false);
        adapter.setActivity(this);
        listView = (ListView)findViewById(R.id.listview);

        listView.setAdapter(adapter);
        adapter.addItem("박준수","호랑이반",false );
        adapter.addItem("최효신","호랑이반",true  );
        adapter.addItem("이영애","호랑이반",true  );
        adapter.addItem("박효신","호랑이반",false  );
        adapter.addItem("이병헌","호랑이반",true  );
        adapter.addItem("이영호","호랑이반",true  );
////        for(String str:list)
////        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
////        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
////        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem("1지점","편의점 앞","경남 창원시 마산회원구 구암동 123-12번지","2명");
//        adapter.addItem("2지점","진선여고 앞","경남 창원시 마산회원구 구암동 123-12번지","2명");
//        adapter.addItem("3지점","역삼아이파크 1단지 정문","경남 창원시 마산회원구 구암동 123-12번지","1명");
//        adapter.addItem("4지점","역삼아이파크 1단지 후문","경남 창원시 마산회원구 구암동 123-12번지","3명");
//        adapter.addItem("5지점","갤러리아포레 정문","경남 창원시 마산회원구 구암동 123-12번지","2명");
//
////        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Intent intent=new Intent(A3_3_student_guidance_point_detail.this,A3_3_student_guidance_point_detail.class);
////                startActivity(intent);
////            }
////        });
//
//
//
    }
//
//
    public void clickRed(View view){
//        Intent intent=new Intent(A3_3_student_guidance_point_detail.this,A3_3_student_guidance_point_detail.class);
//        startActivity(intent);\
        if(i==5){
            Toast.makeText(view.getContext(),"등원을 완료 하였습니다.",Toast.LENGTH_SHORT).show();
            finish();
            A3_1_student_guidance_bus_list a1=A3_1_student_guidance_bus_list.activity;
            a1.finish();
            A3_2_student_guidance_select_point a2=A3_2_student_guidance_select_point.activity;
            a2.finish();
            return;
        }
        i++;
        title.setText(i+"지점 편의점 앞");
        Toast.makeText(view.getContext(),i+" 지점의 탑승자 목록이 나옵니다.",Toast.LENGTH_SHORT).show();
        if(i==5) {
            TextView tv = (TextView) findViewById(R.id.textview_red);
            tv.setText("등원완료");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A1_DocumentSelect.this, MainDawerSelectActivity.class);
//        A1_DocumentSelect.this.startActivity(intent);
//        finish();
    }
}
