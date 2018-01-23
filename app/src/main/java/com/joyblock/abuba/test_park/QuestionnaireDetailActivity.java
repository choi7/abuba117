package com.joyblock.abuba.test_park;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomDialogModifyAndDel;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R21_SelectNoticeOne;
import com.joyblock.abuba.notice.NoticeActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class QuestionnaireDetailActivity extends BaseActivity {

    TextView noticeTitle,noticeBan,noticeName,noticeTime,noticeContent, commentregister;//제목, 반, 작성자, 등록시간, 내용, 덧글등록
    String seq_survey, notice_detail_seq_user, intentPutExtraModifyData, seq_user;
    ImageView insertAndDelete, detailImage, backImage, commentPushImage, checkPeopleListImage;
    Activity activity;
    CustomDialogModifyAndDel mCustomDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_detail);
        seq_survey =getIntent().getStringExtra("seq_survey");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("설문지");
        title.setVisibility(View.VISIBLE);
//        //에디트 텍스트를 누르면 등록텍스트가 활성화할려고 등록함 현재 gone
//        commentregister = (TextView) findViewById(R.id.commentPushText1);
//
//        EditText et = (EditText) findViewById(R.id.editTexttt);
//        et.setVisibility(View.VISIBLE);
//
//        insertAndDelete = (ImageView) findViewById(R.id.noticeDetailinsertAndDeleteText);
//        detailImage=(ImageView) findViewById(R.id.detailImageView);
//        backImage=(ImageView) findViewById(R.id.backImage);
//
//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(QuestionnaireDetailActivity.this, NoticeActivity.class);
//                QuestionnaireDetailActivity.this.startActivity(intent);
//                finish();
//            }
//        });
//
//        commentPushImage = (ImageView) findViewById(R.id.commentPushImage);
//        commentPushImage.setVisibility(View.VISIBLE);
//        commentPushImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(QuestionnaireDetailActivity.this, CommentActivity.class);
//                intent.putExtra("seq_notice", detail.seq_notice);
//                intent.putExtra("flag", "n");
//                QuestionnaireDetailActivity.this.startActivity(intent);
//            }
//        });
//
//        checkPeopleListImage = (ImageView) findViewById(R.id.checkPushImageView);
//        checkPeopleListImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(QuestionnaireDetailActivity.this, CheckPeopleListActivity.class);
//                QuestionnaireDetailActivity.this.startActivity(intent);
//            }
//        });
//

        noticeTitle=(TextView)findViewById(R.id.noticeDetailTitleText);
        noticeBan=(TextView)findViewById(R.id.noticeDetailBanText);
        noticeName=(TextView)findViewById(R.id.noticeDetailNameText);
        noticeTime=(TextView)findViewById(R.id.noticeDetailTimeText);
        noticeContent=(TextView)findViewById(R.id.inTex);
//
//        String seq_notice=getIntent().getStringExtra("seq_notice");
//
////        setNotice(position,position,null,position,position,position,false);
//
//
//
//        new SelectNoticeOne(seq_notice).execute();
//
////        setNotice(detail.seq_kindergarden_class,detail.title,getResources().getDrawable(R.mipmap.ic_document),detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));
//
//
//
//        seq_user = pref.getString("seq_user","");
//        Log.d("유저 시퀀스 " , seq_user);
//
//
//        insertAndDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mCustomDialog = new CustomDialogModifyAndDel(QuestionnaireDetailActivity.this,
//                        modifyListener,
//                        delListener);
//                mCustomDialog.show();
//            }
//        });


    }

    public void setNotice(String ban,String title, String name, String time, String content,boolean availabeReply){
        noticeBan.setText(ban);
        noticeTitle.setText(title);
        noticeName.setText(name);
        noticeTime.setText(time);
        noticeContent.setText(content);


        //userImage, content

    }



    //휴대폰 하단 백 버튼 눌렀을때 실행되는 메소드
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuestionnaireDetailActivity.this, NoticeActivity.class);
        QuestionnaireDetailActivity.this.startActivity(intent);
        finish();
    }

    /*
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
    */

    R21_SelectNoticeOne detail;
    class SelectSurveyOne extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/selectSurveyOne.do";

        //설문지 상세 조회
        public SelectSurveyOne(String seq_notice) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_survey", seq_notice)
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



                detail=new GsonBuilder().create().fromJson(json,R21_SelectNoticeOne.class);
//                detail=new GsonBuilder().create().fromJson(jsonResponse.getString("survey"),R21_SelectNoticeOne.class);
//                detail.survey_list[0];
//                detail.resultcode


                Log.d("detail" , String.valueOf(detail));
//                notice_detail_seq_user = detail.seq_user;
//                intentPutExtraModifyData = jsonResponse.getString("notice");

//                Log.d("유저 시퀀스 ", notice_detail_seq_user);
//                Log.d("유저 시퀀스1 ", seq_user);


                if(seq_user.equals(notice_detail_seq_user)){
                    insertAndDelete.setVisibility(View.VISIBLE);
                    Log.d("sd ", "ss");
                } else {
                    insertAndDelete.setVisibility(View.INVISIBLE);
                    Log.d("sd ", "ee");
                }

//                Picasso.with(getApplicationContext()).load(detail.file_path).into(detailImage);
//                detailImage.setVisibility(View.VISIBLE);
//
//                setNotice(detail.seq_kindergarden_class,detail.title,detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));
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

    class UpdateSurvey extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/deleteNotice.do";

        //설문지 수정
        public UpdateSurvey(String seq_survey, String seq_kindergarden_class, String title , String content,
                            String year , String month, String day, String c_survey_vote, String files, String vote_item) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_survey", seq_survey)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("title", title)
                    .add("content", content)
                    .add("year", year)
                    .add("month", month)
                    .add("day", day)
                    .add("c_survey_vote", c_survey_vote)
                    .add("files", files)
                    .add("vote_item", vote_item)
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

//
//    //수정버튼
//    private View.OnClickListener modifyListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            mCustomDialog.dismiss();
//            Intent intent=new Intent(QuestionnaireDetailActivity.this,NoticeEditorModifyActivity.class);
//            intent.putExtra("ModifyData",intentPutExtraModifyData);
//            QuestionnaireDetailActivity.this.startActivity(intent);
////            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            finish();
//        }
//    };

    //수정버튼
    private View.OnClickListener delListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
            AlertDialog.Builder nd = new AlertDialog.Builder(QuestionnaireDetailActivity.this);
            nd.setMessage("수정하시겠습니까")
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            QuestionnaireDetailActivity.DeleteNotice buyTask = new QuestionnaireDetailActivity.DeleteNotice(seq_notice);
//                            buyTask.execute();
                            Intent intent = new Intent(QuestionnaireDetailActivity.this, QuestionnaireActivity.class);
                            QuestionnaireDetailActivity.this.startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("취소", null)
                    .create()
                    .show();
        }
    };



}
