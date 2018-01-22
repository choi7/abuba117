package com.joyblock.abuba.document;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hyoshinchoi on 2018. 1. 22..
 */

public class A3_ListViewAdapterCustom extends BaseAdapter implements Serializable {
    private ArrayList<A3_ItemList> listViewItems = new ArrayList<A3_ItemList>();
    BaseActivity activity;
    ListViewHolders holder;
    Context context;


    public A3_ListViewAdapterCustom (Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int position) {
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
        ImageView documentCellCheckImageView;
        TextView documentCellClassText, documentCellNameText;
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.documentlistviewcellcustom, parent, false);
            holder=new ListViewHolders();

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            documentCellNameText = (TextView) convertView.findViewById(R.id.documentCellNameText);
            holder.documentCellNameText=documentCellNameText;
            documentCellClassText = (TextView) convertView.findViewById(R.id.documentCellClassText);
            holder.documentCellClassText=documentCellClassText;
            documentCellCheckImageView = (ImageView) convertView.findViewById(R.id.documentCellCheckImageView);
            holder.documentCellCheckImageView=documentCellCheckImageView;
        }else{
            holder=(ListViewHolders)convertView.getTag();
            documentCellNameText=holder.documentCellNameText;
            documentCellClassText=holder.documentCellClassText;
            documentCellCheckImageView=holder.documentCellCheckImageView;
        }

//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        A3_ItemList listViewItem = listViewItems.get(position);

        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);
        }else{
            documentCellClassText.setTextColor(Color.BLUE);
        }
//        // 아이템 내 각 위젯에 데이터 반영
        documentCellNameText.setText(listViewItem.getIcon());
        documentCellClassText.setText(listViewItem.getTitle());
        documentCellCheckImageView.setImageDrawable(listViewItem.getIcon1());



        return convertView;
    }

    public void addItem(String name, String title, Drawable icon1) {
        A3_ItemList item = new A3_ItemList();

        item.setIcon(name);
        item.setTitle(title);
        item.setIcon1(icon1);

        listViewItems.add(item);
    }

    public class A3_ItemList {

        private String documentCellNameText ;
        private String documentCellClassText ;
        private Drawable documentCellCheckImageView ;
        boolean a=false;

        public void setIcon(String name) {
            documentCellNameText = name ;
        }
        public void setTitle(String title) {
            documentCellClassText = title ;
        }

        public void setIcon1(Drawable icon) {
            documentCellCheckImageView = icon ;
        }

        public String getIcon() {
            return this.documentCellNameText ;
        }
        public String getTitle() {
            return this.documentCellClassText ;
        }
        public Drawable getIcon1() {
            return this.documentCellCheckImageView ;
        }
    }

    private class ListViewHolders {
        ImageView documentCellCheckImageView;
        TextView documentCellClassText, documentCellNameText;
    }

}
