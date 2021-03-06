package com.joyblock.abuba.document;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CommentActivity;
import com.joyblock.abuba.CustomDialogModifyAndDel;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R26_SelectEducationalPlanList;
import com.joyblock.abuba.util.TimeConverter;
import com.joyblock.abuba.api_message.R27_SelectEducationalPlanOne;
import com.joyblock.abuba.notice.NoticeActivity;
import com.joyblock.abuba.notice.NoticeEditorActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class A2_3_EducationPlanDetailActivity extends BaseActivity {

    TextView educationPlanTitle,educationPlanAge,educationPlanName,educationPlanTime,educationPlanContent;//제목, 반, 작성자, 등록시간, 내용
    String plan_str, notice_detail_seq_user, intentPutExtraModifyData, seq_kindergarden_class, file_path, reg_date, seq_kindergarden, is_yn, plan_flag, mod_date, seq_user, seq_educational_plan, title, content;
    ImageView insertAndDelete, detailImage, backImage;
    Activity activity;
    CustomDialogModifyAndDel mCustomDialog;
    R27_SelectEducationalPlanOne detail;
    String json1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view1);
        seq_educational_plan=getIntent().getStringExtra("seq_educational_plan");
        plan_str=getIntent().getStringExtra("plan_str");

        api.API_27(seq_educational_plan);
        String json = api.getMessage();
        try {
            JSONObject jsonResponse = new JSONObject(json);
            Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
            json1 = jsonResponse.getString("educational_plan");
        } catch (JSONException e) {
        }


        detail = new GsonBuilder().create().fromJson(json1, R27_SelectEducationalPlanOne.class);
//                detail=new GsonBuilder().create().fromJson(jsonResponse.getString("survey"),R21_SelectNoticeOne.class);
//                detail.survey_list[0];
//                detail.resultcode
//        for (int i = 0; i < detail.survey_vote_item_list.length; i++) {
//            Log.d("detail-1", String.valueOf(detail.survey_vote_item_list[i]));
//
//        }

        seq_kindergarden_class = detail.seq_kindergarden_class;
        file_path = detail.file_path;
        reg_date = detail.reg_date;
        seq_kindergarden = detail.seq_kindergarden;
        is_yn = detail.is_yn;
        mod_date = detail.mod_date;
        seq_user = detail.seq_user;
        seq_educational_plan = detail.seq_educational_plan;
        title = detail.title;
        content = detail.content;
        Log.d("R27", seq_kindergarden_class +" "+ file_path+" " + reg_date +" " +seq_kindergarden +" "+is_yn +" " +mod_date +" " +seq_user +" "+seq_educational_plan+ " "+ title +" "+ content);
//        String class_name = app.kindergarden_class_list.get(Integer.parseInt(seq_kindergarden_class)).kindergarden_class_name;

        educationPlanTitle=(TextView)findViewById(R.id.noticeDetailTitleText);
        educationPlanTitle.setText(title);
        educationPlanAge=(TextView)findViewById(R.id.noticeDetailBanText);

        educationPlanName=(TextView)findViewById(R.id.noticeDetailNameText);
        educationPlanName.setText(title);
        educationPlanTime=(TextView)findViewById(R.id.noticeDetailTimeText);
        educationPlanTime.setText(TimeConverter.convert(reg_date));
        educationPlanContent=(TextView)findViewById(R.id.inTextView);
        educationPlanContent.setText(content);
        detailImage=(ImageView) findViewById(R.id.detailImageView);
        if(!file_path.equals(null) && !file_path.equals("")) {
            Picasso.with(this).load(file_path).into(detailImage);
            detailImage.setVisibility(View.VISIBLE);
        }
//        noticeBan = (TextView) findViewById(R.id.noticeDetailBanText);
//        noticeBan.setText(class_name);



        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText(plan_str+" 교육계획안");
        title.setVisibility(View.VISIBLE);
//
//        EditText et = (EditText) findViewById(R.id.editTexttt);
//        et.setVisibility(View.VISIBLE);

//        ImageView commentPushImage = (ImageView) findViewById(R.id.commentPushImage);
//        commentPushImage.setVisibility(View.VISIBLE);
//        commentPushImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(A2_3_EducationPlanDetailActivity.this, CommentActivity.class);
//                intent.putExtra("seq_notice", detail.seq_notice);
//                intent.putExtra("flag", "A2_3_EducationPlanDetailActivity");
//                A2_3_EducationPlanDetailActivity.this.startActivity(intent);
//            }
//        });

        insertAndDelete = (ImageView) findViewById(R.id.noticeDetailinsertAndDeleteText);

//        backImage=(ImageView) findViewById(R.id.backImage);
//
//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(A2_3_EducationPlanDetailActivity.this, NoticeActivity.class);
//                A2_3_EducationPlanDetailActivity.this.startActivity(intent);
//                finish();
//            }
//        });

        educationPlanTitle=(TextView)findViewById(R.id.noticeDetailTitleText);
        educationPlanAge=(TextView)findViewById(R.id.noticeDetailBanText);
        educationPlanName=(TextView)findViewById(R.id.noticeDetailNameText);
        educationPlanTime=(TextView)findViewById(R.id.noticeDetailTimeText);
        educationPlanContent=(TextView)findViewById(R.id.inTextView);

//        setNotice(position,position,null,position,position,position,false);

//        new SelectEducationalPlanOne(seq_educational_plan).execute();

//        setNotice(detail.seq_kindergarden_class,detail.title,getResources().getDrawable(R.mipmap.ic_document),detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));

        String seq_user = pref.getString("seq_user","");
        if(seq_user != notice_detail_seq_user){
            insertAndDelete.setVisibility(View.INVISIBLE);
        }

        insertAndDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCustomDialog = new CustomDialogModifyAndDel(A2_3_EducationPlanDetailActivity.this,
                        modifyListener,
                        delListener);
                mCustomDialog.show();
            }
        });


    }

    public void setEducationPlan(String age,String title,String name,String time,String content){
        educationPlanAge.setText(age);
        educationPlanTitle.setText(title);
        educationPlanName.setText(name);
        educationPlanTime.setText(time);
        educationPlanContent.setText(content);
        //userImage, content
    }

    //휴대폰 하단 백 버튼 눌렀을때 실행되는 메소드
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(A2_3_EducationPlanDetailActivity.this, A2_1_EducationPlan.class);
//        A2_3_EducationPlanDetailActivity.this.startActivity(intent);
//        finish();
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


    class SelectEducationalPlanOne extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String api_string="selectEducationalPlanOne";
        String message_key="educational_plan";
        String url="http://58.229.208.246/Ububa/"+api_string+".do";

        //전체 공지 조회
        public SelectEducationalPlanOne(String seq_educational_plan) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add(api_string, seq_educational_plan)
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

                detail=new GsonBuilder().create().fromJson(jsonResponse.getString(message_key),R27_SelectEducationalPlanOne.class);
                Log.d("detail" , String.valueOf(detail));
                notice_detail_seq_user = detail.seq_user;
                intentPutExtraModifyData = jsonResponse.getString(message_key);

                Picasso.with(getApplicationContext()).load(detail.file_path).into(detailImage);
                detailImage.setVisibility(View.VISIBLE);

                setEducationPlan(detail.seq_kindergarden_class,detail.title,/*detail.name*/"작성자 이름 요청", TimeConverter.convert(detail.reg_date),detail.content);
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





    //수정버튼
    private View.OnClickListener modifyListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent=new Intent(A2_3_EducationPlanDetailActivity.this,NoticeEditorActivity.class);
            intent.putExtra("sdf",intentPutExtraModifyData);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
        }
    };

    //삭제버튼
    private View.OnClickListener delListener = new View.OnClickListener() {
        public void onClick(View v) {


            mCustomDialog.dismiss();
        }
    };



}
