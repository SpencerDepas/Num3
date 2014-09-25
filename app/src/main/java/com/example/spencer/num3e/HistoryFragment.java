package com.example.spencer.num3e;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by spencer on 9/22/2014.
 */
public class HistoryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.history_fragment,
                container, false);

        MyActivity.inHistoryFragment = true;

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout rl = (LinearLayout) getView().findViewById(R.id.HistoryLayout);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


    }






}