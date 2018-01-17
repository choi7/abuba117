package com.joyblock.abuba;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
}
