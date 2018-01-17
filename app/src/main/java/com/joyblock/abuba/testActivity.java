package com.joyblock.abuba;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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

import java.util.ArrayList;


public class testActivity extends AppCompatActivity {


    private ListView mListView = null;
    private testActivity.ListViewAdapter mAdapter = null;
    Typeface fontface1, fontface2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff71cac2));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setIcon(R.mipmap.ic_logo);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        TextView title = (TextView) findViewById(R.id.titleName);
//        title.setVisibility(View.VISIBLE);
//        title.setText("로그인");
        ImageView titleImage = (ImageView) findViewById(R.id.titleImageView);
        titleImage.setVisibility(View.VISIBLE);
        fontface1 = Typeface.createFromAsset(getAssets(), "NanumSquareRegular.ttf");
        fontface2 = Typeface.createFromAsset(getAssets(), "NanumSquareBold.ttf");
        System.out.println(getAssets());
        System.out.println(getAssets());
        System.out.println(getAssets());
        System.out.println(getAssets());
        System.out.println(getAssets());

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#71CAC2"));
        }


        mListView = (ListView) findViewById(R.id.listView111);

        mAdapter = new testActivity.ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_notice), "공지사항", "Notice");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_buslocation), "버스 위치 확인", "Bus Location");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_schedule), "일정관리", "Schedule & DailyMenu");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_notification), "알림", "Notification");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_document), "문서함", "Document");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_photoalbum), "사진 앨범", "Photo Album");


    }

//    public static Typeface getOpenSansRegular(Context c){
//        return Typeface.createFromAsset(c.getAssets(), "NanumSquareRegular.ttf");
//    }

    public class ViewHolder {
        public ImageView mIcon;
        public TextView mText;
        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public void addItem(Drawable icon, String mTitle, String mDate) {
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
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
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mainlistviewlayout, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.imageView1);
                holder.mText = (TextView) convertView.findViewById(R.id.textView2);
                holder.mDate = (TextView) convertView.findViewById(R.id.textView1);

                convertView.setTag(holder);

            } else {

                holder = (testActivity.ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);

            } else {
                holder.mIcon.setVisibility(View.GONE);

            }

            holder.mText.setText(mData.mTitle);
            holder.mDate.setText(mData.mDate);
            //리스트뷰 안에 홀수마다 백그라운드 컬러 지정
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#F3F3F4"));

            }
            TextView txt = (TextView) convertView.findViewById(R.id.textView1);
            TextView txt2 = (TextView) convertView.findViewById(R.id.textView2);

            txt.setTypeface(fontface1);
            txt2.setTypeface(fontface2);
            switch (position) {
                case 0:
                    txt.setTextColor(Color.parseColor("#26A8E0"));
                    break;
                case 1:
                    txt.setTextColor(Color.parseColor("#3CB39D"));
                    break;
                case 2:
                    txt.setTextColor(Color.parseColor("#FBB918"));
                    break;
                case 3:
                    txt.setTextColor(Color.parseColor("#DC4844"));
                    break;
                case 4:
                    txt.setTextColor(Color.parseColor("#AD878D"));
                    break;
                case 5:
                    txt.setTextColor(Color.parseColor("#A3DDF1"));
                    break;

            }


            return convertView;
        }
    }

}
