package com.joyblock.abuba.data;

import android.app.Application;
import android.content.res.Configuration;

import com.joyblock.abuba.api_message.R17_SelectMyKidsList;
import com.joyblock.abuba.api_message.R6_SelectKindergardenClassList;
import com.joyblock.abuba.api_message.R8_SelectMykindergardenList;

import java.util.ArrayList;

/**
 * Created by POPCON on 2018-01-16.
 */

public class MyApplication extends Application {

    public String
            phone_no,   //전화번호
            authority,  //권한
            name,       //이름
            seq_user,   //유저 시퀀스
            email,      //이메일
            token;      //토큰


    public CalendarIntent calendarIntent = new CalendarIntent();

    public boolean push_yn;    //푸시 on/off

    public ArrayList<R8_SelectMykindergardenList> my_kindergarden_list=new ArrayList<>();
    public ArrayList<R17_SelectMyKidsList> my_kids_list=new ArrayList<>();
    public ArrayList<R6_SelectKindergardenClassList>kindergarden_class_list=new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public class CalendarIntent{
        public boolean on = false;
        public String starthour, startminute, endhour, endminute, startyear, startmonth, startday, endyear, endmonth, endday, start_day_of_week, end_day_of_week;
        public ArrayList<String> calendarList = new ArrayList<>();
        public String[] array;
        public void setCalendarIntent(String starthour,String startminute,String endhour,String endminute,String startyear,String startmonth,String startday,String endyear,String endmonth,String endday, String start_day_of_week, String end_day_of_week){
            calendarList.add(starthour);
            calendarList.add(startminute);
            calendarList.add(endhour);
            calendarList.add(endminute);
            calendarList.add(startyear);
            calendarList.add(startmonth);
            calendarList.add(startday);
            calendarList.add(endyear);
            calendarList.add(endmonth);
            calendarList.add(endday);
            calendarList.add(start_day_of_week);
            calendarList.add(end_day_of_week);
            array = new String[]{starthour, startminute, endhour, endminute, startyear, startmonth, startday, endyear, endmonth, endday, start_day_of_week, end_day_of_week};
            this.starthour=starthour;
            this.startminute=startminute;
            this.endhour=endhour;
            this.endminute=endminute;
            this.startyear=startyear;
            this.startmonth=startmonth;
            this.startday=startday;
            this.endyear=endyear;
            this.endmonth=endmonth;
            this.endday=endday;
            this.start_day_of_week=start_day_of_week;
            this.end_day_of_week=end_day_of_week;


            on=true;

        }
    }




}
