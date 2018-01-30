package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import java.sql.Time;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-30.
 */

public class API3 {
    static Request getRequest(String seq_user,String name,String birthday,String phone_no,String email,String token,byte[] files,String ar1,String ar2,String addr_etc,String lat,String lng){
        String command="updateUser";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("seq_user", seq_user);
        if(!name.equals(""))
            builder.addFormDataPart("name", name);
        if(!birthday.equals(""))
            builder.addFormDataPart("birthday", birthday);
        if(!phone_no.equals(""))
            builder.addFormDataPart("phone_no", phone_no);
        if(!email.equals(""))
            builder.addFormDataPart("email", email);
        if(!token.equals(""))
            builder.addFormDataPart("token", token);
        if(!ar1.equals(""))
            builder.addFormDataPart("ar1",ar1);
        if(!ar2.equals(""))
            builder.addFormDataPart("ar2",ar2);
        if(!addr_etc.equals(""))
            builder.addFormDataPart("addr_etc",addr_etc);
        if(!lat.equals(""))
            builder.addFormDataPart("lat",lat);
        if(!lng.equals(""))
            builder.addFormDataPart("lng",lng);
        if(files!=null)
            builder.addFormDataPart("files", TimeConverter.getFileTime()+".png",RequestBody.create(MultipartBody.FORM, files));

        RequestBody body=builder.build();


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
//        API.printRequest(body);
        return request;
    }
}
