package com.kzhang1.feelsbook_kzhang1;


import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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




    public History() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] names = {"kevin", "nolan", "jack"};
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        ListView listViewNames = (ListView) v.findViewById(R.id.history_listView);
        ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, names);
        listViewNames.setAdapter(adapter);

        return v;

    }

}
