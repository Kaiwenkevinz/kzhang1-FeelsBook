package com.kzhang1.feelsbook_kzhang1;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener, View.OnLongClickListener{

    View view_user_input;
    View view_home;
    LayoutInflater the_inflater;
    ViewGroup the_container;
    SharedPreference sharedPreference = new SharedPreference();
    Calendar c = Calendar.getInstance();
    int the_year = c.get(Calendar.YEAR);
    int the_month = c.get(Calendar.MONTH) + 1;
    int the_day = c.get(Calendar.DAY_OF_MONTH);
    int the_hour = c.get(Calendar.HOUR_OF_DAY);
    int the_minute = c.get(Calendar.MINUTE);
    String comment;
    // dialog references
    EditText editText_userInput;
    Button button_change_time;
    Button button_change_date;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        the_inflater = inflater;
        the_container = container;
        view_user_input = inflater.inflate(R.layout.user_input, the_container, false);

        // Inflate the layout for this fragment
        view_home = inflater.inflate(R.layout.fragment_home, container, false);
        // view fragment_home
        Button button_love = (Button) view_home.findViewById(R.id.button_love);
        Button button_fear = (Button) view_home.findViewById(R.id.button_fear);
        Button button_surprise = (Button) view_home.findViewById(R.id.button_surprise);
        Button button_anger = (Button) view_home.findViewById(R.id.button_anger);
        Button button_joy = (Button) view_home.findViewById(R.id.button_joy);
        Button button_sadness = (Button) view_home.findViewById(R.id.button_sadness);
        Button button_stats = (Button) view_home.findViewById(R.id.button_stats);
        button_love.setOnClickListener(this);
        button_fear.setOnClickListener(this);
        button_surprise.setOnClickListener(this);
        button_anger.setOnClickListener(this);
        button_joy.setOnClickListener(this);
        button_sadness.setOnClickListener(this);
        button_love.setOnLongClickListener(this);
        button_fear.setOnLongClickListener(this);
        button_surprise.setOnLongClickListener(this);
        button_anger.setOnLongClickListener(this);
        button_joy.setOnLongClickListener(this);
        button_sadness.setOnLongClickListener(this);
        // dialog_stats
        button_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View dialog_layout = inflater.inflate(R.layout.dialog_stats, null);
                TextView textView_love_stats = dialog_layout.findViewById(R.id.textView_love_stats);
                TextView textView_surprise_stats = dialog_layout.findViewById(R.id.textView_surprise_stats);
                TextView textView_joy_stats = dialog_layout.findViewById(R.id.textView_joy_stats);
                TextView textView_sadness_stats = dialog_layout.findViewById(R.id.textView_sadness_stats);
                TextView textView_fear_stats = dialog_layout.findViewById(R.id.textView_fear_stats);
                TextView textView_anger_stats = dialog_layout.findViewById(R.id.textView_anger_stats);
                StatGenerator statGenerator = new StatGenerator();
                statGenerator.getStats(sharedPreference.readPreference(getActivity()));

                textView_love_stats.setText(String.format(Locale.CANADA,"%d",statGenerator.getLove()));
                textView_surprise_stats.setText(String.format(Locale.CANADA,"%d",statGenerator.getSurprise()));
                textView_joy_stats.setText(String.format(Locale.CANADA,"%d",statGenerator.getJoy()));
                textView_sadness_stats.setText(String.format(Locale.CANADA,"%d",statGenerator.getSadness()));
                textView_fear_stats.setText(String.format(Locale.CANADA,"%d",statGenerator.getFear()));
                textView_anger_stats.setText(String.format(Locale.CANADA,"%d",statGenerator.getAnger()));

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setView(dialog_layout);
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });

        return view_home;
    }

    @Override public void onClick(View v) {
        final View v_1 = v;
        view_user_input = the_inflater.inflate(R.layout.user_input, the_container, false);
        editText_userInput = (EditText) view_user_input.findViewById(R.id.editText_userInput);
        button_change_time = (Button) view_user_input.findViewById(R.id.button_change_time);
        button_change_date= (Button) view_user_input.findViewById(R.id.button_change_date);
        TextView textView_date = (TextView) view_user_input.findViewById(R.id.textView_date);
        TextView textView_time = (TextView) view_user_input.findViewById(R.id.textView_time);
        // generate dialog
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setView(view_user_input);
        alertBuilder.setCancelable(true);
        final Emotion emotion = new Emotion();

        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //emotion
                switch (v_1.getId()) {
                    case R.id.button_love:
                        emotion.setEmotion("LOVE");
                        break;
                    case R.id.button_surprise:
                        emotion.setEmotion("SURPRISE");
                        break;
                    case R.id.button_anger:
                        emotion.setEmotion("ANGER");
                        break;
                    case R.id.button_joy:
                        emotion.setEmotion("JOY");
                        break;
                    case R.id.button_sadness:
                        emotion.setEmotion("SADNESS");
                        break;
                    case R.id.button_fear:
                        emotion.setEmotion("FEAR");
                        break;
                }
                // comment
                comment = editText_userInput.getText().toString();
                emotion.setComment(comment);
                // date
                String date_and_time = String.format("%04d-%02d-%02dT%02d:%02d", the_year, the_month, the_day, the_hour, the_minute);
                emotion.setDate(date_and_time);
                // save data
                sharedPreference.savePreference(getActivity(), emotion);
                Toast.makeText(getActivity(), String.format("%s added at %s", emotion.getEmotion(), emotion.getDate()),
                        Toast.LENGTH_LONG).show();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        button_change_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTimePicker();
            }
        });

        button_change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDatePicker();
            }
        });
        textView_date.setText(String.format("%04d-%02d-%02d", the_year, the_month ,the_day));
        textView_time.setText(String.format("T%02d:%02d", the_hour, the_minute));
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }

    private void showDialogTimePicker () {
        Log.d("CLICKED", "CLICKED");
        Calendar c = Calendar.getInstance();
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);
        TimePickerDialog dialog_timePicker =
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        TextView textView_time = (TextView) view_user_input.findViewById(R.id.textView_time);
                        the_hour = hourOfDay;
                        the_minute = minute;
                        textView_time.setText(String.format("T%02d:%02d", the_hour, the_minute));
                    }
                }, mHour, mMinute, true);
        dialog_timePicker.show();
    }

    private void showDialogDatePicker () {
        DatePickerDialog.OnDateSetListener mOnDateSetListener;
        final TextView textView_date = (TextView) view_user_input.findViewById(R.id.textView_date);
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                the_year = year;
                the_month = month + 1;
                the_day = dayOfMonth;
                textView_date.setText(String.format("%04d-%02d-%02d", the_year, the_month ,the_day));
            }
        };

        DatePickerDialog dialog_datePicker = new DatePickerDialog(
                getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mOnDateSetListener, mYear, mMonth, mDay
        );

        dialog_datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_datePicker.show();
    }

    @Override
    public boolean onLongClick(View v) {

        Emotion emotion = new Emotion();
        emotion.setDate(String.format("%04d-%02d-%02dT%02d:%02d", the_year, the_month, the_day, the_hour, the_minute));
        //emotion
        switch (v.getId()) {
            case R.id.button_love:
                emotion.setEmotion("LOVE");
                break;
            case R.id.button_surprise:
                emotion.setEmotion("SURPRISE");
                break;
            case R.id.button_anger:
                emotion.setEmotion("ANGER");
                break;
            case R.id.button_joy:
                emotion.setEmotion("JOY");
                break;
            case R.id.button_sadness:
                emotion.setEmotion("SADNESS");
                break;
            case R.id.button_fear:
                emotion.setEmotion("FEAR");
                break;
        }
        sharedPreference.savePreference(getActivity(), emotion);
        Toast.makeText(getActivity(), String.format("%s added at %s", emotion.getEmotion(), emotion.getDate()),
        Toast.LENGTH_LONG).show();

        return true;
    }



}
