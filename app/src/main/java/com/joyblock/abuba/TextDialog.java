package com.joyblock.abuba;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyoshinchoi on 2018. 1. 12..
 */

public class TextDialog extends Dialog {
    int textview_id_start=R.id.textview1;
    int textview_id_end=R.id.textview3;
    int int_size=textview_id_end-textview_id_start+1;
    int int_layout;
    String[] str_array;
    TextView[] textview=new TextView[int_size];

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public TextDialog(Context context,int int_layout) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.int_layout=int_layout;
//        this.int_size=int_size;
//        textview=new TextView[this.int_size];
//        for(int i=0;i<int_size;i++)
//            textview[i]=findViewById(textview_id+i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(int_layout);


        int i=0;
        try {
            for (; i < str_array.length; i++) {
                textview[i] = (TextView)findViewById(textview_id_start + i);
                textview[i].setText(str_array[i]);
            }
        }catch(NullPointerException e){
            int_size=i;
        }

        Log.d("TextDialog",int_size+"");


    }

    public void setTexts(String[] str_array){
//        if(str_array.length!=int_size)
//            Toast.makeText(getContext(), "텍스트 뷰 개수를 확인해 주세요",Toast.LENGTH_LONG).show();
//        else{
//            for(int i=0;i<int_size;i++)
//                textview[i].setText(str_array[i]);
//        }
        this.str_array=str_array;
    }

}
