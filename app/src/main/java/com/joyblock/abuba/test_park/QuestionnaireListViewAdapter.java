package com.joyblock.abuba.test_park;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.util.ImageFileProcessor;
import com.joyblock.abuba.R;

import java.util.ArrayList;

/**
 * Created by POPCON on 2018-01-19.
 */

public class QuestionnaireListViewAdapter extends BaseActivity implements ListAdapter {
    Activity activity;
//    private Context mContext = null;
    ImageFileProcessor ifp=new ImageFileProcessor();
    public ArrayList<Data> data_list = new ArrayList<Data>();

    public QuestionnaireListViewAdapter(Activity activity) {
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



    public void addItem(Drawable icon, String mTitle) {
        Data addInfo = new Data();
        addInfo.mIcon = icon;
        addInfo.mTitle = mTitle;
        data_list.add(addInfo);
    }

    public void setIcon(int position,Drawable icon,byte[] image_byte_array){
        Data item=(Data)getItem(position);
        item.image_byte_array=image_byte_array;
        item.mIcon=icon;

    }




    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.votelistviewcell, null);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.imageView19);
            holder.mText = (EditText) convertView.findViewById(R.id.editText4);
//                holder.mDate = (TextView) convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Data mData = data_list.get(position);

        holder.mIcon.setImageDrawable(mData.mIcon);
        holder.mIcon.setVisibility(View.VISIBLE);
        holder.mText.setText(mData.mTitle);
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public class ViewHolder {
        public ImageView mIcon;
        public EditText mText;
    }


    class Data {
        //아이콘
        public Drawable mIcon;
        //제목
        public String mTitle;

        public byte[] image_byte_array;

    }

}