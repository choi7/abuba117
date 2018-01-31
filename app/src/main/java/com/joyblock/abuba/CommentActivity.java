package com.joyblock.abuba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
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
import java.util.ArrayList;

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
    String seq_notice, flag, page = "1", seq_kids, del_seq_user, del_seq_reply, seq_user, seq_kindergarden;
    Intent intent;
    ArrayList<String> getseq_user = new ArrayList<String>();
    ArrayList<String> getseq_reply = new ArrayList<String>();
    TextDialog mCustomDialog;

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
        addBacklistner();

        commentPushText = (TextView) findViewById(R.id.commentPushText);
        allCommentListView = (ListView) findViewById(R.id.allCommentListView1);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        seq_user = pref.getString("seq_user", "");
        seq_kindergarden = pref.getString("seq_kindergarden", "");
        seq_kids = pref.getString("seq_kids", "");

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

                    Log.d("insertReply",seq_user +""+seq_kindergarden +""+ seq_kids+""+ seq_notice+""+ flag+""+ commentEditText.getText().toString());
                    Log.d("insertReply-seq_kids",seq_kids);
                    Log.d("insertReply-seq_notice",seq_notice);
                    Reply insertReply = new Reply(seq_user, seq_kindergarden, seq_notice, flag, commentEditText.getText().toString());
                    insertReply.execute();
                    commentEditText.clearFocus();
                    commentEditText.setText(null);
//                commentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
//                    titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));

                    new Reply(seq_notice, flag, page).execute();
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
        if(seq_activity == null) {
            seq_activity = "";
        }
        seq_notice = intent.getStringExtra("seq_notice");
        flag = intent.getStringExtra("flag");
        if(flag != null) {
            seq_activity = flag;
        }
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
            case "n":
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff66ccff));
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.parseColor("#66CCFF"));
                }
                break;
            case "NoticeDetailActivity":
                flag = "n";
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
                }
                break;
            default:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
                }


                break;
        }
        Reply insertReply = new Reply(seq_notice, flag, page);
        insertReply.execute();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("댓글");
        title.setVisibility(View.VISIBLE);
    }


    R28_InsertReply insertReply;
    R29_DeleteReply deleteReply;
    R30_SelectReplyList[] selectReplyList;

    int pos;
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
                            selectReplyList = new GsonBuilder().create().fromJson(jsonResponse.getString(message_key), R30_SelectReplyList[].class);
                            commentListVieaAdapter = new CommentListVieaAdapter(getApplicationContext());
                            Log.d("detail114", String.valueOf(selectReplyList));
                            for (int i = 0; i < selectReplyList.length; i++) {
                                R30_SelectReplyList list = selectReplyList[i];
                                commentListVieaAdapter.addItem(list.content, TimeConverter.convert(list.reg_date), list.name, list.seq_user, list.seq_reply);

                                getseq_user.add(String.valueOf(list.seq_user));
                                getseq_reply.add(String.valueOf(list.seq_reply));
                            }

                            allCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    pos=position;
                                    Log.d("p234", String.valueOf(position));
                                    Log.d("prpr11", String.valueOf(position));
                                    Log.d("prpr11", String.valueOf(id));
                                    Log.d("prpr12", String.valueOf(view));
                                    del_seq_user = getseq_user.get(position);
                                    del_seq_reply = getseq_reply.get(position);
                                }
                            });

//                            Log.d("seq_userss", String.valueOf(getseq_user));
//                            Log.d("seq_replyss", String.valueOf(getseq_reply));
                            allCommentListView.setAdapter(commentListVieaAdapter);

                            titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                            commentListVieaAdapter.notifyDataSetChanged();
                            allCommentListView.deferNotifyDataSetChanged();
                            break;
                        case 2: //댓글 삭제
                            titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
                            commentListVieaAdapter.notifyDataSetChanged();
                            allCommentListView.deferNotifyDataSetChanged();
//                            allCommentListView.setAdapter(commentListVieaAdapter);
                            mCustomDialog = new TextDialog(CommentActivity.this, R.layout.dialog_one_check);
                            mCustomDialog.setTexts(new String[]{"삭제되었습니다.", "확인"});
                            mCustomDialog.show();

                            /*
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(CommentActivity.this);
                            builder1.setMessage("삭제되었습니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    new Reply(seq_user, seq_kindergarden, seq_notice, flag, commentEditText.getText().toString()).execute();
//                                    titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
//                                    commentListVieaAdapter.notifyDataSetChanged();
//                                    allCommentListView.deferNotifyDataSetChanged();
//                                    allCommentListView.setAdapter(commentListVieaAdapter);
                                }
                            }).show();
                            */
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

    public void comment_delete(View v) {

        mCustomDialog = new TextDialog(CommentActivity.this, R.layout.dialog_call);
        mCustomDialog.setTexts(new String[]{"정말로 삭제하시겠습니까?", "취소", "확인"});
        mCustomDialog.show();
        String seq_user = pref.getString("seq_user", "");
        /*
        Log.d("comment_delete", seq_user +" " + del_seq_user + " " +del_seq_reply);
        if(seq_user.equals(del_seq_user)) {
            Log.d("comment_delete", seq_user +" " + del_seq_user + " " +del_seq_reply);
            new Reply(del_seq_reply).execute();
        }
        */
//        Reply(R30_SelectReplyList.class.)
    }

    public void clickTextView2(View v) {
        mCustomDialog.dismiss();
    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        del_seq_user = selectReplyList[pos].seq_user;

//        new Reply(del_seq_reply).execute();
    }


    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();



    }



}




