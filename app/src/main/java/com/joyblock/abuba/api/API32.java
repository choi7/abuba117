package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API32 {
    static Request getRequest(String seq_medication_request,String seq_user_teacher,String symptom,String medicine_type,String dosage,String dosage_time,String keep_method,
                              String uniqueness,String year,String month,String day,byte[] files0,byte[]files1 ){
        String command="updateMedicationRequest";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_medication_request",seq_medication_request);
        builder.addFormDataPart("seq_user_teacher",seq_user_teacher);
        if(!symptom.equals(""))
            builder.addFormDataPart("symptom",symptom);
        if(!medicine_type.equals(""))
            builder.addFormDataPart("medicine_type",medicine_type);
        if(!dosage.equals(""))
            builder.addFormDataPart("dosage",dosage);
        if(!dosage_time.equals(""))
            builder.addFormDataPart("dosage_time",dosage_time);
        if(!keep_method.equals(""))
            builder.addFormDataPart("keep_method",keep_method);
        if(!uniqueness.equals(""))
            builder.addFormDataPart("uniqueness",uniqueness);
        if(!year.equals(""))
            builder.addFormDataPart("year",year);
        if(!month.equals(""))
            builder.addFormDataPart("month",month);
        if(!day.equals(""))
            builder.addFormDataPart("day",day);
        builder.addFormDataPart("files[0]", TimeConverter.getFileTime()+".png",RequestBody.create(MultipartBody.FORM,files0));
        if(files1!=null)
            builder.addFormDataPart("files[1]", TimeConverter.getFileTime()+".png",RequestBody.create(MultipartBody.FORM,files1));

        RequestBody body=builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
