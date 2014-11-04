package com.example.spencer.num3e;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by spencer on 11/4/2014.
 */
public class CreateFragments extends Activity {


    static boolean scrollViewState = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_container_with_scroll_view);


        FragmentManager fragmentManagerTop = getFragmentManager();
        FragmentTransaction fragmentTransactionTop = fragmentManagerTop.beginTransaction();

        TopFragmentLogo fragmentTop = new TopFragmentLogo();
        fragmentTransactionTop.add(R.id.topScrollView, fragmentTop);
        fragmentTransactionTop.commit();


        FragmentManager fragmentManagerBottom = getFragmentManager();
        FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

        ComputateFragment computateFragment = new ComputateFragment();
        fragmentTransactionBottom.add(R.id.bottomScrollView, computateFragment);
        fragmentTransactionBottom.commit();


        LinearLayout rl = (LinearLayout) findViewById(R.id.LinearLayoutContainer);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


    }

    //this is so that if we are in the computate fragemnt, when we press back
    //we go back and dont stay in the same place.
    public static boolean inHistoryFragment = false;

    @Override
    public void onBackPressed() {

        if (inHistoryFragment) {

            inHistoryFragment = false;

            FragmentManager fragmentManagerBottom = getFragmentManager();
            FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

            ComputateFragment computateFragment = new ComputateFragment();

            fragmentTransactionBottom.replace(R.id.bottomScrollView, computateFragment);
            fragmentTransactionBottom.commit();

        } else {
            finish();
        }
    }

}
