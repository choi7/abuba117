package com.joyblock.abuba.document;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.R;
import com.joyblock.abuba.notice.BanListViewAdapter;
import com.joyblock.abuba.notice.BanListViewItem;

import java.io.Serializable;
import java.util.ArrayList;

public class A3_1_Medicine_Parent extends BaseActivity {

    ImageView editorImageView;
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a3_1_medicine_parent);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff9966ff));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbarcustom);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#9966FF"));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
        }
        TextView title = ((TextView) findViewById(R.id.titleName));
        title.setText("투약의뢰서");
        title.setVisibility(View.VISIBLE);

        listView = (ListView) findViewById(R.id.a3_1_Parent_listView);


        editorImageView = (ImageView) findViewById(R.id.editorImage);
        editorImageView.setVisibility(View.VISIBLE);
        editorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A3_1_Medicine_Parent.this, A3_3_MedicineEditorView.class);
                A3_1_Medicine_Parent.this.startActivity(intent);
            }
        });

    }


    public class MedicineListViewAdapter extends BaseAdapter implements Serializable {


        public ArrayList<BanListViewItem> list = new ArrayList<BanListViewItem>();
        BaseActivity activity;
        ListViewHolder holder;

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
            TextView time, name;
            ImageView checkImage;
            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cellcustomselectmedicationrequest, parent, false);
                time = (TextView) convertView.findViewById(R.id.a3_1_ListView_TimeText);
                name = (TextView) convertView.findViewById(R.id.a3_1ListView_nameText);
                checkImage = (ImageView) convertView.findViewById(R.id.a3_1ListView_CheckImageView);
                holder = new ListViewHolder();
                holder.time = time;
                holder.name = name;
                holder.checkImage = checkImage;
                convertView.setTag(holder);
                name.setVisibility(View.VISIBLE);
            } else {
                holder = (ListViewHolder) convertView.getTag();
                name = holder.name;
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

        public void addItem(String time, String name, Drawable checkImage) {
            list.add(new BanListViewItem(time, name, checkImage));
        }

        private class ListViewHolder {
            TextView time;
            TextView name;
            ImageView checkImage;
        }

    }


    public class BanListViewItem {

        private String time;
        private String name;
        private Drawable checkImage;


        public BanListViewItem(String time, String name, Drawable checkImage) {
            this.time = time;
            this.name = name;
            this.checkImage = checkImage;
        }

        public String getTime() {
            return time;
        }

        public String getName() {
            return name;
        }

        public Drawable getCheckImage() {
            return checkImage;
        }


//    public Drawable getIcon() {
//        return this.iconDrawable ;
//    }
//    public String getTitle() {
//        return this.titleStr ;
//    }
//    public Drawable getIcon1() {
//        return this.iconDrawable1 ;
//    }


    }


}
