package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-30.
 */

public class API4 {
    static Request getRequest(String ar_name,String ar1){
        String command="selectAreaList";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("ar_name", ar_name);
        if(!ar1.equals(""))
            builder.addFormDataPart("ar1",ar1);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
