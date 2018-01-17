package com.joyblock.abuba;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by POPCON on 2018-01-17.
 */

public class TimeConverter {

    static public String convert(String unixTime){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(Long.parseLong(unixTime))).toString();
    }
}
