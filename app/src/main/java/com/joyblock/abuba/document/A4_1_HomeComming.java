package com.joyblock.abuba.document;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;


public class A4_1_HomeComming extends BaseActivity {

    String[] list = {"교육계획안", "투약의뢰서", "귀가동의서", "출석부"};
    ListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.park_activity_document_select);
        listView = findViewById(R.id.documentSelectListView);
        adapter = new ListViewAdapter(NanumSquareBold);
        listView.setAdapter(adapter);
        for(String str:list)
            adapter.addItem(getResources().getDrawable(R.mipmap.ic_document), str,getResources().getDrawable(R.mipmap.ic_notice));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(MainDawerSelectActivity.this, mData.mTitle,Toast.LENGTH_LONG).show();

                switch (position) {
                    case 0 :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        Intent s1 = new Intent(MainDawerSelectActivity.this, NoticeActivity.class);
//                        MainDawerSelectActivity.this.startActivity(s1);
                        break;
                    case 1 :
                        Intent s2 = new Intent(getApplicationContext(), A4_1_HomeComming.class);
                        getApplicationContext().startActivity(s2);
                        break;
                    case 2 :
//                        Intent s3 = new Intent(MainDawerSelectActivity.this, ScheduleAndDailyMenuActivity.class);
//                        MainDawerSelectActivity.this.startActivity(s3);
                        break;
                    case 3 :
//                        Intent s5 = new Intent(MainDawerSelectActivity.this, 1DocumentSelectActivity.class);
//                        MainDawerSelectActivity.this.startActivity(s5);
                        break;
                        /*
                    case "알림" :
                        Intent s4 = new Intent(MainDawerSelectActivity.this, NoticeActivity.class);
                        MainDawerSelectActivity.this.startActivity(s4);
                        break;

                    case "사진 앨범" :
                        Intent s6 = new Intent(MainDawerSelectActivity.this, PhotoActivity.class);
                        MainDawerSelectActivity.this.startActivity(s6);
                        break;
                        */

                }

            }
        });


    }



}
