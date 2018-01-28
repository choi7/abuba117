package com.joyblock.abuba.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by POPCON on 2018-01-17.
 */

public class TimeConverter {

    static public String convert(String unixTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(Long.parseLong(unixTime))).toString();
    }

    static public String getFileTime() {

        // 현재시간을 msec 으로 구한다.
        long now = System.currentTimeMillis();
        // 현재시간을 date 변수에 저장한다.
//        Date date = new Date(now);
//        // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
//        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        // nowDate 변수에 값을 저장한다.
//        String formatDate = sdfNow.format(date);


        return now+"";
    }
}
