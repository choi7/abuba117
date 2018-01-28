package com.joyblock.abuba.document;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

public class A3_4_MedicineCheck extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_4_medicine_check);
        String user_name = pref.getString("name", "");
        String authority = pref.getString("authority", "");
        Intent intent = getIntent();
        String kid_name = intent.getStringExtra("kid_name");
        String time = intent.getStringExtra("time");
        TextView textView125 = (TextView) findViewById(R.id.textView125);
        TextView a3_4_falseText = (TextView) findViewById(R.id.a3_4_falseText);
        TextView a3_4_trueText = (TextView) findViewById(R.id.a3_4_trueText);
        EditText a3_4_EditText = (EditText) findViewById(R.id.a3_4_EditText);

        switch (authority) {
            case "ROLE_TEACHER":
                textView125.setText(time + "교사 " + user_name);
                break;
            case "ROLE_DIRECTOR_TEACHER":
                textView125.setText(time + "원장 " + user_name);
                break;
            default:
                break;

        }


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        TextView titles = (TextView) findViewById(R.id.titleName);

        titles.setVisibility(View.VISIBLE);
        titles.setText(kid_name);


        a3_4_falseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(A3_4_MedicineCheck.this);
                builder.setMessage("보류하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("취소", null)
                        .create().show();
            }
        });

        a3_4_trueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(A3_4_MedicineCheck.this);
                builder.setMessage("전송하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setNegativeButton("취소", null)
                        .create().show();
            }
        });

    }
}
