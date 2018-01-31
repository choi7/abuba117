package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API15 {
    static Request getRequest(String seq_user,String kids_name,String sex,String birth_year,String birth_month,String birth_day,byte[] files,String name_title){
        String command="insertKids";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user",seq_user);
        builder.addFormDataPart("kids_name",kids_name);
        builder.addFormDataPart("sex",sex);
        if(!birth_year.equals(""))
            builder.addFormDataPart("birth_year",birth_year);
        if(!birth_month.equals(""))
            builder.addFormDataPart("birth_month",birth_month);
        if(!birth_day.equals(""))
            builder.addFormDataPart("birth_month",birth_);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
