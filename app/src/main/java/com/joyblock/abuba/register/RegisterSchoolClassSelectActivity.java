package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomDialog;
import com.joyblock.abuba.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RegisterSchoolClassSelectActivity extends BaseActivity {

    String classList = "http://58.229.208.246/Ububa/selectKindergardenClassList.do?";
    TextView schoolname, schoolclass;
    String id, name, phone, email, authority, garden_address, garden_name, garden_class, garden_class_number, alertmsg;
    ArrayList<String> arraylistss = new ArrayList<>();
    ArrayAdapter<String> mAdapters;
    Integer seq_kindergarden;
    Button btn;
    CustomDialog mCustomDialog;

    //protected
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school_class_select);


        Intent ss = getIntent();

        id = ss.getStringExtra("id");

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
        System.out.println(id);
        System.out.println("xxxxx");

//        pref = getSharedPreferences(id,MODE_PRIVATE);
        name = pref.getString("name", "없음");
        phone = pref.getString("phone_no", "없음");
        authority = pref.getString("authority", "없음");
        email = pref.getString("email", "없음");

        seq_kindergarden = Integer.parseInt(ss.getStringExtra("유치원"));
        garden_address = ss.getStringExtra("유치원주소");
        garden_name = ss.getStringExtra("유치원이름");


        TextView txt = (TextView) findViewById(R.id.textView55);
        txt.setText(garden_name);

        schoolcontact();

        ListView schoolClassSpinner = (ListView) findViewById(R.id.schoolClassListView);
        mAdapters = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylistss);
        schoolClassSpinner.setAdapter(mAdapters);
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


}
