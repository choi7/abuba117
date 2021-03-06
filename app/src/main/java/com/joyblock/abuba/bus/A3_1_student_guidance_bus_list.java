package com.joyblock.abuba.bus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;
import com.joyblock.abuba.util.StringArray;

import org.w3c.dom.Text;

public class A3_1_student_guidance_bus_list extends BaseActivity {
    public static A3_1_student_guidance_bus_list activity;
    String str_boarding;
    boolean flag;


    int int_selected_class;

    int int_selected=-1;

    CustomListViewDialog dialog;

    ConstraintLayout class_ConstraintLayout;

    TextView classText;

    TextListViewAdapter adapter;
    BanListViewAdapter class_adapter;
    ListView listview, class_listview;
    TextDialog mCustomDialog;

    //    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        flag = true;// 승/하차 플래그
        setContentView(R.layout.layout_a3_1_student_guidance_bus_list);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cc99));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

//        editor=pref.edit();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String seq_user = app.seq_user;
        String seq_kindergarden = app.my_kindergarden_list.get(0).seq_kindergarden;
        String notice_title = "긴급공지";
        String content = "내용없음";
        String files = "no files";

        //classText = (TextView) findViewById(R.id.a3_1_Class_TextView);

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("등원지도");
        title.setVisibility(View.VISIBLE);


//
        ImageView imageView = (ImageView) findViewById(R.id.editorImage);
//        imageView.setVisibility(View.VISIBLE);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(A2_1_EducationPlan.this, A2_2_EducationPlanEditor.class);
//                intent.putExtra("plan_flag",plan_flag);
//                A2_1_EducationPlan.this.startActivity(intent);
//                finish();
//            }
//        });
//
//        ImageView backImage = (ImageView) findViewById(R.id.backImage);
//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent loginIntent = new Intent(A2_1_EducationPlan.this, A1_DocumentSelect.class);
//                A2_1_EducationPlan.this.startActivity(loginIntent);
//                finish();
//            }
//        });

        mCustomDialog = new TextDialog(A3_1_student_guidance_bus_list.this,R.layout.dialog_school_or_home);
        mCustomDialog.setActivity(this);
        mCustomDialog.setTexts(new String[]{"등   원","하   원"});



//                class_adapter = new BanListViewAdapter();
//                dialog = new CustomListViewDialog(A3_1_student_guidance_bus_list.this, class_adapter);
//                dialog.setActivity(this);
//                class_adapter.addItem("등원");
//                class_adapter.addItem("하원");
//
//                class_adapter.notifyDataSetChanged();
//                dialog.show();

                //반 다이얼로그 이벤트 처리
//                class_listview = dialog.getListView();
//                class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        listview = findViewById(R.id.listview);
//                        adapter = new TextListViewAdapter(3, R.layout.row_bus);
//                        listview.setAdapter(adapter);
//                        adapter.addItem("5지점","곰돌이 1호차","10명");
//                        adapter.addItem("6지점","곰돌이 2호차","15명");
//                        adapter.addItem("7지점","곰돌이 3호차","7명");
//                        adapter.addItem("4지점","호랑이 1호차","7명");
//                        adapter.addItem("6지점","호랑이 2호차","8명");
//                        adapter.addItem("3지점","호랑이 3호차","10명");
//                        listview.setVisibility(View.VISIBLE);
//                        TextView red=(TextView)findViewById(R.id.textview_red);
//                        red.setVisibility(View.VISIBLE);
//
//                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                adapter.setSelected(position);
//                                int_selected=position;
//                    }
//                });
////        for(String str:list)
////        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "승차관리",getResources().getDrawable(R.drawable.document_next_image));
////        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선변경",getResources().getDrawable(R.drawable.document_next_image));
////        adapter.addItem(getResources().getDrawable(R.drawable.document_image), "노선등록",getResources().getDrawable(R.drawable.document_next_image));
//
//
////                        BanListViewItem item = class_adapter.list.get(position);
////                        int_selected_class = position;
////                        //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
////                        classText.setText(item.getName());
////                        Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
//
//
//                        dialog.dismiss();
//                    }
//                });

    }

    public void onResume(){
        super.onResume();
        mCustomDialog.show();
    }

    public void clickTextView1(View v){
        mCustomDialog.dismiss();;
        listview = findViewById(R.id.listview);
        adapter = new TextListViewAdapter(3, R.layout.row_bus);
        listview.setAdapter(adapter);
        adapter.addItem("5지점","곰돌이 1호차","10명");
        adapter.addItem("6지점","곰돌이 2호차","15명");
        adapter.addItem("7지점","곰돌이 3호차","7명");
        adapter.addItem("4지점","호랑이 1호차","7명");
        adapter.addItem("6지점","호랑이 2호차","8명");
        adapter.addItem("3지점","호랑이 3호차","10명");
        listview.setVisibility(View.VISIBLE);
        TextView red=(TextView)findViewById(R.id.textview_red);
        red.setVisibility(View.VISIBLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                int_selected = position;
            }
        });
    }
    public void clickTextView2(View v){
        mCustomDialog.dismiss();;
        listview = findViewById(R.id.listview);
        adapter = new TextListViewAdapter(3, R.layout.row_bus);
        listview.setAdapter(adapter);
        adapter.addItem("5지점","곰돌이 1호차","10명");
        adapter.addItem("6지점","곰돌이 2호차","15명");
        adapter.addItem("7지점","곰돌이 3호차","7명");
        adapter.addItem("4지점","호랑이 1호차","7명");
        adapter.addItem("6지점","호랑이 2호차","8명");
        adapter.addItem("3지점","호랑이 3호차","10명");
        listview.setVisibility(View.VISIBLE);
        TextView red=(TextView)findViewById(R.id.textview_red);
        red.setVisibility(View.VISIBLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                int_selected = position;
            }
        });


    }

    public void clickRed(View v){

        if(int_selected<0)
            Toast.makeText(A3_1_student_guidance_bus_list.this,"차량을 선택해주세요.",Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(A3_1_student_guidance_bus_list.this, A3_2_student_guidance_select_point.class);
            intent.putExtra("int_bus", int_selected);
            A3_1_student_guidance_bus_list.this.startActivity(intent);
        }


//        Intent intent = new Intent(A3_1_student_guidance_bus_list.this, A3_2_student_guidance_select_point.class);
//        A3_1_student_guidance_bus_list.this.startActivity(intent);
    }

}
