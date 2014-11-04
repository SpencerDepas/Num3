package com.example.spencer.num3e;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MyActivity extends Activity {

    static boolean scrollViewState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();*/

        //setContentView(R.layout.fragment_container_with_scroll_view);

        setContentView(R.layout.clearfaun);
        RelativeLayout clearf = (RelativeLayout) findViewById(R.id.clearfaunrl);
        clearf.setBackgroundColor(getResources().getColor(R.color.white));

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startNewActivity();
                finish();
            }
        }, secondsDelayed * 1000);


        //toaster("hi");
        /*setContentView(R.layout.fragment_container_with_scroll_view);


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
        rl.setBackgroundColor(getResources().getColor(R.color.white));*/



    }

    public void startNewActivity(){
        Intent intent = new Intent(MyActivity.this, CreateFragments.class);
        MyActivity.this.startActivity(intent);

    }

    //this is so that if we are in the computate fragemnt, when we press back
    //we go back and dont stay in the same place.
    public static boolean inHistoryFragment = false;

    @Override
    public void onBackPressed() {

        if(inHistoryFragment) {

            MyActivity.inHistoryFragment = false;

            FragmentManager fragmentManagerBottom = getFragmentManager();
            FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

            ComputateFragment computateFragment = new ComputateFragment();

            fragmentTransactionBottom.replace(R.id.bottomScrollView, computateFragment);
            fragmentTransactionBottom.commit();

        }else{
            finish();
        }
    }



    public void toaster(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}