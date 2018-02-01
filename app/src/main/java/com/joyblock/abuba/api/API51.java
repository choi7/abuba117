package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API51 {
    static Request getRequest(String seq_user,String seq_kindergarden,String is_reply,String seq_kindergarden_class,String title,String content,byte[][] files,String year,String month,String day){
        String command="insertAlbum";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user",seq_user);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        builder.addFormDataPart("is_reply",is_reply);
        if(!seq_kindergarden_class.equals(""))
            builder.addFormDataPart("seq_kindergarden_class",seq_kindergarden_class);
        if(!title.equals(""))
            builder.addFormDataPart("title",title);
        if(!content.equals(""))
            builder.addFormDataPart("content",content);
        if(files!=null)
            for(int i=0;i<files.length;i++)
                if(files[i]!=null)
                    builder.addFormDataPart("files["+i+"]", TimeConverter.getFileTime("png"),RequestBody.create(MultipartBody.FORM,files[i]));
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
