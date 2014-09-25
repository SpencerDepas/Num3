package com.example.spencer.num3e;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by spencer on 9/22/2014.
 */



public class ComputateFragment  extends Fragment {


    String cellNumber;
    String [] theNumberCombos = new String[70];
    String [] wordReturnedFromTxtFile = new String[70];

    int endOfArrayWordsList = 0;
    String finishedWordWithDash;

    final int startOfNumberIndexFromTxt = 23;
    private ProgressBar spinner;
    private EditText editText;
    boolean pushOrClear = true;



    //this is for saving already searched numbers
    public static int makeNumberOneUnique = 0;

    public static int SavedListOfWordsArrayIndex = 0;
    public static final String HistoryArraySize = "arrayIndex";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.computate_fragment,
                container, false);

        //button.setBackgroundColor(getResources().getColor(R.color.white));



        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spinner = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
        final Button button = (Button) getActivity().findViewById(R.id.button);
        editText = (EditText) getActivity().findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);



        ScrollView rl = (ScrollView) getView().findViewById(R.id.ScrollView);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);


        editText.addTextChangedListener(watch);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //button status,
                if (pushOrClear) {

                    if (editText.length() > 1 && editText.length() <= 16) {
                        editText.setEnabled(false);

                        button.setEnabled(false);
                        pushOrClear = false;

                        //this hides the keyboard on click
                        imm.hideSoftInputFromWindow(null, 0);

                        spinner.setVisibility(View.VISIBLE);

                        cellNumber = editText.getText().toString().replaceAll("-","");
                        cellNumber = cellNumber.replaceAll("\\(","");
                        cellNumber = cellNumber.replaceAll("\\)", "");
                        cellNumber = cellNumber.replaceAll(" ","");

                        finishedWordWithDash = cellNumber;
                        editText.setText(" ");

                        //this is to get the combinations of numbers to check with the txt file
                        GetCombinations task = new GetCombinations();
                        task.execute();

                    } else if (editText.length() < 2) {
                        //toaster("Must be more than one digit");
                    }


                } else {
                    //for when the button is in clear mode
                    String buttonTrue = getResources().getString(R.string.buttonTrue);
                    editText.setText("");
                    button.setText(buttonTrue);
                    pushOrClear = true;
                    editText.setEnabled(true);
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

        //this adds and removes dashes to the editText
        @Override
        public void afterTextChanged(Editable s) {
            //48 to 57 char (int) of 0-9
            int indexOne = s.length() - 1;
            int indexTwo = s.length();
            String editTextString = editText.getText().toString();
            boolean editTextContainsBracket = editTextString.contains("(");

            //greater than zero to prevent crashing from deleting and restarting to zero.
            //this makes it so that only numbers can be entered.
            if(s.length() > 0 && pushOrClear) {
                //String stringChar = editText.getText().toString().substring(s.length() - 1, s.length()).charAt(0);

                int charCharacterValue = editText.getText().toString().substring(s.length() - 1, s.length()).charAt(0);
                //48 to 57 char (int) of 0-9
                if (!(charCharacterValue >= 48 && charCharacterValue <= 57)) {
                    //deletes if not a number
                    if (s.length() > 0) {
                        s.delete(indexOne, indexTwo);
                    }
                }
            }

            if (s.length() == 4) {
                String firstDash = editText.getText().toString().substring(3, 4);

                if(firstDash.equals("-")){
                    s.delete(3, 4);
                }else {
                    s.insert(3, "-");
                }
            }

            if ( s.length() == 8) {
                String secondDash = editText.getText().toString().substring(7, 8);
                if(secondDash.equals("-")) {
                    s.delete(7, 8);
                }else{
                    s.insert(7, "-");
                }
            }

            //we do not want it to do this for the returned word.
            if (s.length() == 13 && pushOrClear) {
                String fullyFormattedNumber = editTextString.replaceAll("-", "");

                //this is because you only want one set of brackets
                if(!(editTextString.contains("("))) {

                    fullyFormattedNumber = new StringBuilder(fullyFormattedNumber).insert(1, " (").toString();
                    fullyFormattedNumber = new StringBuilder(fullyFormattedNumber).insert(6, ") ").toString();
                    fullyFormattedNumber = new StringBuilder(fullyFormattedNumber).insert(11, "-").toString();
                    editText.setText(fullyFormattedNumber);
                    editText.setSelection(editText.length());
                }
            }
            //this is for when we have reached a full number and want to delete
            //so we want to change the format back to before brackets
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

    // buffered reader, this is what enters the txt file and returns words
    private class AS extends AsyncTask<String, Void, String> {


        public String sCurrentLine = "";

        public BufferedReader br;
        final AssetManager assetManager = getActivity().getAssets();


        @Override
        protected String doInBackground(String... urls) {

            int arrayLine = 0;

            try {
                for(int z = 0; z < theNumberCombos.length; z++) {

                    InputStream input = assetManager.open("wordlist.txt");
                    br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

                    for ( ; (sCurrentLine = br.readLine()) != null; ) {

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
                    if(wordReturnedFromTxtFile[i] == null && endOfArrayWordsList == 0){
                        endOfArrayWordsList = i ;
                    }
                }

                //this is for if there are no returned words.
                if(wordReturnedFromTxtFile[0] != null) {
                    for (int i = 0; i < endOfArrayWordsList; i++) {

                        int sNumberEndIndex = wordReturnedFromTxtFile[i].indexOf(" ");
                        int sNumberStartIndex = wordReturnedFromTxtFile[i].indexOf(wordReturnedFromTxtFile[i].substring(0, sNumberEndIndex));
                        String numberBeforeItBecomesAWord = wordReturnedFromTxtFile[i].substring(sNumberStartIndex, sNumberEndIndex);
                        String onlyWord = wordReturnedFromTxtFile[i].substring(23);

                        finishedWordWithDash = finishedWordWithDash.replace(onlyWord, "-" + numberBeforeItBecomesAWord + "-");


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



        //public static int makeStringUnique = 0;
        //public static final String NumberOne = "transformedNumber" + Integer.toString(makeStringUnique);
        //int historyOfSearchedNumbersCounter = 0;
        //public static final int SavedListOfWordsArrayIndex = 0;
        //public static final String historyOfSearchedNumbersCounter = "arrayIndex";
        //public String[] returnedNumbers = new String[30];


        @Override
        protected void onPostExecute(String result) {



            //this is to save searched numbers
            if(!(finishedWordWithDash.equals("Sorry mate."))){

                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                int arraySize = (sharedpreferences.getInt(ComputateFragment.HistoryArraySize, 0));
                SharedPreferences.Editor editor = sharedpreferences.edit();

                if(arraySize == 0){
                    editor.putInt(HistoryArraySize, SavedListOfWordsArrayIndex);
                    editor.putString("array_" + SavedListOfWordsArrayIndex, finishedWordWithDash);
                    editor.apply();
                    SavedListOfWordsArrayIndex ++;
                }else {

                    editor.putString("array_" + SavedListOfWordsArrayIndex, finishedWordWithDash);
                    editor.putInt(HistoryArraySize, SavedListOfWordsArrayIndex);
                    editor.apply();
                    SavedListOfWordsArrayIndex++;
                }
            }



            spinner.setVisibility(View.GONE);
            editText.setGravity(Gravity.CENTER);
            editText.setText(finishedWordWithDash);



            final Button button = (Button) getView().findViewById(R.id.button);
            button.setText("Clear");
            button.setEnabled(true);

        }

    }
    //public static String historyStringArray = "transformedNumber";
    //public static ArrayList<String> returnedNumbers = new ArrayList<String>();


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/



  /*  public void toaster(String text) {
        Context context = getView().getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }*/

}
