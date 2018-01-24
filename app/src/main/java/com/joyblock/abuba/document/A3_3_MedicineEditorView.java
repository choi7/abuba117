package com.joyblock.abuba.document;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class A3_3_MedicineEditorView extends BaseActivity {

    ConstraintLayout constraintLayout1, constraintLayout2;
    ImageView checkImage1, checkImage2, medicinePhotoImageView;
    Integer checkInt;
    TextView pushText, timeText;
    String seq_user_parent, seq_kindergarden, seq_kindergarden_class, seq_kids, Symptom,
            medicine_type, dosage, dosage_time, kindergarden_class_name,
            keep_method, uniqueness, year, day, month, files, kindergarden_name, kids_name;
    String getTime, getday, getmonth, getyear;
    EditText symptomEditText, medicineTypeEditText, medicationCapacityEditText,
            medicationTimeEditText, specialNoteEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_3__medicine_editor_view);


        Timeget();

        findviewbyid();
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

        seq_kindergarden_class = "1";
        seq_user_parent = pref.getString("seq_user", "");
        kindergarden_class_name = pref.getString("kindergarden_class_name", "");
        seq_kindergarden = pref.getString("seq_kindergarden", "");
        kindergarden_name = pref.getString("kindergarden_name", "");
        pref.getString("name_title", "");
        String name = pref.getString("name", "");
        kids_name = pref.getString("kids_name", "");
        seq_kids = pref.getString("seq_kids", "");

        timeText.setText(getTime + " " + name);

        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText(kids_name + "(" + kindergarden_class_name + ")");
        title.setVisibility(View.VISIBLE);


        Log.d("반환값 : ", pref.getString("kindergarden_class_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("seq_kindergarden", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("kindergarden_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("kids_image", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("name_title", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("kids_name", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));
        Log.d("반환값 : ", pref.getString("seq_kids", "ㄴㅇㄹㄴㅇㄹㄴㅇㄹ"));


        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkInt = 0;
                keep_method = "상온";
            }
        });

        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkImage1.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                checkImage2.setImageDrawable(getResources().getDrawable(R.drawable.ch_on));
                checkInt = 1;
                keep_method = "냉장";
            }
        });

        pushText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder nd = new AlertDialog.Builder(A3_3_MedicineEditorView.this);
                nd.setMessage("등록하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Symptom = symptomEditText.getText().toString();
                                medicine_type = medicineTypeEditText.getText().toString();
                                dosage = medicationCapacityEditText.getText().toString();
                                dosage_time = medicationTimeEditText.getText().toString();
//                                keep_method = symptomEditText.getText().toString();
                                uniqueness = specialNoteEditText.getText().toString();
//                                year month day files
                                files = "";
                                new InsertMedicationRequest(seq_user_parent, seq_kindergarden,
                                        seq_kindergarden_class, seq_kids, Symptom, medicine_type,
                                        dosage, dosage_time, keep_method, uniqueness, getyear, getmonth, getday)
                                        .execute();

                                Intent intent = new Intent(A3_3_MedicineEditorView.this, A3_1_Medicine_Parent.class);
                                A3_3_MedicineEditorView.this.startActivity(intent);
                                finish();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
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
                                       String keep_method, String uniqueness, String year, String day, String month) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_user_parent", seq_user_parent)
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("seq_kindergarden_class", seq_kindergarden_class)
                    .add("seq_kids", seq_kids)
                    .add("symptom", Symptom)
                    .add("medicine_type", medicine_type)
                    .add("dosage", dosage)
                    .add("dosage_time", dosage_time)
                    .add("keep_method", keep_method)
                    .add("uniqueness", uniqueness)
                    .add("year", year)
                    .add("month", month)
                    .add("day", day)
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

        checkImage1 = (ImageView) findViewById(R.id.a3_3_temp1ImageView);
        checkImage2 = (ImageView) findViewById(R.id.a3_3_temp2ImageView);
        medicinePhotoImageView = (ImageView) findViewById(R.id.medicinePhotoImageView);

        pushText = (TextView) findViewById(R.id.medicineViewPushText);
        timeText = (TextView) findViewById(R.id.textView120);

        symptomEditText = (EditText) findViewById(R.id.symptomEditText);
        medicineTypeEditText = (EditText) findViewById(R.id.medicineTypeEditText);
        medicationCapacityEditText = (EditText) findViewById(R.id.medicationCapacityEditText);
        medicationTimeEditText = (EditText) findViewById(R.id.medicationTimeEditText);
        specialNoteEditText = (EditText) findViewById(R.id.specialNoteEditText);

    }

    public void Timeget() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        getTime = sdf.format(date);
        getday = day.format(date);
        getmonth = month.format(date);
        getyear = year.format(date);
        Log.d("시간은" , getday+""+getmonth+""+getyear);


    }

}
