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

    public void API_2(String ID,String 비밀번호){
        clearMessage();
        TAG="API2";
        request=API2.getRequest(ID,비밀번호);
        start();
        waitResponse();
    }

    public void API_3(String seq_user,String name,String birthday,String phone_no,String email,String token,byte[] files,String ar1,String ar2,String addr_etc,String lat,String lng){
        clearMessage();
        TAG="API3";
        request=API3.getRequest(seq_user,name,birthday,phone_no,email,token,files,ar1,ar2,addr_etc,lat,lng);
        start();
        waitResponse();
    }

    public void API_4(String ar1_또는_ar2_입력,String ar2_입력시사용_예_경상남도){
        clearMessage();
        TAG+="4";
        request=API4.getRequest(ar1_또는_ar2_입력,ar2_입력시사용_예_경상남도);
        start();
        waitResponse();
    }

    public void API_5(String ar1_도단위,String ar2_시단위,String kindergarden_name,String page){
        clearMessage();
        TAG+="5";
        request=API5.getRequest(ar1_도단위,ar2_시단위,kindergarden_name,page);
        start();
        waitResponse();

    }

    public void API_6(String seq_kindergarden,String page){
        clearMessage();
        TAG+="6";
        request=API6.getRequest(seq_kindergarden,page);
        start();
        waitResponse();
    }
    public void API_7(String seq_kindergarden,String seq_user,String seq_kindergarden_class,String req_flag,String seq_kids,String year,String month,String day){
        clearMessage();
        TAG+="7";
        request=API7.getRequest(seq_kindergarden,seq_user,seq_kindergarden_class,req_flag,seq_kids,year,month,day);
        start();
        waitResponse();
    }

    public void API_8(String seq_user,String page){
        clearMessage();
        TAG+="8";
        request=API8.getRequest(seq_user,page);
        start();
        waitResponse();
    }

    public void API_9(String seq_user,String password,String new_password){
        clearMessage();
        TAG+="9";
        request=API9.getRequest(seq_user,password,new_password);
        start();
        waitResponse();
    }

    public void API_10(String seq_user,String seq_kindergarden,String is_reply,String seq_kindergarden_class,String title,String content,byte[] files){
        clearMessage();
        TAG+="10";
        request=API10.getRequest(seq_user,seq_kindergarden,is_reply,seq_kindergarden_class,title,content,files);
        start();
        waitResponse();
    }

    public void API_11(String seq_notice,String is_reply,String seq_kindergarden_class,String title,String content,byte[] files){
        clearMessage();
        TAG+="11";
        request=API11.getRequest(seq_notice,is_reply,seq_kindergarden_class,title,content,files);
        start();
        waitResponse();
    }

    public void API_12(String seq_notice){
        clearMessage();
        TAG+="12";
        request=API12.getRequest(seq_notice);
        start();
        waitResponse();
    }

    public void API_13(String seq_kindergarden,String seq_kindergarden_class,String page){
        clearMessage();
        TAG+="13";
        request=API13.getRequest(seq_kindergarden,seq_kindergarden_class,page);
        start();
        waitResponse();
    }

    public void API_14(String seq_notice){
        clearMessage();
        TAG+="14";
        request=API14.getRequest(seq_notice);
        start();
        waitResponse();
    }

    public void API_15(String seq_notice){
        clearMessage();
        TAG+="15";
        request=API14.getRequest(seq_notice);
        start();
        waitResponse();
    }


    public void API_20(String seq_kindergarden,String seq_kindergarden_class,String page){
        clearMessage();
        TAG+="20";
        request=API20.getRequest(seq_kindergarden,seq_kindergarden_class,page);
        start();
        waitResponse();
    }

    public void API_21(String seq_survey){
        clearMessage();
        TAG+="21";
        request=API21.getRequest(seq_survey);
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