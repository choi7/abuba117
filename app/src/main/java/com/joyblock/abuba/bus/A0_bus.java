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

import com.joyblock.abuba.api.API;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

public class A0_bus extends BaseActivity {
    String authority;

    A0_ListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        API api=new API();
        //        try {
//            api.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        api.API_2("test0","test0");

//        api.API_5("경상남도","창원시","테스트 유치원","1");
//        api.API_6("1","1");
//        api.API7("1","18","","t","","1987","03","11");
//        api.API8("18","");
//        api.API_9("18","test0","test0");
//        api.API_10("18","1","y","0","공지사항","내용없음",null);


        setContentView(R.layout.layout_a0_bus);
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
        title.setText("버스위치확인");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority","");
//
//
        listView = findViewById(R.id.documentSelectListView);
        adapter = new A0_ListViewAdapter(NanumSquareBold);
        listView.setAdapter(adapter);
//        for(String str:list)
        adapter.addItem(R.layout.row_bus_location1,getResources().getDrawable(R.drawable.a0_icon1), "실시간버스위치",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(R.layout.row_bus_location1,getResources().getDrawable(R.drawable.a0_icon1), "노선관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem(R.layout.row_bus_location1,getResources().getDrawable(R.drawable.a0_icon1), "등원지도",getResources().getDrawable(R.drawable.document_next_image));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), Integer.toString(position),Toast.LENGTH_LONG).show();

                switch (adapter.getItem(position).getTitle()) {
                    case "실시간버스위치" :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A0_bus.this.startActivity(new Intent(A0_bus.this, A1_1_bus_location.class));
                        break;
                    case "노선관리":
                        toggleItems();
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
//                        A0_bus.this.startActivity(new Intent(A0_bus.this, A1_1_bus_location.class));
                        break;
                    case "승차관리" :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A0_bus.this.startActivity(new Intent(A0_bus.this, A2_1_boading_management.class));
                        break;
                    case "노선변경" :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A0_bus.this.startActivity(new Intent(A0_bus.this, A2_2_1_route_change_list.class));
                        break;
                    case "노선등록" :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A0_bus.this.startActivity(new Intent(A0_bus.this, A2_3_1_route_registration_point_list.class));
                        break;
                    case "등원지도" :
//                        Toast.makeText(getApplicationContext(),list[position],list[position].length()).show();
                        A0_bus.this.startActivity(new Intent(A0_bus.this, A3_1_student_guidance_bus_list.class));
                        break;

                }

            }
        });

//        api.API_3(app.seq_user,"어부바","","","","",null,"","","","120","30");
//
//        api.API_4("ar2","경상남도");
//        api.API_4("ar2","경상남도");

    }

    boolean toggle=false;
    void toggleItems(){
        if(!toggle){
            adapter.addItem(R.layout.row_bus_location2,getResources().getDrawable(R.drawable.a0_icon2), "노선등록",getResources().getDrawable(R.drawable.document_next_image),2);
            adapter.addItem(R.layout.row_bus_location2,getResources().getDrawable(R.drawable.a0_icon2), "노선변경",getResources().getDrawable(R.drawable.document_next_image),2);
            adapter.addItem(R.layout.row_bus_location2,getResources().getDrawable(R.drawable.a0_icon2), "승차관리",getResources().getDrawable(R.drawable.document_next_image),2);
        }else{
            adapter.removeItem(2);
            adapter.removeItem(2);
            adapter.removeItem(2);
        }
        toggle=!toggle;
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A1_DocumentSelect.this, MainDawerSelectActivity.class);
//        A1_DocumentSelect.this.startActivity(intent);
//        finish();
    }

}
