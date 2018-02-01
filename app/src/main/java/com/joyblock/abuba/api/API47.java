package com.joyblock.abuba.api;

import com.joyblock.abuba.util.TimeConverter;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BLUE1 on 2018-01-31.
 */

public class API47 {
    static Request getRequest(String seq_menu_management,String type,String rice,String soup,
                              String side_dish_1,String side_dish_2,String side_dish_3,String side_dish_4,String side_dish_5,String snack,String memo,byte[]files){
        String command="updateMenuManagement";
        String url=API.url+command+".do";
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("seq_menu_management",seq_menu_management);
        builder.addFormDataPart("type",type);
        if(!rice.equals(""))
            builder.addFormDataPart("rice",rice);
        if(!soup.equals(""))
            builder.addFormDataPart("soup",soup);
        if(!side_dish_1.equals(""))
            builder.addFormDataPart("side_dish_1",side_dish_1);
        if(!side_dish_2.equals(""))
            builder.addFormDataPart("side_dish_2",side_dish_2);
        if(!side_dish_3.equals(""))
            builder.addFormDataPart("side_dish_3",side_dish_3);
        if(!side_dish_4.equals(""))
            builder.addFormDataPart("side_dish_4",side_dish_4);
        if(!side_dish_5.equals(""))
            builder.addFormDataPart("side_dish_5",side_dish_5);
        if(!snack.equals(""))
            builder.addFormDataPart("snack",snack);
        if(!memo.equals(""))
            builder.addFormDataPart("memo",memo);
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
