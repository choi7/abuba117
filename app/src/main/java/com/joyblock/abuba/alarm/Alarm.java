package com.joyblock.abuba.alarm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import java.util.ArrayList;

public class Alarm extends BaseActivity {

    private AlarmListViewAdapter mAdapter = null;
    ListView listView;
    private ArrayList<ListDatas> mListData = new ArrayList<ListDatas>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm);



        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffff3366));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("알림");
        title.setVisibility(View.VISIBLE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#FF3366"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }



        listView = (ListView) findViewById(R.id.alarm_ListView);
        mAdapter = new AlarmListViewAdapter(this);
        listView.setAdapter(mAdapter);
        mAdapter.addItem("김민석 투약의뢰서가 등록되었습니다.","2018/01/02");
        mAdapter.addItem("김민석 투약의뢰서가 등록되었습니다.","2018/01/02");
        mAdapter.addItem("김민석 투약의뢰서가 등록되었습니다.","2018/01/02");
        mAdapter.addItem("김민석 투약의뢰서가 등록되었습니다.","2018/01/02");
        mAdapter.addItem("김민석 투약의뢰서가 등록되었습니다.","2018/01/02");

    }


    public class ViewHolder {
        public TextView mText;
        public TextView mText1;
    }

    public class ListDatas {

        String ss;

        //아이콘
        public Drawable mIcon;
        //제목
        public String mTitle;
        //시간
        public String mDate;

        public boolean a = true;
    }

    private class AlarmListViewAdapter extends BaseAdapter {
        private Context mContext = null;


        public AlarmListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public void addItem(String mTitle, String mDate) {
            ListDatas addInfo = null;
            addInfo = new ListDatas();
            addInfo.mTitle = mTitle;
            addInfo.mDate = mDate;

            mListData.add(addInfo);
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
//            LinearLayout linearLayout = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_cell_alarm_list, null);
                holder.mText = (TextView) convertView.findViewById(R.id.alarm_List_Cell_Content_TextView);
                holder.mText1 = (TextView) convertView.findViewById(R.id.alarm_List_Cell_Day_TextView);
//                linearLayout = (LinearLayout) convertView.findViewById(R.id.joblistviewcustomLayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ListDatas mData = mListData.get(position);
            holder.mText.setText(mData.mTitle);
            holder.mText1.setText(mData.mDate);
            /*
            if (mListData.get(position).a) {
                holder.mText.setTextColor(Color.BLACK);
                convertView.setBackgroundColor(Color.parseColor("#F7F7F7"));
            } else {
                holder.mText.setTextColor(Color.WHITE);
                convertView.setBackgroundColor(Color.parseColor("#2CA6E0"));
            }
            //리스트뷰 안에 홀수마다 백그라운드 컬러 지정
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#F3F3F4"));
            }
            */
            return convertView;
        }

    }


}
