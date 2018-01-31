package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API31 {
    static Request getRequest(String seq_user_parent,String seq_kindergarden,String seq_kindergarden_class,String seq_kids,String symptom,String medicine_type,String dosage,String dosage_time,String keep_method,
                              String uniqueness,String year,String month,String day,byte[] files0,byte[]files1 ){
        String command="insertMedicationRequest";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_user_parent",seq_user_parent);
        builder.addFormDataPart("seq_kindergarden",seq_kindergarden);
        builder.addFormDataPart("seq_kindergarden_class",seq_kindergarden_class);
        builder.addFormDataPart("seq_kids",seq_kids);
        builder.addFormDataPart("symptom",symptom);
        builder.addFormDataPart("medicine_type",medicine_type);
        builder.addFormDataPart("dosage",dosage);
        builder.addFormDataPart("dosage_time",dosage_time);
        builder.addFormDataPart("keep_method",keep_method);
        builder.addFormDataPart("uniqueness",uniqueness);
        builder.addFormDataPart("year",year);
        builder.addFormDataPart("month",month);
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
