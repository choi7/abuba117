package com.joyblock.abuba.register;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegisterActivity extends BaseActivity {

    String userID, userPassword, userName, userBirtyday, userEmail, userPhoneNumber, nextaBoolean = "n", address, ar1, ar2, addr_etc, birthdays;
    EditText idText, passwordText, passwordText2, nameText, emailText, phonenumberText;
    TextView addrresTextView, birthdayText;
    URL urlss1;
    private Date currentDate;
    private int iYear, iMonth, iDay;
    Button registerButton;
    String fullurl = "http://58.229.208.246/Ububa/insertUser.do?";
    String fullurl12 = "http://58.229.208.246/Ububa/insertUser.do?";
    String job = "ROLE_TEACHER";
    ImageView selectUserCheckIDImageView;

    SharedPreferences.Editor editor;
    BuyTask buyTask;
    Boolean aBoolean = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        Intent intent = getIntent();
//        job = intent.getStringExtra("authority");






        selectUserCheckIDImageView = (ImageView) findViewById(R.id.selectUserCheckIDImageView);
        selectUserCheckIDImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ssss", "eeee");
                new selectUserReduplicationIdCheck(idText.getText().toString().trim()).execute();
            }
        });

        addrresTextView = (TextView) findViewById(R.id.addressTextView);
        addrresTextView.setText(intent.getStringExtra("address"));
        address = intent.getStringExtra("address");
        idText = (EditText) findViewById(R.id.idText);
        idText.setText(intent.getStringExtra("id"));
        passwordText = (EditText) findViewById(R.id.passwordText);
        passwordText.setText(intent.getStringExtra("password"));
        passwordText2 = (EditText) findViewById(R.id.passwordText2);
        passwordText2.setText(intent.getStringExtra("checkpassword"));
        nameText = (EditText) findViewById(R.id.nameText);
        nameText.setText(intent.getStringExtra("name"));
        emailText = (EditText) findViewById(R.id.emailText);
        emailText.setText(intent.getStringExtra("email"));
//        birthdayText = (EditText) findViewById(R.id.birthdayText);
        birthdayText = (TextView) findViewById(R.id.emailText44);
        birthdayText.setText(intent.getStringExtra("birthday"));
      birthdays = intent.getStringExtra("birthdays");

        ar1 = intent.getStringExtra("addressD");
        ar2 = intent.getStringExtra("addressS");
        addr_etc = intent.getStringExtra("addressDetail");
        nextaBoolean = intent.getStringExtra("nextaBoolean");
        if(nextaBoolean == null) {
            nextaBoolean = "n";
        }
        birthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                Date date;
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            Date d = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            userBirtyday = year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일";
                            birthdayText.setText(userBirtyday);
                            birthdays = year+""+(monthOfYear+1)+""+dayOfMonth;
                            Log.d("d", userBirtyday);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datePickerDialog.show();

            }
        });
        phonenumberText = (EditText) findViewById(R.id.phonenumberText);
        phonenumberText.setText(intent.getStringExtra("phone"));
        phonenumberText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton teacherRadioBtn = (RadioButton) findViewById(R.id.teacherRadioButton);
        RadioButton parentsRadioBtn = (RadioButton) findViewById(R.id.parentsRadioButton);

        rg.setOnCheckedChangeListener(radioGrouplistner);


        registerButton = (Button) findViewById(R.id.registerButton);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("회원가입");
        title.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        addrresTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, RegisterAddressSelectActivity.class);
                intent.putExtra("id", idText.getText().toString());
                intent.putExtra("password", passwordText.getText().toString());
                intent.putExtra("checkpassword", passwordText2.getText().toString());
                intent.putExtra("name", nameText.getText().toString());
                intent.putExtra("phone", phonenumberText.getText().toString());
                intent.putExtra("birthday", birthdayText.getText().toString());
                intent.putExtra("birthdays",birthdays);
                intent.putExtra("email", emailText.getText().toString());
                intent.putExtra("nextaBoolean", nextaBoolean);
                intent.putExtra("whoActivity", "RegisterActivity");
                RegisterActivity.this.startActivity(intent);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String a = passwordText.getText().toString();
                String b = passwordText2.getText().toString();
                String name = nameText.getText().toString();
                String phone = phonenumberText.getText().toString();
                String birtyday = birthdayText.getText().toString();
                String email = emailText.getText().toString();
                String ad = addrresTextView.getText().toString();
                Log.d("sss", String.valueOf(!a.equals(null)));
                if(a.equals(b) && nextaBoolean.equals("y") && !a.equals("") && !a.equals(null) && !phone.equals("")
                        && !phone.equals(null) && !name.equals("") && !name.equals(null) && !birtyday.equals("")
                        && !birtyday.equals(null) && !email.equals("") && !email.equals(null) && !ad.equals("") && !ad.equals(null)){
                    editor = pref.edit();
                    editor.putString("register_id", idText.getText().toString());
                    editor.putString("register_password", passwordText.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(RegisterActivity.this, RegisterJobSelectActivity.class);
                    intent.putExtra("id", idText.getText().toString());
                    intent.putExtra("password", passwordText.getText().toString());
                    intent.putExtra("name", nameText.getText().toString());
                    intent.putExtra("phone", phonenumberText.getText().toString());
                    intent.putExtra("birthday", birthdayText.getText().toString());
                    intent.putExtra("birthdays", birthdays);
                    intent.putExtra("email", emailText.getText().toString());
                    intent.putExtra("ar1",ar1);
                    intent.putExtra("ar2",ar2);
                    intent.putExtra("addr_etc",addr_etc);
                    RegisterActivity.this.startActivity(intent);
                } else if(!(a.equals(b))){
                    mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"비밀번호가 맞지 않습니다.", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
                } else if(nextaBoolean.equals("n")) {
                    mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"아이디가 중복인지 확인하세요.", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
                } else {
                    mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"정보를 입력해주세요", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
                }




/*
                switch (job) {
                    case "ROLE_PARENTS":
                        intent = new Intent(RegisterActivity.this, ChildrenRegisterActivity.class);
                        RegisterActivity.this.startActivity(intent);
                        break;
                    case "ROLE_DIRECTOR_TEACHER":
                        intent = new Intent(RegisterActivity.this, SchoolRegisterActivity.class);
                        RegisterActivity.this.startActivity(intent);
                        break;
                    case "ROLE_TEACHER":
                        intent = new Intent(RegisterActivity.this, SchoolRegister_1Activity.class);
                        RegisterActivity.this.startActivity(intent);
                        break;
                }
                */








                                /*
                        new BuyTask(
                                idText.getText().toString(),
                                passwordText.getText().toString(),
                                job,
                                nameText.getText().toString(),
                                birthdayText.getText().toString(),
                                phonenumberText.getText().toString(),
                                emailText.getText().toString(),
                                "0").execute();
                                */


            }
        });


    }


    public void postRegister() {

//        String schoolName = schoolNameText.getText().toString();
//        String phoneNumber = phoneNumberText.getText().toString();
//                String
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, fullurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", userID);
                params.put("password", userPassword);
                params.put("name", userName);
                params.put("birtyday", userBirtyday);
                params.put("phone_no", userPhoneNumber);
                params.put("email", userEmail);
                params.put("token", "true");
                params.put("authority", job);
                System.out.println(params);
                return params;


            }

        };

        requestQueue.add(stringRequest);

    }


    public void stringUrls() {


        try {
//            fullurl = URLEncoder.encode("http://58.229.208.246/Ububa/insertUser.do?", "UTF-8");
//            fullurl = URLEncoder.encode("http://58.229.208.246/Ububa/insertUser.do?", "euc-kr");
            System.out.println(fullurl);
            if (userID.length() > 0) {
                userID = URLEncoder.encode(userID, "UTF-8");
//                String name = URLEncoder.encode("id=", "UTF-8");
                fullurl = fullurl + "id=" + URLEncoder.encode(idText.getText().toString(), "UTF-8");
            }
            if (userPassword.length() > 0) {
                String name = URLEncoder.encode("&password=", "UTF-8");
                fullurl = fullurl + "&password=" + URLEncoder.encode(passwordText.getText().toString(), "UTF-8");
            }
            if (job.length() > 0) {
                String name = URLEncoder.encode("&authority=", "UTF-8");
                fullurl = fullurl + "&authority=" + URLEncoder.encode(job, "UTF-8");
                ;
            }
            if (userEmail.length() > 0) {
                String name = URLEncoder.encode("&email=", "UTF-8");
                fullurl = fullurl + "&email=" + URLEncoder.encode(emailText.getText().toString(), "UTF-8");
            }
            if (userBirtyday.length() > 0) {
//                userName = URLEncoder.encode(userBirtyday,"euc-kr");
                userBirtyday = URLEncoder.encode(birthdayText.getText().toString(), "UTF-8");
                String name = URLEncoder.encode("&birthday=", "UTF-8");
                fullurl = fullurl + "&birthday=" + URLEncoder.encode(birthdayText.getText().toString(), "UTF-8");
            }
            if (userPhoneNumber.length() > 0) {
                userName = URLEncoder.encode(userPhoneNumber, "UTF-8");
                String name = URLEncoder.encode("&phone_no=", "UTF-8");
                fullurl = fullurl + "&phone_no=" + URLEncoder.encode(phonenumberText.getText().toString(), "UTF-8");
            }


            if (userName.length() > 0) {
                String name = URLEncoder.encode("&name=", "UTF-8");
                fullurl = fullurl + "&name=" + URLEncoder.encode(nameText.getText().toString(), "UTF-8");
                System.out.println(fullurl);
                System.out.println(fullurl);
                System.out.println(fullurl);
                System.out.println(fullurl);
                System.out.println(userName);

            }
            System.out.println(fullurl);
            System.out.println(fullurl);
            System.out.println(fullurl);
            System.out.println(fullurl);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        fullurl = URLEncoder.encode()
        System.out.println(fullurl);
    }

    RadioGroup.OnCheckedChangeListener radioGrouplistner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.teacherRadioButton) {
//                Toast.makeText(RegisterActivity.this,"교사 선택하였습니다",Toast.LENGTH_LONG).show();

                job = "ROLE_TEACHER";

            } else if (checkedId == R.id.parentsRadioButton) {
//                Toast.makeText(RegisterActivity.this,"학부모 선택하였습니다",Toast.LENGTH_LONG).show();
                job = "ROLE_PARENTS";

            } else if (checkedId == R.id.directorRadioButton) {

                job = "ROLE_DIRECTOR_TEACHER";
            }
            System.out.println(job);

        }
    };


    protected void getDateToday() {

        currentDate = new Date();
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMon = new SimpleDateFormat("MM");
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

        birthdayText.setText(sdfYear.format(currentDate) + "년" + sdfMon.format(currentDate) + "월" + sdfDay.format(currentDate) + "일");

    }

    protected void updateEditText() {

        StringBuffer sb = new StringBuffer();
        birthdayText.setText(sb.append(iYear + "년").append(iMonth + "월").append(iDay + "일"));

    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            iYear = year;
            iMonth = month;
            iDay = dayOfMonth;
            updateEditText();
        }
    };

    public void onTextClicked(View v) {
        String strDate = birthdayText.getText().toString();
        strDate = strDate.replace("월", "/").replace("일", "/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        try {
            Date pickDate = new Date(strDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(pickDate);
            Dialog dia = null;
            dia = new DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            dia.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onButtonClicked(View v) {

        try {
            Toast toastView = Toast.makeText(this, birthdayText.getText().toString(), Toast.LENGTH_LONG);
            toastView.show();
        } catch (NumberFormatException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    public class BuyTask extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public BuyTask(String id, String password, String authority, String name, String birthday, String phone_no, String email, String token) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("id", id)
                    .add("password", password)
                    .add("authority", authority)
                    .add("name", name)
                    .add("birthday", birthday)
                    .add("phone_no", phone_no)
                    .add("email", email)
                    .add("token", token)
//                    .add("cu_num", cu_num + "")
//                    .add("cu_price", cu_price + "")
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(fullurl12)
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


                if (ss == 200) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("회원 등록에 성공했습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                    finish();
                                }
                            })
                            .create()
                            .show();


                } else if (ss == 102 | ss == 101) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("회원 등록에 실패했습니다.")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                } else if (ss == 301) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("이미 등록된 아이디입니다")
                            .setNegativeButton("다시 시도", null)
                            .create()
                            .show();
                } else {
                    System.out.println("Error");
                    System.out.println("Error");
                    System.out.println("Error");
                    System.out.println("Error");
                    System.out.println("Error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class selectUserReduplicationIdCheck extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public selectUserReduplicationIdCheck(String id) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("id", id)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/selectUserReduplicationIdCheck.do")
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
                if (ss == 200) {
                    mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"등록 가능한 아이디입니다.", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
                    aBoolean = true;
                    nextaBoolean = "y";

                } else if (ss == 101) {
                    mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"다른 아이디로 등록하세요.", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
                } else if (ss == 301) {
                    mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"이미 등록된 아이디입니다.", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    boolean agree = true;
    TextDialog mCustomDialog;

    public void provision_ClickLeft(View v) {
        agree = false;
        mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_call);
        mCustomDialog.setTexts(new String[]{"돌아가시겠습니까?", "취소", "확인"});
        mCustomDialog.show();
        mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#595757"));
        mCustomDialog.findViewById(R.id.textview3).setBackgroundColor(Color.parseColor("#E73828"));

    }

    public void provision_ClickRight(View v) {
//        agree = true;
//        if (check1 && check2) {
//            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
//            RegisterActivity.this.startActivity(intent);
//        } else {
//            mCustomDialog = new TextDialog(RegisterActivity.this, R.layout.dialog_one_check);
//            mCustomDialog.setTexts(new String[]{"모든 약관에 체크하세요", "확인"});
//            mCustomDialog.show();
//            mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#E73828"));
//
//        }


    }


    public void clickTextView2(View v) {
        mCustomDialog.dismiss();

    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        finish();
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }


}
