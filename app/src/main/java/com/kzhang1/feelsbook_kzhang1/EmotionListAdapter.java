package com.kzhang1.feelsbook_kzhang1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class EmotionListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Emotion> mEmotionList;

    EmotionListAdapter(Context mContext, ArrayList<Emotion> mEmotionList) {
        this.mContext = mContext;
        this.mEmotionList = mEmotionList;
    }

    @Override
    public int getCount() {
        if (mEmotionList == null) {
            return 0;
        }
        return mEmotionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmotionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the View and its TextViews
        View v = View.inflate(mContext, R.layout.item_emotion, null);
        TextView textViewEmotion = (TextView) v.findViewById(R.id.textView_emotion);
        TextView textViewDate = (TextView) v.findViewById(R.id.textView_date);
        TextView textViewComment = (TextView) v.findViewById(R.id.textView_comment);

        // Set text for TextView
        textViewEmotion.setText(mEmotionList.get(position).getEmotion());
        textViewDate.setText(mEmotionList.get(position).getDate());
        textViewComment.setText(mEmotionList.get(position).getComment());

        return v;
    }
}
