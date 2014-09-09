package com.example.spencer.num3e;



import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


/**
 * Created by spencer on 8/18/2014.
 */
public class NumberConverter extends Activity {


    public String sCurrentLine = "";
    public BufferedReader br;



    public String bufferer(AssetManager assetManager) {


        //toaster("nap");
        new Thread(new Task()).start();


        try {



            InputStream input = assetManager.open("SixLetterWords2.txt");

            br = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            new Thread(new Task()).start();



            input.close();




        }catch (IOException e) {
            e.printStackTrace();
        }


        return sCurrentLine;


    }



    class Task implements Runnable {
        @Override
        public void run() {


            //sCurrentLine = "courtelu road bitch";


           /* int i = 0;

            try {

                while ((sCurrentLine = br.readLine()) != null)

                {


                    if(i >= 10 ){
                        break;
                    }


                }
            }catch (IOException e) {
            e.printStackTrace();
        }*/



        /*    //enter other class
            NumberConverter converter = new NumberConverter();
            returnedWord = converter.bufferer(assetManager);

            //truncates
            int endOfWord = returnedWord.indexOf(" ");
            returnedWord = returnedWord.substring(0, endOfWord);
            //editText.setText(returnedWord);*/



        }


    }











    public static String addOne(String number){


        int stringNumber = Integer.parseInt(number) + 1;
        return Integer.toString(stringNumber);


    }


    public void toaster(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }





}
