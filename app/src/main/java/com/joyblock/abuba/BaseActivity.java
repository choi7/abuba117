package com.joyblock.abuba;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Bitmap.CompressFormat.PNG;

public abstract class BaseActivity extends AppCompatActivity {
    RelativeLayout.LayoutParams rp;
    LinearLayout.LayoutParams lp;
    public Gson gson;
    int width, height;
    public SharedPreferences pref;
    public Typeface NanumSquareBold,NanumSquareExtraBold,NanumSquareLight,NanumSquareRegular;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new GsonBuilder().create();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        NanumSquareBold=Typeface.createFromAsset(getAssets(),"NanumSquareBold.ttf");
        NanumSquareExtraBold=Typeface.createFromAsset(getAssets(), "NanumSquareExtraBold.ttf");
        NanumSquareLight=Typeface.createFromAsset(getAssets(), "NanumSquareLight.ttf");
        NanumSquareRegular=Typeface.createFromAsset(getAssets(), "NanumSquareRegular.ttf");

//        rp.width = cp(90);
        pref = getSharedPreferences("pref", MODE_PRIVATE);

    }

    RelativeLayout.LayoutParams rp(View v) {
        return (RelativeLayout.LayoutParams) v.getLayoutParams();
    }

    LinearLayout.LayoutParams lp(View v) {
        return (LinearLayout.LayoutParams) v.getLayoutParams();
    }

    public int cp(int cp) {
        float pixel = width * cp / 1440.0f;
        return (int) pixel;
    }



    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;

    byte[] image;
    ImageView editorImage;
    String mImageCaptureName;
    private String currentPhotoPath;//실제 사진 파일 경로

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
//    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_CODE:
                    sendPicture(data.getData(),context); //갤러리에서 가져오기
                    break;
                case CAMERA_CODE:
//                    getPictureForPhoto(); //카메라에서 가져오기
                    break;
                default:
                    break;
            }
        }
    }








    //선택한 사진 데이터 갤러리 처리
    public void sendPicture(Uri data,Context context) {
        Log.d("data : ", String.valueOf(data));
        String imagePath = getRealPathFromURI(data); // path 경로
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
            bitmap1 = BaseActivity.decodeUri(context, data, 400);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap1.compress( PNG, 30, stream) ;
            image=stream.toByteArray();
            Log.d("Tag",""+stream.size());


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

        if (ContextCompat.checkSelfPermission(BaseActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(BaseActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BaseActivity.this,
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


}
