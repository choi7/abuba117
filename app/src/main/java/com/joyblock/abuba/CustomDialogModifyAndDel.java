package com.joyblock.abuba;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hyoshinchoi on 2018. 1. 17..
 */

public class CustomDialogModifyAndDel extends Dialog {



    private Button topButton;
    private Button bottomButton;

    private View.OnClickListener topClickListener;
    private View.OnClickListener bottomClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.park_layout_notice_mod_del);

        topButton = (Button) findViewById(R.id.btn_modify);
        bottomButton = (Button) findViewById(R.id.btn_delete);

        // 클릭 이벤트 셋팅
        if (topClickListener != null && bottomClickListener != null) {
            topButton.setOnClickListener(topClickListener);
            bottomButton.setOnClickListener(bottomClickListener);
        } else if (topClickListener != null
                && bottomClickListener == null) {
            topButton.setOnClickListener(topClickListener);
        } else {

        }

    }



    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomDialogModifyAndDel(Context context,
                        View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.topClickListener = singleListener;
    }

    // 클릭버튼이 수정과 삭제 두개일때 생성자 함수로 이벤트를 받는다
    public CustomDialogModifyAndDel(Context context,
                        View.OnClickListener topListener,
                        View.OnClickListener bottomListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.topClickListener = topListener;
        this.bottomClickListener = bottomListener;
    }
}
