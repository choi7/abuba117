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

public class BoardingListViewAdapter extends BaseAdapter implements Serializable {

    private ArrayList<ListViewItem> listViewItems = new ArrayList<ListViewItem>();
    BaseActivity activity;
    Typeface font;
    Context context;
    int id_on=R.drawable.ok_check,id_off=R.drawable.no_check;

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
        return listViewItems.size();
    }

    @Override
    public ListViewItem getItem(int position) {
        return listViewItems.get(position);
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
        ImageView imageview_boarding;
        //        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.documentlistviewcellcustom, parent, false);
            textview_class= (TextView) convertView.findViewById(R.id.documentCellClassText);
            textview_name = (TextView) convertView.findViewById(R.id.documentCellNameText);
            imageview_boarding = (ImageView) convertView.findViewById(R.id.documentCellCheckImageView);
            holder=new ListViewHolder();
            holder.textview_class=textview_class;
            holder.textview_name=textview_name;
            holder.imageview_boarding=imageview_boarding;
            convertView.setTag(holder);
        }else{
            holder=(ListViewHolder)convertView.getTag();
            textview_class=holder.textview_class;
            textview_name=holder.textview_name;
            imageview_boarding=holder.imageview_boarding;
        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItems.get(position);

//        titleTextView.setTypeface(font);
//        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);0
//        }else{
//            titleTextView.setTextColor(Color.BLUE);
//        }

        //위젯에 반, 이름, 탑승/미탑승 반영
        textview_class.setText(listViewItem.getStrClass());
        textview_name.setText(listViewItem.getStrName());
        Picasso.with(context).load(listViewItem.getBoolBoarding()?id_on:id_off).into(imageview_boarding);

        return convertView;
    }

    public void addItem(String str_class,String str_name,boolean bool_boarding) {
        listViewItems.add(new ListViewItem(str_class,str_name,bool_boarding));
    }

    private class ListViewHolder {
        ImageView imageview_boarding;
        TextView textview_class,textview_name;
    }

    private class ListViewItem{

        private String str_class,str_name;
        private boolean bool_boarding;

        public ListViewItem(String str_class,String str_name,boolean bool_boarding){
            this.str_class=str_class;
            this.str_name=str_name;
            this.bool_boarding=bool_boarding;
        }

        public String getStrClass(){
            return str_class;
        }
        public String getStrName(){
            return str_name;
        }
        public boolean getBoolBoarding(){return bool_boarding;}


    }
}
