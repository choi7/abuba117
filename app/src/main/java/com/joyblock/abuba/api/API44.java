package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API44 {
    static Request getRequest(String seq_kindergarden,String year,String month){
        String command="selectScheduleManagementList";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        builder.addFormDataPart("year",year);
        builder.addFormDataPart("month",month);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
