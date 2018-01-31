package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API28 {
    static Request getRequest(String seq_user,String seq_kindergarden,String seq_kids,String seq,String flag,String content){
        String command="insertReply";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user",seq_user);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        if(!seq_kids.equals(""))
            builder.addFormDataPart("seq_kids",seq_kids);
        builder.addFormDataPart("seq",seq);
        builder.addFormDataPart("flag",flag);
        builder.addFormDataPart("content",content);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
