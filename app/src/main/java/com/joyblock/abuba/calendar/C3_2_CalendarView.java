package com.joyblock.abuba.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CommentActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R45_SelectScheduleManagementOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by BLUE on 2018-01-28.
 */

public class C3_2_CalendarView extends BaseActivity {

    R45_SelectScheduleManagementOne detail;
    String end_day, file_path, start_time_hour, lng, start_day, start_year, end_time_min, mod_date, seq_user, title, content, seq_schedule_management, reg_date, end_time_hour, seq_kindergarden, is_yn, name,
            start_month, start_time_min, end_month, addr, end_year, lat;
    TextView textView137, textView138, textView139, inTex, titles;
    String change_day_of_week = null;
    String change_day_of_week1 = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_3_2_calendar_view);
        findViewbyId();
        actionbarCustom();
    }

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();



//        titles.setText("일정관리");
        Intent intent = getIntent();
        seq_schedule_management = intent.getStringExtra("seq_schedule_management");
        int start=intent.getIntExtra("start",0);
        int end=intent.getIntExtra("end",0);

        api.API_45(seq_schedule_management);
        String apis = api.getMessage();
        try {
            JSONObject jsonObject = new JSONObject(apis);
            detail = new GsonBuilder().create().fromJson(jsonObject.getString("schedule_management"), R45_SelectScheduleManagementOne.class);
            this.title = detail.title;
            end_day = detail.end_day;
            file_path = detail.file_path;
            start_time_hour = detail.start_time_hour;
            lng = detail.lng;
            start_day = detail.start_day;
            start_year = detail.start_year;
            end_time_min = detail.end_time_min;
            mod_date = detail.mod_date;
            seq_user = detail.seq_user;
            content = detail.content;
            seq_schedule_management = detail.seq_schedule_management;
            reg_date = detail.reg_date;
            end_time_hour = detail.end_time_hour;
            seq_kindergarden = detail.seq_kindergarden;
            is_yn = detail.is_yn;
            name = detail.name;
            start_month = detail.start_month;
            start_time_min = detail.start_time_min;
            end_month = detail.end_month;
            addr = detail.addr;
            end_year = detail.end_year;
            lat = detail.lat;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("start_day", start_day);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, Calendar.YEAR);
        cal.set(Calendar.MONTH, Calendar.MONTH);
        cal.set(Calendar.DATE, Integer.parseInt(start_day));
        switch (start+1) {
            case 1:change_day_of_week = "일";break;
            case 2:change_day_of_week = "월";break;
            case 3:change_day_of_week = "화";break;
            case 4:change_day_of_week = "수";break;
            case 5:change_day_of_week = "목";break;
            case 6:change_day_of_week = "금";break;
            case 7:change_day_of_week = "토";break;
        }


        Calendar cal1 = Calendar.getInstance();

        cal1.set(Calendar.YEAR, Calendar.YEAR);
        cal1.set(Calendar.MONTH, Integer.parseInt(end_month));
        cal1.set(Calendar.DATE, Integer.parseInt(end_day));
        switch (end+1) {
            case 1:change_day_of_week1 = "일";break;
            case 2:change_day_of_week1 = "월";break;
            case 3:change_day_of_week1 = "화";break;
            case 4:change_day_of_week1 = "수";break;
            case 5:change_day_of_week1 = "목";break;
            case 6:change_day_of_week1 = "금";break;
            case 7:change_day_of_week1 = "토";break;
        }


        String moringandnight, moringandnight1;
        Integer sh, eh, sm, em;
        sh = Integer.valueOf(start_time_hour);
//        eh = end_time_hour;
        sm = Integer.valueOf(start_time_min);
//        em = end_time_min;
        if (sh < 13) {
            moringandnight = "오전";
        } else {
//            if(sh < 10) {
//                start_time_hour = "0" + (sh - 12);
//            } else {
            start_time_hour = "" + (sh - 12);
//            }
            moringandnight = "오후";
        }

        if (sm < 13) {
            moringandnight1 = "오전";
        } else {
//            if(sh < 10) {
//                start_time_hour = "0" + (sh - 12);
//            } else {
            start_time_min = "" + (sm - 12);
//            }
            moringandnight1 = "오후";
        }


        textView137.setText(start_month + "월 " + start_day + "일 " + "(" + change_day_of_week + ") "+ start_time_hour + ":" + start_time_min);// + moringandnight
        textView138.setText(end_month + "월 " + end_day + "일 " + "(" + change_day_of_week1 + ") " + end_time_hour + ":" + end_time_min);//+ moringandnight
        textView139.setText("수정 예정");
        inTex.setText(content);


    }

    public void findViewbyId() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FF9900"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        textView137 = (TextView) findViewById(R.id.textView137);
        textView138 = (TextView) findViewById(R.id.textView138);
        textView139 = (TextView) findViewById(R.id.textView139);
        inTex = (TextView) findViewById(R.id.inTex);

        ImageView commentPushImage = (ImageView) findViewById(R.id.commentPushImage);
        commentPushImage.setVisibility(View.VISIBLE);
        commentPushImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(C3_2_CalendarView.this, CommentActivity.class);
                intent.putExtra("activity", "C3_2_CalendarView");
                C3_2_CalendarView.this.startActivity(intent);
            }
        });

        EditText editText = (EditText) findViewById(R.id.editTexttt);
        editText.setVisibility(View.VISIBLE);

    }


}
