package com.joyblock.abuba.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyblock.abuba.R;

/**
 * Created by hyoshinchoi on 2018. 1. 10..
 */

public class FragmentSurvey extends android.support.v4.app.Fragment {

    public FragmentSurvey() {


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
        return inflater.inflate(R.layout.fragment_notice_survey, container, false);
    }

}
