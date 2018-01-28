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
import android.widget.Toast;

import com.joyblock.abuba.bus.TextListViewAdapter;
import com.joyblock.abuba.calendar.CalendarActivity;
import com.joyblock.abuba.data.MyApplication;

import org.w3c.dom.Text;

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
    private TextView tvDate;
    GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridView;
    private ListView c_1_1_listview, listView2;
    private Calendar mCal;


    public MyApplication app;
    public void setPref(SharedPreferences pref){
        this.pref=pref;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_c_1_1_calendar, container, false);



        final ConstraintLayout constraintLayout = (ConstraintLayout) rootView.findViewById(R.id.constraintLayout7);
        final ConstraintLayout constraintLayout1 = (ConstraintLayout) rootView.findViewById(R.id.constraintLayout8);

        tvDate = (TextView) rootView.findViewById(R.id.tv_date);
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
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
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
                Log.d("포지션은 : ", String.valueOf(position));
                Intent intent = new Intent(getContext(), C3_2_CalendarView.class);
                FragmentCalendar.this.startActivity(intent);
            }
        });

        TextListViewAdapter adapter=new TextListViewAdapter(5,R.layout.custom_cell_c_1_2_calendar_view);
        listView2.setAdapter(adapter);
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        adapter.addItem("5일","금요일", "10시~\n  12시","창원시\n마산회원구","동계장기자랑");
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), C3_2_CalendarView.class);
                FragmentCalendar.this.startActivity(intent);
            }
        });


        ImageView fragment_c_1_1_List_Change_ImageView = (ImageView) rootView.findViewById(R.id.fragment_c_1_1_List_Change_ImageView);
        fragment_c_1_1_List_Change_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.GONE);
                constraintLayout1.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                c_1_1_listview.setVisibility(View.GONE);
                listView2.setVisibility(View.VISIBLE);

//                Intent intent = new Intent(FragmentCalendar.this, C_1_1_Calendar.class);
//                Toast.makeText(getContext(), "sdfsdff", Toast.LENGTH_LONG).show();
            }
        });

        ImageView imageView21 = (ImageView) rootView.findViewById(R.id.imageView21);
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constraintLayout.setVisibility(View.VISIBLE);
                constraintLayout1.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                c_1_1_listview.setVisibility(View.GONE);
                listView2.setVisibility(View.GONE);

//                Intent intent = new Intent(FragmentCalendar.this, C_1_1_Calendar.class);
//                Toast.makeText(getContext(), "sdfsdff", Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }

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
                holder.tvItemGridViewContent.setText("동계방학");
                holder.tvItemGridViewContent.setBackgroundColor(Color.parseColor("#F5FFFE"));
                holder.tvItemGridView.setBackgroundColor(Color.parseColor("#F5FFFE"));
//            }

            return convertView;
        }

        private class ViewHolder {
            TextView tvItemGridView;
            TextView tvItemGridViewContent;
            LinearLayout calendar_linearLayout;
        }

    }


}
