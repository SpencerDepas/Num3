package com.example.spencer.num3e;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MyActivity extends ActionBarActivity {



    String cellNumber;
    String [] theNumberCombos = new String[70];
    String [] wordReturnedFromTxtFile = new String[70];

    int endOfArrayWordsList = 0;
    String finishedWord;
    String finishedWordWithDash;

    final int startOfNumberIndexFromTxt = 23;
    private ProgressBar spinner;
    private EditText editText;
    boolean pushOrClear = true;
    //private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);




        /*View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        editText = (EditText) findViewById(R.id.editText);
        //this is so that the keyboard starts as a text keyboard. it changes the aperance of the keyboard.
        //editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);



        ScrollView rl = (ScrollView)findViewById(R.id.ScrollView);
        rl.setBackgroundColor(getResources().getColor(R.color.white));

        final Button button = (Button) findViewById(R.id.button);
        //button.setBackgroundColor(getResources().getColor(R.color.white));

        final InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);


        editText.addTextChangedListener(watch);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    if (pushOrClear) {

                        if (editText.length() > 1 && editText.length() <= 14) {
                            editText.setEnabled(false);

                            button.setEnabled(false);
                            pushOrClear = false;

                            //this hides the keyboard on click
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                            spinner.setVisibility(View.VISIBLE);
                            //editText.setCursorVisible(false);
                            //setCursorVisible(boolean).
                            //editText.setVisibility(View.INVISIBLE);

                            cellNumber = editText.getText().toString().replaceAll("-","");
                            cellNumber = cellNumber.replaceAll("\\(","");
                            cellNumber = cellNumber.replaceAll("\\)", "");
                            cellNumber = cellNumber.replaceAll(" ","");

                            finishedWord = cellNumber;
                            finishedWordWithDash = cellNumber;
                            editText.setText(" ");

                            //this is to dynamikly change the editText length
                            //as the text returned is often longer
                            int maxLength = 20;
                            InputFilter[] fArray = new InputFilter[1];
                            fArray[0] = new InputFilter.LengthFilter(maxLength);
                            editText.setFilters(fArray);
                            //imageView.setFocusable(true);

                            //this is to get the combinations of numbers to check with the txt file
                            GetCombinations task = new GetCombinations();
                            task.execute(new String[]{cellNumber});

                        } else if (editText.length() < 2) {
                            toaster("Must be more than one digit");
                        }


                    } else {
                        //for clear
                        String buttonTrue = getResources().getString(R.string.buttonTrue);
                        editText.setText("");
                        button.setText(buttonTrue);
                        pushOrClear = true;
                        editText.setEnabled(true);
                        int maxLength = 14;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        editText.setFilters(fArray);
                    }


                }
            });

        // this is for if you press done on the keyboard and you want it to work as onclick
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    button.performClick();
                }
                return false;
            }
        });

    }

    TextWatcher watch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        //this addes and removes dashes to the editText
        @Override
        public void afterTextChanged(Editable s) {
            //48 to 57 char (int) of 0-9
            int indexOne = s.length() - 1;
            int indexTwo = s.length();

            //greater than zero to prevent crashing from deleting and restarting to zero.
            //this makes it so that only numbers can be entered.
            if(s.length() > 0 && pushOrClear) {
                String stringChar = editText.getText().toString().substring(s.length() - 1, s.length());
                char currentChar = stringChar.charAt(0);
                int charNumber = currentChar;



                if (!(charNumber >= 48 && charNumber <= 57)) {
                    // toaster();
                    if (s.length() > 0) {
                        s.delete(indexOne, indexTwo);
                    }
                }
            }


            //toaster(Integer.toString(charNumber));



            if (s.length() == 4) {
                String firstDash = editText.getText().toString().substring(3, 4);

                if(firstDash.equals("-")){
                    s.delete(3, 4);
                }else {
                    //editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    s.insert(3, "-");
                    //editText.setInputType(InputType.TYPE_CLASS_PHONE);
                }

            }
            if ( s.length() == 8) {
                String secondDash = editText.getText().toString().substring(7, 8);
                if(secondDash.equals("-")) {
                    s.delete(7, 8);
                }else{
                    //editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    s.insert(7, "-");
                    //editText.setInputType(InputType.TYPE_CLASS_PHONE);
                }
            }

            String editTextString = editText.getText().toString();
            //we dont want it to do this for the returned word.
            if ( s.length() == 13 && pushOrClear) {
                String beforeEleven = s.toString();
                String afterEleven = beforeEleven.replaceAll("-", "");

                //this is because you only want one set of brakets
                if(!(editTextString.contains("("))) {

                    afterEleven = new StringBuilder(afterEleven).insert(1, " (").toString();
                    afterEleven = new StringBuilder(afterEleven).insert(6, ") ").toString();
                    afterEleven = new StringBuilder(afterEleven).insert(11, "-").toString();
                    editText.setText(afterEleven);
                    //editText.setSelection(editText.getText().toString().length())
                    editText.setSelection(editText.length());
                }
            }
            //this is for when we have reached a full number and want to delete
            //so we want to change the format back to before brakets
            boolean editTextContainsBracket = editTextString.contains("(");
            if (s.length() == 15 && editTextContainsBracket){
                editTextString = editTextString.replace(" (", "");
                editTextString = editTextString.replace(") ", "");
                editTextString = editTextString.replace("-", "");

                editTextString = new StringBuilder(editTextString).insert(3, "-").toString();
                editTextString = new StringBuilder(editTextString).insert(7, "-").toString();
                editText.setText(editTextString);
                editText.setSelection(editText.length());

            }

        }
    };



    private class GetCombinations extends AsyncTask<String, Void, String> {

        //this is to get the combinations of numbers to check with the txt file
        @Override
        protected String doInBackground(String... urls) {



            int endBlockNumberLength = cellNumber.length() - 1;
            final  int combinationsPerBlock = 2;


            theNumberCombos[0] = cellNumber;

            //this is what creates every number combination of the input number
            // 123
            //12
            //23
            //1 , 2, 3
            for(int i = 0, z = 0, k = 1; i < theNumberCombos.length && z < combinationsPerBlock && endBlockNumberLength > 1; i++, k++ ){

                theNumberCombos[k] = cellNumber.substring(i, i + endBlockNumberLength);


                if(i + endBlockNumberLength == cellNumber.length()){
                    endBlockNumberLength --;
                    i = -1;
                }
            }


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

        public BufferedReader br;
        final AssetManager assetManager = getAssets();


        @Override
        protected String doInBackground(String... urls) {

            int arrayLine = 0;

            try {



                for(int z = 0; z < theNumberCombos.length; z++) {

                    InputStream input = assetManager.open("wordlist.txt");

                    br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

                    for (int u = 0; (sCurrentLine = br.readLine()) != null; ) {

                        if (theNumberCombos[z] == null) {
                            break;
                        }
                        if (theNumberCombos[z].equals(sCurrentLine.substring(startOfNumberIndexFromTxt))) {

                            wordReturnedFromTxtFile[arrayLine] = sCurrentLine;
                            arrayLine++;
                            break;

                        }
                    }
                    input.close();
                }

                //renews if you want to run again
                for(int i = 0; i < theNumberCombos.length; i ++){
                    theNumberCombos[i] = null;
                }

                for(int i = 0; i < wordReturnedFromTxtFile.length; i++ ){
                    if(wordReturnedFromTxtFile[i] != null){
                    }else if(endOfArrayWordsList == 0){
                        endOfArrayWordsList = i ;
                    }
                }

                //this is for if there are no returned words.
                if(wordReturnedFromTxtFile[0] != null) {
                    for (int i = 0; i < endOfArrayWordsList; i++) {

                        int sNumberEndIndex = wordReturnedFromTxtFile[i].indexOf(" ");
                        int sNumberStartIndex = wordReturnedFromTxtFile[i].indexOf(wordReturnedFromTxtFile[i].substring(0, sNumberEndIndex));
                        String numberWithoutWord = wordReturnedFromTxtFile[i].substring(sNumberStartIndex, sNumberEndIndex);
                        String onlyWord = wordReturnedFromTxtFile[i].substring(23);


                        finishedWord = finishedWord.replace(onlyWord, numberWithoutWord);
                        finishedWordWithDash = finishedWordWithDash.replace(onlyWord, "-" + numberWithoutWord + "-");


                        finishedWordWithDash = finishedWordWithDash.replace(" ", "");
                        //removes white space at start and end of word
                        if (finishedWordWithDash.charAt(0) == '-') {
                            finishedWordWithDash = finishedWordWithDash.substring(1, finishedWordWithDash.length());
                        }
                        if (finishedWordWithDash.charAt(finishedWordWithDash.length() - 1) == '-') {
                            finishedWordWithDash = finishedWordWithDash.substring(0, finishedWordWithDash.length() - 1);
                        }


                    }
                }else{
                    finishedWordWithDash = "Sorry mate.";
                }

                while(true){
                    if(finishedWordWithDash.contains("--")){
                        finishedWordWithDash = finishedWordWithDash.replace("--", "-");
                    }else{
                        break;
                    }
                }

                //renews if you want to run again
                for(int i = 0; i < wordReturnedFromTxtFile.length; i ++){
                    endOfArrayWordsList = 0;
                    wordReturnedFromTxtFile[i] = null;
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            //editText.setBackgroundResource(R.color.empty);
            //editText.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);

            editText.setGravity(Gravity.CENTER);
            //toaster(wordReturnedFromtxtFile[1]);

            //editText.setText(numberReturnedFromtxtFile[0]);

            editText.setText(finishedWordWithDash);

            final Button button = (Button) findViewById(R.id.button);
            button.setText("Clear");
            button.setEnabled(true);


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
