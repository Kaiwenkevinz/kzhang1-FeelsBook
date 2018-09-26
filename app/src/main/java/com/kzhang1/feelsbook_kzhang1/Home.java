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
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {

    private ArrayList<Emotion> mEmotionList = new ArrayList<>();
    View view_user_input;
    LayoutInflater the_inflater;
    ViewGroup the_container;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        Button button_love;
        the_inflater = inflater;
        the_container = container;

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        button_love = (Button) v.findViewById(R.id.button_love);
        Button button_fear = (Button) v.findViewById(R.id.button_fear);
        button_fear.setOnClickListener(this);
        button_love.setOnClickListener(this);

//        button_love.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
                // editText_userInput from user_input.xml
//                view_user_input = inflater.inflate(R.layout.user_input, container, false);
//                final EditText editText_userInput = (EditText) view_user_input.findViewById(R.id.editText_userInput);
//
//                DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
//                String date = df.format(Calendar.getInstance().getTime());
//                TextView textView_date = (TextView) view_user_input.findViewById(R.id.textView_date);
//                textView_date.setText(date);
//
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
//                alertBuilder.setView(view_user_input);
//                alertBuilder.setCancelable(true);
//                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreference sharedPreference = new SharedPreference();
//
//                        // collect arguments
//                        //emotion
//                        String emotion = "Love";
//
//
//                        //comment
//                        String comment = editText_userInput.getText().toString();
//
//                        // generate emotion object
//
//                        Emotion new_emotion = new Emotion("love", "3", editText_userInput.getText().toString());
//                        sharedPreference.savePreference(getActivity(), new_emotion);
//                    }
//                });
//
//                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                Dialog dialog = alertBuilder.create();
//                dialog.show();
//            }
//
//        });
        return v;
    }

    @Override public void onClick(View v) {
        final View v_1 = v;
        view_user_input = the_inflater.inflate(R.layout.user_input, the_container, false);
        final EditText editText_userInput = (EditText) view_user_input.findViewById(R.id.editText_userInput);
        TextView textView_date = (TextView) view_user_input.findViewById(R.id.textView_date);
        // generate date
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String date = df.format(Calendar.getInstance().getTime());
        textView_date.setText(date);
        // generate dialog
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(view_user_input);
        alertBuilder.setCancelable(true);
        final AlertDialog.Builder finalAlertBuilder = alertBuilder;
        final String[] emotion = new String[1];

        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreference sharedPreference = new SharedPreference();

                //emotion
                switch (v_1.getId()) {
                    case R.id.button_love:
                        emotion[0] = "Love";
                        break;
                    case R.id.button_surprise:
                        emotion[0] = "Surprise";
                        break;
                    case R.id.button_anger:
                        emotion[0] = "Anger";
                        break;
                    case R.id.button_joy:
                        emotion[0] = "Joy";
                        break;
                    case R.id.button_sadness:
                        emotion[0] = "Sadness";
                        break;
                    case R.id.button_fear:
                        emotion[0] = "Fear";
                        break;
                }

                //comment
                String comment = editText_userInput.getText().toString();
                // generate emotion object
                Emotion new_emotion = new Emotion(emotion[0], "DateObject", editText_userInput.getText().toString());
                sharedPreference.savePreference(getActivity(), new_emotion);
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
            public void onClick(DialogInterface dialog, int which) {
                    }
        });

        Dialog dialog = finalAlertBuilder.create();
        dialog.show();
    }
}
