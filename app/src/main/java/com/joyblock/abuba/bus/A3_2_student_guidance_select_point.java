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
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.util.StringArray;

public class A3_2_student_guidance_select_point extends BaseActivity {
    String authority;

    TextListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a2_2_6_route_change_select_point);
        Log.d("BAN LIST",app.kindergarden_class_list.get(0).kindergarden_class_name);
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
        title.setText("차량선택");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority","");
//
//
        listView = findViewById(R.id.listview);
        adapter = new TextListViewAdapter(2,R.layout.row_bus);
        listView.setAdapter(adapter);
//        for(String str:list)
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        String[] item={"1지점","편의점 앞"};
        adapter.addItem("1지점","편의점 앞");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A1_DocumentSelect.this, MainDawerSelectActivity.class);
//        A1_DocumentSelect.this.startActivity(intent);
//        finish();
    }
}
