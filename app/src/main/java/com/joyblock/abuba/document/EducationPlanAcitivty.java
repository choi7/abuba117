package com.joyblock.abuba.document;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.api_message.R27_SelectEducationPlanOne;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class EducationPlanAcitivty extends BaseActivity {

    String[] list = {"교육계획안", "투약의뢰서", "귀가동의서", "출석부"};
    ListViewAdapter adapter;
    ListView listView;
    SharedPreferences.Editor editor;

//    R27_SelectEducationPlanOne data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.park_activity_education);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);

        final TextView title = (TextView)findViewById(R.id.titleName);
        title.setText("교육계획안");

        title.setVisibility(View.VISIBLE);
        editor=pref.edit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String seq_user = pref.getString("seq_user","없음");
        String seq_kindergarden = pref.getString("seq_kindergarden","없음");
        String plan_flag="w";
        String notice_title="긴급공지";
        String content="내용없음";
        String files="no files";

        System.out.println("\n시퀀스 : "+seq_user+" / "+seq_kindergarden);

//        new InsertEducationPlan(seq_user,seq_kindergarden,plan_flag,notice_title,content,files).execute();

//        new SelectEducationPlanList(seq_kindergarden,plan_flag).execute();

//        new DeleteEducationPlan("1").execute();

//        new SelectEducationPlanList(seq_kindergarden,plan_flag).execute();

        new SelectEducationPlanOne("4").execute();

//        System.out.println("제목 : "+data.seq_educational_plan);

//        new UpdateEducationPlan("4","w","공지변경","냉무").execute();


    }

    class InsertEducationPlan extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/insertEducationalPlan.do";

        public InsertEducationPlan(String seq_user,String seq_kindergarden,String plan_flag,String title,String content,String files) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_user", seq_user)
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("plan_flag", plan_flag)
                    .add("title", title)
                    .add("content", content)
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


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    class UpdateEducationPlan extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/updateEducationalPlan.do";

        public UpdateEducationPlan(String seq_educational_plan,String plan_flag,String title,String content) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_educational_plan", seq_educational_plan)
                    .add("plan_flag", plan_flag)
                    .add("title", title)
                    .add("content", content)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        public UpdateEducationPlan(String seq_educational_plan,String plan_flag,String title,String content,String files) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_educational_plan", seq_educational_plan)
                    .add("plan_flag", plan_flag)
                    .add("title", title)
                    .add("content", content)
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


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    class DeleteEducationPlan extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/deleteEducationalPlan.do";

        public DeleteEducationPlan(String seq_educational_plan){
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_educational_plan", seq_educational_plan)
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
                System.out.println(ss);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SelectEducationPlanList extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/selectEducationalPlanList.do";

        public SelectEducationPlanList(String seq_kindergarden,String plan_flag){
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("plan_flag", plan_flag)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        public SelectEducationPlanList(String seq_kindergarden,String plan_flag,String page){
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("plan_flag", plan_flag)
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
                System.out.println(jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                System.out.println(ss);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class SelectEducationPlanOne extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url="http://58.229.208.246/Ububa/selectEducationalPlanOne.do";

        public SelectEducationPlanOne(String seq_educational_plan){
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_educational_plan", seq_educational_plan)
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

//                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                R27_SelectEducationPlanOne msg=gson.fromJson(jsonResponse.getString("educational_plan"), R27_SelectEducationPlanOne.class);
//                data=msg;




            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
