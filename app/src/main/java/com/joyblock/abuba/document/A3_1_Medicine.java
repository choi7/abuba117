package com.joyblock.abuba.document;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CalendarCustomDialong_document;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R34_SelectMedicationRequestList;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class A3_1_Medicine extends BaseActivity {

    ListView listView;
    R34_SelectMedicationRequestList[] r34_selectMedicationRequestList;
    A3_ListViewAdapterCustom a3_listViewAdapterCustom;
    CalendarCustomDialong_document mCustomDialog1;
    ConstraintLayout day_ConstraintLayout;
    Integer year, month, day;
    TextView dayText;
    DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_1_medicine);

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
        TextView title=((TextView)findViewById(R.id.titleName));
        title.setText("투약의뢰서");
        title.setVisibility(View.VISIBLE);

        dayText = (TextView) findViewById(R.id.a3_1dayTextView);
        datePicker = (DatePicker) findViewById(R.id.datePicker);


        listView = (ListView) findViewById(R.id.a3_1_listview);
        listView.setAdapter(a3_listViewAdapterCustom);
        day_ConstraintLayout = (ConstraintLayout) findViewById(R.id.a3_1DayConstraintLayout);
        day_ConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mCustomDialog1 = new CalendarCustomDialong_document(A3_1_Medicine.this);
                mCustomDialog1.show();
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
        String url=" http://58.229.208.246/Ububa/selectMedicationRequestOne.do";

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
                r34_selectMedicationRequestList=new GsonBuilder().create().fromJson(jsonResponse.getString("medication_request_list"),R34_SelectMedicationRequestList[].class);
                //for(R13_SelectNoticeList list:noticeList)
                for(int i=0;i<r34_selectMedicationRequestList.length;i++) {
                    R34_SelectMedicationRequestList list=r34_selectMedicationRequestList[i];
                    a3_listViewAdapterCustom.addItem(list.kindergarden_class_name, list.kids_name, getResources().getDrawable(R.drawable.no_check));


                }
                a3_listViewAdapterCustom.notifyDataSetChanged();
                Log.d("Tag","투약의뢰서 조회 길이 : "+r34_selectMedicationRequestList.length);
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


//        mCustomDialog.qa.day = mCustomDialog.datePicker.getDayOfMonth();
//        day = mCustomDialog.datePicker.getDayOfMonth();
//        month = mCustomDialog.datePicker.getMonth() + 1;
//        year = mCustomDialog.datePicker.getYear();



//        day = mCustomDialog1.qas.datePicker.getDayOfMonth();
//        month = mCustomDialog1.qas.datePicker.getMonth();
//        year = mCustomDialog1.qas.datePicker.getYear();


        year = mCustomDialog1.year;
        month = mCustomDialog1.month;
        day = mCustomDialog1.day;



        System.out.println(mCustomDialog1.qas.day + "" + mCustomDialog1.qas.month + "" + mCustomDialog1.qas.year);
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
        dayText.setText(year + "년 " + months + "월 " + days + "일 " + " 24:00");
        mCustomDialog1.dismiss();
    }

}
