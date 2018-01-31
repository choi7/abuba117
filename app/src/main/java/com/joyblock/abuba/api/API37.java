package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API37 {
    static Request getRequest(String seq_home_request,String seq_user_teacher,String home_reason,String home_time,String home_method,String companion,String tel_no,String uniqueness,byte[]files0,byte[]files1){
        String command="updateHomeRequest";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_home_request",seq_home_request);
        builder.addFormDataPart("seq_user_teacher",seq_user_teacher);
        if(!home_reason.equals(""))
            builder.addFormDataPart("home_reason",home_reason);
        if(!home_time.equals(""))
            builder.addFormDataPart("home_reason",home_time);
        if(!home_method.equals(""))
            builder.addFormDataPart("home_reason",home_method);
        if(!companion.equals(""))
            builder.addFormDataPart("companion",companion);
        if(!tel_no.equals(""))
            builder.addFormDataPart("tel_no",tel_no);
        if(!uniqueness.equals(""))
            builder.addFormDataPart("uniqueness",uniqueness);
        builder.addFormDataPart("files[0]", TimeConverter.getFileTime()+".png",RequestBody.create(MultipartBody.FORM,files0));
        if(files1!=null)
            builder.addFormDataPart("files[1]", TimeConverter.getFileTime()+".png",RequestBody.create(MultipartBody.FORM,files1));


        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
