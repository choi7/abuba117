package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API54 {
    static Request getRequest(String seq_kindergarden,String seq_kindergarden_class,String page){
        String command="selectAlbumList";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        if(!seq_kindergarden_class.equals(""))
            builder.addFormDataPart("seq_kindergarden_class",seq_kindergarden_class);
        builder.addFormDataPart("page",page.equals("")?"1":page);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
