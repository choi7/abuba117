package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API38 {
    static Request getRequest(String seq_home_request){
        String command="deleteHomeRequest";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_home_request",seq_home_request);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
