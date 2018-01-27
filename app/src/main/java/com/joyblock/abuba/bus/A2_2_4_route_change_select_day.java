package com.joyblock.abuba.bus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.joyblock.abuba.R;
public class A2_2_4_route_change_select_day extends AppCompatActivity {
    public static A2_2_4_route_change_select_day A2_2_4_route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        A2_2_4_route=A2_2_4_route_change_select_day.this;
        setContentView(R.layout.layout_a2_2_4_route_change_select_day);
    }
}
