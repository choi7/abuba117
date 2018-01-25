package com.joyblock.abuba;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by BLUE on 2018-01-25.
 */

public class Mypainter extends View {
    int oldX, oldY = -1;
    Bitmap mbitmap;
    Canvas mcanvas;
    Paint mpaint = new Paint();
    Bitmap img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    boolean check, scaled;
    Path path = new Path();

    public Mypainter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public Mypainter(Context context) {
        super(context);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeWidth(3);
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mcanvas = new Canvas();
        //빗맵과 캔버스 연결
        mcanvas.setBitmap(mbitmap);
        mcanvas.drawColor(Color.WHITE);
    }

    private void drawStamp(int X, int Y) {
        if (scaled == true) {
            Bitmap bigimg = Bitmap.createScaledBitmap(img, img.getWidth() * 2, img.getHeight() * 2, false);
            mcanvas.drawBitmap(bigimg, X, Y, mpaint);

        } else {
            mcanvas.drawBitmap(img, X, Y, mpaint);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mbitmap != null)
            canvas.drawBitmap(mbitmap, 0, 0, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);

/*
            float x = event.getX();
            float y  = event.getY();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y); // 자취에 그리지 말고 위치만 이동
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y); // 자취에 선을 그리기
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate(); // 화면을 다시그리기
        */

        /////////////////////
        int X = (int) event.getX();
        int Y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            oldX = X;
            oldY = Y;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (oldX != -1) {
                if (!check) //체크안됐음
                {
                    mcanvas.drawLine(oldX, oldY, X, Y, mpaint);
                }
                invalidate();
                oldX = X;
                oldY = Y;
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (oldX != -1) {
                if (check) {
                    drawStamp(X, Y);
                } else {
                    mcanvas.drawLine(oldX, oldY, X, Y, mpaint);
                }
            }
            invalidate();
            oldX = -1;
            oldY = -1;
        }

        return true;
    }


    public void Open(String filename) {
        try {
            FileInputStream in = new FileInputStream(filename);
            mcanvas.scale(0.5f, 0.5f);
            mcanvas.drawBitmap(BitmapFactory.decodeStream(in), mcanvas.getWidth() / 2, mcanvas.getHeight() / 2, mpaint);
            mcanvas.scale(2.0f, 2.0f);
            in.close();
            invalidate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "저장된 파일이 없습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "IO Exception", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap Save(String filename) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            mbitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "IO Exception", Toast.LENGTH_SHORT).show();
        }

        return mbitmap;
    }

}
