package com.joyblock.abuba.test_park;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.joyblock.abuba.BaseActivity;
import com.joyblock.abuba.util.ImageFileProcessor;
import com.joyblock.abuba.ListData;
import com.joyblock.abuba.R;

import java.util.ArrayList;

/**
 * Created by POPCON on 2018-01-19.
 */

public class QuestionnaireListViewAdapter extends BaseActivity implements ListAdapter {
    Activity activity;
//    private Context mContext = null;
    private ArrayList<ListData> mListData = new ArrayList<ListData>();

    public QuestionnaireListViewAdapter(Activity activity) {
        super();
//        this.mContext = mContext;
        this.activity=activity;
    }

    public void addItem(Drawable icon, String mTitle) {
        ListData addInfo = null;
        addInfo = new ListData();
        addInfo.mIcon = icon;
        addInfo.mTitle = mTitle;
        mListData.add(addInfo);
    }

    public void setIcon(int position,Drawable image){
        ListData item=(ListData)getItem(position);
        item.mIcon=image;

    }




    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
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

        ViewHolder holder;
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
        ListData mData = mListData.get(position);
        if (mData.mIcon != null) {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mData.mIcon);
        } else {
            holder.mIcon.setVisibility(View.GONE);
        }
        holder.mText.setText(mData.mTitle);

        final int pos=position;
        holder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageFileProcessor().selectGallery(activity,pos);

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

}