package com.joyblock.abuba.api;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API56 {
    static Request getRequest(String seq_survey,String seq_survey_vote_item,String seq_user,String seq_kids){
        String command="insertSurveyVote";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_survey",seq_survey);
        builder.addFormDataPart("seq_survey_vote_item",seq_survey_vote_item);
        builder.addFormDataPart("seq_user",seq_user);
        builder.addFormDataPart("seq_kids",seq_kids);

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
