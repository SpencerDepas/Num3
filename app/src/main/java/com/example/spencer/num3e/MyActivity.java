package com.example.spencer.num3e;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MyActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        FragmentManager fragmentManagerTop = getFragmentManager();
        FragmentTransaction fragmentTransactionTop = fragmentManagerTop.beginTransaction();

        TopFragmentLogo fragmentTop = new TopFragmentLogo();
        fragmentTransactionTop.add(R.id.top, fragmentTop);
        fragmentTransactionTop.commit();


        FragmentManager fragmentManagerBottom = getFragmentManager();
        FragmentTransaction fragmentTransactionBottom = fragmentManagerBottom.beginTransaction();

        ComputateFragment computateFragment = new ComputateFragment();
        fragmentTransactionBottom.add(R.id.bottom, computateFragment);
        fragmentTransactionBottom.commit();

        LinearLayout rl = (LinearLayout) findViewById(R.id.LinearLayout3);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


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

            fragmentTransactionBottom.replace(R.id.bottom, computateFragment);
            fragmentTransactionBottom.commit();

        }else{
            finish();
        }
    }

}