package com.example.spencer.num3e;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



/**
 * Created by spencer on
 * 9/22/2014.
 */
public class TopFragmentLogo extends Fragment {


      // The request code
    static final int PICK_CONTACT_REQUEST = 0;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.top_fragment, container, false);


        return view;
    }


    static boolean hasSavedContents;
    static ImageView imageHistory;
    static ImageView imageGetContact;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageHistory = (ImageView) view.findViewById(R.id.imageHistory);
        imageGetContact = (ImageView) view.findViewById(R.id.to_contacts);



        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        hasSavedContents = (sharedpreferences.getInt(ComputateFragment.HISTORY_ARRAY_SIZE, 0)) > 0;

        TextView textView = (TextView) view.findViewById(R.id.greybar);
        textView.setBackgroundColor(getResources().getColor(R.color.DimGray));


        RelativeLayout f1 = (RelativeLayout) view.findViewById(R.id.RelativeLayout);
        f1.setBackgroundColor(getResources().getColor(R.color.LightSkyBlue));




        //if there is no history, you can not see the history button.
        if(!hasSavedContents){
            imageHistory.setVisibility(View.INVISIBLE);
            imageHistory.setClickable(false);

        }


        //for numbers you have previously searched
        imageHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            Intent intent = new Intent(getActivity(), TransitionFromScrollView.class);
            startActivity(intent);


            }


        });

        // to get numbers from contacts
        imageGetContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pickContact();

            }


        });


    }

    public static void trueForVisable(boolean visable){

        if(visable){
            imageHistory.setVisibility(View.VISIBLE);
            imageHistory.setClickable(true);

            imageGetContact.setVisibility(View.VISIBLE);
            imageGetContact.setClickable(true);
        }else{

            imageHistory.setVisibility(View.INVISIBLE);
            imageHistory.setClickable(false);

            imageGetContact.setVisibility(View.INVISIBLE);
            imageGetContact.setClickable(false);

        }

    }

    public static void fromFirstEverBootMakeHistoryButtonVisableAfterFirstClick(){
        imageHistory.setVisibility(View.VISIBLE);
        imageHistory.setClickable(true);
    }


    private void pickContact() {

        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        // Show user only contacts w/ phone numbers
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
        getActivity().setResult(Activity.RESULT_OK);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)

                Uri contactUri = data.getData();
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getActivity().getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);


                if(number.contains("+") || number.contains(" ") || number.contains("(")
                        || number.contains("-")){
                    number = number.replace("+", "");
                    number = number.replace(" ", "");
                    number = number.replace("(", "");
                    number = number.replace(")", "");
                    number = number.replace("-", "");
                }

                ComputateFragment.computeContactNumber(number);
                projection[0] = "";


            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first



    }



}
