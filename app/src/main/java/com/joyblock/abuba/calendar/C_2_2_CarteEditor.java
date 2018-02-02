package com.joyblock.abuba.calendar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_2_2_CarteEditor extends BaseActivity {

    String checkid = "y", is_reply = "y", seq_user, seq_kindergarden, change_year, change_month, change_day, type ="", rice="", soup="", side_dish_1="", side_dish_2="", side_dish_3="", side_dish_4="", side_dish_5="", snack="", memo="";
    Boolean imageChange = true;
    byte[] files;
    TextDialog mCustomDialog;
    int push, years, months, days;
    EditText typeE, riceE, soupE, side_dish_1E, side_dish_2E, side_dish_3E, side_dish_4E, side_dish_5E, snackE, memoE;
    TextView mainDate, title, replyTextview;
    ImageView replyImageview;
    ConstraintLayout constraintLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_2_2_carte_editor);
        seq_user = app.seq_user;
        seq_kindergarden = pref.getString("seq_kindergarden","");
        findViewbyid();
        actionbarCustom();

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                Date date;
                DatePickerDialog datePickerDialog = new DatePickerDialog(C_2_2_CarteEditor.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            years = year;
                            months = monthOfYear + 1;
                            days = dayOfMonth;
//                            userBirtyday = year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일";
//                            birthdayText.setText(userBirtyday);
//                            birthdays = year + "" + (monthOfYear + 1) + "" + dayOfMonth;
//                            Log.d("d", userBirtyday);
                            if(months < 10) {
                                change_month = "0" + months;
                            } else {
                                change_month = String.valueOf(months);
                            }

                            if(days < 10) {
                                change_day = "0" + days;
                            } else {
                                change_day = String.valueOf(days);
                            }

                            mainDate.setText(years+". " + change_month +". "+ change_day);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datePickerDialog.show();

            }
        });

    }

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        title = (TextView) findViewById(R.id.titleName);
        title.setText("식단표");
        title.setVisibility(View.VISIBLE);

        TextView backText = (TextView) findViewById(R.id.editorTextLeft);
        backText.setVisibility(View.VISIBLE);
        backText.setText("취소");
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                push = 2;
                mCustomDialog = new TextDialog(C_2_2_CarteEditor.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"작성을 취소하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();
            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setVisibility(View.GONE);

        TextView textView = (TextView) findViewById(R.id.editorText);
        textView.setVisibility(View.VISIBLE);
        textView.setText("등록");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = typeE.getText().toString();
                rice = riceE.getText().toString();
                soup = soupE.getText().toString();
                side_dish_1 = side_dish_1E.getText().toString();
                side_dish_2 = side_dish_2E.getText().toString();
                side_dish_3 = side_dish_3E.getText().toString();
                snack = snackE.getText().toString();
                memo = memoE.getText().toString();

                push = 1;
                mCustomDialog = new TextDialog(C_2_2_CarteEditor.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"등록하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FF9900"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        ImageView pictureRegister = (ImageView) findViewById(R.id.pictureRegisterimageView);
        pictureRegister.setVisibility(View.VISIBLE);
        pictureRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectGallery();
            }
        });

        TextView txt = (TextView) findViewById(R.id.comenttextView);
        txt.setVisibility(View.VISIBLE);
        final ImageView replyCheck = (ImageView) findViewById(R.id.replyCheckImage);
        replyCheck.setVisibility(View.VISIBLE);
        replyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageChange) {
                    replyCheck.setImageDrawable(getResources().getDrawable(R.drawable.del));
                    is_reply = "n";
                    imageChange = false;
                } else {
                    replyCheck.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                    is_reply = "y";
                    imageChange = true;
                }
            }
        });
    }

    public void clickTextView2(View v) {
        mCustomDialog.dismiss();
    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        switch (push) {
            case 1:
                api.API_46(seq_user, seq_kindergarden, String.valueOf(years), change_month, change_day, type, rice, soup, side_dish_1, side_dish_2, side_dish_3, side_dish_4, side_dish_5, snack, memo, files);
                api.getMessage();
                break;
            case 2:
                Intent intent = new Intent(C_2_2_CarteEditor.this, C_1_1_Calendar.class);
                C_2_2_CarteEditor.this.startActivity(intent);
                break;
            default:
                break;
        }
        finish();
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


    public void findViewbyid() {
        typeE = (EditText) findViewById(R.id.editText5);
        riceE = (EditText) findViewById(R.id.editText7);
        soupE = (EditText) findViewById(R.id.editText8);
        side_dish_1E = (EditText) findViewById(R.id.editText9);
        side_dish_2E = (EditText) findViewById(R.id.editText10);
        side_dish_3E = (EditText) findViewById(R.id.editText11);
//        side_dish_4E = (EditText) findViewById(R.id.editText12);
//        side_dish_5E = (EditText) findViewById(R.id.editText5);
        snackE = (EditText) findViewById(R.id.editText12);
        memoE = (EditText) findViewById(R.id.editText13);

        mainDate = (TextView) findViewById(R.id.textView132);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout24);

        replyTextview = (TextView) findViewById(R.id.comenttextView);
        replyTextview.setVisibility(View.GONE);
        replyImageview = (ImageView) findViewById(R.id.replyCheckImage);
        replyImageview.setVisibility(View.GONE);
    }

}
