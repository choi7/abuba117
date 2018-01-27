package com.joyblock.abuba.bus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
public class A2_2_6_route_change_select_point extends BaseActivity {
    String authority;

    TextListViewAdapter adapter;
    ListView listView;

    int int_bus;
    int int_selected=-1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a2_2_6_route_change_select_point);
        Log.d("BAN LIST", app.kindergarden_class_list.get(0).kindergarden_class_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText("차량선택");
        title.setVisibility(View.VISIBLE);
        authority = pref.getString("authority", "");
//
//
        listView = findViewById(R.id.listview);
    }

    public void onResume(){
        super.onResume();

        Intent intent=getIntent();
        int_bus=intent.getIntExtra("int_bus",0);

        adapter = new TextListViewAdapter(3,R.layout.row_point);
        listView.setAdapter(adapter);
//        for(String str:list)
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem("1지점","편의점 앞","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("2지점","진선여고 앞","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("3지점","역삼아이파크 1단지 정문","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("4지점","역삼아이파크 1단지 후문","경남 창원시 마산회원구 구암동 123-12번지");
        adapter.addItem("5지점","갤러리아포레 정문","경남 창원시 마산회원구 구암동 123-12번지");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                int_selected=position;
            }
        });


    }

    public void clickOK(View v){
        if(int_selected<0)
            Toast.makeText(A2_2_6_route_change_select_point.this,"지점을 선택해주세요.",Toast.LENGTH_SHORT).show();
        else {
//            Intent intent = new Intent(A2_2_6_route_change_select_point.this, A2_2_2_route_change_detail.class);
//            A2_2_6_route_change_select_point.this.startActivity(intent);
            A2_2_5_route_change_select_bus a=A2_2_5_route_change_select_bus.A2_2_5_route;
            a.finish();
            finish();
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
