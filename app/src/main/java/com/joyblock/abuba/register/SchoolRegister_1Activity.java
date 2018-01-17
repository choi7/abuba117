package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.joyblock.abuba.RegisterSchoolClassSelectActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SchoolRegister_1Activity extends BaseActivity {

    String address = "http://58.229.208.246/Ububa/selectAreaList.do?";

    Spinner addressDos, addressSi;
    ArrayList<String> arraylist = new ArrayList<>();
    ArrayList<String> arraylistDetail = new ArrayList<>();
    ArrayList<String> arraylistSchool = new ArrayList<>();
    ArrayAdapter<String> mAdapters, mAdapterSi, mAdapterSchool;
    String addressD, addressS;
    ListView detailAddress;
    String ids, seq_kindergarden, ar1, ar2, addr_etc, tel_no, fullAddress, kindergarden_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_register_1);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("원검색");
        title.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }


        Intent intent = getIntent();
        ids = intent.getStringExtra("id");



        detailAddress = (ListView) findViewById(R.id.detailAddressListview);
        detailAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fullAddress = ar1 + " " + ar2 + " " + addr_etc;
                Intent intent = new Intent(SchoolRegister_1Activity.this, RegisterSchoolClassSelectActivity.class);
                intent.putExtra("id",ids);
                intent.putExtra("유치원", seq_kindergarden);
                intent.putExtra("유치원주소", fullAddress);
                intent.putExtra("유치원전화번호", tel_no);
                intent.putExtra("유치원이름", kindergarden_name);
                System.out.println(arraylistSchool.get(position));
                SchoolRegister_1Activity.this.startActivity(intent);
            }
        });


        /*
        mAdapterSchool = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylistSchool);
        detailAddress.setAdapter(mAdapterSchool);
        mAdapterSchool.add("어부바 유치원");
        */




        addressDos = (Spinner) findViewById(R.id.spinner2);
        mAdapters = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arraylist);

        addressDos.setAdapter(mAdapters);
        mAdapters.add("도 선택");

//        addressDos.setSelection(0);
        addressDos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addressD = arraylist.get(position).toString();
                System.out.println(addressD);
                Toast.makeText(SchoolRegister_1Activity.this, arraylist.get(position) + "포지션 : " + addressD, Toast.LENGTH_LONG).show();
                sicontact();
                System.out.println(arraylistDetail);


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

                    if(position != 0) {
                        schoolcontact();

                        mAdapterSchool = new ArrayAdapter<String>(SchoolRegister_1Activity.this, R.layout.support_simple_spinner_dropdown_item, arraylistSchool);
                        detailAddress.setAdapter(mAdapterSchool);
                        System.out.println("rsdfsdf");
                        System.out.println(arraylistSchool);
                        System.out.println(arraylistSchool);
                        System.out.println(arraylistSchool);
                        System.out.println(arraylistSchool);
                        System.out.println("sdfsdfsd");
                        mAdapterSchool.notifyDataSetChanged();

                    }




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

        System.out.println("전송전");
        System.out.println("전송전");

        docontact();


        //test용 버튼
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextResister2 = new Intent(SchoolRegister_1Activity.this, SchoolRegister_2Activity.class);
                nextResister2.putExtra("유치원",arraylistSchool);

                SchoolRegister_1Activity.this.startActivity(nextResister2);




            }
        });




//            sicontact();





    }

    public void docontact() {


        RequestQueue requestQueue = Volley.newRequestQueue(SchoolRegister_1Activity.this);
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

        RequestQueue requestQueue = Volley.newRequestQueue(SchoolRegister_1Activity.this);
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


    public void schoolcontact() {

        String address1 = "http://58.229.208.246/Ububa/selectKindergardenList.do?";
        arraylistSchool.clear();
        try {
            address1 = address1 + "ar1=" + URLEncoder.encode(addressD, "UTF-8") + "&ar2=" + URLEncoder.encode(addressS, "UTF-8");
            System.out.println(address1);
            System.out.println(address1);
            System.out.println(address1);
            System.out.println(address1);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(SchoolRegister_1Activity.this);
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
                    JSONArray jsonArray = jsonResponse.getJSONArray("kindergarden_list");
//                    JSONObject json = (JSONObject) jsonArray.get(0);
//                    arraylistSchool.add(json.getString("kindergarden_name"));

                    ar1 = jsonArray.getJSONObject(0).getString("ar1");
                    ar2 = jsonArray.getJSONObject(0).getString("ar2");
                    addr_etc = jsonArray.getJSONObject(0).getString("addr_etc");
                    tel_no = jsonArray.getJSONObject(0).getString("tel_no");

                    System.out.println("dsfsdfsdfsdfdsf");
                    System.out.println(ar1);
                    System.out.println(ar2);
                    System.out.println(addr_etc);
                    System.out.println(tel_no);
                    System.out.println("sdfsdfsdfdfsdfsdf");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject json_data = jsonArray.getJSONObject(i);
                        System.out.println(json_data.getString("kindergarden_name"));
//                        arraylistSchool.add(json_data.getString("kindergarden_name"));
                    }

                    seq_kindergarden = jsonArray.getJSONObject(0).getString("seq_kindergarden");
                    kindergarden_name = jsonArray.getJSONObject(0).getString("kindergarden_name");
                    mAdapterSchool.add(kindergarden_name);

                    System.out.println(arraylistSchool);



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
//        mAdapterSchool.notifyDataSetChanged();

    }

}

