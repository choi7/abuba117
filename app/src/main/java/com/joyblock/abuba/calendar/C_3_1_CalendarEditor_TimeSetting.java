package com.joyblock.abuba.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class C_3_1_CalendarEditor_TimeSetting extends BaseActivity {

    TimePicker timePicker;
    CalendarView datePicker;
    ConstraintLayout constraintLayout1, constraintLayout2, constraintLayout3, constraintLayout4;
    private java.util.Calendar mCal;
    GridView gridView;
    GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    TextView tvDate, startDate, startDate1, endDate, endDate1;
    Integer nextAndBack = 1, gridSeletorNum, gridSeletorNumBefore;
    String startday, startmonth, startyear, endday, endmonth, endyear;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_3_1_calendar_editor_time_setting);
        Findviewbyid();
        actionbarCustom();

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        tvDate.setText(curYearFormat.format(date) + "년 " + curMonthFormat.format(date) + "월");

        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");
        mCal = java.util.Calendar.getInstance();

        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(java.util.Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(mCal.get(java.util.Calendar.MONTH) + 1);
        gridAdapter = new GridAdapter(C_3_1_CalendarEditor_TimeSetting.this, dayList);
        gridView.setAdapter(gridAdapter);
        //달력터치시
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("포지션은 : ", String.valueOf(position));
                if (nextAndBack == 1) {
                    startday = String.valueOf(position);
                } else {
                    endday = String.valueOf(position);
                }

//                gridSeletorNum = position;
//                view.setBackgroundColor(Color.parseColor("F3FFFE"));
//                gridSeletorNumBefore = position;

            }
        });
        //시작버튼
        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDate.setTextColor(Color.parseColor("#717071"));
                endDate1.setBackgroundColor(Color.parseColor("#717071"));
                startDate.setTextColor(Color.parseColor("#E95513"));
                startDate1.setBackgroundColor(Color.parseColor("#E95513"));
                nextAndBack = 1;
            }
        });
        //종료버튼
        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDate.setTextColor(Color.parseColor("#717071"));
                startDate1.setBackgroundColor(Color.parseColor("#717071"));
                endDate.setTextColor(Color.parseColor("#E95513"));
                endDate1.setBackgroundColor(Color.parseColor("#E95513"));
                nextAndBack = 0;
            }
        });
        //완료버튼
        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //취소버튼
        constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void Findviewbyid() {

        timePicker = (TimePicker) findViewById(R.id.pmTimePicker);
//        datePicker = (CalendarView) findViewById(R.id.calendarView);
        constraintLayout1 = (ConstraintLayout) findViewById(R.id.constraintLayout12);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.constraintLayout13);
        constraintLayout3 = (ConstraintLayout) findViewById(R.id.constraintLayout14);
        constraintLayout4 = (ConstraintLayout) findViewById(R.id.constraintLayout15);
        startDate = (TextView) findViewById(R.id.startDate);
        startDate1 = (TextView) findViewById(R.id.startDate1);
        endDate = (TextView) findViewById(R.id.endDate);
        endDate1 = (TextView) findViewById(R.id.endDate1);
        gridView = (GridView) findViewById(R.id.c_3_1_calendar_girdview);
        tvDate = (TextView) findViewById(R.id.textView175);

    }


    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("시간설정");
        title.setVisibility(View.VISIBLE);

        ImageView backImage = (ImageView) findViewById(R.id.backImage);


        TextView textView = (TextView) findViewById(R.id.editorText);
        textView.setVisibility(View.VISIBLE);
        textView.setText("확인");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#ff9900"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

    }

    private void setCalendarDate(int month) {
        mCal.set(java.util.Calendar.MONTH, month - 1);
        for (int i = 0; i < mCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }


    private class GridAdapter extends BaseAdapter {

        private final List<String> list;
        private final LayoutInflater inflater;

        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_cell_c_3_1_calendar, parent, false);
                holder = new GridAdapter.ViewHolder();
                holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview1);
                convertView.setTag(holder);
            } else {
                holder = (GridAdapter.ViewHolder) convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));
            //해당 날짜 텍스트 컬러,배경 변경
            mCal = java.util.Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = mCal.get(java.util.Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            if (position % 7 == 0) {
                holder.tvItemGridView.setTextColor(Color.parseColor("#C30D23"));
            }
            if ((position + 1) % 7 == 0) {
                holder.tvItemGridView.setTextColor(Color.parseColor("#036EB7"));
            }


            return convertView;
        }

        private class ViewHolder {
            TextView tvItemGridView;
            TextView tvItemGridViewContent;
            LinearLayout calendar_linearLayout;
        }
    }

}
