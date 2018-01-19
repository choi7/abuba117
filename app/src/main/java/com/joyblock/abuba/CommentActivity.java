package com.joyblock.abuba;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.notice.NoticeListViewAdapter;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentActivity extends BaseActivity implements Serializable {

    TextView commentPushText;
    ListView allCommentListView;
    EditText commentEditText;
    Context context;
    CommentListVieaAdapter commentListVieaAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentPushText = (TextView) findViewById(R.id.commentPushText);
        allCommentListView = (ListView) findViewById(R.id.allCommentListView);
        commentEditText = (EditText) findViewById(R.id.commentEditText);


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
        title.setText("댓글");
        title.setVisibility(View.VISIBLE);

        commentListVieaAdapter = new CommentListVieaAdapter(getApplicationContext());

        allCommentListView.setAdapter(commentListVieaAdapter);
        commentListVieaAdapter.addItem("테스트요","시간이다","어부바원장님");
        commentListVieaAdapter.addItem("테스트요\n\n","시간이다","어부바원장님");
        commentListVieaAdapter.addItem("테스트요\n\n\n\n","시간이다","어부바원장님");

        commentPushText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"코멘트버튼",Toast.LENGTH_LONG).show();
                commentListVieaAdapter.addItem(commentEditText.getText().toString(),"시간이다","어부바원장님");
                commentListVieaAdapter.notifyDataSetChanged();
                commentEditText.clearFocus();
                commentEditText.setText(null);
                commentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
        });

    }






}




