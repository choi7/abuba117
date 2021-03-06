package com.joyblock.abuba.notice;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.CustomListViewDialog;
import com.joyblock.abuba.R;
import com.joyblock.abuba.TextDialog;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.graphics.Bitmap.CompressFormat.PNG;

public class NoticeEditorActivity extends BaseActivity {


    RadioGroup rg;
    String checkid = "y", is_reply = "y", seq_user, seq_kindergarden, seq_kindergarden_class = "0", maintitle, intext, files;
    byte[] bytes;
    String noticeEditorPush = "http://58.229.208.246/Ububa/insertNotice.do";
    Boolean imageChange = true;
    EditText titleText, inText;
    TextDialog mCustomDialog;
    boolean cancelAndRegister = true;
    ImageView titleNameRightImage;

    CustomListViewDialog dialog;

    private Uri photoUri;


    String imageName;

    BanListViewAdapter adapter;
    ListView listview;

    Activity activity;
    AlertDialog.Builder banListDialogBuilder;//,modDelDialogBuidler;
    DialogInterface banListDialogInterface;//,modeDelDialogInteface;

    R6_SelectKindergardenClassList[] classList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_editor1);

        activity=this;

        titleText = (EditText) findViewById(R.id.titleText);
        inText = (EditText) findViewById(R.id.inText);
        editorImage = (ImageView) findViewById(R.id.questionnaireImage);

        ImageView pictureRegister = (ImageView) findViewById(R.id.pictureRegisterimageView);
        pictureRegister.setVisibility(View.VISIBLE);
        pictureRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();
            }
        });

        TextView txt = (TextView) findViewById(R.id.comenttextView);
        txt.setVisibility(View.VISIBLE);
        final ImageView replyCheck = (ImageView) findViewById(R.id.replyCheckImage);
        replyCheck.setVisibility(View.VISIBLE);
        replyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageChange) {
                    replyCheck.setImageDrawable(getResources().getDrawable(R.drawable.del));
                    is_reply = "n";
                    imageChange = false;
                } else {
                    replyCheck.setImageDrawable(getResources().getDrawable(R.drawable.ch_off));
                    is_reply = "y";
                    imageChange = true;
                }
            }
        });

        actionbarCustom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }


    }
    TextView title;
    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        title = (TextView) findViewById(R.id.titleName);
        title.setText("전체");
        titleNameRightImage = (ImageView) findViewById(R.id.titleNameRightImage);
        titleNameRightImage.setVisibility(View.VISIBLE);

        seq_user = pref.getString("seq_user","없음");
        seq_kindergarden = pref.getString("seq_kindergarden","없음");

        title.setVisibility(View.VISIBLE);
        title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                api.API_6(seq_kindergarden,"");
                String json = api.getMessage();
                Integer resultcode = null;
                try{
                    JSONObject jsonResponse = new JSONObject(json);
                    resultcode = Integer.parseInt(jsonResponse.getString("resultCode"));
                    Log.d("resultcode" , String.valueOf(resultcode));
                    classList=new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_class_list"),R6_SelectKindergardenClassList[].class);

//                View view = activity.getLayoutInflater().inflate(R.layout.park_layout_notice_popup_list, null);
                    // 해당 뷰에 리스트뷰 호출
                    listview = (ListView)findViewById(R.id.notice_popup_listview);
                    // 리스트뷰에 어댑터 설정
                    adapter=new BanListViewAdapter();
//                listview.setAdapter(adapter);
                    dialog = new CustomListViewDialog(NoticeEditorActivity.this,adapter);
                    adapter.addItem("전체");
                    for(R6_SelectKindergardenClassList list:classList){
                        adapter.addItem(list.kindergarden_class_name);
                    }
                    adapter.notifyDataSetChanged();
                    dialog.show();
                    listview=dialog.getListView();
                    //반 다이얼로그 이벤트 처리

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            BanListViewItem item = adapter.list.get(position);
                            seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
//                        banListDialogInterface.dismiss();
                            title.setText(item.getName());
                            titleNameRightImage.setVisibility(View.GONE);
                            if (title.getText().toString() == "전체") {
                                titleNameRightImage.setVisibility(View.VISIBLE);
                            }
                            Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();

                        }
                    });
                } catch (Exception e) {

                }
//                new SelectKindergardenClassList(seq_kindergarden).execute();

            }

        });

        TextView backText = (TextView) findViewById(R.id.editorTextLeft);
        backText.setVisibility(View.VISIBLE);
        backText.setText("취소");
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAndRegister = false;
                mCustomDialog = new TextDialog(NoticeEditorActivity.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"작성을 취소하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();
            }
        });

        ImageView backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setVisibility(View.GONE);

        TextView textView = (TextView) findViewById(R.id.editorText);
        textView.setVisibility(View.VISIBLE);
        textView.setText("등록");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAndRegister = true;
                mCustomDialog = new TextDialog(NoticeEditorActivity.this, R.layout.dialog_call);
                mCustomDialog.setTexts(new String[]{"등록하시겠습니까?", "취소", "확인"});
                mCustomDialog.show();
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#0099FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }




    String mImageCaptureName;
    private String currentPhotoPath;//실제 사진 파일 경로
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    String imagePath;
    byte[] image;
    ImageView editorImage;

    /*
    파일생성
    카메라로 찍은 사진을 실제 파일로 생성하는 코드
    상단에 지정한 path이름과 일치해야 에러없이 해당 기능을 수행
     */
    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/path/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/path/"

                + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;


    }

    //갤러리에서 사진을 가져오는 경우
    public void selectGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    /*
    선택한 사진 데이터 처리
    카메라로 사진을 찍거나 갤러리에서 사진 선택에 대한 사용자가 응답을 할경우
     'onActivityResult'가 실행되는데 사진을 선택했을 경우 resultCode값은
     'RESULT_OK' 가 취소 했을때는 'RESULT_CANCEL'
    사진 데이터는 intent 타입으로 반환.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
                case CAMERA_CODE:
//                    getPictureForPhoto(); //카메라에서 가져오기
                    break;
                default:
                    break;
            }
        }
    }
//

    //선택한 사진 데이터 갤러리 처리
    private void sendPicture(Uri data) {
        Log.d("data : ", String.valueOf(data));
        imagePath = getRealPathFromURI(data); // path 경로
        Log.d("imagePath : ", imagePath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

//        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        Bitmap bitmap1 = null;

        try {
            bitmap1 = NoticeEditorActivity.decodeUri(this, data, 400);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            if(bitmap1.getByteCount() > 100000) {
//                bitmap1.compress( PNG, (int) (100*(10000.0/bitmap1.getByteCount())), stream) ;
                bitmap1.compress( PNG, 1, stream) ;
                image=stream.toByteArray();
            } else {
                bitmap1.compress( PNG, 100, stream) ;
                image=stream.toByteArray();

            }

            Log.d("Tag","size"+image.length);
            Log.d("Tag","size"+bitmap1.getByteCount());



//            Uri uri = Uri.fromFile(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        editorImage.setImageBitmap(rotate(bitmap1, exifDegree));//이미지 뷰에 비트맵 넣기
//        editorImage.setImageBitmap(bitmap);//이미지 뷰에 비트맵 넣기
        editorImage.setVisibility(View.VISIBLE);
        editorImage.setScaleType(ImageView.ScaleType.CENTER);

        editorImage.setAdjustViewBounds(true);
//        image = bitmapToByteArray(bitmap1);

    }

    //사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }


    //사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }


    //사진의 회전값 가져오기
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;
        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
//        ivImage.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
        editorImage.setImageBitmap(bitmap);//이미지 뷰에 비트맵 넣기
        editorImage.setVisibility(View.VISIBLE);
    }



    //안드로이드 7.0 부터는 앱권한이 적용되지 않아 유저한테 직접 권한을 받는 메소드
    public void checkPermissions(){

        if (ContextCompat.checkSelfPermission(NoticeEditorActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(NoticeEditorActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(NoticeEditorActivity.this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1052);

        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1052: {
                // If request is cancelled, the result
                // arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED ){

                    // permission was granted.

                } else {
                    // Permission denied - Show a message
                    // to inform the user that this app only works
                    // with these permissions granted

                }
                return;
            }

        }
    }


    //비트맵 사이즈를 줄여줌
    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }


    public void clickTextView2(View v) {
        mCustomDialog.dismiss();

    }

    public void clickTextView3(View v) {
        mCustomDialog.dismiss();
        if(cancelAndRegister){
            maintitle = titleText.getText().toString();
            intext = inText.getText().toString();

            Log.d("타이틀" , maintitle);
            Log.d("sub타이틀" , intext);
            Log.d("seq_kin" , seq_kindergarden);
            Log.d("user타이틀" , seq_user);
            Log.d("reply타이틀" , is_reply);
            Log.d("class타이틀" , seq_kindergarden_class);
            Log.d("image타이틀" , String.valueOf(image));

            api.API_10(seq_user, seq_kindergarden, is_reply, seq_kindergarden_class, maintitle, intext, image);
            String json = api.getMessage();
            Integer resultcode = null;
            try{
                JSONObject jsonResponse = new JSONObject(json);
                resultcode = Integer.parseInt(jsonResponse.getString("resultCode"));
                Log.d("resultcode" , String.valueOf(resultcode));
            } catch (Exception e) {
            }
//            InsertNotice buyTask = new InsertNotice(seq_user,seq_kindergarden,is_reply,maintitle, intext);
//            buyTask.execute();
            finish();
        } else {
            finish();
        }
    }

    public void clickTextViewOne(View v) {
        mCustomDialog.dismiss();
    }

}


/*
    public class InsertNotice extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody1;





        public InsertNotice(String seq_user, String seq_kindergarden, String is_reply, String title, String content) {
            client = new OkHttpClient();

            // 현재시간을 msec 으로 구한다.
            long now = System.currentTimeMillis();
            // 현재시간을 date 변수에 저장한다.
            Date date = new Date(now);
            // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // nowDate 변수에 값을 저장한다.
            String formatDate = sdfNow.format(date);

            imageName = seq_user + "_" +seq_kindergarden + ".png";

//            경로에서 파일로 변환시켜서 넣어줘야 문제가 없음. 이부분에서 문제가 있었음
//            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            try {
                formBody1 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("seq_user", seq_user)
                        .addFormDataPart("seq_kindergarden", seq_kindergarden)
                        .addFormDataPart("is_reply", is_reply)
                        .addFormDataPart("title", title)
                        .addFormDataPart("content", content)
                        .addFormDataPart("files", imageName, RequestBody.create(MultipartBody.FORM, image))
                        .build();
            }catch(NullPointerException e){
                formBody1 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("seq_user", seq_user)
                        .addFormDataPart("seq_kindergarden", seq_kindergarden)
                        .addFormDataPart("is_reply", is_reply)
                        .addFormDataPart("title", title)
                        .addFormDataPart("content", content)
                        .build();
            }

            request = new okhttp3.Request.Builder()
                    .url("http://58.229.208.246/Ububa/insertNotice.do")
                    .post(formBody1)
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
                System.out.println(ss);
                if (ss == 200) {
                    String userID = jsonResponse.getString("resultCode");
                    String userPassword = jsonResponse.getString("resultMsg");
                    System.out.println(userID + userPassword);
//                    JSONObject json1 = new JSONObject(jsonResponse.getString("retMap"));
//                    System.out.println(json1);
//                    Intent intent = new Intent(NoticeEditorActivity.this, NoticeActivity.class);
//                    NoticeEditorActivity.this.startActivity(intent);
                    Toast.makeText(getApplicationContext(), "등록완료.",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "잠시 후에 재시도 해주세요.",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */

//
//    class SelectKindergardenClassList extends AsyncTask<Void, Void, String> {
//        OkHttpClient client;
//        okhttp3.Request request;
//        RequestBody formBody;
//        String url="http://58.229.208.246/Ububa/selectKindergardenClassList.do";
//
//        public SelectKindergardenClassList(String seq_kindergarden) {
//            client = new OkHttpClient();
//            formBody = new FormBody.Builder()
//                    .add("seq_kindergarden", seq_kindergarden)
//                    .build();
//            request = new okhttp3.Request.Builder()
//                    .url(url)
//                    .post(formBody)
//                    .build();
//        }
//
//        public SelectKindergardenClassList(String seq_kindergarden,String page) {
//            client = new OkHttpClient();
//            formBody = new FormBody.Builder()
//                    .add("seq_kindergarden", seq_kindergarden)
//                    .add("page", page)
//                    .build();
//            request = new okhttp3.Request.Builder()
//                    .url(url)
//                    .post(formBody)
//                    .build();
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                okhttp3.Response response = client.newCall(request).execute();
//                if (!response.isSuccessful()) {
//                    return null;
//                }
//                return response.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//        boolean flag=false;
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            Log.d("TAG", json);
//            try {
//                JSONObject jsonResponse = new JSONObject(json);
//                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
//                classList=new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_class_list"),R6_SelectKindergardenClassList[].class);
//
////                View view = activity.getLayoutInflater().inflate(R.layout.park_layout_notice_popup_list, null);
//                // 해당 뷰에 리스트뷰 호출
//                listview = (ListView)findViewById(R.id.notice_popup_listview);
//                // 리스트뷰에 어댑터 설정
//                adapter=new BanListViewAdapter();
////                listview.setAdapter(adapter);
//                dialog = new CustomListViewDialog(NoticeEditorActivity.this,adapter);
//                adapter.addItem("전체");
//                for(R6_SelectKindergardenClassList list:classList){
//                    adapter.addItem(list.kindergarden_class_name);
//                }
//                adapter.notifyDataSetChanged();
//                dialog.show();
//
//                /*
//                // 반 다이얼로그 생성
//                banListDialogBuilder= new AlertDialog.Builder(activity);
//                // 리스트뷰 설정된 레이아웃
//                banListDialogBuilder.setView(view);
//
////                // 확인버튼
////                banListDialogBuilder.setPositiveButton("확인", null);
//
//                // 반 다이얼로그 보기
//                banListDialogInterface=banListDialogBuilder.show();
//                */
//
//                listview=dialog.getListView();
//                //반 다이얼로그 이벤트 처리
//
//                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            BanListViewItem item = adapter.list.get(position);
//                            seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
////                        banListDialogInterface.dismiss();
//                            title.setText(item.getName());
//                            titleNameRightImage.setVisibility(View.GONE);
//                            if (title.getText().toString() == "전체") {
//                                titleNameRightImage.setVisibility(View.VISIBLE);
//                            }
//                            Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
