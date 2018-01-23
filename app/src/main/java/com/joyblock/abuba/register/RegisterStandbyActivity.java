package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.login.LoginActivity;

public class RegisterStandbyActivity extends BaseActivity {

    TextView textView, textView1;
    String garden_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_standby);

        //커스텀 액션바 셋팅
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        //액션바에 있는 메인이미지 보이기
        ImageView imageView = (ImageView) findViewById(R.id.titleImageView);
        imageView.setVisibility(View.VISIBLE);
        //화면에 전 액티비티에서 선택한 유치원 이름 띄우기
        textView = (TextView) findViewById(R.id.textView59);
        final Intent intent = getIntent();
        garden_name = intent.getStringExtra("garden_name");
        textView.setText(garden_name);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        textView1 = (TextView) findViewById(R.id.textView74);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 =  new Intent(RegisterStandbyActivity.this, LoginActivity.class);
                RegisterStandbyActivity.this.startActivity(intent1);
                finishAffinity();
            }
        });


    }
}
