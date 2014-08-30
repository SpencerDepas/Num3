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
    String [] theNumbercombos = new String[70];
    String [] wordReturnedFromtxtFile = new String[70];
    int totalNumberCombos = 1;
    int endOfArray = 0;
    int endOfArrayWordsList = 0;
    int increaseOnClick = 0;
    String finishedWord;

    final int startOfNumberIndexFromtxt = 23;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        final EditText editText = (EditText) findViewById(R.id.editText);


        final Button button = (Button) findViewById(R.id.button);


            button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                cellNumber = editText.getText().toString();
                finishedWord = cellNumber;
                increaseOnClick ++;



                //this is to get the combinations of numbers to check with the txt file
                GetCombinations task = new GetCombinations();
                task.execute(new String[] { cellNumber });



            }
    });


    }



    private class GetCombinations extends AsyncTask<String, Void, String> {

        //this is to get the combinations of numbers to check with the txt file
        @Override
        protected String doInBackground(String... urls) {



            int endBlockNumberLength = cellNumber.length() - 1;
            int combinationsPerBlock = 2;


            theNumbercombos[0] = cellNumber;

            //this is what creates every number combination of the input number
            // 123
            //12
            //23
            //1 , 2, 3
            for(int i = 0, z = 0, k = 1; i < theNumbercombos.length && z < combinationsPerBlock && endBlockNumberLength > 1; i++, k++ ){

                theNumbercombos[k] = cellNumber.substring(i, i + endBlockNumberLength);


                if(i + endBlockNumberLength == cellNumber.length()){
                    endBlockNumberLength --;
                    i = -1;
                }
            }

            //tells you the end of the array
           /* for(int i = 0; i < theNumbercombos.length; i++ ){
                if(theNumbercombos[i] != null){

                }else if(endOfArray == 0){
                    endOfArray = i - 1 ;
                }

            }*/


            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            //this is for buffered reader and going through txt files ect
            AS task = new AS();
            task.execute();
        }




    }




    // buffered reader shit, this is what enters the txt file and returns words
    private class AS extends AsyncTask<String, Void, String> {


        public String sCurrentLine = "";
        String sCurrentLineNumberOnly = "";
        String sCurrentLineWordOnly = "";
        final int startOfNumberIndex = 23;

        public BufferedReader br;
        final AssetManager assetManager = getAssets();


        @Override
        protected String doInBackground(String... urls) {

            int arrayline = 0;

            try {



                for(int z = 0; z < theNumbercombos.length; z++) {

                    InputStream input = assetManager.open("wordlist.txt");

                    br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

                    for (int u = 0; (sCurrentLine = br.readLine()) != null; ) {

                        if (theNumbercombos[z] == null) {
                            break;
                        }

                        if (theNumbercombos[z].equals(sCurrentLine.substring(startOfNumberIndexFromtxt))) {
                            int endOfWordIndex = sCurrentLine.indexOf(' ');


                            wordReturnedFromtxtFile[arrayline] = sCurrentLine;

                            arrayline++;


                            u++;
                            break;

                        }
                    }


                    input.close();
                }

                for(int i = 0; i < wordReturnedFromtxtFile.length; i++ ){
                    if(wordReturnedFromtxtFile[i] != null){
                    }else if(endOfArrayWordsList == 0){
                        endOfArrayWordsList = i ;
                    }
                }

                for(int i = 0; i < endOfArrayWordsList; i++){
                    int sNumberEndIndex = wordReturnedFromtxtFile[i].indexOf(" ");
                    int sNumberStartIndex = wordReturnedFromtxtFile[i].indexOf(wordReturnedFromtxtFile[i].substring(0, sNumberEndIndex));
                    String numberWithoutWord = wordReturnedFromtxtFile[i].substring(sNumberStartIndex, sNumberEndIndex);
                    String onlyWord = wordReturnedFromtxtFile[i].substring(23);


                    finishedWord = finishedWord.replace(onlyWord, numberWithoutWord);
                }




                   /* if(theNumbercombos[i] == null){
                        break;
                    }

                    sCurrentLineNumberOnly = sCurrentLine.substring(startOfNumberIndex);

                    if(theNumbercombos[0].equals(sCurrentLineNumberOnly)){

                        int endOfWord =  sCurrentLine.indexOf(' ');
                        sCurrentLineWordOnly = sCurrentLine.substring(0, endOfWord);
                        break;
                    }*/





            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            toaster(wordReturnedFromtxtFile[1]);
            final EditText editText = (EditText) findViewById(R.id.editText);
            //editText.setText(numberReturnedFromtxtFile[0]);
            //editText.setText(Integer.toString(increaseOnClick));

            editText.setText(finishedWord);
            //editText.setText(theNumbercombos[0]);


        }

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


}
