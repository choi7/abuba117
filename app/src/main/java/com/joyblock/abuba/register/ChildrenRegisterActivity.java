package com.joyblock.abuba.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class ChildrenRegisterActivity extends BaseActivity {
//AppCompatActivity
    Spinner nickSpinner;
    EditText nameEditText1, birthdayEditText, birthMonthEdithText, birthYearEditText;
    RadioGroup rg;
    RadioButton manButton, girlButton;
    String id, nick, name, birthDay, birthMonth, birthYear, seq_user, files;
    String gender = "m";
    String inserKidsUrl = "http://58.229.208.246/Ububa/insertKids.do";
    Button nextButton;
    ArrayAdapter<String> mAdapters;
    ArrayList<String> arraylist = new ArrayList<>();
//    SharedPreferences pref;


//protected
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_register);

        nickSpinner = (Spinner)findViewById(R.id.nickSpinner);
        nameEditText1 = (EditText) findViewById(R.id.childrenNameEditText1);
        birthdayEditText = (EditText) findViewById(R.id.childrenDayEditText);
        birthMonthEdithText = (EditText) findViewById(R.id.childrenMonthEditText);
        birthYearEditText = (EditText) findViewById(R.id.childrenYearEditText);
        rg = (RadioGroup) findViewById(R.id.genderRadioGroup);
        rg.setOnCheckedChangeListener(onCheckedChangeListener);
        manButton = (RadioButton) findViewById(R.id.manRadioButton);
        girlButton = (RadioButton) findViewById(R.id.girlRadioButton);

        nextButton = (Button) findViewById(R.id.childrenPushButton);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("자녀등록하기");
        title.setVisibility(View.VISIBLE);



        mAdapters = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylist);
        nickSpinner.setAdapter(mAdapters);
        mAdapters.add("아빠");
        mAdapters.add("엄마");
        mAdapters.add("할아버지");
        mAdapters.add("할머니");

        nickSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nick = arraylist.get(position);
                Log.d("별명",arraylist.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Intent intent = getIntent();
        id = intent.getStringExtra("id");

//        pref = getSharedPreferences(id,MODE_PRIVATE);
        seq_user = pref.getString("seq_user","없음");





        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ChildrenRegisterActivity.this, SchoolRegister_1Activity.class);
                ChildrenRegisterActivity.this.startActivity(intent1);

                /*
                name = nameEditText1.getText().toString();
                birthDay = birthdayEditText.getText().toString();
                birthMonth = birthMonthEdithText.getText().toString();
                birthYear = birthYearEditText.getText().toString();

                System.out.println("xxxxx");
                System.out.println(seq_user);
                System.out.println(name);
                System.out.println(gender);
                System.out.println(birthYear);
                System.out.println(birthMonth);
                System.out.println(birthDay);
                System.out.println(nick);
                System.out.println("xxxxx");

                new BuyTask(seq_user,name,gender,birthYear,birthMonth,birthDay,nick).execute();

*/



//                Intent intent = new Intent(ChildrenRegisterActivity.this, )
            }
        });






    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.manRadioButton) {
                gender = "m";
                Log.d("성별",gender);
            }else if (checkedId == R.id.girlRadioButton){
                gender = "f";
                Log.d("성별",gender);

            }
        }
    };




    public class BuyTask extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public BuyTask(String seq_user, String kids_name, String sex, String birth_year, String birth_month, String birth_day, String name_title) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_user", seq_user)
                    .add("kids_name", kids_name)
                    .add("sex", sex)
                    .add("birth_year", birth_year)
                    .add("birth_month", birth_month)
                    .add("birth_day", birth_day)
//                    .add("files", files +"")
                    .add("name_title", name_title)
//                    .add("cu_num", cu_num + "")
//                    .add("cu_price", cu_price + "")
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(inserKidsUrl)
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
                JSONObject jsonObject = jsonResponse.getJSONObject("retMap");


                String seq_kids = jsonObject.getString("seq_kids");

                SharedPreferences.Editor editor;
                editor = pref.edit();
                editor.putString("seq_kids",seq_kids);
                editor.commit();
                Log.d("seq_kids", String.valueOf(seq_kids));
                Log.d("seq_kids", String.valueOf(seq_kids));
                Log.d("seq_kids", String.valueOf(seq_kids));


                if (ss == 200) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChildrenRegisterActivity.this);
                    builder.setMessage("자녀 등록에 성공했습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ChildrenRegisterActivity.this, SchoolRegister_1Activity.class);
                                    ChildrenRegisterActivity.this.startActivity(intent);
                                    finish();
                                }
                            })
                            .create()
                            .show();


                } else if (ss == 102 | ss == 101) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChildrenRegisterActivity.this);
                    builder.setMessage("회원 등록에 실패했습니다.")
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

}
