package com.kzhang1.feelsbook_kzhang1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    private ListView listView_emotion;
    private EmotionListAdapter emotionListAdapter;
    private ArrayList<Emotion> mEmotionList = new ArrayList<Emotion>();
    private Fragment history_fragment;

    public History() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final SharedPreference sharedPreference = new SharedPreference();
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        listView_emotion = (ListView) v.findViewById(R.id.history_listView);

        // load data
        mEmotionList = sharedPreference.readPreference(getActivity());
        // generate and set listAdapter
        emotionListAdapter = new EmotionListAdapter(getContext(), mEmotionList);
        listView_emotion.setAdapter(emotionListAdapter);
        // longClick event
        listView_emotion.setLongClickable(true);
        listView_emotion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d("pos", String.valueOf(position));

                // deleteDialog
                View v_deleteDialog = inflater.inflate(R.layout.dialog_delete, container, false);
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
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

                return false;
            }
        });


        return v;

    }
}
