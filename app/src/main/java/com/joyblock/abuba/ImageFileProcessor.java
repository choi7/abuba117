package com.joyblock.abuba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.joyblock.abuba.notice.NoticeEditorActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Bitmap.CompressFormat.PNG;

/**
 * Created by POPCON on 2018-01-18.
 */

public class ImageFileProcessor{
    public Uri photoUri;
    int order;
    public String mImageCaptureName,currentPhotoPath;
    byte[] byteArray;
//    Activity activity;
//
//    public ImageFileProcessor(Activity activity){
//        this.activity=activity
//    }

//    private void selectPhoto(Context context) {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(context.getPackageManager()) != null) {
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//                } catch (IOException ex) {
//                }
//                if (photoFile != null) {
//                    photoUri = FileProvider.getUriForFile(context, context.getPackageName(), photoFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                    startActivityForResult(intent, CAMERA_CODE);
//                }
//            }
//        }
//    }

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
    public void selectGallery(Activity activity,int result) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, result);
    }

//    public int getOrder(){
//        return order;
//    }

    /*
    선택한 사진 데이터 처리
    카메라로 사진을 찍거나 갤러리에서 사진 선택에 대한 사용자가 응답을 할경우
     'onActivityResult'가 실행되는데 사진을 선택했을 경우 resultCode값은
     'RESULT_OK' 가 취소 했을때는 'RESULT_CANCEL'
    사진 데이터는 intent 타입으로 반환.
    */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case GALLERY_CODE:
//                    Log.d("Picture",""+data.getIntExtra("num",-1));
//                    sendPicture(data.getData(),questionnaireImage); //갤러리에서 가져오기
//                    break;
//                case CAMERA_CODE:
////                    getPictureForPhoto(); //카메라에서 가져오기
//                    break;
//                default:
//                    break;
//            }
//        }
//    }


    //선택한 사진 데이터 갤러리 처리
    public void sendPicture(Uri data, ImageView target,Activity activity) {
        String imagePath = getRealPathFromURI(activity,data); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap1 = null;
        try {
            bitmap1 = NoticeEditorActivity.decodeUri(activity, data, 400);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (bitmap1.getByteCount() > 100000) {
//                bitmap1.compress( PNG, (int) (100*(10000.0/bitmap1.getByteCount())), stream) ;
                bitmap1.compress(PNG, 1, stream);
                byteArray = stream.toByteArray();
            } else {
                bitmap1.compress(PNG, 100, stream);
                byteArray = stream.toByteArray();
            }
            Log.d("Tag", "size" + byteArray.length);
            Log.d("Tag", "size" + bitmap1.getByteCount());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        target.setImageBitmap(bitmap1);//이미지 뷰에 비트맵 넣기
        target.setVisibility(View.VISIBLE);
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

    //사진의 절대경로 구하기
    private String getRealPathFromURI(Activity activity,Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }



}
