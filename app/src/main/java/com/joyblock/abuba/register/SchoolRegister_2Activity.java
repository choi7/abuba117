package com.joyblock.abuba.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.graphics.Color.parseColor;

public class SchoolRegister_2Activity extends BaseActivity {
    //AppCompatActivity
    String classList = "http://58.229.208.246/Ububa/selectKindergardenClassList.do?";
    String id, fullurl, name, birthday, phone, email, address, authority, garden_address, garden_name, garden_class, garden_class_number;
    ArrayList<String> arraylistss = new ArrayList<>();
    ArrayAdapter<String> mAdapters;
    EditText nameT, brithdayT, phoneT, emailT, addressT;
    Integer seq_kindergarden;
//    SharedPreferences pref;
    BuyTask buyTask;

//protected
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_register_2);

        nameT = (EditText) findViewById(R.id.childrenNameEditText);
        brithdayT = (EditText) findViewById(R.id.childrenDayEditText);
        phoneT = (EditText) findViewById(R.id.phoneNumberEditText);
        emailT = (EditText) findViewById(R.id.emailEditText);
        addressT = (EditText) findViewById(R.id.fullAddressEditText);


        Intent ss = getIntent();
        id = ss.getStringExtra("id");

        System.out.println("xxxxx");
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println("xxxxx");

//        pref = getSharedPreferences(id,MODE_PRIVATE);
        name = pref.getString("name","없음");
        phone = pref.getString("phone_no","없음");
        authority = pref.getString("authority","없음");
        email = pref.getString("email","없음");

        seq_kindergarden = Integer.parseInt(ss.getStringExtra("유치원"));
        garden_address = ss.getStringExtra("유치원주소");
        garden_name = ss.getStringExtra("유치원이름");


        TextView txt = (TextView) findViewById(R.id.textView10);
        txt.setText(garden_name);
        nameT.setText(name);
        brithdayT.setText(birthday);
        phoneT.setText(phone);
        emailT.setText(email);
        addressT.setText(garden_address);

        /*
        eee = Integer.parseInt(ss.getStringExtra("유치원"));



        */

        schoolcontact();
//        logingetdata();


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9fcdca));

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("원 등록");
        title.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
//        String ss = intent.getExtras().getString("유치원");
//        String ss =  intent.getStringArrayListExtra("유치원");


        Spinner schoolClassSpinner = (Spinner) findViewById(R.id.SchoolClassSpinner);
        mAdapters = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylistss);
        schoolClassSpinner.setAdapter(mAdapters);
        mAdapters.add("반을 선택하세요");
        schoolClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                garden_class = arraylistss.get(position);
                garden_class_number="1";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button pushBtn = (Button) findViewById(R.id.pushbutton);

        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                pref = getSharedPreferences("temporarily", Activity.MODE_PRIVATE);

                pref.getString("name", "없음");
                String seq_user = pref.getString("seq_user", "없음");
                String autority = pref.getString("authority", "없음");
                String seq_kids = pref.getString("seq_kids","");
                String autorityPush = null;

                if(autority.equals("ROLE_PARENTS")){
                    autorityPush = "p";
                }else if (autority.equals("ROLE_TEACHER")) {
                    autorityPush = "t";
                }

                buyTask = new BuyTask(seq_kindergarden.toString(),seq_user,garden_class_number,autorityPush,seq_kids);
                buyTask.execute();
//                shoolDataRegisterPush();

            }
        });


    }


    public void schoolcontact() {


        try {
            classList = classList + "seq_kindergarden=" + URLEncoder.encode(String.valueOf(seq_kindergarden), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //"http://58.229.208.246/Ububa/selectKindergardenClassList.do?seq_kindergarden=1"

        RequestQueue requestQueue = Volley.newRequestQueue(SchoolRegister_2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, classList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                System.out.println("위에껍니다");

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    System.out.println(jsonResponse);
                    Integer rr = Integer.parseInt(jsonResponse.getString("resultCode"));
                    System.out.println(rr);
                    System.out.println(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("kindergarden_class_list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        System.out.println(json_data.getString("kindergarden_class_name"));
                        arraylistss.add(json_data.getString("kindergarden_class_name"));
                        garden_class_number = json_data.getString("seq_kindergarden_class");
                    }



                    System.out.println(arraylistss);


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
//        mAdapters.notifyDataSetChanged();

    }


    public class BuyTask extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public BuyTask(String seq_kindergarden, String seq_user, String seq_kindergarden_class, String req_flag, String seq_kids) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden",seq_kindergarden)
                    .add("seq_user", seq_user)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("req_flag", req_flag)
                    .add("seq_kids", seq_kids)
//                    .add("cu_num", cu_num + "")
                    .build();

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/inserReqKindergardenApply.do")
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
                System.out.println("반환되는 값 : "+jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                System.out.println(ss);

                if (ss == 200) {
                    String userID = jsonResponse.getString("resultCode");
                    String userPassword = jsonResponse.getString("resultMsg");
                    System.out.println(userID + userPassword);
                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
                    System.out.println(json1);
//                        JSONObject json2 = new JSONObject(json.getString("user"));
//                        System.out.println(json2);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                    builder.setMessage("승인요청을 보냈습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent loginIntent = new Intent(SchoolRegister_2Activity.this, MainActivity.class);
                                    SchoolRegister_2Activity.this.startActivity(loginIntent);
                                    finish();
                                }
                            })
                            .create()
                            .show();
                } else if (ss == 102) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                    builder.setMessage("서버와 통신이 원활하지 않습니다")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                } else if (ss == 101) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                    builder.setMessage("반을 선택해주세요")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }else if (ss == 300) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                    builder.setMessage("네트워크가 불안정합니다. 조금 후에 재시도 해주세요.")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }





    //현재 OKHTTP사용으로 인한 Volley로 적용한 부분은 아래로 다 빼버림
/*
    public void shoolDataRegisterPush() {

        pref = getSharedPreferences(id, Activity.MODE_PRIVATE);

        pref.getString("name", "없음");
        String seq_user = pref.getString("seq_user", "없음");
        String autority = pref.getString("authority", "없음");
        String autorityPush = null;

        if(autority.equals("ROLE_PARTENTS")){
            autorityPush = "P";
        }else if (autority.equals("ROLE_TEACHER")) {
            autorityPush = "t";
        }

        fullurl = "http://58.229.208.246/Ububa/inserReqKindergardenApply.do?";
        try {

            if (seq_kindergarden.toString().length() > 0) {
                fullurl = fullurl + "seq_kindergarden=" + URLEncoder.encode(seq_kindergarden.toString(), "UTF-8");
            }
            if (!seq_user.toString().equals("없음")) {
                fullurl = fullurl + "&seq_user=" + URLEncoder.encode(seq_user, "UTF-8");
            }
            if (garden_class_number.toString().length() > 0) {
                fullurl = fullurl + "&seq_kindergarden_class=" + URLEncoder.encode(garden_class_number, "UTF-8");
            }
            if (autorityPush.toString().length() > 0) {
                fullurl = fullurl + "&req_flag=" + URLEncoder.encode(autorityPush, "UTF-8");
            }
            if (autorityPush.toString() == "P") {
                //이부분 자녀 시퀀스 받아서 적용 시켜야함
                fullurl = fullurl + "&seq_kids=" + URLEncoder.encode(seq_user, "UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(SchoolRegister_2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, fullurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                System.out.println("위에껍니다");
                System.out.println(fullurl);
                System.out.println(fullurl);
                System.out.println(fullurl);

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    System.out.println(jsonResponse);
                    Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                    System.out.println(ss);

                    if (ss == 200) {
                        String userID = jsonResponse.getString("resultCode");
                        String userPassword = jsonResponse.getString("resultMsg");
                        System.out.println(userID + userPassword);
                        JSONObject json = new JSONObject(jsonResponse.getString("retMap"));
                        System.out.println(json);
//                        JSONObject json2 = new JSONObject(json.getString("user"));
//                        System.out.println(json2);

                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                        builder.setMessage("로그인에 성공하였습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent loginIntent = new Intent(SchoolRegister_2Activity.this, MainActivity.class);
                                        SchoolRegister_2Activity.this.startActivity(loginIntent);
                                        finish();
                                    }
                                })
                                .create()
                                .show();
                    } else if (ss == 102) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                        builder.setMessage("서버와 통신이 원활하지 않습니다")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    } else if (ss == 101) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                        builder.setMessage("반을 선택해주세요")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }else if (ss == 300) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SchoolRegister_2Activity.this);
                        builder.setMessage("네트워크가 불안정합니다. 조금 후에 재시도 해주세요.")
                                .setNegativeButton("다시 시도", null)
                                .create()
                                .show();
                    }
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
        System.out.println(stringRequest);
        System.out.println("테스트중입니다");

    }


    public void logingetdata() {


        RequestQueue requestQueue = Volley.newRequestQueue(SchoolRegister_2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://58.229.208.246/Ububa/login.do?id=test0&password=test0", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                System.out.println("위에껍니다");
                System.out.println(fullurl);
                System.out.println(fullurl);
                System.out.println(fullurl);

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    System.out.println(jsonResponse);
                    Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                    System.out.println(ss);

                    if (ss == 200) {
                        String userID = jsonResponse.getString("resultCode");
                        String userPassword = jsonResponse.getString("resultMsg");
                        System.out.println(userID + userPassword);


                        JSONObject jsonResponse1 = jsonResponse.getJSONObject("retMap");
                        JSONObject jsonResponse2 = jsonResponse1.getJSONObject("user");
//                        JSONArray jsonArray = jsonResponse.getJSONArray("retMap");
                        System.out.println(jsonResponse1);
                        System.out.println(jsonResponse1);
                        System.out.println(jsonResponse2);
                        System.out.println(jsonResponse2);

                        name = jsonResponse2.getString("name");
                        phone = jsonResponse2.getString("phone_no");
                        authority = jsonResponse2.getString("authority");
                        email = jsonResponse2.getString("email");
                        System.out.println(name + phone + authority);
                        //주소하나 추가해야함.
//                        name = jsonResponse2.getString("name");
                        nameT.setText(name);
                        brithdayT.setText(birthday);
                        phoneT.setText(phone);
                        emailT.setText(email);
                        addressT.setText(garden_address);

                    }
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
        System.out.println(stringRequest);
        System.out.println("테스트중입니다");


    }


    */

}
