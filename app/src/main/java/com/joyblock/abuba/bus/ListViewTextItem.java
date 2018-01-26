package com.joyblock.abuba.bus;


/**
 * Created by BLUE1 on 2018-01-26.
 */

public class ListViewTextItem {
    private String[] str_arr;

    public ListViewTextItem(int size,String[] str_arr){
        str_arr=new String[size];
        this.str_arr=str_arr;
    }

    public String[] getStringArray(){
        return str_arr;
    }

}
