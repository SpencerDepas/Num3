package com.example.spencer.num3e;

import android.app.Fragment;
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

    ArrayList<String> wordReturnedFromTxtFile = new ArrayList<String>();
    ArrayList<String> theNumberCombos = new ArrayList<String>();

    String finishedWordWithDash;

    final int STARTOFNUMBERINDEXFROMTXT = 23;
    private ProgressBar spinner;
    static EditText editText;
    static TextView wordAfterReturnedGreyBar;
    static boolean pushOrClear = true;

    //this is for saving already searched numbers
    public static int SavedListOfWordsArrayIndex = 0;
    public static final String HISTOYARRAYSIZE = "arrayIndex";


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
        final Button button = (Button) getActivity().findViewById(R.id.button);
        editText = (EditText) getActivity().findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);

        button.getBackground().setColorFilter(0xFFBBAA00, PorterDuff.Mode.MULTIPLY);
        button.setBackgroundColor(getResources().getColor(R.color.LightSkyBlue));


        spinner.setVisibility(View.VISIBLE);
        CreateHashTable task = new CreateHashTable();
        task.execute();


        editText.addTextChangedListener(watch);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                /*wordReturnedFromTxtFile.clear();
                theNumberCombos.clear();*/
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
                        String number = sCurrentLine.substring(STARTOFNUMBERINDEXFROMTXT);
                        String word = sCurrentLine.substring(0, STARTOFNUMBERINDEXFROMTXT).trim();
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
            spinner.setVisibility(View.INVISIBLE);
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

            String editTextString = editText.getText().toString();


            // this is so that it computes on every number you enter
            if(editText.length() > 2 && editText.length() <= 14){

                getNumberReturn();

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


            theNumberCombos.add(cellNumber);

            //this is what creates every number combination of the input number
            // 123
            //12
            //23    i < theNumberCombos.size() && endBlockNumberLength > 1
            //1 , 2, 3
            for(int i = 0; i < theNumberCombos.size() && endBlockNumberLength > 1 ; i++){

                theNumberCombos.add(cellNumber.substring(i, i + endBlockNumberLength));

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

            for(String number : theNumberCombos) {
                ArrayList<String> arr = dict.get(number);
                if (arr != null) {
                    String word = arr.get(0);
                    wordReturnedFromTxtFile.add(number + "," + word);
                }
            }



            //renews if you want to run again
            theNumberCombos.clear();


            //this is for if there are no returned words.
            if(!wordReturnedFromTxtFile.isEmpty()) {
                for (String returnedWord: wordReturnedFromTxtFile) {

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



            wordReturnedFromTxtFile.clear();

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
        editText.setEnabled(true);

    }


}
