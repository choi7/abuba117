package com.joyblock.abuba;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.bus.BusLocationActivity;
import com.joyblock.abuba.calendar.CalendarActivity;
import com.joyblock.abuba.document.DocumentSelectActivity;
import com.joyblock.abuba.login.LoginActivity;
import com.joyblock.abuba.notice.NoticeActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class MainDawerSelectActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//AppCompatActivity
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    Typeface fontface1, fontface2;

    SharedPreferences.Editor editor;
    String seq_user;

    String url = "http://58.229.208.246/Ububa/selectMyKindergardenList.do";

    //protected
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dawer_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        seq_user = pref.getString("seq_user","없음");

        new BuyTask(seq_user).execute();
        String test ="test";

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fontface1 = Typeface.createFromAsset(getAssets(), "NanumSquareRegular.ttf");
        fontface2 = Typeface.createFromAsset(getAssets(), "NanumSquareBold.ttf");

        TextView mainSelectSchooltxt = (TextView) findViewById(R.id.mainSelectschoolText);
        TextView mainSelectNametxt = (TextView) findViewById(R.id.mainSelectNameText);
        mainSelectSchooltxt.setTypeface(fontface2);
        mainSelectNametxt.setTypeface(fontface1);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);

//        getSupportActionBar().setIcon(R.mipmap.ic_logo);
        ImageView titleImage = (ImageView) findViewById(R.id.titleImageView);
        titleImage.setVisibility(View.VISIBLE);
        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setVisibility(View.GONE);
//        TextView txt = (TextView) findViewById(R.id.titleName);
//        txt.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }


        mListView = (ListView) findViewById(R.id.listView111);

        mAdapter = new MainDawerSelectActivity.ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_notice), "공지사항", "Notice");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_buslocation), "버스 위치 확인", "Bus Location");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_schedule), "일정관리", "Schedule & DailyMenu");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_notification), "알림", "Notification");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_document), "문서함", "Document");
        mAdapter.addItem(getResources().getDrawable(R.mipmap.ic_photoalbum), "사진 앨범", "Photo Album");



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListData mData = mAdapter.mListData.get(position);
                Toast.makeText(MainDawerSelectActivity.this, mData.mTitle,Toast.LENGTH_LONG).show();
                switch (mData.mTitle) {
                    case "공지사항" :
                        Intent s1 = new Intent(MainDawerSelectActivity.this, NoticeActivity.class);
                        MainDawerSelectActivity.this.startActivity(s1);
                        finish();
                        break;
                    case "버스 위치 확인" :
                        Intent s2 = new Intent(MainDawerSelectActivity.this, BusLocationActivity.class);
                        MainDawerSelectActivity.this.startActivity(s2);
                        finish();
                        break;
                    case "일정관리" :
                        Intent s3 = new Intent(MainDawerSelectActivity.this, CalendarActivity.class);
                        MainDawerSelectActivity.this.startActivity(s3);
                        finish();
                        break;
                    case "문서함" :
                        Intent s5 = new Intent(MainDawerSelectActivity.this, DocumentSelectActivity.class);
                        MainDawerSelectActivity.this.startActivity(s5);
                        finish();
                        break;
                        /*
                    case "알림" :
                        Intent s4 = new Intent(MainDawerSelectActivity.this, NoticeActivity.class);
                        MainDawerSelectActivity.this.startActivity(s4);
                        break;

                    case "사진 앨범" :
                        Intent s6 = new Intent(MainDawerSelectActivity.this, PhotoActivity.class);
                        MainDawerSelectActivity.this.startActivity(s6);
                        break;
                        */

                }

            }
        });


    }

    public class ViewHolder {
        public ImageView mIcon;
        public TextView mText;
        public TextView mDate;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_dawer_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {




            AlertDialog.Builder builder = new AlertDialog.Builder(MainDawerSelectActivity.this);
            builder.setMessage("로그아웃하시겠습니까")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            editor = pref.edit();
                            editor.clear();
                            editor.commit();

                            Intent loginIntent = new Intent(MainDawerSelectActivity.this, LoginActivity.class);
                            MainDawerSelectActivity.this.startActivity(loginIntent);
                            finish();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .create()
                    .show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



        return true;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext){
            super();
            this.mContext = mContext;
        }

        public void addItem (Drawable icon, String mTitle, String mDate) {
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
            if(convertView == null){

                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.mainlistviewlayout,null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.imageView1);
                holder.mText = (TextView) convertView.findViewById(R.id.textView2);
                holder.mDate = (TextView) convertView.findViewById(R.id.textView1);

                convertView.setTag(holder);

            }else {

                holder = (MainDawerSelectActivity.ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if(mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);

            }else {
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


    public class BuyTask extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public BuyTask(String seq_user) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_user", seq_user)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                okhttp3.Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("TAG", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                System.out.println(jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                System.out.println(ss);

                if (ss == 200) {
                    String resultCode = jsonResponse.getString("resultCode");
                    String resultMsg = jsonResponse.getString("resultMsg");
                    Log.d("resultCode : ", resultCode);
                    Log.d("resultMsg : ", resultMsg);

//                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
//                    System.out.println(json);
//                    JSONObject json2 = new JSONObject(json1.getString("user"));
//                    System.out.println(json2);
                    JSONArray json3 = new JSONArray(jsonResponse.getString("my_kindergarden_list"));
                    System.out.print("xx");
                    System.out.print(json3);
                    System.out.print("xx");
                    JSONObject json4 = new JSONObject(json3.getString(0));
//                    JSONObject json4 = new JSONObject(json3.getString(""));
                    editor = pref.edit();
                    editor.putString("kindergarden_class_name", json4.getString("kindergarden_class_name"));
                    editor.putString("seq_kindergarden", json4.getString("seq_kindergarden"));
                    editor.putString("kindergarden_name", json4.getString("kindergarden_name"));
                    editor.putString("rep_flag", json4.getString("rep_flag"));
                    editor.commit();
                    Log.d("반환값 : ", pref.getString("kindergarden_class_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
                    Log.d("반환값 : ", pref.getString("seq_kindergarden", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
                    Log.d("반환값 : ", pref.getString("kindergarden_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
                    Log.d("반환값 : ", pref.getString("rep_flag", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));

                } else if (ss == 102) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}


