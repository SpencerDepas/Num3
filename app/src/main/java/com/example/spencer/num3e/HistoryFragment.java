package com.example.spencer.num3e;


import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;


import java.util.ArrayList;

/**
 * Created by spencer
 * on 9/22/2014.
 */
public class HistoryFragment extends ListFragment {



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CreateFragments.inHistoryFragment = true;

        TopFragmentLogo.trueForVisable(false);

        ArrayList<String> savedWords = new ArrayList<String>();
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());



        boolean hasSavedContents = (sharedpreferences.getInt(ComputateFragment.HISTORY_ARRAY_SIZE, 0)) > 0;

        if(hasSavedContents) {

            int arraySize = (sharedpreferences.getInt(ComputateFragment.HISTORY_ARRAY_SIZE, 0));

            if (arraySize >= 0) {
                for (int i = arraySize ; i != 0; i--) {
                    savedWords.add(sharedpreferences.getString("array_" + i, null));

                }
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, savedWords);
            setListAdapter(adapter);
        }else{
            String[] tempStringArray = new String[]{"Android", "iPhone", "WindowsMobile",
                    "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                    "Linux", "OS/2"};


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, tempStringArray);
            setListAdapter(adapter);
        }
    }



}


