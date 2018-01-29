package com.joyblock.abuba.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.login.LoginActivity;

public class ProvisionActivity extends BaseActivity {

    CheckBox checkBox1, checkBox2;
    Button cancel_button, next_button;
    ConstraintLayout constraintLayout1, constraintLayout2;
    ImageView imageView1, imageView2;
    Boolean check1 = false, check2 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_provision);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("약관동의");
        title.setVisibility(View.VISIBLE);

        constraintLayout1 = (ConstraintLayout) findViewById(R.id.provision_constraintLayout1);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.provision_constraintLayout2);
        imageView1 = (ImageView) findViewById(R.id.provison_check_image1);
        imageView2 = (ImageView) findViewById(R.id.provison_check_image2);

        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1) {
                    imageView1.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                    check1 = false;
                } else {
                    imageView1.setImageDrawable(getResources().getDrawable(R.drawable.check_on));
                    check1 = true;

                }
            }
        });
        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check2) {
                    imageView2.setImageDrawable(getResources().getDrawable(R.drawable.check_off));
                    check2 = false;
                } else {
                    imageView2.setImageDrawable(getResources().getDrawable(R.drawable.check_on));
                    check2 = true;

                }
            }
        });

        Log.d("로그는", String.valueOf(getSupportActionBar().isShowing()));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        cancel_button = (Button) findViewById(R.id.provision_cancel_button);
        next_button = (Button) findViewById(R.id.provision_next_button);
    }

    boolean agree = true;
    TextDialog mCustomDialog;

    public void provision_ClickLeft(View v) {
        agree = false;
        mCustomDialog = new TextDialog(ProvisionActivity.this, R.layout.dialog_call);
        mCustomDialog.setTexts(new String[]{"돌아가시겠습니까?", "취소", "확인"});
        mCustomDialog.show();
        mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#595757"));
        mCustomDialog.findViewById(R.id.textview3).setBackgroundColor(Color.parseColor("#E73828"));

    }

    public void provision_ClickRight(View v) {
        agree = true;
        if (check1 && check2) {
            Intent intent = new Intent(ProvisionActivity.this, RegisterActivity.class);
            ProvisionActivity.this.startActivity(intent);
        } else {
            mCustomDialog = new TextDialog(ProvisionActivity.this, R.layout.dialog_one_check);
            mCustomDialog.setTexts(new String[]{"모든 약관에 체크하세요", "확인"});
            mCustomDialog.show();
            mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));

        }


    }


    public void clickTextView2(View v) {
        mCustomDialog.dismiss();

    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        finish();
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


}
