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
    public static final String NumberOne = "nameKey";
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout rl = (LinearLayout) getView().findViewById(R.id.HistoryLayout);
        rl.setBackgroundColor(getResources().getColor(R.color.white));

        String[] monthsArray = { "JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY",
                "AUG", "SEPT", "OCT", "NOV", "DEC" };

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        historyListView = (ListView) getView().findViewById(R.id.history_list);

        if (sharedpreferences.contains(NumberOne))
        {
            monthsArray[0] = (sharedpreferences.getString(NumberOne, ""));

        }


        arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, monthsArray);


        historyListView.setAdapter(arrayAdapter);
    }






}