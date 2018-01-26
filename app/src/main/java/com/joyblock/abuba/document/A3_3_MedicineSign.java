package com.joyblock.abuba.document;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.Mypainter;
import com.joyblock.abuba.R;
import com.joyblock.abuba.util.TimeConverter;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okio.Buffer;

import static android.graphics.Bitmap.CompressFormat.PNG;

public class A3_3_MedicineSign extends BaseActivity {

    Mypainter mypainter;
    TextView textView111, textView12, title, timeText;
    boolean aBoolean = true;
    String seq_user_parent, seq_kindergarden, seq_kindergarden_class, seq_kids, symptom,
            medicine_type, dosage, dosage_time, kindergarden_class_name,
            keep_method, uniqueness, year, day, month, files, kindergarden_name, kids_name;
    String getTime, getday, getmonth, getyear;
    byte[] bytes;
    Bitmap bitmap;
    public byte[] byteArray;
    byte[] objects;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_3_medicine_sign);

        Timeget();

        Intent intent = getIntent();
        seq_user_parent = intent.getStringExtra("seq_user_parent");
        seq_kindergarden = intent.getStringExtra("seq_kindergarden");
        seq_kindergarden_class = intent.getStringExtra("seq_kindergarden_class");
        seq_kids = intent.getStringExtra("seq_kids");
        symptom = intent.getStringExtra("symptom");
        medicine_type = intent.getStringExtra("medicine_type");
        dosage = intent.getStringExtra("dosage");
        dosage_time = intent.getStringExtra("dosage_time");
        keep_method = intent.getStringExtra("keep_method");
        uniqueness = intent.getStringExtra("uniqueness");
        getyear = intent.getStringExtra("getyear");
        getmonth = intent.getStringExtra("getmonth");
        getday = intent.getStringExtra("getday");
        bytes = intent.getByteArrayExtra("bytes");
        kindergarden_class_name = pref.getString("kindergarden_class_name", "");

        textView111 = (TextView) findViewById(R.id.textView111);
        textView12 = (TextView) findViewById(R.id.textViewtjaud);

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

        String name = pref.getString("name", "");
        kids_name = pref.getString("kids_name", "");
        seq_kids = pref.getString("seq_kids", "");

        timeText = (TextView) findViewById(R.id.textView120);
        timeText.setText(getTime + " " + name);
        title = (TextView) findViewById(R.id.titleName);
        title.setText(kids_name + "(" + kindergarden_class_name + ")");
        title.setVisibility(View.VISIBLE);

        checkpermission();
        init();

        textView111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = mypainter.Save(getExternalPath() + "sign-img.png");
                Log.d("bitmap", String.valueOf(bitmap));
//                mypainter.Open(getExternalPath() + "sign-img.jpg");
                objects = BitmapToByte(bitmap);
//                System.gc();
                new InsertMedicationRequest(seq_user_parent, seq_kindergarden,
                        seq_kindergarden_class, seq_kids, symptom, medicine_type,
                        dosage, dosage_time, keep_method, uniqueness, getyear, getmonth, getday)
                        .execute();
                Log.d("bitmapsssss", "ssssssss");

                AlertDialog.Builder builder = new AlertDialog.Builder(A3_3_MedicineSign.this);
                builder.setMessage("투약을 의뢰했습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), A3_1_Medicine.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        finish();
                    }
                }).create().show();
            }
        });
    }

    //액티비티 화면 터치시 발생되는 이벤트
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (aBoolean) {
            textView12.setVisibility(View.GONE);
            aBoolean = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void init() {
        mypainter = (Mypainter) findViewById(R.id.painter);
        //디렉토리 생성
        String path = getExternalPath();
        File file = new File(path + "hwimg");
        file.mkdir();
//        String mkdirerrormsg = "디렉토리 생성";
//        if(file.isDirectory() == false)
//        {
//            mkdirerrormsg = "디렉토리 생성 오류";
//        }
//        Toast.makeText(this,mkdirerrormsg,Toast.LENGTH_SHORT).show();
    }

    public String getExternalPath() {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            //sdPath = "mnt/sdcard/";
        } else {
            sdPath = getFilesDir() + "";
        }
//        Toast.makeText(getApplicationContext(),sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }

    public void checkpermission() {
        int permissioninfo = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this,
//                    "SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this,
                        "어플리케이션 설정에서 저장소 사용 권한을 허용해주세요", Toast.LENGTH_SHORT).show();

                //이밑에꺼 해야 권한허용 대화상자가 다시뜸
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }
    }

    public byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (bitmap.getByteCount() > 100000) {
//                bitmap1.compress( PNG, (int) (100*(10000.0/bitmap1.getByteCount())), stream) ;
            bitmap.compress(PNG, 1, stream);
            byteArray = stream.toByteArray();
        } else {
            bitmap.compress(PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
        Log.d("Tag", "size" + byteArray.length);
        Log.d("Tag", "size" + bitmap.getByteCount());
        //Object[] object = {new BitmapDrawable(bitmap), byteArray};
        return byteArray;
    }

    class InsertMedicationRequest extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/insertMedicationRequest.do";
        MultipartBody.Builder builder;

        //투약의뢰서 등록
        public InsertMedicationRequest(String seq_user_parent, String seq_kindergarden, String seq_kindergarden_class,
                                       String seq_kids, String Symptom, String medicine_type, String dosage, String dosage_time,
                                       String keep_method, String uniqueness, String year, String month, String day) {
            client = new OkHttpClient();
            builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//            formBody = new MultipartBody.Builder().setType(MultipartBody.FORM)//new FormBody.Builder()
            builder.addFormDataPart("seq_user_parent", seq_user_parent)
                    .addFormDataPart("seq_kindergarden", seq_kindergarden)
                    .addFormDataPart("seq_kindergarden_class", seq_kindergarden_class)
                    .addFormDataPart("seq_kids", seq_kids)
                    .addFormDataPart("symptom", Symptom)
                    .addFormDataPart("medicine_type", medicine_type)
                    .addFormDataPart("dosage", dosage)
                    .addFormDataPart("dosage_time", dosage_time)
                    .addFormDataPart("keep_method", keep_method)
                    .addFormDataPart("uniqueness", uniqueness)
                    .addFormDataPart("year", year)
                    .addFormDataPart("month", month)
                    .addFormDataPart("day", day)
                    .addFormDataPart("files[0]", TimeConverter.getFileTime()+".png", RequestBody.create(MultipartBody.FORM,objects))
                    .addFormDataPart("files[1]", TimeConverter.getFileTime()+".png", RequestBody.create(MultipartBody.FORM,bytes));// bytes));
//                    .build();
//
//            if(bytes.length > 1) {
//
//            }

            formBody = builder.build();

            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            printRequest("ap18");
        }

               void printRequest(String tag) {
            try {
                final Buffer buffer = new Buffer();
                formBody.writeTo(buffer);
                Log.d(tag + " request", buffer.readUtf8());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    Log.d("bitmapsssssaaaaaaaaaaa", "ssssssss");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
        Log.d("시간은", getday + "" + getmonth + "" + getyear);


    }


}
