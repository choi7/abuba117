package com.joyblock.abuba.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.joyblock.abuba.BaseActivity;

import com.joyblock.abuba.RegisterJobSelectActivity;
import com.joyblock.abuba.register.MainActivity;
import com.joyblock.abuba.MainDawerSelectActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.register.RegisterActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {
    InputMethodManager imm;
    EditText idText, passwordText;
    Button loginButton, registerButton;
    ConstraintLayout LoginLayout;
    String fullurl11 = "http://58.229.208.246/Ububa/login.do?";
    String id, autoId, autoPassword;
    BuyTask buyTask;
    Boolean loginChecked = false;
    SharedPreferences.Editor editor;
    CheckBox autoLoginCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //fbi 셋팅
        findViewbyIdSet();

        //체크박스의 체크가 풀리면 SharePreferences에 저장되어 있는 데이터를 다 리셋 시킴
        autoLoginCheckBox.setOnCheckedChangeListener(checkedChangeListener);

        //SharePreferences에 값이 저장되어 있는 id와 pass가 있을시 자동으로 로그인
        autoLogin();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //뷰마다 다른 뷰 터치시 키보드 자판을 숨기는 리스너 적용
        LoginLayout.setOnClickListener(myClickListener);
        loginButton.setOnClickListener(myClickListener);
        registerButton.setOnClickListener(myClickListener);

        //로그인화면에서 회원가입 버튼 클릭시 회원가입화면으로 이동
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //로그인 버튼 클릭시 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyTask = new BuyTask(idText.getText().toString(), passwordText.getText().toString());
                buyTask.execute();
            }
        });
    }

    public class BuyTask extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public BuyTask(String id, String password) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("id", id)
                    .add("password", password)
//                    .add("cu_num", cu_num + "")
//                    .add("cu_price", cu_price + "")
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(fullurl11)
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

                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
                    System.out.println(json);
                    JSONObject json2 = new JSONObject(json1.getString("user"));
                    System.out.println(json2);

                    editor = pref.edit();
                    editor.putString("name", json2.getString("name"));
                    editor.putString("phone_no", json2.getString("phone_no"));
                    editor.putString("authority", json2.getString("authority"));
                    editor.putString("seq_user", json2.getString("seq_user"));
                    editor.putString("push_yn", json2.getString("push_yn"));
                    editor.putString("email", json2.getString("email"));
                    editor.putString("token", json2.getString("token"));
                    editor.putString("id", idText.getText().toString());
                    editor.putString("password", passwordText.getText().toString());
                    editor.commit();
                    Log.d("반환값 : ", pref.getString("name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
                    autoId = pref.getString("id", null);
                    autoPassword = pref.getString("password", null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("로그인에 성공하였습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
//                                    Log.d("아이디는 ", id);
                                    loginIntent.putExtra("id", id);
                                    LoginActivity.this.startActivity(loginIntent);
                                    finish();
                                }
                            })
                            .create()
                            .show();
                } else if (ss == 102) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("아이디 또는 비밀번호가 틀립니다")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private boolean loginValidation(String id, String password) {
        if (pref.getString("id", " ").equals(id) && pref.getString("password", " ").equals(password)) {
            // login success
            return true;
        } else if (pref.getString("id", "").equals(null)) {
            // sign in first
            Toast.makeText(LoginActivity.this, "Please Sign in first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }

    //자동 로그인 체크박스 선택시  SharePreferences에 저장되어 있는 아이디와 비밀번호가 일치하는지를 확인하여
    //로그인 액티비티에서 바로 실행될때 자동 로그인하여 메인 액티비티로 이동하는 기능
    public void autoLogin() {
        //처음 로그인시 autoLogin안에 데이터가 있지 않으면 else로 넘어가서 자동로그인이 되지 않음.
        //또한 자동 로그인 체크 박스에 체크가 되어 있으면 SharePreferences안에 있는 autoLogin 값을
        //true로 리턴 시켜줘서 if를 true 값으로 진행시킴
        if (pref.getBoolean("autoLogin", false)) {
            idText.setText(pref.getString("id", " "));
            passwordText.setText(pref.getString("password", " "));
            autoLoginCheckBox.setChecked(true);
            String id = idText.getText().toString();
            String password = passwordText.getText().toString();
            //로그인 버튼 클릭시 SharePreferences 안에 저장되는 값을 가지고 와서 loginValidation에 적용시켜
            //현재 입력된 id, pass값을 비교하여 값이 일치하면 true값을 반영하게 됨.
            Boolean validation = loginValidation(id, password);
            if (validation) {
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                // save id, password to Database
                Log.d("login : ", String.valueOf(loginChecked));
                Intent intent = new Intent(LoginActivity.this, MainDawerSelectActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
                if (loginChecked) {
                    // if autoLogin Checked, save values
                    editor.putString("id", autoId);
                    editor.putString("password", autoPassword);
                    editor.commit();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }


    public void findViewbyIdSet() {
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.birthdayText);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        LoginLayout = (ConstraintLayout) findViewById(R.id.LoginLayout);

        autoLoginCheckBox = (CheckBox) findViewById(R.id.autoLoginCheckBox);
    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener;

    {
        checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginChecked = true;

                    Log.d("아이디는", pref.getString("id", ""));
                    Log.d("아이디는", pref.getString("password", ""));
                    Log.d("아이디는", pref.getString("authority", ""));
                    Log.d("아이디는", pref.getString("phone_no", ""));
                    Log.d("아이디는", pref.getString("seq_user", ""));

                    editor = pref.edit();
                    editor.putBoolean("autoLogin", true);
                    editor.commit();
                } else {
                    loginChecked = false;
                    editor = pref.edit();
                    editor.putBoolean("autoLogin", false);
                    editor.commit();
                    /*
                    editor.clear();
                    editor.commit();
                    */

                    Log.d("아이디는", pref.getString("id", ""));
                    Log.d("아이디는", pref.getString("password", ""));
                    Log.d("아이디는", pref.getString("authority", ""));
                    Log.d("아이디는", pref.getString("phone_no", ""));
                    Log.d("아이디는", pref.getString("seq_user", ""));

                }
            }
        };
    }

    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            imm.hideSoftInputFromWindow(idText.getWindowToken(), 0);
            /*switch (view.getId()) {
                case R.id.LoginLayout:
                    break;
            }*/
        }
    };

}



/*
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
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

//                                JSONArray ss = jsonResponse.getString("retMap");

                                JSONObject json = new JSONObject(jsonResponse.getString("retMap"));
                                System.out.println(json);
                                JSONObject json2 = new JSONObject(json.getString("user"));
                                System.out.println(json2);
                                user_data.name = json2.getString("name");
                                user_data.phone_no = json2.getString("phone_no");
                                user_data.authority = json2.getString("authority");
                                user_data.seq_user = json2.getString("seq_user");
                                user_data.push_yn = json2.getString("push_yn");
                                user_data.email = json2.getString("email");
                                user_data.token = json2.getString("token");

                                pref = getSharedPreferences("test_User_data", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("name",json2.getString("name"));
                                editor.putString("phone_no",json2.getString("phone_no"));
                                editor.putString("authority",json2.getString("authority"));
                                editor.putString("seq_user",json2.getString("seq_user"));
                                editor.putString("push_yn",json2.getString("push_yn"));
                                editor.putString("email",json2.getString("email"));
                                editor.putString("token",json2.getString("token"));
                                editor.commit();

                                String authority = jsonResponse.getString("retMap");

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 성공하였습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
//                                                loginIntent.putExtra("userID", userID);
//                                                loginIntent.putExtra("userPassword", userPassword);
                                                LoginActivity.this.startActivity(loginIntent);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else if (ss == 102) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("아이디 또는 비밀번호가 틀립니다")
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

                */

        /*
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff71cac2));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("로그인");
        title.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#71CAC2"));
        }
        */



        /*

        id = idText.getText().toString();
                fullurl = "http://58.229.208.246/Ububa/login.do?";
                try {
                    if (idText.length() > 0) {
                        fullurl = fullurl + "id=" + URLEncoder.encode(idText.getText().toString(), "UTF-8");
                    }
                    if (passwordText.length() > 0) {
                        fullurl = fullurl + "&password=" + URLEncoder.encode(passwordText.getText().toString(), "UTF-8");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

         */