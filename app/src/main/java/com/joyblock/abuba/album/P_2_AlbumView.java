package com.joyblock.abuba.album;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CheckPeopleListActivity;
import com.joyblock.abuba.CommentActivity;
import com.joyblock.abuba.CustomDialogModifyAndDel;
import com.joyblock.abuba.MainDawerSelectActivity;
import com.joyblock.abuba.R;

/**
 * Created by hyoshinchoi on 2018. 1. 23..
 */

public class P_2_AlbumView extends BaseActivity {

    TextView noticeTitle,noticeBan,noticeName,noticeTime,noticeContent, commentregister;//제목, 반, 작성자, 등록시간, 내용, 덧글등록
    String seq_notice, notice_detail_seq_user, intentPutExtraModifyData, seq_user;
    ImageView insertAndDelete, detailImage, backImage, commentPushImage, checkPeopleListImage;
    Activity activity;
    CustomDialogModifyAndDel mCustomDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_p_2_albumview);
        seq_notice=getIntent().getStringExtra("seq_notice");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff66ccff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#66CCFF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("사진앨범");
        title.setVisibility(View.VISIBLE);
        //에디트 텍스트를 누르면 등록텍스트가 활성화할려고 등록함 현재 gone
        commentregister = (TextView) findViewById(R.id.commentPushText1);

        EditText et = (EditText) findViewById(R.id.editTexttt);
        et.setVisibility(View.VISIBLE);

        insertAndDelete = (ImageView) findViewById(R.id.noticeDetailinsertAndDeleteText);
        detailImage=(ImageView) findViewById(R.id.detailImageView);
        backImage=(ImageView) findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(P_2_AlbumView.this, MainDawerSelectActivity.class);
                P_2_AlbumView.this.startActivity(intent);
                finish();
            }
        });

        commentPushImage = (ImageView) findViewById(R.id.commentPushImage);
        commentPushImage.setVisibility(View.VISIBLE);
        commentPushImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(P_2_AlbumView.this, CommentActivity.class);
//                intent.putExtra("seq_notice", detail.seq_notice);
                intent.putExtra("flag", "n");
                P_2_AlbumView.this.startActivity(intent);
            }
        });

//        checkPeopleListImage = (ImageView) findViewById(R.id.checkPushImageView);
//        checkPeopleListImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(P_2_AlbumView.this, CheckPeopleListActivity.class);
//                P_2_AlbumView.this.startActivity(intent);
//            }
//        });


        noticeTitle=(TextView)findViewById(R.id.noticeDetailTitleText);
        noticeBan=(TextView)findViewById(R.id.noticeDetailBanText);
        noticeName=(TextView)findViewById(R.id.noticeDetailNameText);
        noticeTime=(TextView)findViewById(R.id.noticeDetailTimeText);
        noticeContent=(TextView)findViewById(R.id.inTextView);

        String seq_notice=getIntent().getStringExtra("seq_notice");

//        setNotice(position,position,null,position,position,position,false);



//        new SelectNoticeOne(seq_notice).execute();

//        setNotice(detail.seq_kindergarden_class,detail.title,getResources().getDrawable(R.mipmap.ic_document),detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));



        seq_user = pref.getString("seq_user","");
        Log.d("유저 시퀀스 " , seq_user);


        insertAndDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCustomDialog = new CustomDialogModifyAndDel(P_2_AlbumView.this,
                        modifyListener,
                        delListener);
                mCustomDialog.show();
            }
        });


    }


    public void setNotice(String ban,String title, String name,String time,String content,boolean availabeReply){
        noticeBan.setText(ban);
        noticeTitle.setText(title);
        noticeName.setText(name);
        noticeTime.setText(time);
        noticeContent.setText(content);


        //userImage, content

    }

/*
    R14_SelectNoticeOne detail;
    class SelectNoticeOne extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/selectNoticeOne.do";

        //전체 공지 조회
        public SelectNoticeOne(String seq_notice) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_notice", seq_notice)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("response : ",json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));

                detail=new GsonBuilder().create().fromJson(jsonResponse.getString("notice"),R14_SelectNoticeOne.class);
                Log.d("detail" , String.valueOf(detail));
                notice_detail_seq_user = detail.seq_user;
                intentPutExtraModifyData = jsonResponse.getString("notice");

                Log.d("유저 시퀀스 ", notice_detail_seq_user);
                Log.d("유저 시퀀스1 ", seq_user);


                if(seq_user.equals(notice_detail_seq_user)){
                    insertAndDelete.setVisibility(View.VISIBLE);
                    Log.d("sd ", "ss");
                } else {
                    insertAndDelete.setVisibility(View.INVISIBLE);
                    Log.d("sd ", "ee");
                }

                Picasso.with(getApplicationContext()).load(detail.file_path).into(detailImage);
                detailImage.setVisibility(View.VISIBLE);

                setNotice(detail.seq_kindergarden_class,detail.title,detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));
//                for(R14_SelectNoticeOne list:noticeList)


//                    adapter.addItem(getResources().getDrawable(R.mipmap.ic_document),list.title,list.reg_date,list.name);
//                adapter.notifyDataSetChanged();
//                Log.d("Tag","공지사항 길이 : "+noticeList.length);
//                if (ss == 200) {
//                    String userID = jsonResponse.getString("resultCode");
//                    String userPassword = jsonResponse.getString("resultMsg");
//                    System.out.println(userID + userPassword);
//                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
//                    System.out.println(json1);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class DeleteNotice extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/deleteNotice.do";

        //공지 삭제
        public DeleteNotice(String seq_notice) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_notice", seq_notice)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("response : ",json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

*/
    //수정버튼
    private View.OnClickListener modifyListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
//            Intent intent=new Intent(P_2_AlbumView.this,NoticeEditorModifyActivity.class);
//            intent.putExtra("ModifyData",intentPutExtraModifyData);
//            P_2_AlbumView.this.startActivity(intent);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
        }
    };

    //삭제버튼
    private View.OnClickListener delListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
            AlertDialog.Builder nd = new AlertDialog.Builder(P_2_AlbumView.this);
            nd.setMessage("정말로 삭제하시겠습니까")
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            P_2_AlbumView.DeleteNotice buyTask = new P_2_AlbumView.DeleteNotice(seq_notice);
//                            buyTask.execute();
//                            Intent intent = new Intent(P_2_AlbumView.this, NoticeActivity.class);
//                            P_2_AlbumView.this.startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("취소", null)
                    .create()
                    .show();
        }
    };



}
