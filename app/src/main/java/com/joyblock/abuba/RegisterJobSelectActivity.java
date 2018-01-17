package com.joyblock.abuba;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.joyblock.abuba.register.ChildrenRegisterActivity;
import com.joyblock.abuba.register.RegisterActivity;
import com.joyblock.abuba.register.SchoolRegisterActivity;
import com.joyblock.abuba.register.SchoolRegister_1Activity;

import java.util.ArrayList;

public class RegisterJobSelectActivity extends AppCompatActivity {

    Integer listposition;
    Button btn;
    private ListView mListView = null;
    private RegisterJobSelectActivity.ListViewAdapter mAdapter = null;
    private ArrayList<ListData> mListData = new ArrayList<ListData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_job_select);

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
                listposition = position;
            }
        });


        btn = (Button) findViewById(R.id.register2Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });


    }


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


}
