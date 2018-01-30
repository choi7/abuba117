package com.joyblock.abuba.api;

import android.util.Log;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-30.
 */

public class API2{
    static Request getRequest(String ID,String password){
        String command="longin";
        String url=API.url+command+".do";
        RequestBody body = new FormBody.Builder()
                .add("id", ID)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
