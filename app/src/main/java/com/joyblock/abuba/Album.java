package com.joyblock.abuba;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.notice.NoticeEditorActivity;

public class Album extends BaseActivity {

    ImageView editorImageView;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_album);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff66ccff));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("사진앨범");
        title.setVisibility(View.VISIBLE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editorImageView = (ImageView) findViewById(R.id.editorImage);
        editorImageView.setVisibility(View.VISIBLE);
        editorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Album.this, NoticeEditorActivity.class);
                Album.this.startActivity(intent);
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#66CCFF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


    }
}
