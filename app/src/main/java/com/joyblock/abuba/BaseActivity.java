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
        pref = getSharedPreferences("pref", MODE_PRIVATE);

    }


}
