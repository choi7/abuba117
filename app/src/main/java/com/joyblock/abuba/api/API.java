package com.joyblock.abuba.api;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by BLUE1 on 2018-01-30.
 */

public class API extends Thread{
    private OkHttpClient client=new OkHttpClient();
    static String TAG="API";
    static String url="http://58.229.208.246/Ububa/";
    private String message;
    private Request request;
    public MessageCallback callback=new MessageCallback();
    /** 웹 서버로 요청을 한다. */

    private void clearMessage(){
        message="";
    }

    public void waitResponse(){
        while(message.equals(""));
    }

    public String getMessage() {
        return message;
    }

    public void run(){
//        clearMessage();
        client.newCall(request).enqueue(callback);
    }

    class MessageCallback implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "콜백오류:"+e.getMessage());
            message="응답을 못받음";
            Log.d(TAG, message);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            message = response.body().string();
            Log.d(TAG, "서버에서 응답한 Body:"+message);
        }
    }

    public void API2(String ID,String 비밀번호){
        clearMessage();
        TAG="API2";
        request=API2.getRequest(ID,비밀번호);
        start();
        waitResponse();
    }

    public void API3(String seq_user,String name,String birthday,String phone_no,String email,String token,byte[] files,String ar1,String ar2,String addr_etc,String lat,String lng){
        clearMessage();
        TAG="API3";
        request=API3.getRequest(seq_user,name,birthday,phone_no,email,token,files,ar1,ar2,addr_etc,lat,lng);
        start();
        waitResponse();
    }

    public void API4(String ar1_또는_ar2_입력,String ar2_입력시사용_예_경상남도){
        clearMessage();
        TAG+="4";
        request=API4.getRequest(ar1_또는_ar2_입력,ar2_입력시사용_예_경상남도);
        start();
        waitResponse();
    }

    static void printRequest(RequestBody body) {
        try {
            Buffer buffer = new Buffer();
            body.writeTo(buffer);

            Log.d(TAG+" request", buffer.readUtf8());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}