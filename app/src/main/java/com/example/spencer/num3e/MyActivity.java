package com.example.spencer.num3e;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RelativeLayout;



public class MyActivity extends Activity {

    //app was originally called Num3e




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(MyActivity.this);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //boolean firstEverBoot = (sharedpreferences.getInt("first_boot", 0)) == 420;

        int firstBoot = sharedpreferences.getInt("first_boot", 0);

        if (firstBoot == 0) {

            setContentView(R.layout.clearfaun);
            RelativeLayout clearf = (RelativeLayout) findViewById(R.id.clearfaunrl);
            clearf.setBackgroundColor(getResources().getColor(R.color.white));
            finish();


            int secondsDelayed = 2;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(MyActivity.this, CreateFragments.class);
                    MyActivity.this.startActivity(intent);

                }
            }, secondsDelayed * 1000);
            editor.putInt("first_boot", 420);
            editor.apply();
        }else{
            Intent intent = new Intent(MyActivity.this, CreateFragments.class);
            MyActivity.this.startActivity(intent);
            finish();
        }
    }







}