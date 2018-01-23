package com.joyblock.abuba.test_park;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

public class A0_bus extends BaseActivity {
    String authority;

    A0_ListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a0_bus);

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
        title.setText("버스위치확인");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority","");
        Log.d("ddddd", authority);
//
//
        listView = findViewById(R.id.documentSelectListView);
        adapter = new A0_ListViewAdapter(NanumSquareBold);
        listView.setAdapter(adapter);
//        for(String str:list)
        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "실시간버스위치",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선관리",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "등원지도",getResources().getDrawable(R.drawable.document_next_image));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_LONG).show();
//                switch (position) {
//                    //교육계획안
//                    case 0 :
////                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A2_1_EducationPlan.class));
//                        break;
//                    //투약의뢰서
//                    case 1 :
//                        if(authority.equals("ROLE_PARENTS")) {
//                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A3_1_Medicine_Parent.class));
//                        } else {
//                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A3_1_Medicine.class));
//                        }
//                        break;
//                    //귀가동의서
//                    case 2 :
//                        if(authority.equals("ROLE_PARENTS")) {
//                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A4_1_HomeComming_Parent.class));
//                        } else {
//                            A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A4_1_HomeComming.class));
//                        }
//                        break;
//                    //출석부
//                    case 3 :
//                        A1_DocumentSelect.this.startActivity(new Intent(A1_DocumentSelect.this, A5_1_Attendance.class));
//                        break;
//                        /*
//                    case "알림" :
//                        Intent s4 = new Intent(MainDawerSelectActivity.this, NoticeActivity.class);
//                        MainDawerSelectActivity.this.startActivity(s4);
//                        break;
//
//                    case "사진 앨범" :
//                        Intent s6 = new Intent(MainDawerSelectActivity.this, PhotoActivity.class);
//                        MainDawerSelectActivity.this.startActivity(s6);
//                        break;
//                        */
//
//                }
//
//            }
//        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A1_DocumentSelect.this, MainDawerSelectActivity.class);
//        A1_DocumentSelect.this.startActivity(intent);
//        finish();
    }
}
