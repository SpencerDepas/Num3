package com.example.spencer.num3e;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MyActivity extends ActionBarActivity {


    String cellNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        final EditText editText = (EditText) findViewById(R.id.editText);


        final Button button = (Button) findViewById(R.id.button);


            button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cellNumber = editText.getText().toString();

                AS task = new AS();
                task.execute(new String[] { cellNumber });

            }
    });


    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        return true;
    }



    public void toaster(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class AS extends AsyncTask<String, Void, String> {


        public String sCurrentLine = "";
        String sCurrentLineNumberOnly = "";
        String sCurrentLineWordOnly = "";
        final int startOfNumberIndex = 23;

        public BufferedReader br;
        final AssetManager assetManager = getAssets();


        @Override
        protected String doInBackground(String... urls) {

            try {

                int i = 0;

                InputStream input = assetManager.open("wordlist.txt");

                br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

                while ((sCurrentLine = br.readLine()) != null){

                    sCurrentLineNumberOnly = sCurrentLine.substring(startOfNumberIndex);

                    if(cellNumber.equals(sCurrentLineNumberOnly)){

                        int endOfWord =  sCurrentLine.indexOf(' ');
                        sCurrentLineWordOnly = sCurrentLine.substring(0, endOfWord);
                        break;
                    }
                }

                input.close();

            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            toaster(sCurrentLineNumberOnly);
            final EditText editText = (EditText) findViewById(R.id.editText);
            editText.setText(sCurrentLineWordOnly);
        }




    }




}
