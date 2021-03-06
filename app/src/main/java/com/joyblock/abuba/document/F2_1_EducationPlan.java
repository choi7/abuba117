package com.joyblock.abuba.document;


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
import com.joyblock.abuba.util.TimeConverter;
import com.joyblock.abuba.api_message.R26_SelectEducationalPlanList;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * Created by hyoshinchoi on 2018. 1. 10..
 */

public class F2_1_EducationPlan extends android.support.v4.app.Fragment {


    SharedPreferences pref;
    String seq_user,seq_kindergarden,seq_kindergarden_class;
    EducationPlanListViewAdapter adapter;
    ListView listView;

    String plan_flag;


    void setFlag(String plan_flag){
        this.plan_flag=plan_flag;
    }



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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_document_education_plan, container, false);

        adapter = new EducationPlanListViewAdapter();

        listView = rootView.findViewById(R.id.planListView);

        listView.setAdapter(adapter);

        String seq_kindergarden=pref.getString("seq_kindergarden","없음");
        String seq_kindergarden_class=pref.getString("seq_kindergarden_class","없음");

//        new SelectNoticeList(seq_kindergarden).execute();
//        Log.d("Tag","공지 개수"+noticeList.length);


//        for(int i=0;i<4;i++ ){
//            adapter.addItem(getResources().getDrawable(R.mipmap.ic_document), "11","11","11");
//        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), A2_3_EducationPlanDetailActivity.class);
                intent.putExtra("seq_educational_plan",message[position].seq_educational_plan);
                intent.putExtra("plan_str",plan_flag.equals("m")?"월간":"주간");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

        new BuyTask(seq_kindergarden,plan_flag).execute();
        return rootView;


//        return inflater.inflate(R.layout.noticelistviewcustom, container, false);


    }

    R26_SelectEducationalPlanList[] message;
    class BuyTask extends AsyncTask<Void, Void, String> {

        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String api="selectEducationalPlanList";


        //해당 반 공지 조회
        public BuyTask(String seq_kindergarden, String plan_flag) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("plan_flag", plan_flag)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/"+api+".do")
                    .post(formBody)
                    .build();
        }

        //해당 반 공지 조회
        public BuyTask(String seq_kindergarden, String plan_flag,String page) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("plan_flag", plan_flag)
                    .add("page", page)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/"+api+".do")
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
                message=new GsonBuilder().create().fromJson(jsonResponse.getString("educational_plan_list"),R26_SelectEducationalPlanList[].class);
                for(R26_SelectEducationalPlanList list:message)
                    adapter.addItem(list.file_path,list.title, TimeConverter.convert(list.reg_date),"API 수정 필요");
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
