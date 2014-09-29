package com.example.spencer.num3e;

import android.app.Fragment;
import android.app.ListFragment;
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
public class HistoryFragment extends ListFragment {


    private ListView historyListView;
    private ArrayAdapter arrayAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyActivity.inHistoryFragment = true;



        ArrayList<String> savedWords = new ArrayList<String>();
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        historyListView = (ListView) getView().findViewById(R.id.history_list);

        int arraySize = (sharedpreferences.getInt(ComputateFragment.HistoryArraySize, 0));
        if (arraySize >= 0){
            for(int i = arraySize; i != 0; i--){
                savedWords.add(sharedpreferences.getString("array_" + i, null));

            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, savedWords);
        setListAdapter(adapter);
    }


}


    /*@Override
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
                savedWords.add(sharedpreferences.getString("array_" + i, null));

            }
        }


        //test SHIT

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, savedWords);
        historyListView.setAdapter(arrayAdapter);


    }

}*/