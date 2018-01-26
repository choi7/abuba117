package com.joyblock.abuba.document;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.util.ImageFileProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hyoshinchoi on 2018. 1. 22..
 */

public class A4_2_HomeCommingEditorView_Parent extends BaseActivity {

    ConstraintLayout constraintLayout1, constraintLayout2, constraintLayout3;
    ImageView checkImage1, checkImage2, checkImage3, medicinePhotoImageView;
    Integer checkInt;
    TextView pushText, timeText, medicineViewPushText;
    String getTime, seq_user_parent, seq_kindergarden, seq_kindergarden_class, seq_kids, home_method = "자가",
            home_reason, home_time, companion, tel_no, uniqueness, getyear, getday, getmonth, files, kindergarden_name, kids_name, kindergarden_class_name;
    EditText symptomEditText, medicineTypeEditText, medicationCapacityEditText,
            medicationTimeEditText, specialNoteEditText;

    ImageFileProcessor imageFileProcessor = new ImageFileProcessor();
    byte[] bytes;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
//                imageFileProcessor.sendPicture(data.getData(), medicinePhotoImageView, A3_3_MedicineEditorView.this);
                Object[] objects = imageFileProcessor.getDrawableAndByteArray(data.getData(), A4_2_HomeCommingEditorView_Parent.this);
                bytes = (byte[]) objects[1];
                Drawable drawable = (Drawable) objects[0];
                medicinePhotoImageView.setImageDrawable(drawable);

//                byte[] byteArray = imageFileProcessor.byteArray;
            }


        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a4_2_homecomming_editor_view_parent);



        Timeget();
        findviewbyid();
        checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));


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


        seq_kindergarden_class = "1";
        seq_user_parent = pref.getString("seq_user", "");
        kindergarden_class_name = pref.getString("kindergarden_class_name", "");
        seq_kindergarden = pref.getString("seq_kindergarden", "");
        kindergarden_name = pref.getString("kindergarden_name", "");
        pref.getString("name_title", "");
        String name = pref.getString("name", "");
        kids_name = pref.getString("kids_name", "");
        seq_kids = pref.getString("seq_kids", "");
        timeText.setText(getTime + " " + name);

        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText(kids_name + "(" + kindergarden_class_name + ")");
        title.setVisibility(View.VISIBLE);

        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage3.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkInt = 0;
                home_method = "자가";
            }
        });

        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkImage3.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkInt = 1;
                home_method = "도보";
            }
        });

        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage3.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkInt = 2;
                home_method = "통학버스";
            }
        });


        medicinePhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFileProcessor.selectGallery(A4_2_HomeCommingEditorView_Parent.this, 100);
            }
        });

        medicineViewPushText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nd = new AlertDialog.Builder(A4_2_HomeCommingEditorView_Parent.this);
                nd.setMessage("등록하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                home_reason = symptomEditText.getText().toString();
                                home_time = medicineTypeEditText.getText().toString();
                                tel_no = medicationCapacityEditText.getText().toString();
                                companion = medicationTimeEditText.getText().toString();
//                                keep_method = symptomEditText.getText().toString();
                                uniqueness = specialNoteEditText.getText().toString();
//                                year month day files
                                files = "";

                                Intent intent = new Intent(A4_2_HomeCommingEditorView_Parent.this, A_4_3_HomeComming_Sign_Parent.class);
                                intent.putExtra("seq_user_parent", seq_user_parent);
                                intent.putExtra("seq_kindergarden", seq_kindergarden);
                                intent.putExtra("seq_kindergarden_class", seq_kindergarden_class);
                                intent.putExtra("seq_kids", seq_kids);
                                intent.putExtra("home_reason", home_reason);
                                intent.putExtra("home_time", home_time);
                                intent.putExtra("home_method", home_method);
                                intent.putExtra("companion", companion);
                                intent.putExtra("tel_no", tel_no);
                                intent.putExtra("uniqueness", uniqueness);
                                intent.putExtra("getyear", getyear);
                                intent.putExtra("getmonth", getmonth);
                                intent.putExtra("getday", getday);
                                intent.putExtra("bytes", bytes);

                                A4_2_HomeCommingEditorView_Parent.this.startActivity(intent);
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
            }
        });

    }


    public void findviewbyid() {

        constraintLayout1 = (ConstraintLayout) findViewById(R.id.a_3_3Checktemp1ConstraintLayout);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.a_3_3Checktemp2ConstraintLayout);
        constraintLayout3 = (ConstraintLayout) findViewById(R.id.a_3_3Checktemp3ConstraintLayout);

        checkImage1 = (ImageView) findViewById(R.id.a3_3_temp1ImageView);
        checkImage2 = (ImageView) findViewById(R.id.a3_3_temp2ImageView);
        checkImage3 = (ImageView) findViewById(R.id.a3_3_temp3ImageView);
        medicinePhotoImageView = (ImageView) findViewById(R.id.medicinePhotoImageView);

        pushText = (TextView) findViewById(R.id.textview_ok);
        timeText = (TextView) findViewById(R.id.a3_2_timeText);
//        medicineViewPushText = (TextView) findViewById(R.id.medicine);

        symptomEditText = (EditText) findViewById(R.id.symptomEditText);
        medicineTypeEditText = (EditText) findViewById(R.id.medicineTypeEditText);
        medicationCapacityEditText = (EditText) findViewById(R.id.medicationCapacityEditText);
        medicationTimeEditText = (EditText) findViewById(R.id.medicationTimeEditText);
        specialNoteEditText = (EditText) findViewById(R.id.specialNoteEditText);

    }

    public void Timeget() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        getTime = sdf.format(date);
        getday = day.format(date);
        getmonth = month.format(date);
        getyear = year.format(date);
        Log.d("시간은", getday + "" + getmonth + "" + getyear);


    }



}
