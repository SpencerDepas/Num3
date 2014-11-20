package com.example.spencer.num3e;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by spencer
 * on 11/4/2014.
 */
public class CreateFragments extends Activity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_container_with_scroll_view);




        FragmentManager fragmentManagerTop = getFragmentManager();
        FragmentTransaction fragmentTransactionTop = fragmentManagerTop.beginTransaction();

        TopFragmentLogo fragmentTop = new TopFragmentLogo();
        fragmentTransactionTop.replace(R.id.topScrollView, fragmentTop);
        fragmentTransactionTop.commit();


        FragmentManager fragmentManagerBottom = getFragmentManager();
        FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

        ComputateFragment computateFragment = new ComputateFragment();
        fragmentTransactionBottom.replace(R.id.bottomScrollView, computateFragment);
        fragmentTransactionBottom.commit();


        LinearLayout rl = (LinearLayout) findViewById(R.id.LinearLayoutContainer);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


    }

    //this is so that if we are in the computate fragemnt, when we press back
    //we go back and dont stay in the same place.
    public static boolean inHistoryFragment = false;

    TextView numberAfterReturnedWord;
    FragmentManager fragmentManagerBottom;
    FragmentTransaction fragmentTransactionBottom;

    @Override
    public void onBackPressed() {

        numberAfterReturnedWord = (TextView) this.findViewById(R.id.greybar);
        numberAfterReturnedWord.setText("");

        if (inHistoryFragment) {

            inHistoryFragment = false;

            fragmentManagerBottom = getFragmentManager();
            fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

            ComputateFragment computateFragment = new ComputateFragment();

            fragmentTransactionBottom.replace(R.id.bottomScrollView, computateFragment);
            fragmentTransactionBottom.commit();

        } else {
            finish();

        }
    }

    public interface Constants {
        String LOG = "SZD2";
    }

    @Override
    public void onRestart() {
        super.onRestart();



    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        Log.d(Constants.LOG, "onPause called in MyActivity");

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(Constants.LOG, "onStop called in MyActivity");
        //onDestroy();



    }

    @Override
    public void onResume(){
        super.onResume();

        Log.d(Constants.LOG, "onResume called in MyActivity");

    }

    public void onDestroy() {
        super.onDestroy();



        //MyActivity.destroyPlease();
        Log.d(Constants.LOG, "onDestroy called in MyActivity");


    }






}
