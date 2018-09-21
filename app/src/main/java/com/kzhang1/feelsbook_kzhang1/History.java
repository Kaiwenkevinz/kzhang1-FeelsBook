package com.kzhang1.feelsbook_kzhang1;


import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_1;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {

    private ListView listView_emotion;
    private List<Emotion> mEmotionList;
    private EmotionListAdapter emotionListAdapter;

    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_history, container, false);
        listView_emotion = (ListView) v.findViewById(R.id.history_listView);

        mEmotionList = new ArrayList<> ();
        mEmotionList.add(new Emotion("love", "1", "some comments love"));
        mEmotionList.add(new Emotion("fear", "4", "some comments fear"));
        mEmotionList.add(new Emotion("surprise", "9", "some comments surprise"));

        emotionListAdapter = new EmotionListAdapter(getContext(), mEmotionList);
        listView_emotion.setAdapter(emotionListAdapter);


        return v;

    }

}
