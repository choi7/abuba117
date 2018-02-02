package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R5_SelectKindergardenList;
import com.joyblock.abuba.bus.TextListViewAdapter;

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
    TextListViewAdapter adapter;
    String addressD, addressS, kids_name, sex, birth_year, birth_month, birth_day, files, name_title, activity;
    ListView detailAddress;
    String ids, seq_kindergarden, ar1, ar2, addr_etc, tel_no, fullAddress, kindergarden_name;
    R5_SelectKindergardenList[] r5_selectKindergardenList;



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
        activity = intent.getStringExtra("activity");
        try {
            switch (activity) {
                case "ChildrenRegisterActivity":
                    kids_name = intent.getStringExtra("kids_name");
                    sex = intent.getStringExtra("sex");
                    birth_year = intent.getStringExtra("birth_year");
                    birth_month = intent.getStringExtra("birth_month");
                    birth_day = intent.getStringExtra("birth_day");
                    files = intent.getStringExtra("files");
                    name_title = intent.getStringExtra("name_title");
                    break;
                case "":
                    break;
                case "s":
                    break;
                default:
                    break;
            }
        } catch (NullPointerException e) {

        }


        detailAddress = (ListView) findViewById(R.id.school_detailAddressListview);
        detailAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fullAddress = ar1 + " " + ar2 + " " + addr_etc;
                Intent intent = new Intent(SchoolRegister_1Activity.this, RegisterSchoolClassSelectActivity.class);
                intent.putExtra("id", ids);
                intent.putExtra("유치원", seq_kindergarden);
                intent.putExtra("유치원이름", kindergarden_name);

                try {
                    if (activity.equals("ChildrenRegisterActivity")) {
                        intent.putExtra("kids_name", kids_name);
                        intent.putExtra("sex", sex);
                        intent.putExtra("birth_year", birth_year);
                        intent.putExtra("birth_month", birth_month);
                        intent.putExtra("birth_day", birth_day);
                        intent.putExtra("files", files);
                        intent.putExtra("name_title", name_title);
                        intent.putExtra("activity", "ChildrenRegisterActivity");
                    }
                }catch (NullPointerException e) {

                }


                SchoolRegister_1Activity.this.startActivity(intent);
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

                if (position != 0) {
                    schoolcontact();

//                        mAdapterSchool = new ArrayAdapter<String>(SchoolRegister_1Activity.this, R.layout.support_simple_spinner_dropdown_item, arraylistSchool);
                    //                        detailAddress.setAdapter(mAdapterSchool);
                    adapter = new TextListViewAdapter(2, R.layout.custom_cell_school_register_1);
                    adapter.setToggleOn(false);
                    detailAddress.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    r5_selectKindergardenList = new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_list"), R5_SelectKindergardenList[].class);
                    for (int i = 0; i < r5_selectKindergardenList.length; i++) {
                        R5_SelectKindergardenList list = r5_selectKindergardenList[i];
                        adapter.addItem(list.kindergarden_name, list.ar1 + " " + list.ar2 + " " + list.addr_etc + " / " + list.tel_no);
                        seq_kindergarden = list.seq_kindergarden;
                        kindergarden_name = list.kindergarden_name;
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

}

