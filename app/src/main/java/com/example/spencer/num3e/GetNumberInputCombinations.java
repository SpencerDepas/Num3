package com.example.spencer.num3e;

import android.app.Fragment;


/**
 * Created by spencer on 9/30/2014.
 */
public class GetNumberInputCombinations extends Fragment{


   /* class GetCombinations extends AsyncTask<String, Void, String> {

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

*/


   // }
}
