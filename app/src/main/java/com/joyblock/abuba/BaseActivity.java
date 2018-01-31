package com.joyblock.abuba;

import android.Manifest;
import android.app.Activity;
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
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.joyblock.abuba.api.API;
import com.joyblock.abuba.data.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Bitmap.CompressFormat.PNG;

public abstract class BaseActivity extends AppCompatActivity{
    RelativeLayout.LayoutParams rp;
    LinearLayout.LayoutParams lp;

    public API api=new API();
    public Gson gson;
    int width, height;
    public SharedPreferences pref;
    public Typeface NanumSquareBold,NanumSquareExtraBold,NanumSquareLight,NanumSquareRegular;

    public MyApplication app;

    public double latitude,longitude;
    private View v1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new GsonBuilder().create();
        app=(MyApplication)getApplicationContext();
        //액션바가 있을때 하단 명암 제거

        try {
            getSupportActionBar().setElevation(0);
//            getSupportActionBar().setDisplayShowCustomEnabled(true);
//            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//            getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        }catch (Exception e) {
            e.printStackTrace();
        }


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff0099ff));



//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        width = size.x;
//        height = size.y;
        NanumSquareBold=Typeface.createFromAsset(getAssets(),"NanumSquareBold.ttf");
        NanumSquareExtraBold=Typeface.createFromAsset(getAssets(), "NanumSquareExtraBold.ttf");
        NanumSquareLight=Typeface.createFromAsset(getAssets(), "NanumSquareLight.ttf");
        NanumSquareRegular=Typeface.createFromAsset(getAssets(), "NanumSquareRegular.ttf");
        pref = getSharedPreferences("pref", MODE_PRIVATE);

//        try{
//            ImageView backImage = (ImageView) findViewById(R.id.backImage);
//            backImage.setOnClickListener(new View.OnClickListener() {
//            });
//        } catch (NullPointerException ex){
//
//        }


    }

    public void addBacklistner(){
        LinearLayout linear = (LinearLayout)findViewById(R.id.back_linear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent loginIntent = new Intent(NoticeActivity.this, MainDawerSelectActivity.class);
//                NoticeActivity.this.startActivity(loginIntent);
                finish();
            }
        });
    }

    public void clickBack1(View v1){
        finish();
    }


    public void processLocation(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

}
