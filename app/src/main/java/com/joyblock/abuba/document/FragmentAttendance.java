package com.joyblock.abuba.document;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.CalendarCustomDialong_document;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R13_SelectNoticeList;
import com.joyblock.abuba.api_message.R34_SelectMedicationRequestList;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.bus.A2_1_boading_management;
import com.joyblock.abuba.data.MyApplication;
import com.joyblock.abuba.document.A3_1_Medicine;
import com.joyblock.abuba.document.A3_ListViewAdapterCustom;
import com.joyblock.abuba.document.A5_1_Attendance;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;
import com.joyblock.abuba.notice.FragmentNotice;
import com.joyblock.abuba.notice.NoticeDetailActivity;
import com.joyblock.abuba.notice.NoticeListViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BLUE on 2018-01-26.
 */

public class FragmentAttendance extends Fragment {

    SharedPreferences pref;
    String seq_user,seq_kindergarden,seq_kindergarden_class;
    NoticeListViewAdapter adapters;
    ListView listView;

    R13_SelectNoticeList[] noticeList;

    ListView mainlistView, listview,class_listview;
    R34_SelectMedicationRequestList[] r34_selectMedicationRequestList;
    CalendarCustomDialong_document mCustomDialog1;
    ConstraintLayout day_ConstraintLayout, class_ConstraintLayout;
    Integer year, month, day;
    TextView dayText, title, classText;
    Activity activity;
    String string_year, string_month, string_day, seq_medication_request;
    BanListViewAdapter adapter;
    AlertDialog.Builder banListDialogBuilder;//,modDelDialogBuidler;
    DialogInterface banListDialogInterface;//,modeDelDialogInteface;
    A3_ListViewAdapterCustom a3_listViewAdapterCustom;

    R6_SelectKindergardenClassList[] classList;

    BanListViewAdapter class_adapter;
    CustomListViewDialog dialog;
    int int_selected_class;
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.framgment_a_5_1_attendance, container, false);
        day_ConstraintLayout = (ConstraintLayout) rootView.findViewById(R.id.a3_1DayConstraintLayout);
        day_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mCustomDialog1 = new CalendarCustomDialong_document(getContext());
                mCustomDialog1.show();
            }
        });

        classText = (TextView) rootView.findViewById(R.id.a3_1_Class_TextView);

        class_ConstraintLayout = (ConstraintLayout) rootView.findViewById(R.id.a3_1ClassConstraintLayout);
        class_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //어댑터 생성 후 customListViewDialog에 반 리스트를 보여준다.
                class_adapter = new BanListViewAdapter();
                dialog = new CustomListViewDialog(getContext(),class_adapter);
                class_adapter.addItem("전체");
                for (R6_SelectKindergardenClassList list : app.kindergarden_class_list) {
                    class_adapter.addItem(list.kindergarden_class_name);
                }
                class_adapter.notifyDataSetChanged();
                dialog.show();

                //반 다이얼로그 이벤트 처리
                class_listview=dialog.getListView();
                class_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BanListViewItem item = class_adapter.list.get(position);
                        int_selected_class=position;
                        //seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
                        classText.setText(item.getName());
                        Toast.makeText(getContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        dayText = (TextView) rootView.findViewById(R.id.a3_1dayTextView);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String getTime = year.format(date);
        String getTime1 = month.format(date);
        String getTime2 = day.format(date);
        dayText.setText(getTime + "년 " + getTime1 + "월 " + getTime2 + "일");



        mainlistView = (ListView) rootView.findViewById(R.id.a3_1_listview);
        a3_listViewAdapterCustom = new A3_ListViewAdapterCustom(getContext());

        a3_listViewAdapterCustom.addItem("최지우", "보라반", getResources().getDrawable(R.drawable.ch_on));
        a3_listViewAdapterCustom.addItem("박철구", "보라반", getResources().getDrawable(R.drawable.ch_on));
        mainlistView.setAdapter(a3_listViewAdapterCustom);



        return rootView;

    }

    public void datepush1(View v) {

        day = mCustomDialog1.datePicker.getDayOfMonth();
        month = mCustomDialog1.datePicker.getMonth() + 1;
        year = mCustomDialog1.datePicker.getYear();

        //System.out.println(mCustomDialog1.qas.day + "sss" + mCustomDialog1.qas.month + "ssss" + mCustomDialog1.qas.year);
        String days = null, months = null;
        if (day < 10) {
            days = "0" + day;
        } else {
            days = String.valueOf(day);
        }
        if (month < 10) {
            months = "0" + month;
        } else {
            months = String.valueOf(month);
        }

        string_day = days;
        string_month = months;
        string_year = String.valueOf(year);

        dayText.setText(year + "년 " + months + "월 " + days + "일 ");

        mCustomDialog1.dismiss();
    }


}


