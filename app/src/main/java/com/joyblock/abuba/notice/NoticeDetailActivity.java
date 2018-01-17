package com.joyblock.abuba.notice;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.R;

public class NoticeDetailActivity extends AppCompatActivity {

    TextView noticeTitle,noticeBan,noticeName,noticeTime,noticeContent;//제목, 반, 작성자, 등록시간, 내용
    String seq_notice;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);
        seq_notice=getIntent().getStringExtra("seq_notice");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33a09e));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33A09E"));
        }


        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("공지사항");
        title.setVisibility(View.VISIBLE);

        EditText et = (EditText) findViewById(R.id.editTexttt);
        et.setVisibility(View.VISIBLE);

        ImageView insertAndDelete = (ImageView) findViewById(R.id.noticeDetailinsertAndDeleteText);


        noticeTitle=(TextView)findViewById(R.id.noticeDetailTitleText);
        noticeBan=(TextView)findViewById(R.id.noticeDetailBanText);
        noticeName=(TextView)findViewById(R.id.noticeDetailNameText);
        noticeTime=(TextView)findViewById(R.id.noticeDetailTimeText);

        String position=Integer.toString(getIntent().getIntExtra("seq_notice",0));
        setNotice(position,position,null,position,position,position,false);

        insertAndDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = activity.getLayoutInflater().inflate(R.layout.park_layout_notice_mod_del, null);
                // 해당 뷰에 리스트뷰 호출
                //listview = (ListView)view.findViewById(R.id.notice_popup_listview);
                // 리스트뷰에 어댑터 설정

                // 반 다이얼로그 생성
                AlertDialog.Builder modDelDialogBuilder= new AlertDialog.Builder(activity);
                // 리스트뷰 설정된 레이아웃
                modDelDialogBuilder.setView(view);

//                // 확인버튼
//                banListDialogBuilder.setPositiveButton("확인", null);

                // 반 다이얼로그 보기
                DialogInterface modDelDialogInterface=modDelDialogBuilder.show();

                //반 다이얼로그 이벤트 처리
//                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        BanListViewItem item= adapter.list.get(position);
//
//                        seq_kindergarden_class=position==0?"0":classList[position-1].seq_kindergarden_class;
//                        banListDialogInterface.dismiss();
//                        title.setText(item.getName());
//                        Toast.makeText(getApplicationContext(), position==0?"전체":item.getName(),Toast.LENGTH_LONG).show();
//                    }
//                });

//.setMessage("작성글을 수정하시겠습니까")


//        AlertDialog.Builder nd = new AlertDialog.Builder(NoticeDetailActivity.this);
//        nd.setMessage("작성글을 수정하시겠습니까")
//                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(NoticeDetailActivity.this, "공지사항 수정 화면",Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(NoticeDetailActivity.this, NoticeEditorActivity.class);
//                        NoticeDetailActivity.this.startActivity(intent);
//                        finish();
//                    }
//                })
//                .setPositiveButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .create()
//                .show();



            }
        });


    }

    public void setNotice(String ban,String title,Drawable userImage, String name,String time,String content,boolean availabeReply){
        noticeBan.setText(ban);
        noticeTitle.setText(title);
        noticeName.setText(name);
        noticeTime.setText(time);


        //userImage, content

    }


    //휴대폰 하단 백 버튼 눌렀을때 실행되는 메소드
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NoticeDetailActivity.this, NoticeActivity.class);
        NoticeDetailActivity.this.startActivity(intent);
        finish();
    }

    //화면 상단 왼쪽에 있는 백 버튼 눌렀을때 실행되는 메소드
    //백 버튼 활성화는 액션바.setDisplayHomeAsUpEnabled(true);
    @Override
    public boolean onSupportNavigateUp() {

        Toast.makeText(NoticeDetailActivity.this, "뒤로가기 버튼 눌름",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(NoticeDetailActivity.this, NoticeActivity.class);
        NoticeDetailActivity.this.startActivity(intent);
        finish();

        return super.onSupportNavigateUp();
    }

}
