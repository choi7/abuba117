package com.joyblock.abuba.document;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.joyblock.abuba.R;

/**
 * Created by hyoshinchoi on 2018. 1. 17..
 */

public class CustomDialogAge extends Dialog {



    private Button btn7,btn6,btn5;

    private View.OnClickListener topClickListener;
    private View.OnClickListener bottomClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.layout_document_education_plan_age_dialog);

        btn7 = (Button) findViewById(R.id.textview1);
        btn6 = (Button) findViewById(R.id.textview2);
        btn5 = (Button) findViewById(R.id.textview3);

        // 클릭 이벤트 셋팅
//        if (topClickListener != null && bottomClickListener != null) {
//            topButton.setOnClickListener(topClickListener);
//            bottomButton.setOnClickListener(bottomClickListener);
//        } else if (topClickListener != null
//                && bottomClickListener == null) {
//            topButton.setOnClickListener(topClickListener);
//        } else {
//
//        }

    }

    public CustomDialogAge(Context context){
        super(context);
    }


    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomDialogAge(Context context,
                           View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.topClickListener = singleListener;
    }

    // 클릭버튼이 수정과 삭제 두개일때 생성자 함수로 이벤트를 받는다
    public CustomDialogAge(Context context,
                           View.OnClickListener topListener,
                           View.OnClickListener bottomListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.topClickListener = topListener;
        this.bottomClickListener = bottomListener;
    }
}
