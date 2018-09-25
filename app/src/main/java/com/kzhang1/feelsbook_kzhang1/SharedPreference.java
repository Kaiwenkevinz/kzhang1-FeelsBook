package com.kzhang1.feelsbook_kzhang1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "shared preference";
    public static final String TAG = "emotions";
    private ArrayList<Emotion> emotionList = new ArrayList<Emotion>();

    public SharedPreference() {
        super();
    }

    public void savePreference(Context context, Emotion emotion) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        emotionList = readPreference(context);
        System.out.println(emotionList.getClass());
        emotionList.add(emotion);
        String json = gson.toJson(emotionList);
        editor.putString(TAG, json);
        editor.commit();
    }

    public ArrayList<Emotion> readPreference(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, "");
        Type type = new TypeToken<List<Emotion>>() {}.getType();
        emotionList = gson.fromJson(json, type);

        return emotionList;
    }
//    // This four methods are used for maintaining favorites.
//    public void saveEmotion(Context context, List<Emotion> emotionList) {
//        SharedPreferences settings;
//        SharedPreferences.Editor editor;
//
//        settings = context.getSharedPreferences(PREFS_NAME,
//                Context.MODE_PRIVATE);
//        editor = settings.edit();
//
//        Gson gson = new Gson();
//        String jsonFavorites = gson.toJson(EMOTIONS);
//
//        editor.putString(EMOTIONS, jsonFavorites);
//
//        editor.commit();
//    }
//
//    public void addEmotion(Context context, Emotion emotion) {
//        List<Emotion> emotionList = getEmotionList(context);
//        if (emotionList == null)
//            emotionList = new ArrayList<Emotion>();
//        emotionList.add(emotion);
//        saveEmotion(context, emotionList);
//    }
//
////    public void removeEmotion(Context context, Emotion product) {
////        ArrayList<Product> favorites = getFavorites(context);
////        if (favorites != null) {
////            favorites.remove(product);
////            saveFavorites(context, favorites);
////        }
////    }
//
//    public List<Emotion> getEmotionList(Context context) {
//        SharedPreferences settings;
//        List<Emotion> emotionList;
//
//        settings = context.getSharedPreferences(PREFS_NAME,
//                Context.MODE_PRIVATE);
//
//         Gson gson = new Gson();
//         String json = settings.getString(EMOTIONS, null);
////        if (settings.contains(EMOTIONS)) {
////            Gson gson = new Gson();
////            Emotion[] favoriteItems = gson.fromJson(jsonFavorites,
////                    Emotion[].class);
////
////            emotionList = Arrays.asList(favoriteItems);
////            emotionList = new ArrayList<Emotion>(emotionList);
////        } else
////            return null;
//
//        Type type = new TypeToken<List<Emotion>>(){}.getType();
//        emotionList = gson.fromJson(json, type);
//
//        return emotionList;
//    }

}
