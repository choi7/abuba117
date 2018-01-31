package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API23 {
    static Request getRequest(String seq_user,String seq_kindergarden,String plan_flag,String title,String content,byte[]files){
        String command="insertEducationalPlan";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user",seq_user);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        builder.addFormDataPart("plan_flag",plan_flag);
        builder.addFormDataPart("title",title);
        builder.addFormDataPart("content",content);
        if(files!=null)
            builder.addFormDataPart("files", TimeConverter.getFileTime()+".png",RequestBody.create(MultipartBody.FORM,files));

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
