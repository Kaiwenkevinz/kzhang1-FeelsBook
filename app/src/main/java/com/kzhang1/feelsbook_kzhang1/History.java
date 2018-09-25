package com.kzhang1.feelsbook_kzhang1;


import android.content.Context;
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

    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreference sharedPreference = new SharedPreference();
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        listView_emotion = (ListView) v.findViewById(R.id.history_listView);

        mEmotionList = sharedPreference.readPreference(getActivity());
//        Log.d("tag", mEmotionList.toString());

        // generate and set listAdapter
        emotionListAdapter = new EmotionListAdapter(getContext(), mEmotionList);
        listView_emotion.setAdapter(emotionListAdapter);
        return v;

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
