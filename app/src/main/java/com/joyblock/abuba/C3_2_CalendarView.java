package com.joyblock.abuba;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by BLUE on 2018-01-28.
 */

public class C3_2_CalendarView extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_3_2_calendar_view);
        actionbarCustom();
    }

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        TextView title;
        title = (TextView) findViewById(R.id.titleName);
        title.setText("일정관리");

        title.setVisibility(View.VISIBLE);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }

        });



        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FF9900"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        ImageView commentPushImage = (ImageView) findViewById(R.id.commentPushImage);
        commentPushImage.setVisibility(View.VISIBLE);
        commentPushImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C3_2_CalendarView.this, CommentActivity.class);
                intent.putExtra("activity","C3_2_CalendarView");
                C3_2_CalendarView.this.startActivity(intent);
            }
        });

        EditText editText = (EditText) findViewById(R.id.editTexttt);
        editText.setVisibility(View.VISIBLE);



    }

}
