package com.joyblock.abuba.document;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by hyoshinchoi on 2018. 1. 22..
 */

public class A4_2_HomeCommingEditorView_Parent extends BaseActivity {

    ConstraintLayout constraintLayout1, constraintLayout2, constraintLayout3;
    ImageView checkImage1, checkImage2, checkImage3, medicinePhotoImageView;
    Integer checkInt;
    TextView pushText, timeText;
    String seq_user_parent, seq_kindergarden, seq_kindergarden_class, seq_kids, Symptom,
            medicine_type, dosage, dosage_time,
            keep_method, uniqueness, year, day, month, files;
    EditText symptomEditText, medicineTypeEditText, medicationCapacityEditText,
            medicationTimeEditText, specialNoteEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a4_2_homecomming_editor_view_parent);
        findviewbyid();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String getTime = sdf.format(date);



        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }


        String class_name = pref.getString("kindergarden_class_name","");
        pref.getString("seq_kindergarden","");
        pref.getString("kindergarden_name","");
        pref.getString("name_title","");
        String kids_name = pref.getString("kids_name","");
        String name = pref.getString("name","");
        timeText.setText(getTime + " " + name);

        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText(kids_name+"("+class_name+")");
        title.setVisibility(View.VISIBLE);



        Log.d("반환값 : ", pref.getString("kindergarden_class_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("seq_kindergarden", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("kindergarden_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("kids_image", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("name_title", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("kids_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));




        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage3.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkInt = 0;
            }
        });

        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkImage3.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkInt = 1;
            }
        });

        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage3.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkInt = 2;
            }
        });

    }


    class InsertMedicationRequest extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/insertMedicationRequest.do";

        //투약의뢰서 등록
        public InsertMedicationRequest(String seq_user_parent, String seq_kindergarden, String seq_kindergarden_class,
                                       String seq_kids, String Symptom, String medicine_type, String dosage, String dosage_time,
                                       String keep_method, String uniqueness, String year, String day, String month, String files) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_user_parent", seq_user_parent)
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("seq_kids", seq_kids)
                    .add("Symptom", Symptom)
                    .add("medicine_type", medicine_type)
                    .add("dosage", dosage)
                    .add("dosage_time", dosage_time)
                    .add("keep_method", keep_method)
                    .add("uniqueness", uniqueness)
                    .add("year", year)
                    .add("month", month)
                    .add("day", day)
                    .add("files", files)
                    .build();

            request = new okhttp3.Request.Builder()
                    .url(url)
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
                System.out.println("반환되는 값 : " + jsonResponse);
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                if (ss == 200) {
                    String userID = jsonResponse.getString("resultCode");
                    String userPassword = jsonResponse.getString("resultMsg");
                    System.out.println(userID + userPassword);
                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
                    System.out.println(json1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void findviewbyid() {

        constraintLayout1 = (ConstraintLayout) findViewById(R.id.a_3_3Checktemp1ConstraintLayout);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.a_3_3Checktemp2ConstraintLayout);
        constraintLayout3 = (ConstraintLayout) findViewById(R.id.a_3_3Checktemp3ConstraintLayout);

        checkImage1 = (ImageView) findViewById(R.id.a3_3_temp1ImageView);
        checkImage2 = (ImageView) findViewById(R.id.a3_3_temp2ImageView);
        checkImage3 = (ImageView) findViewById(R.id.a3_3_temp3ImageView);
        medicinePhotoImageView = (ImageView) findViewById(R.id.medicinePhotoImageView);

        pushText = (TextView) findViewById(R.id.medicineViewPushText);
        timeText = (TextView) findViewById(R.id.a3_2_timeText);

        symptomEditText = (EditText) findViewById(R.id.symptomEditText);
        medicineTypeEditText = (EditText) findViewById(R.id.medicineTypeEditText);
        medicationCapacityEditText = (EditText) findViewById(R.id.medicationCapacityEditText);
        medicationTimeEditText = (EditText) findViewById(R.id.medicationTimeEditText);
        specialNoteEditText = (EditText) findViewById(R.id.specialNoteEditText);
    }
}
