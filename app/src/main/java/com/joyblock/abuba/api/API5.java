package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API5 {

    static Request getRequest(String ar1,String ar2,String kindergarden_name,String page){
        String command="selectKindergardenList";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(!ar1.equals(""))
                builder.addFormDataPart("ar1",ar1);
        if(!ar2.equals(""))
            builder.addFormDataPart("ar2",ar2);
        if(!kindergarden_name.equals(""))
            builder.addFormDataPart("kindergarden_name",kindergarden_name);
        if(!page.equals(""))
            builder.addFormDataPart("page",page);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
