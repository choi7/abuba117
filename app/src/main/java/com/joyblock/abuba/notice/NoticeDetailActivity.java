package com.joyblock.abuba.notice;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CheckPeopleListActivity;
import com.joyblock.abuba.CommentActivity;
import com.joyblock.abuba.CommentListVieaAdapter;
import com.joyblock.abuba.CustomDialogModifyAndDel;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.api_message.R30_SelectReplyList;
import com.joyblock.abuba.util.TimeConverter;
import com.joyblock.abuba.api_message.R14_SelectNoticeOne;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class NoticeDetailActivity extends BaseActivity {

    TextView noticeTitle, noticeBan, noticeName, noticeTime, noticeContent, commentregister;//제목, 반, 작성자, 등록시간, 내용, 덧글등록
    String seq_notice, notice_detail_seq_user, intentPutExtraModifyData, seq_user, seq_kids, flag, seq_kindergarden_class;
    ImageView insertAndDelete, detailImage, backImage, commentPushImage, checkPeopleListImage;
    Activity activity;
    EditText et;
    CustomDialogModifyAndDel mCustomDialog;
    TextDialog mCustomDialogs;
    InputMethodManager imm;
    Integer commentpush;
    boolean cancelAndRegister = true, editTouch = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view1);
        seq_notice = getIntent().getStringExtra("seq_notice");
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

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
        title.setText("공지사항");
        title.setVisibility(View.VISIBLE);
        //에디트 텍스트를 누르면 등록텍스트가 활성화할려고 등록함 현재 gone
        commentregister = (TextView) findViewById(R.id.commentPushText1);


        et = (EditText) findViewById(R.id.editTexttt);


        insertAndDelete = (ImageView) findViewById(R.id.noticeDetailinsertAndDeleteText);
        detailImage = (ImageView) findViewById(R.id.detailImageView);

        backImage = (ImageView) findViewById(R.id.backImage);

//        backImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NoticeDetailActivity.this, NoticeActivity.class);
//                NoticeDetailActivity.this.startActivity(intent);
//                finish();
//            }
//        });

        commentPushImage = (ImageView) findViewById(R.id.commentPushImage);
        commentPushImage.setVisibility(View.VISIBLE);
        commentPushImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeDetailActivity.this, CommentActivity.class);
                intent.putExtra("seq_notice", detail.seq_notice);
                intent.putExtra("flag", "NoticeDetailActivity");
                NoticeDetailActivity.this.startActivity(intent);
            }
        });
        ScrollView scrollView5 = (ScrollView) findViewById(R.id.scrollView5);
        scrollView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sdfsdf", "sdfsdf");
                et.clearFocus();
                et.setCursorVisible(false);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                commentregister.setVisibility(View.GONE);
                commentPushImage.setVisibility(View.VISIBLE);
                editTouch = true;
            }
        });

        ConstraintLayout notice_view_const = (ConstraintLayout) findViewById(R.id.notice_view_const);
        notice_view_const.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Log.d("sdfsdf","sdfsdf");
                et.clearFocus();
                et.setCursorVisible(false);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                commentregister.setVisibility(View.GONE);
                commentPushImage.setVisibility(View.VISIBLE);
                editTouch = true;
                */
            }
        });


        LinearLayout noticeviewlinear = (LinearLayout) findViewById(R.id.noticeviewlinear);
        noticeviewlinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("sdfssss", "sdfsdf");
            }
        });


        et.setVisibility(View.VISIBLE);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("hasFocus", String.valueOf(hasFocus));
                if (v.hasFocus()) {
                    commentregister.setVisibility(View.VISIBLE);
                    commentPushImage.setVisibility(View.GONE);
                    editTouch = true;
                } else {
                    et.clearFocus();
//                    et.setCursorVisible(false);
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    commentregister.setVisibility(View.GONE);
                    commentPushImage.setVisibility(View.VISIBLE);
                }
//
//                if (hasFocus) {
//                    commentregister.setVisibility(View.VISIBLE);
//                    commentPushImage.setVisibility(View.GONE);
//                } else {
//
//                }
                    /*
                    if(editTouch) {
                        et.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        commentregister.setVisibility(View.GONE);
                        commentPushImage.setVisibility(View.VISIBLE);
                        editTouch = false;
                    } else {
                        editTouch = true;
                    }
                    */

//                    et.clearFocus();


            }
        });

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d("back", KeyEvent.KEYCODE_BACK);
                Log.d("keyCode", String.valueOf(keyCode));
                Log.d("keyCode", String.valueOf(event.getKeyCode()));
//                if(event.getAction() == KeyEvent.KEYCODE_BACK){

                /*
                if(keyCode == 4){
                    et.clearFocus();
                    et.setCursorVisible(false);
                    commentregister.setVisibility(View.GONE);
                    commentPushImage.setVisibility(View.VISIBLE);
                }
                */
                Log.d("getkey", String.valueOf(event.getAction()));
                return false;
            }
        });

        /*
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(editTouch) {
                    commentregister.setVisibility(View.VISIBLE);
                    commentPushImage.setVisibility(View.GONE);
                    editTouch = false;
                } else {
                    commentregister.setVisibility(View.GONE);
                    commentPushImage.setVisibility(View.VISIBLE);
                    editTouch = true;
                }
                return false;
            }
        });
        */

        /*
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTouch) {
                    commentregister.setVisibility(View.VISIBLE);
                    commentPushImage.setVisibility(View.GONE);
                    editTouch = false;
                } else {
                    commentregister.setVisibility(View.GONE);
                    commentPushImage.setVisibility(View.VISIBLE);
                    editTouch = true;
                }
            }
        });
        */

        commentregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "/" + et.getText() + "/", Toast.LENGTH_LONG).show();
                    String seq_user = pref.getString("seq_user", "");
                    String seq_kindergarden = pref.getString("seq_kindergarden", "");
                    seq_kids = pref.getString("seq_kids", "");
//                    Log.d("insertReply",seq_user +""+seq_kindergarden +""+ seq_kids+""+ seq_notice+""+ flag+""+ et.getText().toString());
//                    Log.d("insertReply-seq_kids",seq_kids);
                    Log.d("insertReply-seq_notice", seq_notice);
                    flag = "n";

                    api.API_28(seq_user, seq_kindergarden, "", seq_notice, flag, et.getText().toString());

                    String json = api.getMessage();
                    Integer resultcode = null;
                    try {
                        JSONObject jsonResponse = new JSONObject(json);
                        resultcode = Integer.parseInt(jsonResponse.getString("resultCode"));
                        Log.d("resultcode", String.valueOf(resultcode));
                        switch (resultcode) {
                            case 200:
                                commentpush = 1;
                                mCustomDialogs = new TextDialog(NoticeDetailActivity.this, R.layout.dialog_one_check);
                                mCustomDialogs.setTexts(new String[]{"등록되었습니다.", "확인"});
                                mCustomDialogs.show();

                        }
                    } catch (JSONException e) {
                    }


//                    Reply insertReply = new Reply(seq_user, seq_kindergarden, seq_notice, flag, et.getText().toString());
//                    insertReply.execute();
                    et.clearFocus();
                    et.setText(null);
//                commentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
//                    titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                    String page = "1";
                } else {
                    commentpush = 2;
                    mCustomDialogs = new TextDialog(NoticeDetailActivity.this, R.layout.dialog_one_check);
                    mCustomDialogs.setTexts(new String[]{"내용을 입력해주세요.", "확인"});
                    mCustomDialogs.show();
                }
            }
        });

//        checkPeopleListImage = (ImageView) findViewById(R.id.checkPushImageView);
//        checkPeopleListImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NoticeDetailActivity.this, CheckPeopleListActivity.class);
//                NoticeDetailActivity.this.startActivity(intent);
//            }
//        });


        noticeTitle = (TextView) findViewById(R.id.noticeDetailTitleText);
        noticeBan = (TextView) findViewById(R.id.noticeDetailBanText);
        noticeName = (TextView) findViewById(R.id.noticeDetailNameText);
        noticeTime = (TextView) findViewById(R.id.noticeDetailTimeText);
        noticeContent = (TextView) findViewById(R.id.inTextView);


        String seq_notice = getIntent().getStringExtra("seq_notice");

//        setNotice(position,position,null,position,position,position,false);


        new SelectNoticeOne(seq_notice).execute();

//        setNotice(detail.seq_kindergarden_class,detail.title,getResources().getDrawable(R.mipmap.ic_document),detail.name, TimeConverter.convert(detail.reg_date),detail.content,detail.equals("y"));


        seq_user = pref.getString("seq_user", "");
        Log.d("유저 시퀀스 ", seq_user);


        insertAndDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCustomDialog = new CustomDialogModifyAndDel(NoticeDetailActivity.this,
                        modifyListener,
                        delListener);
                mCustomDialog.show();
            }
        });


    }

    public void clickModDel(View v) {
        mCustomDialog = new CustomDialogModifyAndDel(NoticeDetailActivity.this,
                modifyListener,
                delListener);
        mCustomDialog.show();
    }

    public void setNotice(String ban, String title, String name, String time, String content, boolean availabeReply) {
        noticeBan.setText(app.kindergarden_class_list.get(Integer.parseInt(seq_kindergarden_class)).kindergarden_class_name);
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
//        Intent intent = new Intent(NoticeDetailActivity.this, NoticeActivity.class);
//        NoticeDetailActivity.this.startActivity(intent);
        Log.d("editTouch", String.valueOf(editTouch));
        if (editTouch) {
            et.clearFocus();
            et.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            commentregister.setVisibility(View.GONE);
            commentPushImage.setVisibility(View.VISIBLE);
        } else {
            finish();
        }

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

    R14_SelectNoticeOne detail;

    class SelectNoticeOne extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/selectNoticeOne.do";

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
            Log.d("response : ", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                System.out.println("반환되는 값 : " + jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));

                detail = new GsonBuilder().create().fromJson(jsonResponse.getString("notice"), R14_SelectNoticeOne.class);
                Log.d("detail", String.valueOf(detail));
                notice_detail_seq_user = detail.seq_user;
                seq_kindergarden_class = detail.seq_kindergarden_class;
                intentPutExtraModifyData = jsonResponse.getString("notice");

                Log.d("유저 시퀀스 ", notice_detail_seq_user);
                Log.d("유저 시퀀스1 ", seq_user);


                if (seq_user.equals(notice_detail_seq_user)) {
                    insertAndDelete.setVisibility(View.VISIBLE);
                    Log.d("sd ", "ss");
                } else {
                    insertAndDelete.setVisibility(View.INVISIBLE);
                    Log.d("sd ", "ee");
                }
                try {
                    Picasso.with(getApplicationContext()).load(detail.file_path).into(detailImage);
                    detailImage.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }
                setNotice(detail.seq_kindergarden_class, detail.title, detail.name, TimeConverter.convert(detail.reg_date), detail.content, detail.equals("y"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    //수정버튼
    private View.OnClickListener modifyListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
            Intent intent = new Intent(NoticeDetailActivity.this, NoticeEditorModifyActivity.class);
            intent.putExtra("ModifyData", intentPutExtraModifyData);
            NoticeDetailActivity.this.startActivity(intent);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
        }
    };

    //삭제버튼
    private View.OnClickListener delListener = new View.OnClickListener() {
        public void onClick(View v) {
            commentpush = 3;
            mCustomDialogs = new TextDialog(NoticeDetailActivity.this, R.layout.dialog_call);
            mCustomDialogs.setTexts(new String[]{"정말로 삭제하시겠습니까?", "취소", "확인"});
            mCustomDialogs.show();



/*
            mCustomDialog.dismiss();
            AlertDialog.Builder nd = new AlertDialog.Builder(NoticeDetailActivity.this);
            nd.setMessage("정말로 삭제하시겠습니까")
                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NoticeDetailActivity.DeleteNotice buyTask = new NoticeDetailActivity.DeleteNotice(seq_notice);
                            buyTask.execute();
//                            Intent intent = new Intent(NoticeDetailActivity.this, NoticeActivity.class);
//                            intent.putExtra("fragment_num",1);
//                            NoticeDetailActivity.this.startActivity(intent);
                            finish();
                        }
                    })
                    .setPositiveButton("취소", null)
                    .create()
                    .show();
                    */
        }


    };


    public void clickTextView2(View v) {
        mCustomDialogs.dismiss();
    }

    public void clickTextView3(View v) {
        mCustomDialogs.dismiss();
        mCustomDialog.dismiss();


        switch (commentpush) {
            case 1:
                Intent intent = new Intent(NoticeDetailActivity.this, CommentActivity.class);
                intent.putExtra("seq_notice", detail.seq_notice);
                intent.putExtra("flag", "NoticeDetailActivity");
                NoticeDetailActivity.this.startActivity(intent);
                break;
            case 2:
                break;
            //게시글 삭제
            case 3:
                api.API_12(seq_notice);
                String json = api.getMessage();
                Integer resultcode = null;
                try {
                    JSONObject jsonResponse = new JSONObject(json);
                    resultcode = Integer.parseInt(jsonResponse.getString("resultCode"));
                    Log.d("resultcode", String.valueOf(resultcode));

                } catch (Exception e) {

                }
                break;
            default:
                break;
        }


//        NoticeDetailActivity.DeleteNotice buyTask = new NoticeDetailActivity.DeleteNotice(seq_notice);
//        buyTask.execute();
        finish();
    }

    public void clickTextViewOne(View v) {
        mCustomDialogs.dismiss();
    }


    class Reply extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String message_key = "reply_list";
        int type;

        //덧글 등록
        public Reply(String seq_user, String seq_kindergarden, String seq, String flag, String content) {
            client = new OkHttpClient();

            if (!(seq_kids.equals(""))) {
                formBody = new FormBody.Builder()
                        .add("seq_user", seq_user)
                        .add("seq_kindergarden", seq_kindergarden)
                        .add("seq", seq)
                        .add("flag", flag)
                        .add("content", content)
                        .build();
            } else {
                formBody = new FormBody.Builder()
                        .add("seq_user", seq_user)
                        .add("seq_kindergarden", seq_kindergarden)
                        .add("seq_kids", seq_kids)
                        .add("seq", seq)
                        .add("flag", flag)
                        .add("content", content)
                        .build();
            }

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/insertReply.do")
                    .post(formBody)
                    .build();
            type = 0;
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
                if (ss == 200) {
                    Log.d("타입", String.valueOf(type));
                    //댓글 등록
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeDetailActivity.this);
                    builder.setMessage("등록되었습니다").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(NoticeDetailActivity.this, CommentActivity.class);
                            intent.putExtra("seq_notice", detail.seq_notice);
                            intent.putExtra("flag", "NoticeDetailActivity");
                            NoticeDetailActivity.this.startActivity(intent);
                        }
                    }).show();
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}


  /*
    class DeleteNotice extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/deleteNotice.do";

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
            Log.d("response : ", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
*/