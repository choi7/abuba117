package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API19 {
    static Request getRequest(String seq_user,String seq_kindergarden_class,String title,String content,String year,String month,String day,String c_survey_vote,byte[][]files,String[]vote_item){
        String command="updateSurvey";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user",seq_user);
        if(!seq_kindergarden_class.equals(""))
            builder.addFormDataPart("seq_kindergarden_class",seq_kindergarden_class);
        builder.addFormDataPart("title",title);
        builder.addFormDataPart("content",content);
        builder.addFormDataPart("year",year);
        builder.addFormDataPart("month",month);
        builder.addFormDataPart("day",day);
        builder.addFormDataPart("c_survey_vote",c_survey_vote);



        for(int i=0;i<vote_item.length;i++) {
            builder.addFormDataPart("vote_item["+i+"]",vote_item[i] );
            if(files[i]!=null)
                builder.addFormDataPart("files[" + i + "]", TimeConverter.getFileTime() + ".png", RequestBody.create(MultipartBody.FORM, files[i]));
        }

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();



        return request;
    }
}
