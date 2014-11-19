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

        setContentView(R.layout.clearfaun);
        RelativeLayout clearf = (RelativeLayout) findViewById(R.id.clearfaunrl);
        clearf.setBackgroundColor(getResources().getColor(R.color.white));



        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(MyActivity.this);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        // removes saved prefrences

       /* editor.clear();
        editor.apply();*/

        int firstBoot = sharedpreferences.getInt("first_boot", 0);



        if (firstBoot == 310) {

            //then start like normal
            Intent intent = new Intent(MyActivity.this, CreateFragments.class);
            MyActivity.this.startActivity(intent);
            finish();

        }else{
            //show logo on first boot
            int secondsDelayed = 2;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    editor.putInt("first_boot", 310);
                    editor.apply();
                    Intent intent = new Intent(MyActivity.this, CreateFragments.class);
                    MyActivity.this.startActivity(intent);
                    finish();

                }
            }, secondsDelayed * 1000);
        }
    }

}