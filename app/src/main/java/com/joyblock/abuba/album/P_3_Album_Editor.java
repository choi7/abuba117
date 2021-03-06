package com.joyblock.abuba.album;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.drm.DrmStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.google.gson.GsonBuilder;
import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;
import com.joyblock.abuba.notice.NoticeActivity;
import com.joyblock.abuba.notice.NoticeEditorActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static android.graphics.Bitmap.CompressFormat.PNG;

/**
 * Created by BLUE on 2018-01-24.
 */

public class P_3_Album_Editor extends BaseActivity {

    String checkid = "y", is_reply = "y", seq_user, seq_kindergarden, seq_kindergarden_class, maintitle, intext, files;
    String noticeEditorPush = "http://58.229.208.246/Ububa/insertNotice.do";
    Boolean imageChange = true;
    EditText titleText, inText;

    private Uri photoUri;


    String imageName;

    BanListViewAdapter adapter;
    Album_View_Adapter album_view_adapter;
    ListView listview, album_iamge_listview;

    Activity activity;
    AlertDialog.Builder banListDialogBuilder;//,modDelDialogBuidler;
    DialogInterface banListDialogInterface;//,modeDelDialogInteface;

    R6_SelectKindergardenClassList[] classList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_p_3_album_editor);


        activity = this;

        titleText = (EditText) findViewById(R.id.titleText);
        inText = (EditText) findViewById(R.id.inText);
        editorImage = (ImageView) findViewById(R.id.questionnaireImage);
        album_iamge_listview = (ListView) findViewById(R.id.p_3_Album_Editor_ListView);
//        album_iamge_listview.setAdapter(album_view_adapter);


        ImageView pictureRegister = (ImageView) findViewById(R.id.pictureRegisterimageView);
        pictureRegister.setVisibility(View.VISIBLE);
        pictureRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectGallery();

//                selectMutiplyGallery();
//                ArrayList<File> arrayList = new ArrayList<>();
                ImagePicker.create(P_3_Album_Editor.this).showCamera(false).limit(10).start();
//                Log.d("arrayList", String.valueOf(arrayList));

            }
        });

        TextView txt = (TextView) findViewById(R.id.comenttextView);
        txt.setVisibility(View.VISIBLE);
        final ImageView replyCheck = (ImageView) findViewById(R.id.replyCheckImage);
        replyCheck.setVisibility(View.VISIBLE);
        replyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album_view_adapter.notifyDataSetChanged();
                album_iamge_listview.deferNotifyDataSetChanged();

                if (imageChange) {
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


        //리소스를 Uri로 변환하기
        Resources resources = this.getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.alarm_image) + '/' + resources.getResourceTypeName(R.drawable.alarm_image) + '/' + resources.getResourceEntryName(R.drawable.alarm_image));

        album_view_adapter = new Album_View_Adapter();
//        album_iamge_listview.setAdapter(album_view_adapter);
//        album_view_adapter.addItem(uri);
//        album_view_adapter.addItem(uri);
//        album_view_adapter.addItem(uri);
//        album_view_adapter.addItem(uri);
//        album_view_adapter.addItem(uri);
        setListViewHeightBasedOnChildren(album_iamge_listview);



    }
    //설문지 항목 리스트가 추가 될때마다 스크롤이 되는데 이때 스크롤을 없애고 공간을 늘리는 메소드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }


    TextView title;

    public void actionbarCustom() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff66ccff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        title = (TextView) findViewById(R.id.titleName);
        title.setText("전체");

        seq_user = pref.getString("seq_user", "없음");
        seq_kindergarden = pref.getString("seq_kindergarden", "없음");

        title.setVisibility(View.VISIBLE);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new P_3_Album_Editor.SelectKindergardenClassList(seq_kindergarden).execute();

            }

        });

        TextView backText = (TextView) findViewById(R.id.editorTextLeft);
        backText.setVisibility(View.VISIBLE);
        backText.setText("취소");
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder nd = new android.support.v7.app.AlertDialog.Builder(P_3_Album_Editor.this);
                nd.setMessage("작성을 취소하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(P_3_Album_Editor.this, NoticeActivity.class);
                                P_3_Album_Editor.this.startActivity(intent);

                                finish();
                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
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

                maintitle = titleText.getText().toString();
                intext = inText.getText().toString();
//                api.API_51(seq_user, seq_kindergarden, is_reply, seq_kindergarden_class, title, content, files, year, month, day);

                android.support.v7.app.AlertDialog.Builder nd = new android.support.v7.app.AlertDialog.Builder(P_3_Album_Editor.this);
                nd.setMessage("등록하시겠습니까")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                maintitle = titleText.getText().toString();
                                intext = inText.getText().toString();

                                Log.d("타이틀", maintitle);
                                Log.d("sub타이틀", intext);

                                P_3_Album_Editor.InsertNotice buyTask = new P_3_Album_Editor.InsertNotice(seq_user, seq_kindergarden, is_reply, maintitle, intext);
                                buyTask.execute();

                            }
                        })
                        .setPositiveButton("취소", null)
                        .create()
                        .show();
            }
        });


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#66CCFF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
    }


    class InsertNotice extends AsyncTask<Void, Void, String> {
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

            imageName = seq_user + "_" + seq_kindergarden + ".png";

//            경로에서 파일로 변환시켜서 넣어줘야 문제가 없음. 이부분에서 문제가 있었음
//            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


            formBody1 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("seq_user", seq_user)
                    .addFormDataPart("seq_kindergarden", seq_kindergarden)
                    .addFormDataPart("is_reply", is_reply)
                    .addFormDataPart("title", title)
                    .addFormDataPart("content", content)
//                    .addFormDataPart("files", imageName, RequestBody.create(MultipartBody.FORM, image))
                    .build();

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
                    Intent intent = new Intent(P_3_Album_Editor.this, NoticeActivity.class);
                    P_3_Album_Editor.this.startActivity(intent);
                    Toast.makeText(getApplicationContext(), "등록완료.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "잠시 후에 재시도 해주세요.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SelectKindergardenClassList extends AsyncTask<Void, Void, String> {
        OkHttpClient client;
        okhttp3.Request request;
        RequestBody formBody;
        String url = "http://58.229.208.246/Ububa/selectKindergardenClassList.do";

        public SelectKindergardenClassList(String seq_kindergarden) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .build();
            request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        public SelectKindergardenClassList(String seq_kindergarden, String page) {
            client = new OkHttpClient();
            formBody = new FormBody.Builder()
                    .add("seq_kindergarden", seq_kindergarden)
                    .add("page", page)
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
                Integer ss = Integer.parseInt(jsonResponse.getString("resultCode"));
                classList = new GsonBuilder().create().fromJson(jsonResponse.getString("kindergarden_class_list"), R6_SelectKindergardenClassList[].class);

                View view = activity.getLayoutInflater().inflate(R.layout.park_layout_notice_popup_list, null);
                // 해당 뷰에 리스트뷰 호출
                listview = (ListView) view.findViewById(R.id.notice_popup_listview);
                // 리스트뷰에 어댑터 설정
                adapter = new BanListViewAdapter();
                listview.setAdapter(adapter);
                adapter.addItem("전체");
                for (R6_SelectKindergardenClassList list : classList) {
                    adapter.addItem(list.kindergarden_class_name);
                }
                adapter.notifyDataSetChanged();

                // 반 다이얼로그 생성
                banListDialogBuilder = new AlertDialog.Builder(activity);
                // 리스트뷰 설정된 레이아웃
                banListDialogBuilder.setView(view);

//                // 확인버튼
//                banListDialogBuilder.setPositiveButton("확인", null);

                // 반 다이얼로그 보기
                banListDialogInterface = banListDialogBuilder.show();

                //반 다이얼로그 이벤트 처리
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BanListViewItem item = adapter.list.get(position);


                        seq_kindergarden_class = position == 0 ? "0" : classList[position - 1].seq_kindergarden_class;
                        banListDialogInterface.dismiss();
                        title.setText(item.getName());
                        Toast.makeText(getApplicationContext(), position == 0 ? "전체" : item.getName(), Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    String mImageCaptureName;
    private String currentPhotoPath;//실제 사진 파일 경로
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;
    String imagePath;
    byte[][] image=new byte[10][];
    ImageView editorImage;

    /*
    선택한 사진 데이터 처리
    카메라로 사진을 찍거나 갤러리에서 사진 선택에 대한 사용자가 응답을 할경우
     'onActivityResult'가 실행되는데 사진을 선택했을 경우 resultCode값은
     'RESULT_OK' 가 취소 했을때는 'RESULT_CANCEL'
    사진 데이터는 intent 타입으로 반환.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("imagesss", String.valueOf(data.getData()));
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<com.esafirm.imagepicker.model.Image> images = ImagePicker.getImages(data);
            com.esafirm.imagepicker.model.Image image = ImagePicker.getFirstImageOrNull(data);
            Log.d("images", String.valueOf(images));
            Log.d("images", String.valueOf(image));

            Log.d("images", String.valueOf(data.getData()));
//            sendPicture(data.getData());
            printImages(images);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    Log.d("111", String.valueOf(data.getData()));
//                    sendPicture(data.getData()); //갤러리에서 가져오기
//                    sendPicture(data.getClipData()); //갤러리에서 가져오기
                    break;
                case CAMERA_CODE:
//                    getPictureForPhoto(); //카메라에서 가져오기
                    break;
                default:
                    break;
            }
        }
    }

    private void printImages(List<com.esafirm.imagepicker.model.Image> images) {
        if (images == null) return;
        String fileName = "";
        StringBuilder stringBuffer = new StringBuilder();
        image=new byte[10][];
        for (int i = 0, l = images.size(); i < l; i++) {
            Log.d("getpath", images.get(i).getPath());
//            stringBuffer.append(images.get(i).getPath()).append("\n");
            fileName = images.get(i).getPath();
            Uri fileUrl = Uri.parse(fileName);
            String filePath = fileUrl.getPath();
            Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data = '" + filePath + "'", null, null);
            c.moveToNext();
            int id = c.getInt(c.getColumnIndex("_id"));
            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            inText.setText(stringBuffer.toString());
            //        byte[] a1={1,1};
            image[i]=(byte[])(imp.getDrawableAndByteArray(uri,this))[1];


        }

        api.API_51("","","","","","",image,"","","");

//        Log.d("stringbuff", stringBuffer.toString());
    }

//    //선택한 사진 데이터 갤러리 처리
//    private void sendPicture(Uri data) {
//        Log.d("data : ", String.valueOf(data));
//        imagePath = getRealPathFromURI(data); // path 경로
//        Log.d("imagePath : ", imagePath);
//        ExifInterface exif = null;
//        try {
//            exif = new ExifInterface(imagePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        int exifDegree = exifOrientationToDegrees(exifOrientation);
//
////        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
//        Bitmap bitmap1 = null;
//
//        try {
//            bitmap1 = NoticeEditorActivity.decodeUri(this, data, 400);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//            if (bitmap1.getByteCount() > 100000) {
////                bitmap1.compress( PNG, (int) (100*(10000.0/bitmap1.getByteCount())), stream) ;
//                bitmap1.compress(PNG, 1, stream);
//                image = stream.toByteArray();
//            } else {
//                bitmap1.compress(PNG, 100, stream);
//                image = stream.toByteArray();
//
//            }
//
//            Log.d("Tag", "size" + image.length);
//            Log.d("Tag", "size" + bitmap1.getByteCount());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        /*
//        editorImage.setImageBitmap(rotate(bitmap1, exifDegree));//이미지 뷰에 비트맵 넣기
////        editorImage.setImageBitmap(bitmap);//이미지 뷰에 비트맵 넣기
//        editorImage.setVisibility(View.VISIBLE);
//        editorImage.setScaleType(ImageView.ScaleType.CENTER);
//        editorImage.setAdjustViewBounds(true);
//        */
//
//
////        image = bitmapToByteArray(bitmap1);
//
//        Drawable d = new BitmapDrawable(getResources(), bitmap1);
//        album_iamge_listview.setAdapter(album_view_adapter);
//
////        byte[] a1={1,1};
////        byte[] a2={2,1};
////        api.API_51(,,,,,,new byte[][]{a1,a2});
//
//        album_view_adapter.addItem(data);
//        setListViewHeightBasedOnChildren(album_iamge_listview);
//
//
//        Log.d("albumimage", "ss");
//        Log.d("albumimage", String.valueOf(bitmap1));
//        Log.d("albumimage", "ss");
//
//    }

    //사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
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

    //안드로이드 7.0 부터는 앱권한이 적용되지 않아 유저한테 직접 권한을 받는 메소드
    public void checkPermissions() {

        if (ContextCompat.checkSelfPermission(P_3_Album_Editor.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(P_3_Album_Editor.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(P_3_Album_Editor.this,
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
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

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


}
