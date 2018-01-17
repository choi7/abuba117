package com.joyblock.abuba.register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.MainDawerSelectActivity;
import com.joyblock.abuba.R;

public class MainActivity extends BaseActivity {
//AppCompatActivity
//    SharedPreferences pref;
    String id;
//    protected
    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        EditText idText = (EditText) findViewById(R.id.idText);
        EditText passwordText = (EditText) findViewById(R.id.birthdayText);
        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Button schoolRegisterButton = (Button) findViewById(R.id.schoolRegisterButton);



        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String userID = intent.getStringExtra("userID");
        String userPassword = intent.getStringExtra("userPassword");
        String message = "환영합니다. " + userID + "님!";

        if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.parseColor("#9FCDCA"));
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9fcdca));

        idText.setText(userID);
        passwordText.setText(userPassword);
        welcomeMessage.setText(message);

        schoolRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                pref = getSharedPreferences("temporarily", MODE_PRIVATE);
                String authority = pref.getString("authority", "없음");
                Log.d("학부모",authority);

                // 원장, 학부모, 교사 일때 들어가는 부분이 각각 액티비티로 전송
                if (authority.equals("ROLE_PARENTS")) {
                    Intent loginIntent = new Intent(MainActivity.this, ChildrenRegisterActivity.class);
                    loginIntent.putExtra("id",id);
                    MainActivity.this.startActivity(loginIntent);
                } else if (authority.equals("ROLE_TEACHER")) {
                    Intent loginIntent = new Intent(MainActivity.this, SchoolRegister_1Activity.class);
                    loginIntent.putExtra("id",id);
                    MainActivity.this.startActivity(loginIntent);
                } else if (authority.equals("ROLE_DIRECTOR_TEACHER")) {
                    Intent loginIntent = new Intent(MainActivity.this, ChildrenRegisterActivity.class);
                    loginIntent.putExtra("id",id);
                    MainActivity.this.startActivity(loginIntent);
                }



            }
        });

        //유치원에 교사가 등록 되었다고 가정한 테스트 버튼
        Button btn = (Button) findViewById(R.id.testbutton2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent test = new Intent(MainActivity.this, MainDawerSelectActivity.class);
                MainActivity.this.startActivity(test);
            }
        });

    }
}
