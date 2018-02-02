package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API41 {
    static Request getRequest(String seq_user,String seq_kindergarden,String start_year,String start_month,String start_day,String end_year,String end_month,String end_day,
                              String start_time_hour,String start_time_min,String end_time_hour,String end_time_min,String title,String content,String lat,String lng,String addr,byte[] files){
        String command="insertScheduleManagement";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user",seq_user);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        builder.addFormDataPart("start_year",start_year);
        builder.addFormDataPart("start_month",start_month);
        builder.addFormDataPart("start_day",start_day);
        builder.addFormDataPart("end_year",end_year);
        builder.addFormDataPart("end_month",end_month);
        builder.addFormDataPart("end_day",end_day);
        builder.addFormDataPart("start_time_hour",start_time_hour);
        builder.addFormDataPart("start_time_min",start_time_min);
        builder.addFormDataPart("end_time_hour",end_time_hour);
        builder.addFormDataPart("end_time_min",end_time_min);
        if(!title.equals(""))
            builder.addFormDataPart("title",title);
        if(!content.equals(""))
            builder.addFormDataPart("content",content);
        if(!lat.equals(""))
            builder.addFormDataPart("lat",lat);
        if(!lng.equals(""))
            builder.addFormDataPart("lng",lng);
        if(!addr.equals(""))
            builder.addFormDataPart("addr",addr);
        if(files!=null)
            builder.addFormDataPart("files", TimeConverter.getFileTime("png"),RequestBody.create(MultipartBody.FORM,files));

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
