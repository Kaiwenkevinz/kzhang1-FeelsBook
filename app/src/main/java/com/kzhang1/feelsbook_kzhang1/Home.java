package com.kzhang1.feelsbook_kzhang1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private ArrayList<Emotion> mEmotionList = new ArrayList<>();

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        Button button_love;

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        button_love = (Button) v.findViewById(R.id.button_love);
//        final TextView textView_userInput = (TextView) v.findViewById(R.id.textView_userInput);

        button_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // editText_userInput from user_input.xml
                View view_user_input = inflater.inflate(R.layout.user_input, container, false);
                final EditText editText_userInput = (EditText) view_user_input.findViewById(R.id.editText_userInput);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setView(view_user_input);
                alertBuilder.setCancelable(true);

                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreference sharedPreference = new SharedPreference();
                        Emotion new_emotion = new Emotion("love", "3", editText_userInput.getText().toString());
                        sharedPreference.savePreference(getActivity(), new_emotion);
                    }
                });

                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("home", mEmotionList.toString());
//                        System.out.println(mEmotionList);
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }

        });
        return v;
    }

}
