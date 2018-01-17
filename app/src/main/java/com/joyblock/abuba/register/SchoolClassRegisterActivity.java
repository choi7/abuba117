package com.joyblock.abuba.register;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SchoolClassRegisterActivity extends BaseActivity {
    private final int DYNAMIC_VIEW_ID  = 0x8000;
    private LinearLayout linearLayout;
    Integer numButton = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_class_register);

        BuyTask buyTask = new BuyTask("ar1");

        buyTask.execute();








        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

        Button button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ivBowl = new EditText(SchoolClassRegisterActivity.this);

                ivBowl.setText("hi");
                LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(100, 100);
                layoutParams.setMargins(5, 3, 0, 0); // left, top, right, bottom
                ivBowl.setLayoutParams(layoutParams);
                linearLayout.addView(ivBowl);


            }
        });


    }







    public class BuyTask extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        Request request;
        RequestBody formBody;

        public BuyTask(String cu_mem, String cu_mem2) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("ar_name",cu_mem)
                    .add("ar1", cu_mem2)
//                    .add("cu_num", cu_num + "")
//                    .add("cu_price", cu_price + "")
                    .build();
            request = new Request.Builder()
                    .url("http://58.229.208.246/Ububa/selectAreaList.do")
                    .post(formBody)
                    .build();
        }

        public BuyTask(String cu_mem) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("ar_name",cu_mem)
//                    .add("cu_num", cu_num + "")
//                    .add("cu_price", cu_price + "")
                    .build();
            request = new Request.Builder()
                    .url("http://58.229.208.246/Ububa/selectAreaList.do")
                    .post(formBody)
                    .build();
        }



        @Override
        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(request).execute();
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
        }
    }



    /*
    private void pushButton() {
        numButton++;


        EditText dynamicButton = new EditText(this);
        dynamicButton.setId(DYNAMIC_VIEW_ID + numButton);
        dynamicButton.setText(getString(R.string.btn_name)  + numButton);
        dynamicButton.setOnClickListener(this);

        linearLayout.addView(dynamicButton, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
    */
}
