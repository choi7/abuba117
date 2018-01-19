package com.joyblock.abuba;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CheckPeopleListActivity extends BaseActivity {

    ListView checkListView;
    Button pushButton;
    CheckPeopleListAdapter checkPeopleListAdapter;
    TextView titleCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_people_list);

        pushButton = (Button) findViewById(R.id.checkPeopleListPushButton);
        checkListView = (ListView) findViewById(R.id.checkPeopleListView);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }

        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("미확인");
        title.setVisibility(View.VISIBLE);

        TextView titleLefttext = (TextView) findViewById(R.id.editorText);
        titleLefttext.setText("등록");
        titleLefttext.setVisibility(View.VISIBLE);

        checkPeopleListAdapter = new CheckPeopleListAdapter(getApplicationContext());
        checkListView.setAdapter(checkPeopleListAdapter);
        checkPeopleListAdapter.addItem("","노랑반","김철수","(부모 : 홍길동)");
        checkPeopleListAdapter.addItem("","노랑반","김철수","(부모 : 홍길동)");
        checkPeopleListAdapter.addItem("","노랑반","김철수","(부모 : 홍길동)");
        checkPeopleListAdapter.addItem("","노랑반","김철수","(부모 : 홍길동)");

        titleCount = (TextView) findViewById(R.id.titleCountText);

        titleCount.setText(String.valueOf(checkPeopleListAdapter.getCount()));

    }
}
