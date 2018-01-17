package com.joyblock.abuba.document;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;


public class DocumentSelectActivity extends BaseActivity {

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

                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_LONG).show();

                switch (position) {
                    case 0 :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        getApplicationContext().startActivity(new Intent(getApplicationContext(), EducationPlanAcitivty.class));
                        break;
                    case 1 :
                        getApplicationContext().startActivity(new Intent(getApplicationContext(), MedicineActivity.class));
                        break;
                    case 2 :
                        getApplicationContext().startActivity(new Intent(getApplicationContext(), HomeCommingActivity.class));
                        break;
                    case 3 :
                        getApplicationContext().startActivity(new Intent(getApplicationContext(), AttendanceActivity.class));
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
