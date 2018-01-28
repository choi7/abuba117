package com.joyblock.abuba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.api_message.R28_InsertReply;
import com.joyblock.abuba.api_message.R29_DeleteReply;
import com.joyblock.abuba.api_message.R30_SelectReplyList;
import com.joyblock.abuba.util.TimeConverter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class CommentActivity extends BaseActivity implements Serializable {

    TextView commentPushText, titleCount;
    ListView allCommentListView;
    EditText commentEditText;
    Context context;
    CommentListVieaAdapter commentListVieaAdapter;
    InputMethodManager imm;
    String seq_notice, flag, page = "1";
    Intent intent;

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        intent = getIntent();
        Customactionbar();




        commentPushText = (TextView) findViewById(R.id.commentPushText);
        allCommentListView = (ListView) findViewById(R.id.allCommentListView);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);




        commentListVieaAdapter = new CommentListVieaAdapter(getApplicationContext());

        allCommentListView.setAdapter(commentListVieaAdapter);
//        commentListVieaAdapter.addItem("테스트요", "시간이다", "어부바원장님");
//        commentListVieaAdapter.addItem("테스트요\n\n", "시간이다", "어부바원장님");
//        commentListVieaAdapter.addItem("테스트요\n\n\n\n", "시간이다", "어부바원장님");

        titleCount = (TextView) findViewById(R.id.titleCountText);
        titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));


        commentPushText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!commentEditText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "/" + commentEditText.getText() + "/", Toast.LENGTH_LONG).show();
                    String seq_user = pref.getString("seq_user", "");
                    String seq_kindergarden = pref.getString("seq_kindergarden", "");
                    String seq_kids = pref.getString("seq_kids", "");
                    Log.d("seq_notice", seq_notice);
                    Reply insertReply = new Reply(seq_user, seq_kindergarden, seq_kids, seq_notice, flag, commentEditText.getText().toString());
                    insertReply.execute();
                    commentEditText.clearFocus();
                    commentEditText.setText(null);
//                commentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
//                    titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                    commentListVieaAdapter.notifyDataSetChanged();
                    allCommentListView.deferNotifyDataSetChanged();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                    builder.setMessage("내용을 입력해주세요").setNegativeButton("확인", null).show();
                }

            }
        });

        allCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
            }
        });


    }

    public void Customactionbar() {

        String seq_activity = intent.getStringExtra("activity");
        switch (seq_activity) {
            case "C2_3_CarteView":
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.parseColor("#FF9900"));
                }
                break;
            case "C3_2_CalendarView":
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.parseColor("#FF9900"));
                }
                break;
            default:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
                }
                seq_notice = intent.getStringExtra("seq_notice");
                flag = intent.getStringExtra("flag");
                Reply insertReply = new Reply(seq_notice, flag, page);
                insertReply.execute();
                break;
        }



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("댓글");
        title.setVisibility(View.VISIBLE);

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    R28_InsertReply insertReply;
    R29_DeleteReply deleteReply;
    R30_SelectReplyList[] selectReplyList;

    class Reply extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String message_key = "reply_list";
        int type;

        //덧글 등록
        public Reply(String seq_user, String seq_kindergarden, String seq_kids, String seq, String flag, String content) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_user", seq_user)
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_kids", seq_kids)
                    .add("seq", seq)
                    .add("flag", flag)
                    .add("content", content)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/insertReply.do")
                    .post(formBody)
                    .build();
            type = 0;
        }

        //덧글 조회
        public Reply(String seq, String flag, String page) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq", seq)
                    .add("flag", flag)
                    .add("page", page)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/selectReplyList.do")
                    .post(formBody)
                    .build();

            type = 1;
        }

        //덧글 삭제
        public Reply(String seq_reply) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_reply", seq_reply)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/deleteReply.do")
                    .post(formBody)
                    .build();

            type = 2;
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
                    switch (type) {
                        case 0: //댓글 등록
                            AlertDialog.Builder builder = new AlertDialog.Builder(CommentActivity.this);
                            builder.setMessage("등록되었습니다").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                                    commentListVieaAdapter.notifyDataSetChanged();
                                    allCommentListView.deferNotifyDataSetChanged();
                                }
                            }).show();
                            break;
                        case 1: //댓글 조회
//                            selectReplyList = new GsonBuilder().create().fromJson(jsonResponse.getString(message_key), R30_SelectReplyList.class);
                            selectReplyList = new GsonBuilder().create().fromJson(jsonResponse.getString(message_key), R30_SelectReplyList[].class);
                            Log.d("detail114", String.valueOf(selectReplyList));
                            for (int i = 0; i < selectReplyList.length; i++) {
                                R30_SelectReplyList list = selectReplyList[i];
//                                commentListVieaAdapter.addItem(list.file_path, list.title, TimeConverter.convert(list.reg_date), list.name);
                                commentListVieaAdapter.addItem(list.content, TimeConverter.convert(list.reg_date), list.name);
                            }
                            titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                            commentListVieaAdapter.notifyDataSetChanged();
                            allCommentListView.deferNotifyDataSetChanged();
                            break;
                        case 2: //댓글 삭제
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(CommentActivity.this);
                            builder1.setMessage("삭제되었습니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                                    commentListVieaAdapter.notifyDataSetChanged();
                                    allCommentListView.deferNotifyDataSetChanged();
                                }
                            }).show();
                            break;
                    }
                    allCommentListView.deferNotifyDataSetChanged();

//                    notice_detail_seq_user = selectReplyList.seq_user;
//                    intentPutExtraModifyData = jsonResponse.getString(message_key);

//                    Picasso.with(getApplicationContext()).load(selectReplyList.user_image).into(detailImage);
//                    detailImage.setVisibility(View.VISIBLE);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}




