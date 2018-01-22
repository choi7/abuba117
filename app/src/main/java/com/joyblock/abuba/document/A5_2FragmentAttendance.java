package com.joyblock.abuba.document;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hyoshinchoi on 2018. 1. 22..
 */

public class A5_2FragmentAttendance extends android.support.v4.app.Fragment {

    SharedPreferences pref;
    String seq_user,seq_kindergarden,seq_kindergarden_class;
//    NoticeListViewAdapter adapter;
//    ListView listView;
//
//    R13_SelectNoticeList[] noticeList;

    void setPref(SharedPreferences pref){
        this.pref=pref;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notice_notice, container, false);

//        adapter = new NoticeListViewAdapter(getContext());
//
//        listView = rootView.findViewById(R.id.noticeListView);
//
//        listView.setAdapter(adapter);

//        String seq_kindergarden=pref.getString("seq_kindergarden","없음");
//        String seq_kindergarden_class=pref.getString("seq_kindergarden_class","없음");

//        new SelectNoticeList(seq_kindergarden).execute();
//        Log.d("Tag","공지 개수"+noticeList.length);


//        for(int i=0;i<4;i++ ){
//            adapter.addItem(getResources().getDrawable(R.mipmap.ic_document), "11","11","11");
//        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
//                intent.putExtra("seq_notice",noticeList[position].seq_notice);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//                startActivity(intent);
//            }
//        });
//        adapter.notifyDataSetChanged();
//
//        return rootView;


//        return inflater.inflate(R.layout.noticelistviewcustom, container, false);

    }
}
