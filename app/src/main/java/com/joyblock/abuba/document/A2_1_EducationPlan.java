package com.joyblock.abuba.document;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.api_message.R27_SelectEducationalPlanOne;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class A2_1_EducationPlan extends BaseActivity {

    String[] list = {"교육계획안", "투약의뢰서", "귀가동의서", "출석부"};
    String plan_flag;
    ListViewAdapter adapter;
    ListView listView;
    SharedPreferences.Editor editor;
    boolean month;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        month=true;// 월간/주간 플래그
        setContentView(R.layout.activity_education);

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
        String notice_title="긴급공지";
        String content="내용없음";
        String files="no files";


        actionbarCustom();
        plan_flag="m";
        callFragment(plan_flag);





        //System.out.println("\n시퀀스 : "+seq_user+" / "+seq_kindergarden);

//        new InsertEducationPlan(seq_user,seq_kindergarden,plan_flag,notice_title,content,files).execute();

//        new SelectEducationPlanList(seq_kindergarden,plan_flag).execute();

//        new DeleteEducationPlan("1").execute();

//        new SelectEducationPlanList(seq_kindergarden,plan_flag).execute();

//        new SelectEducationPlanOne("4").execute();

//        System.out.println("제목 : "+data.seq_educational_plan);

//        new UpdateEducationPlan("4","w","공지변경","냉무").execute();


    }

    private void callFragment(String plan_flag) {

        // 프래그먼트 사용을 위해
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        F2_1_EducationPlan fragment = new F2_1_EducationPlan();
        fragment.setFlag(plan_flag);
        fragment.setPref(pref);
        transaction.replace(R.id.education_plan_fragment_container, fragment);
        transaction.commit();

//        if(month) {
//            // '공지사항 리스트 fragment' 호출
//            FragmentEducationPlan1 fragment1 = new 1FragmentEducationPlan();
//            fragment1.setPref(pref);
//            transaction.replace(R.id.education_plan_fragment_container, fragment1);
//            transaction.commit();
//        }else{
//            // '설문지 fragment' 호출
//            FragmentEducationPlanWeek fragment2 = new FragmentEducationPlanWeek();
//            fragment2.setPref(pref);
//            transaction.replace(R.id.education_plan_fragment_container, fragment2);
//            transaction.commit();
//        }
    }

    public void actionbarCustom() {

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        final TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("교육계획안");
        title.setVisibility(View.VISIBLE);

//
        ImageView imageView = (ImageView) findViewById(R.id.editorImage);
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A2_1_EducationPlan.this, A2_2_EducationPlanEditor.class);
                intent.putExtra("plan_flag",plan_flag);
                A2_1_EducationPlan.this.startActivity(intent);
                finish();
            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(A2_1_EducationPlan.this, A1_DocumentSelect.class);
                A2_1_EducationPlan.this.startActivity(loginIntent);
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
        } if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }


    public void showMonthList(View v){
        plan_flag="m";
        callFragment(plan_flag);
    }

    public void showWeekList(View v){
        plan_flag="w";
        callFragment(plan_flag);
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
                R27_SelectEducationalPlanOne msg=gson.fromJson(jsonResponse.getString("educational_plan"), R27_SelectEducationalPlanOne.class);
//                data=msg;




            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
