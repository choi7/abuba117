package com.joyblock.abuba.document;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CalendarCustomDialong_document;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R34_SelectMedicationRequestList;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class A3_1_Medicine extends BaseActivity {

    ListView mainlistView, listview;
    R34_SelectMedicationRequestList[] r34_selectMedicationRequestList;
    A3_ListViewAdapterCustom a3_listViewAdapterCustom;
    CalendarCustomDialong_document mCustomDialog1;
    ConstraintLayout day_ConstraintLayout, class_ConstraintLayout;
    Integer year, month, day;
    TextView dayText, title, classText;
    Activity activity;
    String seq_kindergarden, seq_kindergarden_class;
    BanListViewAdapter adapter;
    AlertDialog.Builder banListDialogBuilder;//,modDelDialogBuidler;
    DialogInterface banListDialogInterface;//,modeDelDialogInteface;

    R6_SelectKindergardenClassList[] classList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_1_medicine);

        activity = this;

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText("투약의뢰서");
        title.setVisibility(View.VISIBLE);

        dayText = (TextView) findViewById(R.id.a3_1dayTextView);
        seq_kindergarden = pref.getString("seq_kindergarden", "");
        classText = (TextView) findViewById(R.id.a3_1_Class_TextView);


        mainlistView = (ListView) findViewById(R.id.a3_1_listview);
        a3_listViewAdapterCustom = new A3_ListViewAdapterCustom(getBaseContext());




        a3_listViewAdapterCustom.addItem("병아리반","김철수",getResources().getDrawable(R.drawable.no_check));
        a3_listViewAdapterCustom.addItem("병아리반","김철수",getResources().getDrawable(R.drawable.no_check));
        a3_listViewAdapterCustom.addItem("병아리반","김철수",getResources().getDrawable(R.drawable.no_check));
        a3_listViewAdapterCustom.addItem("병아리반","김철수",getResources().getDrawable(R.drawable.no_check));
        mainlistView.setAdapter(a3_listViewAdapterCustom);
        Log.d("ee", String.valueOf(a3_listViewAdapterCustom.getCount()));

        mainlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


        day_ConstraintLayout = (ConstraintLayout) findViewById(R.id.a3_1DayConstraintLayout);
        day_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mCustomDialog1 = new CalendarCustomDialong_document(A3_1_Medicine.this);
                mCustomDialog1.show();
            }
        });

        class_ConstraintLayout = (ConstraintLayout) findViewById(R.id.a3_1ClassConstraintLayout);
        class_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SelectKindergardenClassList(seq_kindergarden).execute();
                Log.d("ss", "ss");
            }
        });

//
//        a3_listViewAdapterCustom.addItem("호랑랑이반","김철수",getResources().getDrawable(R.drawable.no_check));
//        a3_listViewAdapterCustom.addItem("호랑이반","김철수",getResources().getDrawable(R.drawable.no_check));


    }


    class SelectMedicationRequestList extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = " http://58.229.208.246/Ububa/selectMedicationRequestOne.do";

        //투약의뢰서 조회(목록)
        public SelectMedicationRequestList(String seq_kindergarden, String seq_kindergarden_class, String year, String day, String month) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("year", year)
                    .add("month", month)
                    .add("day", day)
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
                System.out.println("반환되는 값 : " + jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                System.out.println(jsonResponse.getString("medication_request_list"));
                r34_selectMedicationRequestList = new GsonBuilder().create().fromJson(jsonResponse.getString("medication_request_list"), R34_SelectMedicationRequestList[].class);
                //for(R13_SelectNoticeList list:noticeList)
                for (int i = 0; i < r34_selectMedicationRequestList.length; i++) {
                    R34_SelectMedicationRequestList list = r34_selectMedicationRequestList[i];
                    a3_listViewAdapterCustom.addItem(list.kindergarden_class_name, list.kids_name, getResources().getDrawable(R.drawable.no_check));


                }
                a3_listViewAdapterCustom.notifyDataSetChanged();
                Log.d("Tag", "투약의뢰서 조회 길이 : " + r34_selectMedicationRequestList.length);
                if (ss == 200) {
                    String userID = jsonResponse.getString("resultCode");
                    String userPassword = jsonResponse.getString("resultMsg");
                    System.out.println(userID + userPassword);
                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
                    System.out.println(json1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void datepush1(View v) {

        day = mCustomDialog1.datePicker.getDayOfMonth();
        month = mCustomDialog1.datePicker.getMonth() + 1;
        year = mCustomDialog1.datePicker.getYear();

        System.out.println(mCustomDialog1.qas.day + "sss" + mCustomDialog1.qas.month + "ssss" + mCustomDialog1.qas.year);
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
        dayText.setText(year + "년 " + months + "월 " + days + "일 ");
        mCustomDialog1.dismiss();
    }


    class SelectKindergardenClassList extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/selectKindergardenClassList.do";

        public SelectKindergardenClassList(String seq_kindergarden) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        public SelectKindergardenClassList(String seq_kindergarden, String page) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("page", page)
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
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                classList = new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_class_list"), R6_SelectKindergardenClassList[].class);

                View view = activity.getLayoutInflater().inflate(R.layout.park_layout_notice_popup_list, null);
                // 해당 뷰에 리스트뷰 호출
                listview = (ListView) view.findViewById(R.id.notice_popup_listview);
                // 리스트뷰에 어댑터 설정
                adapter = new BanListViewAdapter();
                listview.setAdapter(adapter);
                adapter.addItem("전체");
                for (R6_SelectKindergardenClassList list : classList) {
                    adapter.addItem(list.kindergarden_class_name);
                }
                adapter.notifyDataSetChanged();

                // 반 다이얼로그 생성
                banListDialogBuilder = new AlertDialog.Builder(activity);
                // 리스트뷰 설정된 레이아웃
                banListDialogBuilder.setView(view);

//                // 확인버튼
//                banListDialogBuilder.setPositiveButton("확인", null);

                // 반 다이얼로그 보기
                banListDialogInterface = banListDialogBuilder.show();


                //반 다이얼로그 이벤트 처리
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BanListViewItem item = adapter.list.get(position);
                        seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
                        classText.setText(item.getName());
                        Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
                        banListDialogInterface.dismiss();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
