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
import com.joyblock.abuba.TextDialog;

public class A3_4_MedicineCheck extends BaseActivity {
    TextDialog mCustomDialog;
    int pushAndcancel;

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
        addBacklistner();
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
                pushAndcancel = 1;
                mCustomDialog = new TextDialog(A3_4_MedicineCheck.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"보류하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();

            }
        });

        a3_4_trueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushAndcancel = 2;
                mCustomDialog = new TextDialog(A3_4_MedicineCheck.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"전송하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();
            }
        });

    }


    public void clickTextView2(View v) {
        mCustomDialog.dismiss();

    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        switch (pushAndcancel) {
            //보류시
            case 1:
                break;
            //삭제시
            case 2:
                break;
            default:
                break;
        }
        //임의로 설정한 종료
        finish();
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


}
