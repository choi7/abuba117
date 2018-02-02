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
import com.joyblock.abuba.TextDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    String startday, startmonth, startyear, starthour, startminute, endhour, endminute, endday, endmonth, endyear, changeday, changemonth, start_day_of_week, end_day_of_week, change_day_of_week;
    ;
    TextDialog mCustomDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_c_3_1_calendar_editor_time_setting);
        Findviewbyid();
        actionbarCustom();

//        today();


        dayList = new ArrayList<String>();
        dayList.add("일");
        dayList.add("월");
        dayList.add("화");
        dayList.add("수");
        dayList.add("목");
        dayList.add("금");
        dayList.add("토");
        mCal = java.util.Calendar.getInstance();

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
//        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
//        final SimpleDateFormat curHourFormat = new SimpleDateFormat("hh", Locale.KOREA);
//        final SimpleDateFormat curMinuteFormat = new SimpleDateFormat("mm", Locale.KOREA);


        tvDate.setText(curYearFormat.format(date) + "년 " + curMonthFormat.format(date) + "월");




        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(java.util.Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }


//        starthour, startminute, endhour, endminute, startyear, startmonth, startday, endyear, endmonth, endday, start_day_of_week, end_day_of_week


        setCalendarDate(mCal.get(java.util.Calendar.MONTH) + 1);
        gridAdapter = new GridAdapter(C_3_1_CalendarEditor_TimeSetting.this, dayList);
        gridView.setAdapter(gridAdapter);
        //달력터치시
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("포지션은 : ", String.valueOf(position));

                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.YEAR, Integer.parseInt(curYearFormat.format(date)));
                cal.set(Calendar.MONTH, Calendar.MONTH);
                cal.set(Calendar.DATE, position - 10);
                switch (cal.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        System.out.println("일");
                        change_day_of_week = "일";
                        break;
                    case 2:
                        System.out.println("월");
                        change_day_of_week = "월";
                        break;
                    case 3:
                        System.out.println("화");
                        change_day_of_week = "화";
                        break;
                    case 4:
                        System.out.println("수");
                        change_day_of_week = "수";
                        break;
                    case 5:
                        System.out.println("목");
                        change_day_of_week = "목";
                        break;
                    case 6:
                        System.out.println("금");
                        change_day_of_week = "금";
                        break;
                    case 7:
                        System.out.println("토");
                        change_day_of_week = "토";
                        break;
                }

                if (position < 10) {
                    changeday = "" + (position - 10);
                } else {
                    changeday = String.valueOf(position - 10);
                }
                if (mCal.get(Calendar.MONTH) + 1 < 10) {
                    changemonth = "" + (mCal.get(Calendar.MONTH) + 1);
                } else {
                    changemonth = String.valueOf(mCal.get(Calendar.MONTH) + 1);
                }

                if (nextAndBack == 1) {
                    startyear = curYearFormat.format(date);
                    startmonth = changemonth;
                    startday = changeday;
                    start_day_of_week = change_day_of_week;
                    Log.d("시작날짜 : ", startyear + " " + startmonth + " " + startday);
                } else {
                    endyear = curYearFormat.format(date);
                    endmonth = changemonth;
                    endday = changeday;
                    end_day_of_week = change_day_of_week;
                    Log.d("종료날짜 : ", endyear + " " + endmonth + " " + endday);

                }


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

                mCustomDialog = new TextDialog(C_3_1_CalendarEditor_TimeSetting.this, R.layout.dialog_one_check);

                Log.d("starthour", starthour);

                if (starthour.equals(null)) {
                    mCustomDialog.setTexts(new String[]{"시작 시간을 선택해주세요.", "확인"});
                } else if (endhour.equals(null)) {
                    mCustomDialog.setTexts(new String[]{"종료 시간을 선택해주세요.", "확인"});
                } else if (startday.equals(null)) {
                    mCustomDialog.setTexts(new String[]{"시작 날짜를 선택해주세요.", "확인"});
                } else if (endday.equals(null)) {
                    mCustomDialog.setTexts(new String[]{"시작 날짜를 선택해주세요.", "확인"});
                } else {
                    mCustomDialog.show();
                    app.calendarIntent.setCalendarIntent(starthour, startminute, endhour, endminute, startyear, startmonth, startday, endyear, endmonth, endday, start_day_of_week, end_day_of_week);
                    Log.d("startminute",startminute);
                    finish();
                }



            }
        });
        //취소버튼
        constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("hourOfDay", String.valueOf(hourOfDay));
                Log.d("minute", String.valueOf(minute));
                String hour, minutes;

                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = String.valueOf(hourOfDay);
                }
                if (minute < 10) {
                    minutes = "0" + minute;
                } else {
                    minutes = String.valueOf(minute);
                }

                if (nextAndBack == 1) {
                    starthour = String.valueOf(hour);
                    startminute = String.valueOf(minutes);
                } else {
                    endhour = String.valueOf(hour);
                    endminute = String.valueOf(minutes);
                }
            }
        });

    }

    public void setCalendar(final int year,final int month){
        tvDate.setText((year + "년 " + (month<10?"0":""+month) + "월"));



        Date date=new Date();

        mCal.set(year, month-1, 1);
        int dayNum = mCal.get(java.util.Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }


//        starthour, startminute, endhour, endminute, startyear, startmonth, startday, endyear, endmonth, endday, start_day_of_week, end_day_of_week


        setCalendarDate(mCal.get(java.util.Calendar.MONTH) + 1);
        gridAdapter = new GridAdapter(C_3_1_CalendarEditor_TimeSetting.this, dayList);
        gridView.setAdapter(gridAdapter);
        //달력터치시
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("포지션은 : ", String.valueOf(position));

                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DATE, position - 10);
                switch (cal.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        System.out.println("일");
                        change_day_of_week = "일";
                        break;
                    case 2:
                        System.out.println("월");
                        change_day_of_week = "월";
                        break;
                    case 3:
                        System.out.println("화");
                        change_day_of_week = "화";
                        break;
                    case 4:
                        System.out.println("수");
                        change_day_of_week = "수";
                        break;
                    case 5:
                        System.out.println("목");
                        change_day_of_week = "목";
                        break;
                    case 6:
                        System.out.println("금");
                        change_day_of_week = "금";
                        break;
                    case 7:
                        System.out.println("토");
                        change_day_of_week = "토";
                        break;
                }

                if (position < 10) {
                    changeday = "" + (position - 10);
                } else {
                    changeday = String.valueOf(position - 10);
                }
                if (mCal.get(Calendar.MONTH) + 1 < 10) {
                    changemonth = "" + (mCal.get(Calendar.MONTH) + 1);
                } else {
                    changemonth = String.valueOf(mCal.get(Calendar.MONTH) + 1);
                }

                if (nextAndBack == 1) {
                    startyear = year+"";
                    startmonth = changemonth;
                    startday = changeday;
                    start_day_of_week = change_day_of_week;
                    Log.d("시작날짜 : ", startyear + " " + startmonth + " " + startday);
                } else {
                    endyear = year+"";
                    endmonth = changemonth;
                    endday = changeday;
                    end_day_of_week = change_day_of_week;
                    Log.d("종료날짜 : ", endyear + " " + endmonth + " " + endday);

                }


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
        endDate = (TextView) findViewById(R.id.listview_text2);
        endDate1 = (TextView) findViewById(R.id.endDate1);
        gridView = (GridView) findViewById(R.id.c_3_1_calendar_girdview);
        tvDate = (TextView) findViewById(R.id.textView175);

    }


    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff9900));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();
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


    public void clickTextView2(View v) {
        mCustomDialog.dismiss();
    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


}
