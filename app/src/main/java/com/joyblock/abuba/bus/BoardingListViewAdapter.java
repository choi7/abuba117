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
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NSER-00 on 2017-07-25.
 */

public class BoardingListViewAdapter extends BaseAdapter implements Serializable {

    public ArrayList<ListViewItem> list= new ArrayList<ListViewItem>();
    BaseActivity activity;
    Typeface font;
    Context context;
    int id_on=R.drawable.ok_check,id_off=R.drawable.no_check;
    boolean bool_boarding_type_on;
    int int_layout;

    public BoardingListViewAdapter(int int_layout,boolean bool_boarding_type_on){
        this.int_layout=int_layout;
        this.bool_boarding_type_on=bool_boarding_type_on;
    }

//    public BoardingListViewAdapter(boolean bool_boarding_type_on){
//        this.bool_boarding_type_on=bool_boarding_type_on;
//    }

//    public BoardingListViewAdapter(Drawable drawable_on,Drawable drawable_off){
//        this.drawable_on=drawable_on;
//        this.drawable_off=drawable_off;
//    }
//
//    public EducationPlanListViewAdapter(Typeface font){
//        this.font=font;
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ListViewItem getItem(int position) {
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
        TextView textview_class,textview_name;
        ImageView imageview_boarding,boarding_type1,boarding_type2,boarding_type3;
        //        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(int_layout, parent, false);
            textview_class= (TextView) convertView.findViewById(R.id.listview_text1);
            textview_name = (TextView) convertView.findViewById(R.id.listview_text2);
            imageview_boarding = (ImageView) convertView.findViewById(R.id.listview_image1);
            boarding_type1=(ImageView) convertView.findViewById(R.id.boarding_type1);
            boarding_type2=(ImageView) convertView.findViewById(R.id.boarding_type2);
            boarding_type3=(ImageView) convertView.findViewById(R.id.boarding_type3);
            if(bool_boarding_type_on) {
                imageview_boarding.setVisibility(View.GONE);
                boarding_type1.setVisibility(View.VISIBLE);
                boarding_type2.setVisibility(View.VISIBLE);
                boarding_type3.setVisibility(View.VISIBLE);

            }

            holder=new ListViewHolder();
            holder.textview_class=textview_class;
            holder.textview_name=textview_name;
            holder.imageview_boarding=imageview_boarding;
            holder.boarding_type1=boarding_type1;
            holder.boarding_type2=boarding_type2;
            holder.boarding_type3=boarding_type3;
            convertView.setTag(holder);

            ImageView imageview_tel=(ImageView)convertView.findViewById(R.id.imageview_tel);
            if(imageview_tel!=null)
                imageview_tel.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        Toast.makeText(v.getContext(),pos+"",Toast.LENGTH_SHORT).show();
                    }
                });
        }else{
            holder=(ListViewHolder)convertView.getTag();
            textview_class=holder.textview_class;
            textview_name=holder.textview_name;
            imageview_boarding=holder.imageview_boarding;
            boarding_type1=holder.boarding_type1;
            boarding_type2=holder.boarding_type2;
            boarding_type3=holder.boarding_type3;

        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = list.get(position);

//        titleTextView.setTypeface(font);
//        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);0
//        }else{
//            titleTextView.setTextColor(Color.BLUE);
//        }

        //위젯에 반, 이름, 탑승/미탑승 반영
        textview_class.setText(listViewItem.getStrClass());
        textview_name.setText(listViewItem.getStrName());
        if(listViewItem.bool_selected) {
            textview_class.setTextColor(Color.BLUE);
            textview_name.setTextColor(Color.BLUE);
        }else{
            textview_class.setTextColor(Color.BLACK);
            textview_name.setTextColor(Color.BLACK);
        }
        if(!bool_boarding_type_on) {
            Picasso.with(context).load(listViewItem.getBoolBoarding() ? id_on : id_off).into(imageview_boarding);
        }else {
            if(listViewItem.getBoolBoarding()) {
                boarding_type1.setVisibility(View.VISIBLE);
//                Picasso.with(context).load(R.drawable.boarding_to_shcool).into(boarding_type1);
                Picasso.with(context).load(R.drawable.boarding_to_home).into(boarding_type2);
                Picasso.with(context).load(R.drawable.boarding_to_another).into(boarding_type3);
            }else{
                boarding_type1.setVisibility(View.GONE);
                Picasso.with(context).load(R.drawable.boarding_to_shcool).into(boarding_type2);
                Picasso.with(context).load(R.drawable.boarding_to_home ).into(boarding_type3);

            }
        }



        return convertView;
    }

    void setSelected(int int_position){
        for(ListViewItem item:list)
            item.setSelected(false);
        list.get(int_position).setSelected(true);
        notifyDataSetChanged();
    }

    void setBoarding(int int_position,boolean bool_boarding){
        list.get(int_position).setBoarding(bool_boarding);
        notifyDataSetChanged();
    }

    public void addItem(String str_class,String str_name,boolean bool_boarding) {
        list.add(new ListViewItem(str_class,str_name,bool_boarding));
    }

    private class ListViewHolder {
        ImageView imageview_boarding,boarding_type1,boarding_type2,boarding_type3;
        TextView textview_class,textview_name;
    }

    public class ListViewItem{

        private String str_class,str_name;
        private boolean bool_boarding;
        private boolean bool_selected;

        public ListViewItem(String str_class,String str_name,boolean bool_boarding){
            this.str_class=str_class;
            this.str_name=str_name;
            this.bool_boarding=bool_boarding;
        }

        void setSelected(boolean bool_selected){this.bool_selected=bool_selected;}

        void setBoarding(boolean bool_boarding){this.bool_boarding=bool_boarding;}


        public String getStrClass(){
            return str_class;
        }
        public String getStrName(){
            return str_name;
        }
        public boolean getBoolBoarding(){return bool_boarding;}


    }
}
