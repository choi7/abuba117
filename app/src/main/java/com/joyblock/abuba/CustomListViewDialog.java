package com.joyblock.abuba;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by hyoshinchoi on 2018. 1. 12..
 */

public class CustomListViewDialog extends Dialog {

    ListView listview;
    BaseAdapter adapter;

    int int_layout_id=R.layout.custom_listview_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(int_layout_id);

        listview=(ListView)findViewById(R.id.custom_listview);
        listview.setAdapter(adapter);

//        mTitleView = (TextView) findViewById(R.id.txt_title);
//        mLeftButton = (Button) findViewById(R.id.btn_left);
//        mRightButton = (Button) findViewById(R.id.btn_right);

        // 제목을 생성자에서 셋팅한다.
//        mTitleView.setText(mTitle);
//
//        // 클릭 이벤트 셋팅
//        if (mLeftClickListener != null && mRightClickListener != null) {
//            mLeftButton.setOnClickListener(mLeftClickListener);
//            mRightButton.setOnClickListener(mRightClickListener);
//        } else if (mLeftClickListener != null
//                && mRightClickListener == null) {
//            mLeftButton.setOnClickListener(mLeftClickListener);
//        } else {
//
//        }

    }
    public ListView getListView(){
        return listview;
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomListViewDialog(Context context,BaseAdapter adapter) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.adapter=adapter;
    }

    public CustomListViewDialog(Context context,BaseAdapter adapter,int int_layout_id) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.adapter=adapter;
        this.int_layout_id=int_layout_id;
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
//    public Class1ListCustomDialog(Context context, String title,
//                                 View.OnClickListener leftListener,
//                                 View.OnClickListener rightListener) {
//        super(context, android.R.style.Theme_Translucent_NoTitleBar);
//        this.mTitle = title;
//        this.mLeftClickListener = leftListener;
//        this.mRightClickListener = rightListener;
//    }

}
