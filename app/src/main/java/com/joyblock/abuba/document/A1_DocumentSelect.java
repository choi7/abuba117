package com.joyblock.abuba.document;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;


public class A1_DocumentSelect extends BaseActivity {

    String[] list = {"교육계획안", "투약의뢰서", "귀가동의서", "출석부"};
    ListViewAdapter adapter;
    ListView listView;
    String authority;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.park_activity_document_select);
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
        TextView title=((TextView)findViewById(R.id.titleName));
        title.setText("문서함");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority","");
        Log.d("ddddd", authority);


        listView = findViewById(R.id.documentSelectListView);
        adapter = new ListViewAdapter(NanumSquareBold);
        listView.setAdapter(adapter);
        for(String str:list)
            adapter.addItem(getResources().getDrawable(R.drawable.document_image), str,getResources().getDrawable(R.drawable.document_next_image));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_LONG).show();
                switch (position) {
                        //교육계획안
                    case 0 :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A2_1_EducationPlan.class));
                        break;
                        //투약의뢰서
                    case 1 :
                        if(authority.equals("ROLE_PARENTS")) {
                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A3_1_Medicine_Parent.class));
                        } else {
                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A3_1_Medicine.class));
                        }
                        break;
                        //귀가동의서
                    case 2 :
                        if(authority.equals("ROLE_PARENTS")) {
                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A4_1_HomeComming_Parent.class));
                        } else {
                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A4_1_HomeComming.class));
                        }
                        break;
                        //출석부
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
