package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API52 {
    static Request getRequest(String seq_album,String is_reply,String seq_kindergarden_class,String title,String content,byte[][] files){
        String command="updateAlbum";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_album",seq_album);
        if(!is_reply.equals(""))
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

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
