package com.example.spencer.num3e;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * Created by spencer on 9/30/2014.
 */
public class NumberInputFormater extends Fragment {

/*
    public String numberFormatter(String inputNumber){

        EditText editText = (EditText) getActivity().findViewById(R.id.editText);
        final Button button = (Button) getActivity().findViewById(R.id.button);
        ProgressBar spinner = (ProgressBar) getActivity().findViewById(R.id.progressBar1);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);



        //button status,
        if (ComputateFragment.pushOrClear) {

            if (editText.length() > 1 && editText.length() <= 16) {
                editText.setEnabled(false);

                button.setEnabled(false);
                ComputateFragment.pushOrClear = false;

                //this hides the keyboard on click
                imm.hideSoftInputFromWindow(null, 0);

                spinner.setVisibility(View.VISIBLE);

                inputNumber = editText.getText().toString().replaceAll("-","");
                inputNumber = inputNumber.replaceAll("\\(","");
                inputNumber = inputNumber.replaceAll("\\)", "");
                inputNumber = inputNumber.replaceAll(" ","");

                ComputateFragment.finishedWordWithDash = inputNumber;
                editText.setText(" ");

                //this is to get the combinations of numbers to check with the txt file
                ComputateFragment.ActivateASTask();

            } else if (editText.length() < 2) {
                //toaster("Must be more than one digit");
            }


        } else {
            //for when the button is in clear mode
            String buttonTrue = getResources().getString(R.string.buttonTrue);
            editText.setText("");
            button.setText(buttonTrue);
            ComputateFragment.pushOrClear = true;
            editText.setEnabled(true);
        }

        return null;
    }*/
}
