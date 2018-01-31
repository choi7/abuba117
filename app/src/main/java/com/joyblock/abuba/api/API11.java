package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API11 {
    static Request getRequest(String seq_notice,String is_reply,String seq_kindergarden_class,String title,String content,byte[] files){
        String command="updateNotice";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_notice",seq_notice);
        if(!is_reply.equals(""))
            builder.addFormDataPart("is_reply",is_reply);
        if(!seq_kindergarden_class.equals(""))
            builder.addFormDataPart("seq_kindergarden_class",seq_kindergarden_class);
        builder.addFormDataPart("title",!title.equals("")?title:"제목없음");
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
