package com.joyblock.abuba;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class C2_3_CarteView extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c2_3_carte_view);
        actionbarCustom();

    }

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        TextView title;
        title = (TextView) findViewById(R.id.titleName);
        title.setText("식단표");

        title.setVisibility(View.VISIBLE);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }

        });

        ImageView imageView = (ImageView) findViewById(R.id.backImage);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nd = new AlertDialog.Builder(C2_3_CarteView.this);
                nd.setMessage("작성을 취소하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(C2_3_CarteView.this, C_1_1_Calendar.class);
                                C2_3_CarteView.this.startActivity(intent);

                                finish();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
            }
        });


        ImageView editorImage = (ImageView) findViewById(R.id.editorImage);
        editorImage.setVisibility(View.VISIBLE);
//        editorImage.setImageDrawable(getResources().getDrawable(R.drawable.detail_del_modify));
        editorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nd = new AlertDialog.Builder(C2_3_CarteView.this);
                nd.setMessage("수정하시겠습니까?")
                        .setNegativeButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
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
                Intent intent = new Intent(C2_3_CarteView.this, CommentActivity.class);
                intent.putExtra("activity","C2_3_CarteView");
                C2_3_CarteView.this.startActivity(intent);
            }
        });

        EditText editText = (EditText) findViewById(R.id.editTexttt);
        editText.setVisibility(View.VISIBLE);



    }


}
