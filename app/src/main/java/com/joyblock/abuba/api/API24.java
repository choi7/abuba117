package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API24 {
    static Request getRequest(String seq_educational_plan,String plan_flag,String title,String content,byte[]files){
        String command="updateEducationalPlan";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_educational_plan",seq_educational_plan);
        if(!plan_flag.equals(""))
            builder.addFormDataPart("plan_flag",plan_flag);
        if(!title.equals(""))
            builder.addFormDataPart("title",title);
        if(!content.equals(""))
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
