package com.joyblock.abuba.register;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.ListData;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RegisterJobSelectActivity extends BaseActivity {

    Integer listposition;
    Button btn;
    private ListView mListView = null;
    private RegisterJobSelectActivity.ListViewAdapter mAdapter = null;
    private ArrayList<ListData> mListData = new ArrayList<ListData>();
    String id, password, authority, name, birthday, birthdays, phone, email, ar1, ar2, addr_etc;
    byte[] files;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_job_select);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        birthday = intent.getStringExtra("birthday");
        birthdays = intent.getStringExtra("birthdays");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        ar1 = intent.getStringExtra("ar1");
        ar2 = intent.getStringExtra("ar2");
        addr_etc = intent.getStringExtra("addr_etc");
        files = intent.getByteArrayExtra("files");

//        authority


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("사용자확인");
        title.setVisibility(View.VISIBLE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#33CCCC"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        mListView = (ListView) findViewById(R.id.jobListView);

        mAdapter = new RegisterJobSelectActivity.ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.addItem("원장님");
        mAdapter.addItem("선생님");
        mAdapter.addItem("부모님");


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mListData.size(); i++) {
                    if (i != position) {
                        mListData.get(i).a = true;
                    } else if (mListData.get(position).a) {
                        mListData.get(position).a = false;
                        System.out.println("포지션은 : " + mListData.get(position).a);
                    } else {
                        mListData.get(position).a = true;
                        System.out.println("포지션sdf : " + mListData.get(position).a);
                    }
                }
                mAdapter.notifyDataSetChanged();
                switch (position) {
                    case 0 :
                        authority = "ROLE_DIRECTOR_TEACHER";
                        break;
                    case 1:
                        authority = "ROLE_TEACHER";
                        break;
                    case 2:
                        authority = "ROLE_PARENTS";
                        break;
                }
                listposition = position;
            }
        });


        btn = (Button) findViewById(R.id.register2Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(authority == null) {
                    mCustomDialog = new TextDialog(RegisterJobSelectActivity.this, R.layout.dialog_one_check);
                    mCustomDialog.setTexts(new String[]{"선택해주세요", "확인"});
                    mCustomDialog.show();
                    mCustomDialog.findViewById(R.id.textview2).setBackgroundColor(Color.parseColor("#595757"));
                    mCustomDialog.findViewById(R.id.textview3).setBackgroundColor(Color.parseColor("#E73828"));
                } else {
                    Log.d("ididid", id+" "+password+" "+authority+" "+ name+" "+ birthdays+" "+ phone+" "+email+" "+ar1+" "+ar2+" "+ addr_etc);
                    



                    new InsertUser(id, password, authority, name, birthdays, phone, email, "n", ar1, ar2, addr_etc).execute();
                }


            }
        });


    }
    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }

    TextDialog mCustomDialog;
    public class ViewHolder {
        public TextView mText;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;


        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public void addItem(String mTitle) {
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mTitle = mTitle;

            mListData.add(addInfo);
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RegisterJobSelectActivity.ViewHolder holder;
//            LinearLayout linearLayout = null;
            if (convertView == null) {
                holder = new RegisterJobSelectActivity.ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.joblistviewcustom, null);
                holder.mText = (TextView) convertView.findViewById(R.id.textView53);
//                linearLayout = (LinearLayout) convertView.findViewById(R.id.joblistviewcustomLayout);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ListData mData = mListData.get(position);
            holder.mText.setText(mData.mTitle);
            if (mListData.get(position).a) {
                holder.mText.setTextColor(Color.BLACK);
//                linearLayout.setBackgroundColor(Color.parseColor("#F7F7F7"));
                convertView.setBackgroundColor(Color.parseColor("#F7F7F7"));
            } else {
                holder.mText.setTextColor(Color.WHITE);
//                linearLayout.setBackgroundColor(Color.parseColor("#2CA6E0"));
                convertView.setBackgroundColor(Color.parseColor("#2CA6E0"));
            }
            /*
            //리스트뷰 안에 홀수마다 백그라운드 컬러 지정
            if (position % 2 == 0) {
                convertView.setBackgroundColor(Color.parseColor("#F3F3F4"));
            }
            */
            return convertView;
        }

    }

    public class InsertUser extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;

        public InsertUser(String id, String password, String authority, String name, String birthday, String phone_no, String email, String token, String ar1, String ar2, String addr_etc) {
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
                    .add("ar1", ar1)
                    .add("ar2", ar2)
                    .add("addr_etc", addr_etc)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/insertUser.do")
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
                switch (listposition) {
                    case 0:
                        Intent intent = new Intent(RegisterJobSelectActivity.this, SchoolRegisterActivity.class);
                        intent.putExtra("authority", "ROLE_DIRECTOR_TEACHER");
                        RegisterJobSelectActivity.this.startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Intent intent1 = new Intent(RegisterJobSelectActivity.this, SchoolRegister_1Activity.class);
                        intent1.putExtra("authority", "ROLE_TEACHER");
                        RegisterJobSelectActivity.this.startActivity(intent1);
                        finish();
                        break;
                    case 2:
                        Intent intent2 = new Intent(RegisterJobSelectActivity.this, ChildrenRegisterActivity.class);
                        intent2.putExtra("authority", "ROLE_PARENTS");
                        RegisterJobSelectActivity.this.startActivity(intent2);
                        finish();
                        break;
                }
                if (ss == 200) {
                } else {
                    System.out.println("Error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }




}
