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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private ArrayList<Emotion> mEmotionList = new ArrayList<>();
    View view_user_input;
    LayoutInflater the_inflater;
    ViewGroup the_container;
    SharedPreference sharedPreference = new SharedPreference();
    Calendar c = Calendar.getInstance();
    int the_year = c.get(Calendar.YEAR);
    int the_month = c.get(Calendar.MONTH) + 1;
    int the_day = c.get(Calendar.DAY_OF_MONTH);
    int the_hour = c.get(Calendar.HOUR_OF_DAY);
    int the_minute = c.get(Calendar.MINUTE);

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        the_inflater = inflater;
        the_container = container;
        view_user_input = the_inflater.inflate(R.layout.user_input, the_container, false);

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        Button button_love = (Button) v.findViewById(R.id.button_love);
        Button button_fear = (Button) v.findViewById(R.id.button_fear);
        Button button_surprise = (Button) v.findViewById(R.id.button_surprise);
        Button button_anger = (Button) v.findViewById(R.id.button_anger);
        Button button_joy = (Button) v.findViewById(R.id.button_joy);
        Button button_sadness = (Button) v.findViewById(R.id.button_sadness);
        button_love.setOnClickListener(this);
        button_fear.setOnClickListener(this);
        button_surprise.setOnClickListener(this);
        button_anger.setOnClickListener(this);
        button_joy.setOnClickListener(this);
        button_sadness.setOnClickListener(this);
        return v;
    }

    @Override public void onClick(View v) {
        final View v_1 = v;
        view_user_input = the_inflater.inflate(R.layout.user_input, the_container, false);
        final EditText editText_userInput = (EditText) view_user_input.findViewById(R.id.editText_userInput);
        Button button_change_time = (Button) view_user_input.findViewById(R.id.button_change_time);
        Button button_change_date= (Button) view_user_input.findViewById(R.id.button_change_date);
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
                emotion.setComment(editText_userInput.getText().toString());

                // date
                String date_and_time = String.format("%04d-%02d-%02dT%02d:%02d", the_year, the_month, the_day, the_hour, the_minute);
                emotion.setDate(date_and_time);


                // save data
                sharedPreference.savePreference(getActivity(), emotion);
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
//        String current_date = new Date().getStringDate();
//        String current_time = new TimeOfDay().getStringTime();
        textView_date.setText(String.format("%04d-%02d-%02d", the_year, the_month ,the_day));
        textView_time.setText(String.format("T%02d:%02d", the_hour, the_minute));
        Dialog dialog = alertBuilder.create();
        dialog.show();



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        TextView textView_date = (TextView) view_user_input.findViewById(R.id.textView_date);
//        textView_date.setText("hour" + Integer.toString(hourOfDay) + "minute" + Integer.toString(minute));
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

}
