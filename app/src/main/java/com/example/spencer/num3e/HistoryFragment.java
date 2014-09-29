package com.example.spencer.num3e;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by spencer on 9/22/2014.
 */
public class HistoryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.history_fragment,
                container, false);

        MyActivity.inHistoryFragment = true;

        return view;
    }


    private ListView historyListView;
    private ArrayAdapter arrayAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout rl = (LinearLayout) getView().findViewById(R.id.HistoryLayout);
        rl.setBackgroundColor(getResources().getColor(R.color.white));


        ArrayList<String> savedWords = new ArrayList<String>();
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        historyListView = (ListView) getView().findViewById(R.id.history_list);

        int arraySize = (sharedpreferences.getInt(ComputateFragment.HistoryArraySize, 0));
        if (arraySize >= 0){


            for(int i = arraySize; i != 0; i--){


                //monthsArray[i] = (sharedpreferences.getString("array_" + i, null));
                savedWords.add(sharedpreferences.getString("array_" + i, null));

            }



        }


        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, savedWords);
        historyListView.setAdapter(arrayAdapter);
    }






}