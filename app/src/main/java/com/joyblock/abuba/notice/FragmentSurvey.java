package com.joyblock.abuba.notice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R20_SelectSurveyList;
import com.joyblock.abuba.test_park.QuestionnaireDetailActivity;
import com.joyblock.abuba.util.TimeConverter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by hyoshinchoi on 2018. 1. 10..
 */

public class FragmentSurvey extends android.support.v4.app.Fragment {

    SharedPreferences pref;
    String seq_user,seq_kindergarden,seq_kindergarden_class;
    NoticeListViewAdapter adapter;
    ListView listView;

    R20_SelectSurveyList[] surveyList;

    void setPref(SharedPreferences pref){
        this.pref=pref;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        seq_user = pref.getString("seq_user","dd");
//        Log.d("seq_user : ", seq_user);


    }


    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    */




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice_notice, container, false);

        adapter = new NoticeListViewAdapter(getContext());

        listView = rootView.findViewById(R.id.noticeListView);

        listView.setAdapter(adapter);

        String seq_kindergarden=pref.getString("seq_kindergarden","");
        String seq_kindergarden_class=pref.getString("seq_kindergarden_class","");

        new SelectNoticeList(seq_kindergarden).execute();
//        Log.d("Tag","공지 개수"+noticeList.length);


//        for(int i=0;i<4;i++ ){
//            adapter.addItem(getResources().getDrawable(R.mipmap.ic_document), "11","11","11");
//        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), QuestionnaireDetailActivity.class);
                intent.putExtra("seq_survey",surveyList[position].seq_survey);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

        return rootView;


//        return inflater.inflate(R.layout.noticelistviewcustom, container, false);


    }

    class SelectNoticeList extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        String api_string="selectSurveyList";
        String message_key="survey_list";
        String url = "http://58.229.208.246/Ububa/"+api_string+".do";

        //전체 공지 조회
        public SelectNoticeList(String seq_kindergarden) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }


        //해당 반 공지 조회
        public SelectNoticeList(String seq_kindergarden, String seq_kindergarden_class) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        //해당 반 페이지 번호 조회
        public SelectNoticeList(String seq_kindergarden, String seq_kindergarden_class, String page) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("page",page)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        void printRequest(String tag) {
            try {
                final Buffer buffer = new Buffer();
                formBody.writeTo(buffer);
                Log.d(tag+" request", buffer.readUtf8());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            Log.d("API20 response", json);
            try {
                JSONObject jsonResponse = new JSONObject(json);
                System.out.println("반환되는 값 : " + jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                surveyList=new GsonBuilder().create().fromJson(jsonResponse.getString(message_key),R20_SelectSurveyList[].class);
                //for(R13_SelectNoticeList list:noticeList)
                for(int i=0;i<surveyList.length;i++) {
                    R20_SelectSurveyList list=surveyList[i];
                    adapter.addItem(/*list.file_path*/"", list.title, TimeConverter.convert(list.reg_date), "수정 요청");
                }
                adapter.notifyDataSetChanged();
//                Log.d("Tag","공지사항 길이 : "+noticeList.length);
//                if (ss == 200) {
//                    String userID = jsonResponse.getString("resultCode");
//                    String userPassword = jsonResponse.getString("resultMsg");
//                    System.out.println(userID + userPassword);
//                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
//                    System.out.println(json1);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
