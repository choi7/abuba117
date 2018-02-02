package com.joyblock.abuba.bus;

import android.content.Context;
import android.graphics.Color;
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
//텍스트만 있는 리스트뷰 어댑터 정의
public class TextListViewAdapter extends BaseAdapter implements Serializable {

    //필요한 만큼 텍스트 뷰를 증가시면 된다.
    int[] textview_id={R.id.listview_text1,R.id.listview_text2,R.id.listview_text3,R.id.listview_text4,R.id.listview_text5,R.id.listview_image1};

    ArrayList<View> view_row_list= new ArrayList<>();

    public ArrayList<Item> list= new ArrayList<>();
    int int_layout;
    int size;
    boolean bool_toggle_on=true;

    public TextListViewAdapter(int size, int int_layout){
        this.size=size;
        this.int_layout=int_layout;
    }

    public void setToggleOn(boolean bool_toggle_on){
        this.bool_toggle_on=bool_toggle_on;
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
//            if(view_row_list.size)
            if(view_row_list.size()<position+1)
                view_row_list.add(position, convertView);

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

        for(int i=0;i<size;i++) {
            textview[i].setText(item.getStringArray()[i]);
            if(bool_toggle_on)
                textview[i].setTextColor(item.bool_selected?Color.BLUE:Color.BLACK);
        }






        return convertView;
    }

    public View getPositionView(int position){
        return view_row_list.get(position);
    }

    //필요에 따라 addItem을 정의한다.
    public void addItem(String str1) {
        String[] array={str1};
        list.add(new Item(array));
    }
    public void addItem(String str1,String str2) {
        String[] array={str1,str2};
        list.add(new Item(array));
    }
    public void addItem(String str1,String str2,String str3) {
        String[] array={str1,str2,str3};
        list.add(new Item(array));
    }

    public void addItem(String str1,String str2,String str3,String str4) {
        String[] array={str1,str2,str3,str4};
        list.add(new Item(array));
    }

    public void addItem(String str1,String str2,String str3,String str4,String str5) {
        String[] array={str1,str2,str3,str4,str5};
        list.add(new Item(array));
    }


    public void setSelected(int int_position){
        for(Item item:list)
            item.bool_selected=false;
        list.get(int_position).bool_selected=true;
        notifyDataSetChanged();
    }

    public void toggleSelected(int int_position){
        list.get(int_position).toggleSelected();
        notifyDataSetChanged();
    }

    String[] getString(int int_position){
        return list.get(int_position).str_arr;
    }



    public class ListViewHolder {
        TextView[] textview=new TextView[size];

    }

    public class Item {
        private String[] str_arr={""};
        private boolean bool_selected;


        void toggleSelected(){
            bool_selected=!bool_selected;
        }
        public Item(String[] str_arr){this.str_arr=str_arr;}

        public String[] getStringArray(){
            return str_arr;
        }

    }
}
