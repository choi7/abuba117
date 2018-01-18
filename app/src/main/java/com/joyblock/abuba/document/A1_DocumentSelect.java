package com.joyblock.abuba.document;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.MainDawerSelectActivity;
import com.joyblock.abuba.R;


public class A1_DocumentSelect extends BaseActivity {

    String[] list = {"교육계획안", "투약의뢰서", "귀가동의서", "출석부"};
    ListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.park_activity_document_select);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);

        TextView title=((TextView)findViewById(R.id.titleName));
        title.setText("문서함");
        title.setVisibility(View.VISIBLE);


        listView = findViewById(R.id.documentSelectListView);
        adapter = new ListViewAdapter(NanumSquareBold);
        listView.setAdapter(adapter);
        for(String str:list)
            adapter.addItem(getResources().getDrawable(R.mipmap.ic_document), str,getResources().getDrawable(R.mipmap.ic_notice));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0 :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A2_1_EducationPlan.class));
                        break;
                    case 1 :
                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A3_1_Medicine.class));
                        break;
                    case 2 :
                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A4_1_HomeComming.class));
                        break;
                    case 3 :
                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A5_1_Attendance.class));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A1_DocumentSelect.this, MainDawerSelectActivity.class);
//        A1_DocumentSelect.this.startActivity(intent);
//        finish();
    }



}
