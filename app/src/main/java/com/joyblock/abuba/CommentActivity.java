package com.joyblock.abuba;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
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

    TextView commentPushText, titleCount;
    ListView allCommentListView;
    EditText commentEditText;
    Context context;
    CommentListVieaAdapter commentListVieaAdapter;
    InputMethodManager imm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentPushText = (TextView) findViewById(R.id.commentPushText);
        allCommentListView = (ListView) findViewById(R.id.allCommentListView);
        commentEditText = (EditText) findViewById(R.id.commentEditText);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

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

        titleCount = (TextView) findViewById(R.id.titleCountText);
        titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));

        commentPushText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"코멘트버튼",Toast.LENGTH_LONG).show();
                commentListVieaAdapter.addItem(commentEditText.getText().toString(),"시간이다","어부바원장님");
                commentListVieaAdapter.notifyDataSetChanged();
                commentEditText.clearFocus();
                commentEditText.setText(null);
                commentEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
                titleCount.setText(String.valueOf(commentListVieaAdapter.getCount()));
            }
        });

        allCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imm.hideSoftInputFromWindow(commentEditText.getWindowToken(), 0);
            }
        });

    }

}




