package com.joyblock.abuba.bus;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NSER-00 on 2017-07-25.
 */
//한개의 텍스트만 있는 리스트 뷰 정의
public class Text1ListViewAdapter extends BaseAdapter implements Serializable {

    int[] textview_id={R.id.listview_text1,R.id.listview_text2};//,R.id.listview_text3};

    public ArrayList<Item> list= new ArrayList<>();
    int int_layout;
    int size;

    public Text1ListViewAdapter(int size,int int_layout){
        this.size=size;
        this.int_layout=int_layout;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Item getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();


        ListViewHolder holder;
        TextView[] textview=new TextView[size];
        //        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(int_layout, parent, false);

            for(int i=0;i<size;i++)
                textview[i]= (TextView) convertView.findViewById(textview_id[i]);


            holder=new ListViewHolder();
            holder.textview=textview;
            convertView.setTag(holder);
        }else{
            holder=(ListViewHolder)convertView.getTag();
            textview=holder.textview;

        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Item item = list.get(position);

//        titleTextView.setTypeface(font);
//        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);0
//        }else{
//            titleTextView.setTextColor(Color.BLUE);
//        }

        //위젯에 반, 이름, 탑승/미탑승 반영
        for(int i=0;i<size;i++)
            textview[i].setText(item.getStringArray()[i]);

        return convertView;
    }


    public void addItem(String[] str_arr) {
        list.add(new Item(str_arr));
    }

    private class ListViewHolder {
        TextView[] textview=new TextView[size];
    }

    public class Item {
        private String[] str_arr={""};

        public Item(String[] str_arr){
//            this.str_arr=new String[size];
            this.str_arr=str_arr;
        }

        public String[] getStringArray(){
            return str_arr;
        }

    }
}
