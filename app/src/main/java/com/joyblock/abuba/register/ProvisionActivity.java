package com.joyblock.abuba.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.login.LoginActivity;

public class ProvisionActivity extends BaseActivity {

    CheckBox checkBox1, checkBox2;
    Button cancel_button, next_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provision);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("약관동의");
        title.setVisibility(View.VISIBLE);

        Log.d("로그는", String.valueOf(getSupportActionBar().isShowing()));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        checkBox1 = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);

        cancel_button = (Button)findViewById(R.id.provision_cancel_button);
        next_button = (Button)findViewById(R.id.provision_next_button);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProvisionActivity.this);
                builder.setMessage("취소하시겠습니까")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ProvisionActivity.this, LoginActivity.class);
                                ProvisionActivity.this.startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });



        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox1.isChecked() && checkBox2.isChecked()) {
                    Intent intent = new Intent(ProvisionActivity.this, RegisterActivity.class);
                    ProvisionActivity.this.startActivity(intent);
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ProvisionActivity.this);
                    builder1.setMessage("약관에 체크를 하세요")
                            .setPositiveButton("확인",null)
                            .create()
                            .show();
                }

            }
        });


    }
}
