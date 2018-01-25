package com.joyblock.abuba.data;

import android.app.Application;
import android.content.res.Configuration;

import com.joyblock.abuba.api_message.R17_SelectMyKidsList;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.api_message.R8_SelectMykindergardenList;

import java.util.ArrayList;

/**
 * Created by POPCON on 2018-01-16.
 */

public class MyApplication extends Application {

    public String
            phone_no,   //전화번호
            authority,  //권한
            name,       //이름
            seq_user,   //유저 시퀀스
            email,      //이메일
            token;      //토큰

    public boolean push_yn;    //푸시 on/off

    public ArrayList<R8_SelectMykindergardenList> my_kindergarden_list=new ArrayList<>();
    public ArrayList<R17_SelectMyKidsList> my_kids_list=new ArrayList<>();
    public ArrayList<R6_SelectKindergardenClassList>kindergarden_class_list=new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
