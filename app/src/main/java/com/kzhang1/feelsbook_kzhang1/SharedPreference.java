package com.kzhang1.feelsbook_kzhang1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreference {

    private static final String TAG = "emotions";
    private ArrayList<Emotion> emotionList = new ArrayList<Emotion>();

    SharedPreference() {
        super();
    }

    public void refreshPreference(Context context, ArrayList<Emotion> emotionList) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(emotionList);
        editor.putString(TAG, json);
        editor.commit();
    }

    public void savePreference(Context context, Emotion emotion) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        emotionList = readPreference(context);
        emotionList.add(emotion);
        String json = gson.toJson(emotionList);
        editor.putString(TAG, json);
        editor.commit();
    }

    public ArrayList<Emotion> readPreference(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<List<Emotion>>() {}.getType();
        emotionList = gson.fromJson(json, type);
        if (emotionList == null) {
            emotionList = new ArrayList<Emotion>();
        }

        return emotionList;
    }
}
