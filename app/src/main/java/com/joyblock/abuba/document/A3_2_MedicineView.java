package com.joyblock.abuba.document;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R35_SelectMedicationRequestOne;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class A3_2_MedicineView extends BaseActivity {

    TextView symptomText, medicineTypeText, medicationCapacityText, medicationTimeText, methodOfKeepingText,
            specialNoteText, a3_2_timeText, title, titles, medicineViewPushText;

    ImageView medicinePhotoImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_2_medicine_view);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        addBacklistner();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        titles = (TextView) findViewById(R.id.titleName);

        titles.setVisibility(View.VISIBLE);

        findviewid();
        Intent intent = getIntent();
        String seq_medication_request = intent.getStringExtra("seq_medication_request");
//        Log.d("ee", seq_medication_request);

        //임시로 넣어놓음
        if(seq_medication_request == null) {
            seq_medication_request = "17";
        }
        //주석처리
        new selectMedicationRequestOne(seq_medication_request).execute();

        medicineViewPushText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(A3_2_MedicineView.this);
                builder.setMessage("투약을 완료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent loginIntent = new Intent(A3_2_MedicineView.this, A3_4_MedicineCheck.class);
                                loginIntent.putExtra("kid_name", list.kids_name);
                                loginIntent.putExtra("time", list.year + "." + list.month + "." + list.day + " ");


                                A3_2_MedicineView.this.startActivity(loginIntent);
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show();
            }
        });
    }


    R35_SelectMedicationRequestOne list;

    class selectMedicationRequestOne extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/selectMedicationRequestOne.do";

        //투약의뢰서 조회(상세)
        public selectMedicationRequestOne(String seq_medication_request) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_medication_request", seq_medication_request)
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
                list = new GsonBuilder().create().fromJson(jsonResponse.getString("medication_request"), R35_SelectMedicationRequestOne.class);
                symptomText.setText(list.symptom);
                medicineTypeText.setText(list.medicine_type);
                medicationCapacityText.setText(list.dosage);
                medicationTimeText.setText(list.dosage_time);
                methodOfKeepingText.setText(list.keep_method);
                specialNoteText.setText(list.uniqueness);
                a3_2_timeText.setText(list.year + "." + list.month + "." + list.day + " " + pref.getString("name", ""));
//                title.setText(lists.kids_name);
                Log.d("append_image", list.append_image);
                Picasso.with(A3_2_MedicineView.this).load(list.append_image).into(medicinePhotoImageView);
                titles.setText(list.kids_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void findviewid() {
        symptomText = (TextView) findViewById(R.id.symptomText);
        medicineTypeText = (TextView) findViewById(R.id.medicineTypeText);
        medicationCapacityText = (TextView) findViewById(R.id.medicationCapacityText);
        medicationTimeText = (TextView) findViewById(R.id.medicationTimeText);
        methodOfKeepingText = (TextView) findViewById(R.id.methodOfKeepingText);
        specialNoteText = (TextView) findViewById(R.id.specialNoteText);
        a3_2_timeText = (TextView) findViewById(R.id.a3_2_timeText);
        medicineViewPushText = (TextView) findViewById(R.id.medicineViewPushText);
        medicinePhotoImageView = (ImageView) findViewById(R.id.medicinePhotoImageView1);
    }

}
