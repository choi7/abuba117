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

public class  A2_2_5_route_change_select_bus extends BaseActivity {
    public static A2_2_5_route_change_select_bus A2_2_5_route;

    String authority;

    TextListViewAdapter adapter;
    ListView listView;

    int int_selected=-1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        A2_2_5_route=A2_2_5_route_change_select_bus.this;
        setContentView(R.layout.layout_a2_2_5_route_change_select_bus);
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
        adapter = new TextListViewAdapter(2,R.layout.row_bus);
        listView.setAdapter(adapter);
//        for(String str:list)
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem("6지점","곰돌이 1호차");
        adapter.addItem("6지점","곰돌이 2호차");
        adapter.addItem("6지점","곰돌이 3호차");
        adapter.addItem("6지점","호랑이 1호차");
        adapter.addItem("6지점","호랑이 2호차");
        adapter.addItem("6지점","호랑이 3호차");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                int_selected=position;
            }
        });


    }

    public void clickNext(View v){
        if(int_selected<0)
            Toast.makeText(A2_2_5_route_change_select_bus.this,"차량을 선택해주세요.",Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(A2_2_5_route_change_select_bus.this, A2_2_6_route_change_select_point.class);
            intent.putExtra("int_bus", int_selected);
            A2_2_5_route_change_select_bus.this.startActivity(intent);
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
