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
import android.icu.util.ValueIterator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.R.layout.simple_list_item_1;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    private EmotionListAdapter emotionListAdapter;
    private ArrayList<Emotion> mEmotionList = new ArrayList<Emotion>();
    SharedPreference sharedPreference = new SharedPreference();
    LayoutInflater the_inflater;
    ViewGroup the_container;
    View view_user_input;
    Emotion emotion;
    String comment;
    int the_year;
    int the_month;
    int the_day;
    int the_hour;
    int the_minute;
    String date;
    TextView textView_time;
    TextView textView_date;

    public History() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view_user_input = inflater.inflate(R.layout.user_input, container, false);
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        the_inflater = inflater;
        the_container = container;
        mEmotionList = sharedPreference.readPreference(getActivity());

        // generate and set listAdapter
        emotionListAdapter = new EmotionListAdapter(getContext(), mEmotionList);
        ListView listView_emotion = (ListView) v.findViewById(R.id.history_listView);
        listView_emotion.setAdapter(emotionListAdapter);
        listView_emotion.setLongClickable(true);
        // add eventListeners
        listView_emotion.setOnItemLongClickListener(longClickListener);
        listView_emotion.setOnItemClickListener(clickListener);

        return v;
    }

    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // modify history dialog
                View view_user_input = the_inflater.inflate(R.layout.user_input, the_container, false);
                textView_time = (TextView) view_user_input.findViewById(R.id.textView_time);
                textView_date = (TextView) view_user_input.findViewById(R.id.textView_date);
                Button button_change_time = (Button) view_user_input.findViewById(R.id.button_change_time);
                Button button_change_date= (Button) view_user_input.findViewById(R.id.button_change_date);
                emotion = mEmotionList.get(position);
                comment = emotion.getComment();
                final EditText editText_userInput = (EditText) view_user_input.findViewById(R.id.editText_userInput);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setView(view_user_input);
                alertBuilder.setCancelable(true);
                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // update data
                        emotion.setComment(editText_userInput.getText().toString());
                        String date_and_time = String.format("%04d-%02d-%02dT%02d:%02d", the_year, the_month, the_day, the_hour, the_minute);
                        emotion.setDate(date_and_time);
                        ArrayList<Emotion> tempList;
                        tempList = sharedPreference.readPreference(getActivity());
                        tempList.remove(position);
                        tempList.add(position, emotion);
                        sharedPreference.refreshPreference(getActivity(), tempList);
                        mEmotionList.clear();
                        mEmotionList.addAll(tempList);
                        emotionListAdapter.notifyDataSetChanged();
                    }
                });
                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                editText_userInput.setText(comment);
                date = emotion.getDate();
                the_year = Integer.parseInt(date.substring(0,4));
                the_month = Integer.parseInt(date.substring(5,7));
                the_day = Integer.parseInt(date.substring(8,10));
                the_hour = Integer.parseInt(date.substring(date.indexOf('T')+1, date.indexOf(':')));
                the_minute = Integer.parseInt(date.substring(date.length()-2, date.length()));
                textView_date.setText(String.format("%04d-%02d-%02d", the_year, the_month ,the_day));
                textView_time.setText(String.format("T%02d:%02d", the_hour, the_minute));
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
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        };


    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            // deleteDialog
            View v_deleteDialog = the_inflater.inflate(R.layout.dialog_delete, the_container, false);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
            alertBuilder.setView(v_deleteDialog);
            alertBuilder.setCancelable(true);
            alertBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // load data
                    ArrayList<Emotion> tempList;
                    tempList = sharedPreference.readPreference(getActivity());
                    tempList.remove(position);
                    // update data
                    sharedPreference.refreshPreference(getActivity(), tempList);
                    // update frame
                    mEmotionList.clear();
                    mEmotionList.addAll(tempList);
                    emotionListAdapter.notifyDataSetChanged();
                }
            });
            Dialog dialog = alertBuilder.create();
            dialog.show();
            return true;
        }
    };


    private void showDialogTimePicker () {
        TimePickerDialog dialog_timePicker =
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        the_hour = hourOfDay;
                        the_minute = minute;
                        textView_time.setText(String.format("T%02d:%02d", the_hour, the_minute));
                    }
                }, the_hour, the_minute, true);
        dialog_timePicker.show();
    }

    private void showDialogDatePicker () {
        DatePickerDialog.OnDateSetListener mOnDateSetListener;
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
//        the_year = mYear;


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
