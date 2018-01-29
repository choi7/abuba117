package com.joyblock.abuba.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.api_message.R2_Login;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.bus.TextListViewAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegisterSchoolClassSelectActivity extends BaseActivity {

    String classList = "http://58.229.208.246/Ububa/selectKindergardenClassList.do?";
    TextView schoolname, schoolclass;
    String id, authority, garden_address, garden_name, alertmsg, getday, getmonth, getyear, getTime, seq_user, seq_kindergarden_class, req_flag, seq_kids;
    ArrayList<String> arraylistss = new ArrayList<>();
    ArrayAdapter<String> mAdapters;
    Integer seq_kindergarden;
    Button btn;
    CustomDialog mCustomDialog;
    TextListViewAdapter adapter;
    R6_SelectKindergardenClassList[] r6_selectKindergardenClassList;
    ListView schoolClassSpinner;
    TextDialog mCustomDialogs;


    //protected
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school_class_select);
        Timeget();

        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        String re_id = pref.getString("register_id","");
        String re_pw = pref.getString("register_password","");
        Log.d("reidpw" ,re_id + "" + re_pw);
        new Login(re_id,re_pw).execute();
        System.out.println("xxxxx");
        System.out.println(id);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("반검색");
        title.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        System.out.println(id);

        seq_kindergarden = Integer.parseInt(intent.getStringExtra("유치원"));
        garden_address = intent.getStringExtra("유치원주소");
        garden_name = intent.getStringExtra("유치원이름");
//         seq_user, seq_kindergarden_class, req_flag, seq_kids, year, month, day

        TextView txt = (TextView) findViewById(R.id.textView55);
        txt.setText(garden_name);

        schoolcontact();

        schoolClassSpinner = (ListView) findViewById(R.id.schoolClassListView);
        adapter = new TextListViewAdapter(1, R.layout.custom_cell_register_school_class_select);
        schoolClassSpinner.setAdapter(adapter);
        schoolClassSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCustomDialogs = new TextDialog(RegisterSchoolClassSelectActivity.this, R.layout.dialog_call);
                mCustomDialogs.setTexts(new String[]{alertmsg, "취소", "확인"});
                mCustomDialogs.show();
                mCustomDialogs.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#595757"));
                mCustomDialogs.findViewById(R.id.textview3).setBackgroundColor(Color.parseColor("#E73828"));
                String ss = r6_selectKindergardenClassList[position].seq_kindergarden;
            }
        });

//        schoolClassSpinner.setAdapter(mAdapters);
        alertmsg = garden_name + "으로\n승인요청하시겠습니까?";
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new CustomDialog(RegisterSchoolClassSelectActivity.this,
                        alertmsg,
                        leftListener,
                        rightListener);
                mCustomDialog.show();
            }
        });


    }


    //유치원에 해당되는 반을 파싱해서 보여줌
    public void schoolcontact() {
        try {
            classList = classList + "seq_kindergarden=" + URLEncoder.encode(String.valueOf(seq_kindergarden), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterSchoolClassSelectActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, classList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                System.out.println("위에껍니다");
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    r6_selectKindergardenClassList = new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_class_list"), R6_SelectKindergardenClassList[].class);
                    for (int i = 0; i < r6_selectKindergardenClassList.length; i++) {
                        R6_SelectKindergardenClassList list = r6_selectKindergardenClassList[i];
                        adapter.addItem(list.kindergarden_class_name);
                        seq_kindergarden_class = list.seq_kindergarden_class;

                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);
    }

    //왼쪽버튼 클릭시 종료버튼
    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
        }
    };

    //오른쪽버튼 클릭시 등록 끝 화면
    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent loginIntent = new Intent(RegisterSchoolClassSelectActivity.this, RegisterStandbyActivity.class);
            loginIntent.putExtra("garden_name", garden_name);
            RegisterSchoolClassSelectActivity.this.startActivity(loginIntent);
            finish();
        }
    };

    public void clickTextView2(View v) {
        mCustomDialogs.dismiss();
        Log.d("전부다", seq_kindergarden + " " + seq_user + " " + seq_kindergarden_class + " " + req_flag + " " + seq_kids);
    }

    public void clickTextView3(View v) {
        mCustomDialogs.dismiss();
        new InserReqKindergardenApply(String.valueOf(seq_kindergarden), seq_user, seq_kindergarden_class, req_flag, seq_kids, getyear, getmonth, getday).execute();
    }

    class InserReqKindergardenApply extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/inserReqKindergardenApply.do";

        //승인요청(선생님, 자녀)
        public InserReqKindergardenApply(String seq_kindergarden, String seq_user, String seq_kindergarden_class, String req_flag, String seq_kids, String year, String month, String day) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_user", seq_user)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("req_flag", req_flag)
                    .add("seq_kids", seq_kids)
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
                if (ss == 200) {
                    Intent loginIntent = new Intent(RegisterSchoolClassSelectActivity.this, RegisterStandbyActivity.class);
                    loginIntent.putExtra("garden_name", garden_name);
                    RegisterSchoolClassSelectActivity.this.startActivity(loginIntent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    R2_Login r2_login;

    public class Login extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public Login(String id, String password) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("id", id)
                    .add("password", password)
//                    .add("cu_num", cu_num + "")
//                    .add("cu_price", cu_price + "")
                    .build();
            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/login.do")
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
                    r2_login = new GsonBuilder().create().fromJson(jsonResponse.getString("retMap"), R2_Login.class);
//                    for(int i=0;i<r2_login.length;i++) {
//                        R2_Login list=r2_login[i];
                        authority = r2_login.authority;
                        seq_user = String.valueOf(r2_login.seq_user);
//                    }
                    switch (authority) {
                        case "ROLE_TEACHER":
                            req_flag = "t";
                            break;
                        case "ROLE_DIRECTOR_TEACHER":
                            break;
                        case "ROLE_PARENTS":
                            req_flag = "p";
                            break;
                    }


//
//                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
//                    System.out.println(json);
//                    JSONObject json2 = new JSONObject(json1.getString("user"));
//                    System.out.println(json2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public void Timeget() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        getTime = sdf.format(date);
        getday = day.format(date);
        getmonth = month.format(date);
        getyear = year.format(date);
        Log.d("시간은", getday + "" + getmonth + "" + getyear);


    }

}
