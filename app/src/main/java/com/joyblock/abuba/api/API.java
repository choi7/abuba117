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

    public void API_15(String seq_user,String kids_name,String sex,String birth_year,String birth_month,String birth_day,byte[] files,String name_title){
        clearMessage();
        TAG+="15";
        request=API15.getRequest(seq_user,kids_name,sex,birth_year,birth_month,birth_day,files,name_title);
        start();
        waitResponse();
    }

    public void API_16(String seq_user,String seq_kids,String sex,String birth_year,String birth_month,String birth_day,byte[]files,String name_title,String kids_name){
        clearMessage();
        TAG+="16";
        request=API16.getRequest(seq_user,seq_kids,sex,birth_year,birth_month,birth_day,files,name_title,kids_name);
        start();
        waitResponse();
    }


    public void API_17(String seq_user,String page){
        clearMessage();
        TAG+="17";
        request=API17.getRequest(seq_user,page);
        start();
        waitResponse();
    }

    public void API_18(String seq_user,String seq_kindergarden, String seq_kindergarden_class,String title,String content,String year,String month,String day,String c_survey_vote,byte[][]files,String[]vote_item){
        clearMessage();
        TAG+="18";
        request=API18.getRequest(seq_user,seq_kindergarden,seq_kindergarden_class,title,content,year,month,day,c_survey_vote,files,vote_item);
        start();
        waitResponse();
    }

    public void API_19(String seq_user,String seq_kindergarden_class,String title,String content,String year,String month,String day,String c_survey_vote,byte[][]files,String[]vote_item){
        clearMessage();
        TAG+="19";
        request=API19.getRequest(seq_user,seq_kindergarden_class,title,content,year,month,day,c_survey_vote,files,vote_item);
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

    public void API_22(String id){
        clearMessage();
        TAG+="22";
        request=API22.getRequest(id);
        start();
        waitResponse();
    }

    public void API_23(String seq_user,String seq_kindergarden,String plan_flag,String title,String content,byte[]files){
        clearMessage();
        TAG+="23";
        request=API23.getRequest(seq_user,seq_kindergarden,plan_flag,title,content,files);
        start();
        waitResponse();
    }

    public void API_24(String seq_educational_plan,String plan_flag,String title,String content,byte[]files){
        clearMessage();
        TAG+="24";
        request=API24.getRequest(seq_educational_plan,plan_flag,title,content,files);
        start();
        waitResponse();
    }

    public void API_25(String seq_educational_plan){
        clearMessage();
        TAG+="25";
        request=API25.getRequest(seq_educational_plan);
        start();
        waitResponse();
    }

    public void API_26(String seq_kindergarden,String plan_flag,String page){
        clearMessage();
        TAG+="26";
        request=API26.getRequest(seq_kindergarden,plan_flag,page);
        start();
        waitResponse();
    }

    public void API_27(String seq_educational_plan){
        clearMessage();
        TAG+="27";
        request=API27.getRequest(seq_educational_plan);
        start();
        waitResponse();
    }

    public void API_28(String seq_user,String seq_kindergarden,String seq_kids,String seq,String flag,String content){
        clearMessage();
        TAG+="28";
        request=API28.getRequest(seq_user,seq_kindergarden,seq_kids,seq,flag,content);
        start();
        waitResponse();
    }

    public void API_29(String seq_reply){
        clearMessage();
        TAG+="29";
        request=API29.getRequest(seq_reply);
        start();
        waitResponse();
    }

    public void API_30(String seq,String flag,String page){
        clearMessage();
        TAG+="30";
        request=API30.getRequest(seq,flag,page);
        start();
        waitResponse();
    }

    public void API_31(String seq_user_parent,String seq_kindergarden,String seq_kindergarden_class,String seq_kids,String symptom,String medicine_type,String dosage,String dosage_time,String keep_method,
                       String uniqueness,String year,String month,String day,byte[] files0,byte[]files1){
        clearMessage();
        TAG+="31";
        request=API31.getRequest(seq_user_parent,seq_kindergarden,seq_kindergarden_class,seq_kids,symptom,medicine_type,dosage,dosage_time,keep_method,
                uniqueness,year,month,day,files0,files1);
        start();
        waitResponse();
    }

    public void API_32(String seq_medication_request,String seq_user_teacher,String symptom,String medicine_type,String dosage,String dosage_time,String keep_method,
                       String uniqueness,String year,String month,String day,byte[] files0,byte[]files1){
        clearMessage();
        TAG+="32";
        request=API32.getRequest(seq_medication_request,seq_user_teacher,symptom,medicine_type,dosage,dosage_time,keep_method,
                uniqueness,year,month,day,files0,files1);
        start();
        waitResponse();
    }

    public void API_33(String seq_medication_request){
        clearMessage();
        TAG+="33";
        request=API33.getRequest(seq_medication_request);
        start();
        waitResponse();
    }

    public void API_34(String seq_kindergarden,String seq_kindergarden_class,String year,String month,String day,String page){
        clearMessage();
        TAG+="34";
        request=API34.getRequest(seq_kindergarden,seq_kindergarden_class,year,month,day,page);
        start();
        waitResponse();
    }

    public void API_35(String seq_medication_request){
        clearMessage();
        TAG+="35";
        request=API35.getRequest(seq_medication_request);
        start();
        waitResponse();
    }

    public void API_36(String seq_user_parent,String seq_kindergarden,String seq_kindergarden_class,String seq_kids,String home_reason,String home_time,String home_method,String companion,String tel_no,String uniqueness,
                       String year,String month,String day,byte[]files0,byte[]files1){
        clearMessage();
        TAG+="36";
        request=API36.getRequest(seq_user_parent,seq_kindergarden,seq_kindergarden_class,seq_kids,home_reason,home_time,home_method,companion,tel_no,uniqueness,
                year,month,day,files0,files1);
        start();
        waitResponse();
    }

    public void API_37(String seq_home_request,String seq_user_teacher,String home_reason,String home_time,String home_method,String companion,String tel_no,String uniqueness,byte[]files0,byte[]files1){
        clearMessage();
        TAG+="37";
        request=API37.getRequest(seq_home_request,seq_user_teacher,home_reason,home_time,home_method,companion,tel_no,uniqueness,files0,files1);
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