package com.joyblock.abuba;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyblock.abuba.util.ImageFileProcessor;

import java.util.ArrayList;

/**
 * Created by BLUE on 2018-01-31.
 */

public class QuestionaireDetailListViewAdapter extends BaseAdapter {
    Activity activity;
    //    private Context mContext = null;
    ImageFileProcessor ifp=new ImageFileProcessor();

    public ArrayList<Data> data_list = new ArrayList<Data>();
    public int mSelectedItem;


    public int getSize(){
        int size=0;
        for(Data list:data_list){
            if(list.bool_image||!(list.mTitle.equals("")))
                size++;
        }
        Log.d("size",size+"");
        return size;
    }

    public QuestionaireDetailListViewAdapter(Activity activity) {
        super();
//        this.mContext = mContext;
        this.activity=activity;
    }

    public void addDefualtItem(Drawable icon, String text) {
        Data addInfo= new Data();
        addInfo.mIcon = icon;
        addInfo.mTitle = text;
        data_list.add(addInfo);
    }



    public void addItem(Drawable icon, String mTitle, String mCount) {
        Data addInfo = new Data();
        addInfo.mIcon = icon;
        addInfo.mTitle = mTitle;
        addInfo.mCount = mCount;
        data_list.add(addInfo);
    }

    public void setIcon(int position,Drawable icon,byte[] image_byte_array){
        Data item=(Data)getItem(position);
        item.image_byte_array=image_byte_array;
        item.bool_image=true;
        item.mIcon=icon;

    }


    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return  data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.votedetaillistviewcell, null);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.imageView19);
            holder.mText = (TextView) convertView.findViewById(R.id.editText4);
            holder.mCount = (TextView) convertView.findViewById(R.id.textView164);
//                holder.mDate = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Data mData = data_list.get(position);

        holder.mIcon.setImageDrawable(mData.mIcon);
        holder.mIcon.setVisibility(View.VISIBLE);
        holder.mText.setText(mData.mTitle);
        holder.mCount.setText(mData.mCount);


//        holder.mText.setEditableFactory(Editab);//.setEnabled(!(position>size+1));



        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageFileProcessor().selectGallery(activity,pos);

            }
        });


        holder.mText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.
                Data data=data_list.get(pos);
                data.mTitle=holder.mText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 호출된다.
            }
        });

        return convertView;
    }


    public class ViewHolder {
        public ImageView mIcon;
        public TextView mText;
        public TextView mCount;
    }


    class Data {
        //아이콘
        public Drawable mIcon;
        //제목
        public String mTitle;
        public String mCount;
        boolean bool_image;

        public byte[] image_byte_array;

    }

}
