package com.kzhang1.feelsbook_kzhang1;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    private EmotionListAdapter emotionListAdapter;
    private ArrayList<Emotion> mEmotionList = new ArrayList<Emotion>();
    private SharedPreference sharedPreference = new SharedPreference();
    private Emotion emotion;
    private TextView textView_time;
    private TextView textView_date;
    private int the_year;
    private int the_month;
    private int the_day;
    private int the_hour;
    private int the_minute;

    public History() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_history = inflater.inflate(R.layout.fragment_history, container, false);
        mEmotionList = sharedPreference.readPreference(getActivity());
        Collections.sort(mEmotionList, new DateComparator());
        // generate and set listAdapter
        emotionListAdapter = new EmotionListAdapter(getContext(), mEmotionList);
        ListView listView_emotion = (ListView) layout_history.findViewById(R.id.history_listView);
        listView_emotion.setAdapter(emotionListAdapter);
        listView_emotion.setLongClickable(true);
        // add eventListeners to emotion listView
        listView_emotion.setOnItemLongClickListener(longClickListener);
        listView_emotion.setOnItemClickListener(clickListener);

        return layout_history;
    }


    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // modify history dialog
                LayoutInflater inflater = getLayoutInflater();
                View layout_user_input = inflater.inflate(R.layout.user_input, null);
                textView_time = (TextView) layout_user_input.findViewById(R.id.textView_time);
                textView_date = (TextView) layout_user_input.findViewById(R.id.textView_date);
                Button button_change_time = (Button) layout_user_input.findViewById(R.id.button_change_time);
                Button button_change_date= (Button) layout_user_input.findViewById(R.id.button_change_date);
                emotion = mEmotionList.get(position);
                String comment = emotion.getComment();
                String date = emotion.getDate();
                final EditText editText_userInput = (EditText) layout_user_input.findViewById(R.id.editText_userInput);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setView(layout_user_input);
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
                        Collections.sort(tempList, new DateComparator());
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
            LayoutInflater inflater = getLayoutInflater();
            View layout_deleteDialog = inflater.inflate(R.layout.dialog_delete, null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
            alertBuilder.setView(layout_deleteDialog);
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
        int mYear = the_year;
        int mMonth = the_month;
        int mDay = the_day;


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
