package com.joyblock.abuba.document;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CalendarCustomDialong_document;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R13_SelectNoticeList;
import com.joyblock.abuba.api_message.R34_SelectMedicationRequestList;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;
import com.joyblock.abuba.util.TimeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class A3_1_Medicine extends BaseActivity {

    ListView mainlistView, listview,class_listview;
    R34_SelectMedicationRequestList[] r34_selectMedicationRequestList;
    A3_ListViewAdapterCustom a3_listViewAdapterCustom;
    CalendarCustomDialong_document mCustomDialog1;
    ConstraintLayout day_ConstraintLayout, class_ConstraintLayout;
    Integer year, month, day;
    TextView dayText, title, classText;
    Activity activity;
    String seq_kindergarden, seq_kindergarden_class, string_year, string_month, string_day, seq_medication_request;
    BanListViewAdapter adapter;
    AlertDialog.Builder banListDialogBuilder;//,modDelDialogBuidler;
    DialogInterface banListDialogInterface;//,modeDelDialogInteface;

    R6_SelectKindergardenClassList[] classList;

    BanListViewAdapter class_adapter;
    CustomListViewDialog dialog;
    int int_selected_class;

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

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        String getTime = year.format(date);
        String getTime1 = month.format(date);
        String getTime2 = day.format(date);
        dayText.setText(getTime + "년 " + getTime1 + "월 " + getTime2 + "일");




        mainlistView = (ListView) findViewById(R.id.a3_1_listview);
//        a3_listViewAdapterCustom = new A3_ListViewAdapterCustom(getBaseContext());

//        a3_listViewAdapterCustom.addItem("김철수", "병아리반", getResources().getDrawable(R.drawable.no_check));
//        a3_listViewAdapterCustom.addItem("김철수", "병아리반", getResources().getDrawable(R.drawable.no_check));
//        a3_listViewAdapterCustom.addItem("김철수", "병아리반", getResources().getDrawable(R.drawable.no_check));
//        a3_listViewAdapterCustom.addItem("김철수", "병아리반", getResources().getDrawable(R.drawable.no_check));
//        mainlistView.setAdapter(a3_listViewAdapterCustom);

        new SelectKindergardenClassList(seq_kindergarden, getTime, getTime1, getTime2).execute();
//        Log.d("ee", String.valueOf(a3_listViewAdapterCustom.getCount()));

        mainlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(A3_1_Medicine.this, A3_2_MedicineView.class);
                intent.putExtra("seq_medication_request",r34_selectMedicationRequestList[position].seq_medication_request);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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

        new SelectKindergardenClassList(seq_kindergarden, string_year, string_month, string_day).execute();

        mCustomDialog1.dismiss();
    }


    class SelectKindergardenClassList extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/selectKindergardenClassList.do";
        Integer integer;

        public SelectKindergardenClassList(String seq_kindergarden) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            integer = 0;
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
            integer = 1;
        }

        //투약의뢰서 조회(목록)
        public SelectKindergardenClassList(String seq_kindergarden, String year,
                                           String month, String day) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
//                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("year", year)
                    .add("month", month)
                    .add("day", day)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/selectMedicationRequestList.do")
                    .post(formBody)
                    .build();
            integer = 2;
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


            switch (integer) {
                case 0:
                    try {
                        JSONObject jsonResponse = new JSONObject(json);
                        Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                        classList = new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_class_list"), R6_SelectKindergardenClassList[].class);

                        //어댑터 생성 후 customListViewDialog에 반 리스트를 보여준다.
                        class_adapter = new BanListViewAdapter();
                        dialog = new CustomListViewDialog(A3_1_Medicine.this,class_adapter);
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
                                Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        });


                        /*
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
                        */
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case 1:


                    break;
                case 2:

                    try {
                        JSONObject jsonResponse = new JSONObject(json);
                        Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                        r34_selectMedicationRequestList = new GsonBuilder().create().fromJson(jsonResponse.getString("medication_request_list"), R34_SelectMedicationRequestList[].class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    a3_listViewAdapterCustom = new A3_ListViewAdapterCustom(getBaseContext());
                    mainlistView.setAdapter(a3_listViewAdapterCustom);

                    for (int i = 0; i < r34_selectMedicationRequestList.length; i++) {
                        R34_SelectMedicationRequestList list = r34_selectMedicationRequestList[i];
                        Log.d("list", String.valueOf(list));
                        Log.d("lists", String.valueOf(list.kids_name));
                        Log.d("listss", String.valueOf(list.kindergarden_class_name));
//                        adapter.addItem(list.file_path, list.title, TimeConverter.convert(list.reg_date), list.name);
                        a3_listViewAdapterCustom.addItem(list.kids_name, list.kindergarden_class_name, getResources().getDrawable(R.drawable.no_check));
                        a3_listViewAdapterCustom.notifyDataSetChanged();

                    }
                    break;
            }
        }

    }
}
