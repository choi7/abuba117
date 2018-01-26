package com.joyblock.abuba;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.data.MyApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_1_2_Calendar_Detail_View extends Fragment {

    SharedPreferences pref;
    private TextView tvDate;
    private ArrayList<String> dayList;
    private GridView gridView;
    private Calendar mCal;


    public MyApplication app;
    public void setPref(SharedPreferences pref){
        this.pref=pref;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.custom_c_1_2_calendar_list_view, container, false);

//        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
//        gridView = (GridView) rootView.findViewById(R.id.gridview);
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
//        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date));

        return rootView;
    }
}

