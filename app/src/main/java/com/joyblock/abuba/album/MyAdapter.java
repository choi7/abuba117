package com.joyblock.abuba.album;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joyblock.abuba.R;

import java.util.ArrayList;

/**
 * Created by hyoshinchoi on 2018. 1. 23..
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<MyData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
//        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.custom_cell_album_image);
//            mTextView = (TextView) view.findViewById(R.id.textview);
        }
    }

    public MyAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_cell_album_image_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
//        holder.mTextView.setText(mDataset.get(position).text);
        holder.mImageView.setImageResource(mDataset.get(position).img);
//        holder.mImageView.setImageResource(mDataset.get(position).img);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    static class MyData {
        public int img;
//        public String text;
        public MyData(int img) {
//            this.text = text;
            this.img = img;
        }


    }
}
