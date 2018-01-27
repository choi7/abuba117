package com.joyblock.abuba.bus;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joyblock.abuba.R;


/**
 * Created by hyoshinchoi on 2018. 1. 10..
 */

public class A2_1_boarding_management_fragment extends android.support.v4.app.Fragment {


    SharedPreferences pref;
    String seq_user,seq_kindergarden,seq_kindergarden_class;
    BoardingListViewAdapter adapter;
    ListView listView;

    String plan_flag;

    int int_selected=-51;


    void setFlag(String plan_flag){
        this.plan_flag=plan_flag;
    }



    void setPref(SharedPreferences pref){
        this.pref=pref;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        seq_user = pref.getString("seq_user","dd");
//        Log.d("seq_user : ", seq_user);


    }

    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    */




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.layout_a2_1_boarding_management_fragment, container, false);

        adapter = new BoardingListViewAdapter(R.layout.row_boarding_list,false);

        listView = rootView.findViewById(R.id.planListView);

        listView.setAdapter(adapter);
        adapter.addItem("호랑이반","김철수",false );
        adapter.addItem("호랑이반","김영미",false );
        adapter.addItem("호랑이반","김미영",true );
        adapter.addItem("호랑이반","이영호",true );
        adapter.addItem("호랑이반","정현",true );
        adapter.addItem("호랑이반","이종석",false );



//        String seq_kindergarden=pref.getString("seq_kindergarden","없음");
//        String seq_kindergarden_class=pref.getString("seq_kindergarden_class","없음");

//        new SelectNoticeList(seq_kindergarden).execute();
//        Log.d("Tag","공지 개수"+noticeList.length);


//        for(int i=0;i<4;i++ ){
//            adapter.addItem(getResources().getDrawable(R.mipmap.ic_document), "11","11","11");
//        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                int_selected=position;
            }
        });
        adapter.notifyDataSetChanged();

        TextView left=(TextView)rootView.findViewById(R.id.textview_left);
        TextView right=(TextView)rootView.findViewById(R.id.textview_right);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoarding(false);
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBoarding(true);
            }
        });
//        new BuyTask(seq_kindergarden,plan_flag).execute();
        return rootView;
//        return inflater.inflate(R.layout.noticelistviewcustom, container, false);
    }

    void setBoarding(boolean bool_boarding){
        if(int_selected<0)
            Toast.makeText(getContext(),"아이를 먼저 선택해주세요.",Toast.LENGTH_SHORT).show();
        else {
            adapter.setBoarding(int_selected,bool_boarding);
        }
    }

}
