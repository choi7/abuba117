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
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;

public class A2_3_2_route_registration_point_student_list extends BaseActivity {

    String authority;

    TextListViewAdapter adapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a2_3_2_route_registration_point_student_list);
        Log.d("BAN LIST", app.kindergarden_class_list.get(0).kindergarden_class_name);
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
        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText("1지점 - 편의점 앞");
        title.setVisibility(View.VISIBLE);

        authority = pref.getString("authority", "");
//
//
        listView = findViewById(R.id.listview);
        adapter = new TextListViewAdapter(2, R.layout.row_bus);
        listView.setAdapter(adapter);
//        for(String str:list)
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
//        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
        adapter.addItem("호랑이반", "김철수");
        adapter.addItem("코끼리반", "김민석");
        adapter.addItem("토끼반", "박찬열");
        adapter.addItem("호랑이반", "오세훈");
        adapter.addItem("코끼리반", "도경수");
        adapter.addItem("토끼반", "김준면");
        adapter.addItem("호랑이반", "김수호");
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


    int int_selected_class;
    public void clickRed(View v) {
        TextListViewAdapter class_adapter= new TextListViewAdapter(1,R.layout.row_center_text1_center_nauamsquare_bold_55pt);
        final CustomListViewDialog dialog = new CustomListViewDialog(A2_3_2_route_registration_point_student_list.this,class_adapter);
//                class_adapter.addItem("전체");
        class_adapter.addItem("전체");
        class_adapter.addItem("호랑이반");
        class_adapter.addItem("코끼리반");
        class_adapter.addItem("토끼반");

//                for (R6_SelectKindergardenClassList list : app.kindergarden_class_list) {
//                    class_adapter.addItem(list.kindergarden_class_name);
//                }
        class_adapter.notifyDataSetChanged();
        dialog.show();

        Toast.makeText(v.getContext(),"원장님 전용화면 입니다.",Toast.LENGTH_SHORT).show();
        //반 다이얼로그 이벤트 처리
        ListView class_listview=dialog.getListView();
        class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int_selected_class=position;
                //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
                Toast.makeText(getApplicationContext(), adapter.getString(position)[0], Toast.LENGTH_LONG).show();
                dialog.dismiss();
                Intent intent = new Intent(A2_3_2_route_registration_point_student_list.this, A2_3_3_route_registration_class_student_list.class);
                A2_3_2_route_registration_point_student_list.this.startActivity(intent);
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
