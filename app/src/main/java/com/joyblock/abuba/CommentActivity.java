package com.joyblock.abuba;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class CommentActivity extends BaseActivity {

    TextView commentPushText;
    ListView allCommentListView;
    EditText commentEditText;

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








    }
}
