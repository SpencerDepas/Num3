package com.example.spencer.num3e;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Created by spencer
 * on 9/22/2014.
 */



public class ComputateFragment  extends Fragment {


    static String cellNumber;

    ArrayList<String> wordReturnedFromDict = new ArrayList<String>();
    ArrayList<String> numberIterations = new ArrayList<String>();

    String finishedWordWithDash;

    final int START_OF_NUMBER_INDEX_FROM_TXT = 23;
    private ProgressBar spinner;
    private ProgressBar onBootSpinner;
    static EditText editText;
    static TextView wordAfterReturnedGreyBar;
    Button button;

    //this is for saving already searched numbers
    public static int SavedListOfWordsArrayIndex = 0;
    public static final String HISTOY_ARRAY_SIZE = "arrayIndex";


    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.computate_fragment,
                container, false);

        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinner = (ProgressBar) getActivity().findViewById(R.id.progressBar1);
        onBootSpinner = (ProgressBar) getActivity().findViewById(R.id.onBootProgressBar);

        button = (Button) getActivity().findViewById(R.id.button);
        editText = (EditText) getActivity().findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        wordAfterReturnedGreyBar = (TextView) getActivity().findViewById(R.id.greybar);

        button.getBackground().setColorFilter(0xFFBBAA00, PorterDuff.Mode.MULTIPLY);
        button.setBackgroundColor(getResources().getColor(R.color.LightSkyBlue));

        editText.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        onBootSpinner.setVisibility(View.VISIBLE);

        CreateHashTable task = new CreateHashTable();
        task.execute();


        editText.addTextChangedListener(watch);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                /*wordReturnedFromDict.clear();
                numberIterations.clear();*/
                finishedWordWithDash = "";
                wordAfterReturnedGreyBar.setText("");
                //for when the button is in clear mode
                editText.setText("");




            }
        });

    }


    private class CreateHashTable extends AsyncTask<String, Void, String> {


        public String sCurrentLine = "";

        public BufferedReader br;
        final AssetManager assetManager = getActivity().getAssets();

        @Override
        protected String doInBackground(String... params) {
            try {
                if (dict == null) {
                    dict = new Hashtable<String, ArrayList<String>>();
                    InputStream input = assetManager.open("wordlist.txt");
                    br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

                    for (; (sCurrentLine = br.readLine()) != null; ) {
                        String number = sCurrentLine.substring(START_OF_NUMBER_INDEX_FROM_TXT);
                        String word = sCurrentLine.substring(0, START_OF_NUMBER_INDEX_FROM_TXT).trim();
                        ArrayList<String> arr = dict.get(number);
                        if (arr == null) {
                            arr = new ArrayList<String>();
                            dict.put(number, arr);
                        }

                        arr.add(word);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            onBootSpinner.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);

        }
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

            if(s.length() > 0) {
                int currentCharCharacterValue = editText.getText().toString().substring(s.length() - 1, s.length()).charAt(0);
                //REMOVES everything that is not a number
                if (!(currentCharCharacterValue >= 48 && currentCharCharacterValue <= 57)) {
                    //deletes if not a number
                    if (s.length() > 0) {
                        s.delete(indexOne, indexTwo);
                    }
                }else if(editText.length() > 1 && editText.length() <= 14){

                    // this is so that it computes on every number you enter
                    getNumberReturn();

                }else if(editText.length() < 2 ){

                    wordAfterReturnedGreyBar.setText("");

                }
            }



        }
    };

    //this is for when a NUMBER IS ENTERED AND YOU WANT TO GET THE REURN
    public void getNumberReturn(){

        spinner.setVisibility(View.VISIBLE);

        cellNumber = editText.getText().toString().replaceAll("-","");
        cellNumber = cellNumber.replaceAll("\\(","");
        cellNumber = cellNumber.replaceAll("\\)", "");
        cellNumber = cellNumber.replaceAll(" ","");




        finishedWordWithDash = cellNumber;
        //editText.setText(" ");

        //this is to get the combinations of numbers to check with the txt file
        GetCombinations task = new GetCombinations();
        task.execute();


    }

    class GetCombinations extends AsyncTask<String, Void, String> {

        //this is to get the combinations of numbers to check with the txt file
        @Override
        protected String doInBackground(String... urls) {

            int endBlockNumberLength = cellNumber.length() - 1;


            numberIterations.add(cellNumber);

            //this is what creates every number combination of the input number
            // 123
            //12
            //23    i < numberIterations.size() && endBlockNumberLength > 1
            //1 , 2, 3
            for(int i = 0; i < numberIterations.size() && endBlockNumberLength > 1 ; i++){

                numberIterations.add(cellNumber.substring(i, i + endBlockNumberLength));

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


            AS_HashTable taskTwo = new AS_HashTable();
            taskTwo.execute();
        }




    }

    static Hashtable<String, ArrayList<String>> dict;


    private class AS_HashTable extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            for(String number : numberIterations) {
                ArrayList<String> arr = dict.get(number);
                if (arr != null) {
                    String word = arr.get(0);
                    wordReturnedFromDict.add(number + "," + word);
                }
            }



            //renews if you want to run again
            numberIterations.clear();


            //this is for if there are no returned words.
            if(!wordReturnedFromDict.isEmpty()) {
                for (String returnedWord: wordReturnedFromDict) {

                    String[] numberAndWord = returnedWord.split(",");
                    String number = numberAndWord[0];
                    String word = numberAndWord[1];

                    finishedWordWithDash = finishedWordWithDash.replace(number, "-" + word + "-");


                    finishedWordWithDash = finishedWordWithDash.replace(" ", "");
                    //removes white space at start and end of word
                    if (finishedWordWithDash.charAt(0) == '-') {
                        finishedWordWithDash = finishedWordWithDash.substring(1, finishedWordWithDash.length());
                    }
                    if (finishedWordWithDash.charAt(finishedWordWithDash.length() - 1) == '-') {
                        finishedWordWithDash = finishedWordWithDash.substring(0, finishedWordWithDash.length() - 1);
                    }
                }
            }
            else{
                finishedWordWithDash = "Sorry mate.";
            }

            while(finishedWordWithDash.contains("--")){

                finishedWordWithDash = finishedWordWithDash.replace("--", "-");

            }





            return null;
        }






        @Override
        protected void onPostExecute(String result) {

            


            wordAfterReturnedGreyBar = (TextView) getActivity().findViewById(R.id.greybar);
            //wordAfterReturnedGreyBar.setVisibility(View.VISIBLE);



            wordAfterReturnedGreyBar.setText(finishedWordWithDash);

            editText.removeTextChangedListener(watch);

            spinner.setVisibility(View.GONE);
            editText.setGravity(Gravity.CENTER);

            editText.setSelection(editText.getText().toString().length());
            editText.addTextChangedListener(watch);

            wordReturnedFromDict.clear();

        }

    }






    public static void computeContactNumber(String fromContactsNumber) {

        editText.setText(fromContactsNumber);


    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        editText.setText("");
        editText.addTextChangedListener(watch);
        editText.setText("");

    }

    public void toast(String string){

        Context context = getActivity().getApplicationContext();
        CharSequence text = string;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }


}
