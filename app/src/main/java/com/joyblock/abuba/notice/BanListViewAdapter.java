package com.joyblock.abuba.notice;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NSER-00 on 2017-07-25.
 */

public class BanListViewAdapter extends BaseAdapter implements Serializable {

    public ArrayList<BanListViewItem> list = new ArrayList<BanListViewItem>();
    BaseActivity activity;
    Typeface font;
    ListViewHolder holder;

    public BanListViewAdapter(){
    }

    public BanListViewAdapter(Typeface font){
        this.font=font;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BanListViewItem getItem(int position) {
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
        TextView name;
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.park_layout_notice_popup_list_row, parent, false);
            name = (TextView) convertView.findViewById(R.id.ban_name);
            holder=new ListViewHolder();
            holder.name=name;
            convertView.setTag(holder);
            name.setVisibility(View.VISIBLE);
        }else {
            holder=(ListViewHolder)convertView.getTag();
            name=holder.name;
        }
        name.setText(list.get(pos).getName());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(,"선택한 이름:"+list.get(pos).getName(),Toast.LENGTH_SHORT).show();
//            }
//        });




//        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        ImageView photo = (ImageView) convertView.findViewById(R.id.photo);
//        TextView title= (TextView) convertView.findViewById(R.id.titleListViewCell);
//        TextView count = (TextView) convertView.findViewById(R.id.countListViewCell);


//        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
//        NoticeListViewItem listViewItem = listViewItems.get(position);


//        titleTextView.setTypeface(font);
//        if(!listViewItem.a){
//            titleTextView.setTextColor(Color.RED);
//        }else{
//            titleTextView.setTextColor(Color.BLUE);
            // 아이템 내 각 위젯에 데이터 반영

//        photo.setImageDrawable(listViewItem.getPhoto());
//        title.setText(listViewItem.getTitle());
//        count.setText(listViewItem.getCount());
//        name.setText(listViewItem.getName());



            return convertView;
        }

    public void addItem(String name) {
        list.add(new BanListViewItem(name));
    }
    private class ListViewHolder {
        TextView name;
    }
}
