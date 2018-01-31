package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API7 {

    static Request getRequest(String seq_kindergarden,String seq_user,String seq_kindergarden_class,String req_flag,String seq_kids,String year,String month,String day){
        String command="inserReqKindergardenApply";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        builder.addFormDataPart("seq_user",seq_user);
        if(!seq_kindergarden_class.equals(""))
            builder.addFormDataPart("seq_kindergarden_class",seq_kindergarden_class);
        builder.addFormDataPart("req_flag",req_flag);
        if(req_flag.equals("p"))
            builder.addFormDataPart("seq_kids",seq_kids);
        builder.addFormDataPart("year",year);
        builder.addFormDataPart("month",month);
        builder.addFormDataPart("day",day);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
