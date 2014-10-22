package com.example.spencer.num3e;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;

/**
 * Created by spencer on 9/30/2014.
 */
public class TransitionFromScrollView extends ActionBarActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        LinearLayout rl = (LinearLayout) findViewById(R.id.LinearLayout3);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


        FragmentManager fragmentManagerTop = getFragmentManager();
        FragmentTransaction fragmentTransactionTop = fragmentManagerTop.beginTransaction();

        TopFragmentLogo fragmentTop = new TopFragmentLogo();
        fragmentTransactionTop.replace(R.id.top, fragmentTop);
        fragmentTransactionTop.commit();


        FragmentManager fragmentManagerBottom = getFragmentManager();
        FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

        HistoryFragment historyFragment = new HistoryFragment();
        fragmentTransactionBottom.add(R.id.bottom, historyFragment);
        fragmentTransactionBottom.commit();

    }
}
