package com.example.spencer.num3e;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by spencer on 9/22/2014.
 */
public class TopFragmentLogo extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_fragment,
                container, false);

        return view;
    }


    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RelativeLayout rl = (RelativeLayout) getView().findViewById(R.id.RelativeLayout);
        rl.setBackgroundColor(getResources().getColor(R.color.white));

        final ImageView image = (ImageView) getView().findViewById(R.id.imageView3);

        image.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){


                FragmentManager fragmentManagerBottom = getFragmentManager();
                FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransactionBottom.replace(R.id.bottom, historyFragment, TAG_FRAGMENT);
                fragmentTransactionBottom.commit();
            }


    });

    }







}
