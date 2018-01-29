package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.joyblock.abuba.bus.TextListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class RegisterAddressSelectActivity extends BaseActivity {

    Button btn;
    String address = "http://58.229.208.246/Ububa/selectAreaList.do?";

    Spinner addressDos, addressSi;
    ArrayList<String> arraylist = new ArrayList<>();
    ArrayList<String> arraylistDetail = new ArrayList<>();
    ArrayAdapter<String> mAdapters, mAdapterSi;
    String addressD, addressS, detailAddress, fullAddress, id, password, checkpassword, name, phone, birthday, birthdays, email, nextaBoolean, whoActivity;
    EditText editText;
    TextListViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_address_select);

        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            password = intent.getStringExtra("password");
            checkpassword = intent.getStringExtra("checkpassword");
            name = intent.getStringExtra("name");
            phone = intent.getStringExtra("phone");
            birthday = intent.getStringExtra("birthday");
            birthdays = intent.getStringExtra("birthdays");
            email = intent.getStringExtra("email");
            nextaBoolean = intent.getStringExtra("nextaBoolean");
            whoActivity = intent.getStringExtra("whoActivity");

        } catch (NullPointerException e) {

        }
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("주소입력");
        title.setVisibility(View.VISIBLE);


        btn = (Button) findViewById(R.id.registerAddressSelectButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (whoActivity) {
                    case "RegisterActivity":
                        editText = (EditText) findViewById(R.id.editText2);
                        detailAddress = editText.getText().toString();
                        fullAddress = addressD + " " + addressS + " " + detailAddress;
                        Intent intent = new Intent(RegisterAddressSelectActivity.this, RegisterActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("password", password);
                        intent.putExtra("checkpassword", checkpassword);
                        intent.putExtra("name", name);
                        intent.putExtra("phone", phone);
                        intent.putExtra("birthday", birthday);
                        intent.putExtra("birthdays", birthdays);
                        intent.putExtra("email", email);
                        intent.putExtra("address", fullAddress);
                        intent.putExtra("addressD", addressD);
                        intent.putExtra("addressS", addressS);
                        intent.putExtra("addressDetail", detailAddress);
                        intent.putExtra("nextaBoolean", nextaBoolean);
                        RegisterAddressSelectActivity.this.startActivity(intent);
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    default:
                        break;
                }


            }
        });


        addressDos = (Spinner) findViewById(R.id.spinner2);
        mAdapters = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylist);

        addressDos.setAdapter(mAdapters);
        mAdapters.add("도 선택");

//        addressDos.setSelection(0);
        addressDos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addressD = arraylist.get(position).toString();
                sicontact();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        addressSi = (Spinner) findViewById(R.id.adressSiSpinner1);

        mAdapterSi = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylistDetail);
        addressSi.setAdapter(mAdapterSi);
        mAdapterSi.add("시 선택");


        addressSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addressS = arraylistDetail.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String ar = "ar1";

        try {
            address = address + "ar_name=" + URLEncoder.encode(ar, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        docontact();
    }


    public void docontact() {


        RequestQueue requestQueue = Volley.newRequestQueue(RegisterAddressSelectActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, address, new Response.Listener<String>() {
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
                    JSONArray jsonArray = jsonResponse.getJSONArray("area_list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        System.out.println(json_data.getString("ar_name"));
                        arraylist.add(json_data.getString("ar_name"));
//                        mAdapters.add(json_data.getString("ar_name"));

                    }


                    System.out.println(arraylist);

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
        System.out.println(requestQueue);
        System.out.println(requestQueue);
        System.out.println(requestQueue);
        System.out.println(requestQueue);
        System.out.println(requestQueue);
        System.out.println("전송후");
        System.out.println("전송후");


        System.out.println(arraylist);
        System.out.println(arraylist);
        System.out.println(arraylist);
        System.out.println(arraylist);

    }

    public void sicontact() {

//        mAdapters.notifyDataSetChanged();
        String address1 = "http://58.229.208.246/Ububa/selectAreaList.do?";
        arraylistDetail.clear();
        mAdapterSi.add("시 선택");

        try {
            address1 = address1 + "ar_name=ar2&ar1=" + URLEncoder.encode(addressD, "UTF-8");
            System.out.println(address1);
            System.out.println(address1);
            System.out.println(address1);
            System.out.println(address1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterAddressSelectActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, address1, new Response.Listener<String>() {
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
                    JSONArray jsonArray = jsonResponse.getJSONArray("area_list");
                    System.out.println(jsonArray);
                    System.out.println(jsonArray);
                    System.out.println(jsonArray);
                    System.out.println(jsonArray);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        System.out.println(json_data.getString("ar_name"));
                        arraylistDetail.add(json_data.getString("ar_name"));
                    }
                    System.out.println(arraylistDetail);
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



}
