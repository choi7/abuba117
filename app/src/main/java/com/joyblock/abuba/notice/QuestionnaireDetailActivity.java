package com.joyblock.abuba.notice;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomDialogModifyAndDel;
//import com.joyblock.abuba.QuestionaireDetailListViewAdapter;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R21_SelectNoticeOne;
import com.joyblock.abuba.bus.TextListViewAdapter;
import com.joyblock.abuba.util.ImageFileProcessor;
import com.joyblock.abuba.util.TimeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class QuestionnaireDetailActivity extends BaseActivity {

    TextView noticeTitle, noticeBan, noticeName, noticeTime, noticeContent, commentregister;//제목, 반, 작성자, 등록시간, 내용, 덧글등록
    String notice_detail_seq_user, intentPutExtraModifyData;
    ImageView insertAndDelete, detailImage, backImage, commentPushImage, checkPeopleListImage;
    Activity activity;
    CustomDialogModifyAndDel mCustomDialog;
    String seq_kindergarden_class, reg_date, month, year, seq_kindergarden, is_yn, mod_date, seq_user, seq_survey, titles, day, content;
    R21_SelectNoticeOne detail;
    ListView listView;
    TextListViewAdapter listViewAdapter;
//    ListViewAdapter listViewAdapter;
    public int mSelectedItem;

    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_detail);
        seq_survey = getIntent().getStringExtra("seq_survey");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);

        addBacklistner();
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


        api.API_21(seq_survey);
        String json = api.getMessage();
        try {
            JSONObject jsonResponse = new JSONObject(json);
            Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
        } catch (JSONException e) {
        }


        detail = new GsonBuilder().create().fromJson(json, R21_SelectNoticeOne.class);
//                detail=new GsonBuilder().create().fromJson(jsonResponse.getString("survey"),R21_SelectNoticeOne.class);
//                detail.survey_list[0];
//                detail.resultcode
        for (int i = 0; i < detail.survey_vote_item_list.length; i++) {
            Log.d("detail-1", String.valueOf(detail.survey_vote_item_list[i]));

        }
        seq_kindergarden_class = detail.survey.seq_kindergarden_class;
        reg_date = detail.survey.reg_date;
        month = detail.survey.month;
        year = detail.survey.year;
        seq_kindergarden = detail.survey.seq_kindergarden;
        is_yn = detail.survey.is_yn;
        mod_date = detail.survey.seq_kindergarden_class;
        seq_user = detail.survey.seq_kindergarden_class;
        seq_survey = detail.survey.seq_kindergarden_class;
        titles = detail.survey.title;
        day = detail.survey.day;
        content = detail.survey.content;
        String class_name = app.kindergarden_class_list.get(Integer.parseInt(seq_kindergarden_class)).kindergarden_class_name;

        noticeTitle = (TextView) findViewById(R.id.noticeDetailTitleText);
        noticeTitle.setText(titles);
        noticeBan = (TextView) findViewById(R.id.noticeDetailBanText);
        noticeBan.setText(class_name);
        noticeName = (TextView) findViewById(R.id.noticeDetailNameText);
        noticeName.setText(titles);
        noticeTime = (TextView) findViewById(R.id.noticeDetailTimeText);
        noticeTime.setText(TimeConverter.convert(reg_date));
        noticeContent = (TextView) findViewById(R.id.inTex);
        noticeContent.setText(content);
        listView = (ListView) findViewById(R.id.questListview);
        listViewAdapter=new TextListViewAdapter(2,R.layout.votedetaillistviewcell);
        listViewAdapter.addItem("찬성","3");
        listViewAdapter.addItem("반대","3");
        listViewAdapter.addItem("기권","4");
        listView.setAdapter(listViewAdapter);

        setListViewHeightBasedOnChildren(listView);
//        mAdapter11 = new QuestionaireDetailListViewAdapter(this);
//        listViewAdapter = new ListViewAdapter(this);
//        listView.setAdapter(listViewAdapter);
//        listViewAdapter.addItem(getResources().getDrawable(R.drawable.vote_image),"찬성","3");
//        listViewAdapter.addItem(getResources().getDrawable(R.drawable.vote_image),"반대","3");
//        listViewAdapter.addItem(getResources().getDrawable(R.drawable.vote_image),"기권","4");
//        setListViewHeightBasedOnChildren(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedItem = position;
//                listViewAdapter.getView(position,)
//                listViewAdapter.notifyDataSetChanged();
//                for (int i = 0; i < ListData.size(); i++) {
//                    if (i != position) {
//                        ListData.get(i).a = true;
//                    } else if (ListData.get(position).a) {
//                        ListData.get(position).a = false;
//                        System.out.println("포지션은 : " + ListData.get(position).a);
//                    } else {
//                        ListData.get(position).a = true;
//                        System.out.println("포지션sdf : " + ListData.get(position).a);
//                    }
//                }

            }
        });

        Log.d("detail-2", String.valueOf(detail.survey_vote_item_list));


//                notice_detail_seq_user = detail.seq_user;
//                intentPutExtraModifyData = jsonResponse.getString("notice");

//                Log.d("유저 시퀀스 ", notice_detail_seq_user);
//                Log.d("유저 시퀀스1 ", seq_user);

/*
        if (seq_user.equals(notice_detail_seq_user)) {
            insertAndDelete.setVisibility(View.VISIBLE);
            Log.d("sd ", "ss");
        } else {
            insertAndDelete.setVisibility(View.INVISIBLE);
            Log.d("sd ", "ee");
        }
        */

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





    //수정버튼
    private View.OnClickListener modifyListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
//            Intent intent=new Intent(NoticeDetailActivity.this,NoticeEditorModifyActivity.class);
//            intent.putExtra("ModifyData",intentPutExtraModifyData);
//            NoticeDetailActivity.this.startActivity(intent);
////            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            finish();
        }
    };


    public void clickModDel(View v) {
        mCustomDialog = new CustomDialogModifyAndDel(QuestionnaireDetailActivity.this,
                modifyListener,
                delListener);
        mCustomDialog.show();
    }

    public void setNotice(String ban, String title, String name, String time, String content, boolean availabeReply) {
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
//        Intent intent = new Intent(QuestionnaireDetailActivity.this, NoticeActivity.class);
//        QuestionnaireDetailActivity.this.startActivity(intent);
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


    class UpdateSurvey extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/deleteNotice.do";

        //설문지 수정
        public UpdateSurvey(String seq_survey, String seq_kindergarden_class, String title, String content,
                            String year, String month, String day, String c_survey_vote, String files, String vote_item) {
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
            Log.d("response : ", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //설문지 항목 리스트가 추가 될때마다 스크롤이 되는데 이때 스크롤을 없애고 공간을 늘리는 메소드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
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
            nd.setMessage("삭제하시겠습니까")
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            QuestionnaireDetailActivity.DeleteNotice buyTask = new QuestionnaireDetailActivity.DeleteNotice(seq_notice);
//                            buyTask.execute();
//                            Intent intent = new Intent(QuestionnaireDetailActivity.this, NoticeActivity.class);
//                            intent.putExtra("fragment_num",2);
//                            QuestionnaireDetailActivity.this.startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("취소", null)
                    .create()
                    .show();
        }
    };

}
