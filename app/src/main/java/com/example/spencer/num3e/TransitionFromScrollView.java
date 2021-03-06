package com.example.spencer.num3e;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by spencer on
 * 9/30/2014.
 */
public class TransitionFromScrollView extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("MyTransitionFromScroll", "TransitionFromScrollView ");

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
