package com.joyblock.abuba.album;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.notice.NoticeEditorActivity;

import java.util.ArrayList;

public class Album extends BaseActivity {

    ImageView editorImageView;
    ListView listView;
    AlarmsListViewAdapter mAdapters = null;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<MyAdapter.MyData> myDataset;
    private ArrayList<ListDatas> mListData = new ArrayList<ListDatas>();

    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_album);

        mContext = getApplicationContext();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff66ccff));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("사진앨범");
        title.setVisibility(View.VISIBLE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editorImageView = (ImageView) findViewById(R.id.editorImage);
        editorImageView.setVisibility(View.VISIBLE);
        editorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Album.this, P_3_Album_Editor.class);
                Album.this.startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.album_ListView);
        mAdapters = new AlarmsListViewAdapter(this);
        listView.setAdapter(mAdapters);
//        mAdapters.addItem("ddd","ddd");
//        mAdapters.addItem("ddd","ddd");
//        mAdapters.addItem("ddd","ddd");
//        mAdapters.addItem("ddd","ddd");
//        mAdapters.addItem("ddd","ddd");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");
        mAdapters.addItem(getResources().getDrawable(R.drawable.no_check), "노랑반", "이쁜이들이에요", "아무개", "2018.01.01");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("포지션은", String.valueOf(position));
                Intent intent = new Intent(Album.this, NoticeEditorActivity.class);
                Album.this.startActivity(intent);
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#66CCFF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        /*
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
//        mRecyclerView.setHasFixedSize(true);

        // StaggeredGrid 레이아웃을 사용한다
        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        //layoutManager = new LinearLayoutManager(this);
        // layoutManager = new GridLayoutManager(this,3);
        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        mRecyclerView.setLayoutManager(mLayoutManager);
//        Adapter = new AlbumListViewAdapter(items, mContext);


        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
        myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
        myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
        myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
        myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
        myDataset.add(new MyAdapter.MyData(R.drawable.bus_2));
        myDataset.add(new MyAdapter.MyData(R.drawable.ch_off));
        */


    }


    public class ViewHolder {
        public TextView banText;
        public TextView title;
        public TextView name;
        public TextView time;
        public ImageView userImage;

        public TextView text;
        public ImageView insertAndDelete;
        public ImageView checkPushImage;
    }

    public class ListDatas {

        String ss;

        //유저 이미지
        public Drawable userImage;
        //반
        public String mBan;
        //제목
        public String mTitle;
        //유저이름
        public String mName;
        //작성시간
        public String mTime;

        public boolean a = true;
    }

    private class AlarmsListViewAdapter extends BaseAdapter {
        private Context mContext = null;


        public AlarmsListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public void addItem(Drawable userImage, String mBan, String mTitle, String mName, String mTime) {
            ListDatas addInfo = null;
            addInfo = new ListDatas();
            addInfo.userImage = userImage;
            addInfo.mBan = mBan;
            addInfo.mTitle = mTitle;
            addInfo.mName = mName;
            addInfo.mTime = mTime;

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
//            LinearLayout linearLayout = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.custom_cell_album_list, null);
                holder.banText = (TextView) convertView.findViewById(R.id.noticeDetailBanText);
                holder.title = (TextView) convertView.findViewById(R.id.noticeDetailTitleText);
                holder.name = (TextView) convertView.findViewById(R.id.noticeDetailNameText);
                holder.time = (TextView) convertView.findViewById(R.id.noticeDetailTimeText);
                holder.text = (TextView) convertView.findViewById(R.id.textView27);
                TextView txt = (TextView) convertView.findViewById(R.id.textView91);
                txt.setVisibility(View.GONE);
                holder.userImage = (ImageView) convertView.findViewById(R.id.noticeDetailUserImage);
                holder.insertAndDelete = (ImageView) convertView.findViewById(R.id.noticeDetailinsertAndDeleteText);
                holder.checkPushImage = (ImageView) convertView.findViewById(R.id.checkPushImageView);
                mRecyclerView = (RecyclerView) convertView.findViewById(R.id.album_recyclerView);
                mRecyclerView.setFocusable(false);
                mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.

                mRecyclerView.setLayoutManager(mLayoutManager);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListDatas mData = mListData.get(position);
            holder.banText.setText(mData.mBan);
            holder.title.setText(mData.mTitle);
            holder.name.setText(mData.mName);
            holder.time.setText(mData.mTime);
            holder.userImage.setImageDrawable(mData.userImage);


            holder.text.setVisibility(View.GONE);
            holder.insertAndDelete.setVisibility(View.GONE);
            holder.checkPushImage.setVisibility(View.GONE);

            myDataset = new ArrayList<>();
            mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);
            myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
            myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
            myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
            myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
            myDataset.add(new MyAdapter.MyData(R.drawable.alarm_image));
            myDataset.add(new MyAdapter.MyData(R.drawable.bus_2));
            myDataset.add(new MyAdapter.MyData(R.drawable.ch_off));


            //터치인식
            final GestureDetector gestureDetector = new GestureDetector(Album.this, new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {return false;}

                @Override
                public void onShowPress(MotionEvent e) {}

                @Override
                public boolean onSingleTapUp(MotionEvent e) {return true;}

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {return false;}

                @Override
                public void onLongPress(MotionEvent e) {}

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {return false;}
            });

            //리사이클뷰를 터치했을때도 화면이 넘어감
            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    Log.d("onInterceptTouchEvent", String.valueOf(rv));
                    Log.d("onInterceptTouchEvent", String.valueOf(e));
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && gestureDetector.onTouchEvent(e)) {
                        Intent intent = new Intent(Album.this, NoticeEditorActivity.class);
                        Log.d("onInterceptTouchEvent", "ttest");
                        Album.this.startActivity(intent);
                    }
                    return false;

                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
            return convertView;
        }

    }


}
