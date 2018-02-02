package com.joyblock.abuba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.api.API;
import com.joyblock.abuba.api_message.R44_SelectScheduleManagementList;
import com.joyblock.abuba.bus.TextListViewAdapter;
import com.joyblock.abuba.calendar.C3_2_CalendarView;
import com.joyblock.abuba.data.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by BLUE on 2018-01-26.
 */

public class FragmentCalendar extends Fragment {
    SharedPreferences pref;
    private TextView tvDate, tvDate1, tvDate2;
    GridAdapter gridAdapter;
    private ArrayList<String> dayList;


//    private int[] num_day={31,}
    Schdule[] schdule=new Schdule[31];


    private GridView gridView;
    private ListView c_1_1_listview, listView2;
    private Calendar mCal;
    public API api=new API();
    String start_day, end_day, cal_title;
    int dayNum;

    public MyApplication app;
    public void setPref(SharedPreferences pref){
        this.pref=pref;
    }
    R44_SelectScheduleManagementList[] detail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_c_1_1_calendar, container, false);

        final ConstraintLayout constraintLayout = (ConstraintLayout) rootView.findViewById(R.id.constraintLayout7);
        final ConstraintLayout constraintLayout1 = (ConstraintLayout) rootView.findViewById(R.id.constraintLayout8);
        TextListViewAdapter adapter=new TextListViewAdapter(5,R.layout.custom_cell_c_1_2_calendar_view);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
        tvDate1 = (TextView) rootView.findViewById(R.id.tv_date4);
        tvDate2 = (TextView) rootView.findViewById(R.id.tv_date5);

        gridView = (GridView) rootView.findViewById(R.id.calendar_Gridview);
        c_1_1_listview = (ListView) rootView.findViewById(R.id.c_1_1_listview);
        c_1_1_listview.setVisibility(View.GONE);
        listView2 = (ListView) rootView.findViewById(R.id.listView2);
        constraintLayout.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.VISIBLE);
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        tvDate.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date));
        tvDate1.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date));
        tvDate2.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date));

        String seq_kindergarden = pref.getString("seq_kindergarden","");

        Log.d("seq_kin", seq_kindergarden);
        api.API_44(seq_kindergarden, curYearFormat.format(date), curMonthFormat.format(date));
        String ss = api.getMessage();

        try {
            JSONObject jsonObject = new JSONObject(ss);
            ss = jsonObject.getString("schedule_management_list");
            detail=new GsonBuilder().create().fromJson(jsonObject.getString("schedule_management_list"),R44_SelectScheduleManagementList[].class);
//            for(int i=0;i<schdule.length;i++)
//                schdule[i]=new Schdule();
//
//            for(int i=0;i<detail.length;i++){
//                for(int j=Integer.parseInt(detail[i].start_day)-1;j<Integer.parseInt(end_day);j++){
//                    schdule[j].order.add(i);
//                }
//            }
            //for(R13_SelectNoticeList list:noticeList)
            for(int i=0;i<detail.length;i++) {
                R44_SelectScheduleManagementList list=detail[i];
                start_day = list.start_day;
                end_day = list.end_day;
                cal_title = list.title;
                Log.d("start_end", start_day + " " + end_day);



                //시작날짜 요일 구하기
                Calendar cal = Calendar.getInstance();

                String change_day_of_week = null;
                cal.set(Calendar.YEAR, Integer.parseInt(curYearFormat.format(date)));
                cal.set(Calendar.MONTH, Calendar.MONTH);
                cal.set(Calendar.DATE, Integer.parseInt(list.start_day));
                switch (cal.get(Calendar.DAY_OF_WEEK)) {
                    case 1:change_day_of_week = "일";break;
                    case 2:change_day_of_week = "월";break;
                    case 3:change_day_of_week = "화";break;
                    case 4:change_day_of_week = "수";break;
                    case 5:change_day_of_week = "목";break;
                    case 6:change_day_of_week = "금";break;
                    case 7:change_day_of_week = "토";break;
                }
                //리스트뷰에 추가
                adapter.addItem(list.start_day+"일",change_day_of_week.equals("")?"금요일":change_day_of_week, list.start_time_hour +"시~\n  "+ list.end_time_hour +"시",list.addr.equals("")?"창원시\n마산회원구":list.addr,list.title);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dayList = new ArrayList<String>();
//        dayList.add("SUN");
//        dayList.add("MON");
//        dayList.add("TUE");
//        dayList.add("WED");
//        dayList.add("THD");
//        dayList.add("FRI");
//        dayList.add("SAT");
        mCal = Calendar.getInstance();

        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);
        gridAdapter = new GridAdapter(getContext(), dayList);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                position-dayNum+2;

                Log.d("포지션은 : ", String.valueOf(position));
                Intent intent = new Intent(getContext(), C3_2_CalendarView.class);
                ArrayList<Integer> s=getPositionSchdules(position-dayNum+2);
//                printS(position-dayNum+2);
                for(int i=0;i<s.size();i++) {
                    intent.putExtra("seq_schedule_management", detail[s.get(i)].seq_schedule_management);
                    int start = Integer.parseInt(detail[0].start_day) + dayNum - 1;
                    int end = Integer.parseInt(detail[0].end_day) + dayNum - 1;
                    intent.putExtra(i+"start", start % 7);
                    intent.putExtra(i+"end", end % 7);
                }
//                intent.putExtra("seq_schedule_management",detail[position-dayNum+2].seq_schedule_management);
                FragmentCalendar.this.startActivity(intent);
            }
        });




        listView2.setAdapter(adapter);
//        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), C3_2_CalendarView.class);
                FragmentCalendar.this.startActivity(intent);
            }
        });

        //터치시 캘린더 안보이게 하고 리스트뷰 보이게 하기
        ImageView fragment_c_1_1_List_Change_ImageView = (ImageView) rootView.findViewById(R.id.fragment_c_1_1_List_Change_ImageView);
        fragment_c_1_1_List_Change_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.GONE);
                constraintLayout1.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                c_1_1_listview.setVisibility(View.GONE);
                listView2.setVisibility(View.VISIBLE);
            }
        });
        //터치시 리스트뷰 안보이게 하고 캘린더 보이게 하기
        ImageView imageView21 = (ImageView) rootView.findViewById(R.id.imageView21);
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                constraintLayout1.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                c_1_1_listview.setVisibility(View.GONE);
                listView2.setVisibility(View.GONE);
            }
        });

        return rootView;
    }



    public ArrayList<Integer> getPositionSchdules(int position){
        ArrayList<Integer> schdule_order_list=new ArrayList<>();
        for(int i=0;i<detail.length;i++) {
            if(Integer.parseInt(detail[i].start_day)>=position&&position<=Integer.parseInt(detail[i].end_day))
                schdule_order_list.add(i);
        }
        return schdule_order_list;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    //월마다 다른 날짜넣기
    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }


    //그리드뷰 아답터설정
    private class GridAdapter extends BaseAdapter {

        private final List<String> list;
        private final LayoutInflater inflater;

        /**
         * 생성자
         *
         * @param context
         * @param list
         */

        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                holder = new ViewHolder();
                holder.tvItemGridView = (TextView) convertView.findViewById(R.id.tv_item_gridview);
                holder.tvItemGridViewContent = (TextView) convertView.findViewById(R.id.tv_item_gridvew_content);
                holder.calendar_linearLayout = (LinearLayout) convertView.findViewById(R.id.calendar_linearLayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));
            //해당 날짜 텍스트 컬러,배경 변경
            mCal = Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = mCal.get(Calendar.DAY_OF_MONTH);

            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.colorAccent));
            }

            if(position % 7 == 0) {holder.tvItemGridView.setTextColor(Color.parseColor("#C30D23"));}
            if((position+1) % 7 == 0) {holder.tvItemGridView.setTextColor(Color.parseColor("#036EB7"));}
//            if(!(position % 7 == 0 || position+1 % 7 == 0)) {



            Integer int_start_day = Integer.valueOf(start_day);
            Integer int_end_day = Integer.valueOf(end_day);


//            holder.tvItemGridViewContent.setText("동계방학");
//            holder.tvItemGridViewContent.setBackgroundColor(Color.parseColor("#F5FFFE"));
//            holder.tvItemGridView.setBackgroundColor(Color.parseColor("#F5FFFE"));
            int pos = position-dayNum+2;
            if(pos >= int_start_day && pos < int_end_day+1) {
                Log.d("테스트" ,int_start_day + "와" + int_end_day + "의" + pos);
                holder.tvItemGridViewContent.setText(cal_title);
                    holder.tvItemGridViewContent.setBackgroundColor(Color.parseColor("#F5FFFE"));
                    holder.tvItemGridView.setBackgroundColor(Color.parseColor("#F5FFFE"));
            }

            return convertView;
        }

        private class ViewHolder {
            TextView tvItemGridView;
            TextView tvItemGridViewContent;
            LinearLayout calendar_linearLayout;
            ArrayList<Integer> schulde_order_list=new ArrayList<>();
        }

    }
    class Schdule{
        ArrayList<Integer> order=new ArrayList<>();
    }


}
