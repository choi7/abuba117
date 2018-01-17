package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.joyblock.abuba.CustomDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.RegisterSchoolClassSelectActivity;
import com.joyblock.abuba.RegisterStandbyActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SchoolRegisterActivity extends BaseActivity {
    String garden_name, alertmsg;
    CustomDialog mCustomDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_register);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff33cccc));
        TextView title = (TextView) findViewById(R.id.titleName);
        title.setText("원등록");
        title.setVisibility(View.VISIBLE);

        EditText schoolNameText = (EditText) findViewById(R.id.schoolNameText);
        EditText detailText = (EditText) findViewById(R.id.detailText);
        Button nextButton = (Button) findViewById(R.id.registerButton);

        alertmsg = garden_name + "으로\n승인요청하시겠습니까?";
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomDialog = new CustomDialog(SchoolRegisterActivity.this,
                        alertmsg,
                        leftListener,
                        rightListener);
                mCustomDialog.show();
            }
        });


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
            Intent loginIntent = new Intent(SchoolRegisterActivity.this, RegisterStandbyActivity.class);
            loginIntent.putExtra("garden_name", garden_name);
            SchoolRegisterActivity.this.startActivity(loginIntent);
            finish();
        }
    };

}
