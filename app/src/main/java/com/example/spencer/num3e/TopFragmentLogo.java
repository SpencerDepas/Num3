package com.example.spencer.num3e;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by spencer on 9/22/2014.
 */
public class TopFragmentLogo extends Fragment {

    int PICK_CONTACT = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.top_fragment,
                container, false);

        return view;
    }


    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        RelativeLayout f1 = (RelativeLayout) getView().findViewById(R.id.RelativeLayout);
        f1.setBackgroundColor(getResources().getColor(R.color.MediumTurquoise));

        final ImageView imageHistory = (ImageView) getView().findViewById(R.id.imageHistory);

        final ImageView imageGetContact = (ImageView) getView().findViewById(R.id.to_contacts);

        if(!MyActivity.scrollViewState){
            ImageView imageTwo = (ImageView) getView().findViewById(R.id.imageHistory);
            imageTwo.setVisibility(View.INVISIBLE);
            imageTwo.setClickable(false);

            imageGetContact.setVisibility(View.INVISIBLE);
            imageGetContact.setClickable(false);
        }


        imageHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                MyActivity.scrollViewState = false;


                Intent intent = new Intent(getActivity(), TransitionFromScrollView.class);
                startActivity(intent);

            }


        });

        imageGetContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            startActivityForResult(intent,PICK_CONTACT );








            }


        });

    }






}
