package com.kzhang1.feelsbook_kzhang1;

import java.util.ArrayList;

public class StatGenerator {
    int love = 0;
    int surprise = 0;
    int fear = 0;
    int anger = 0;
    int sadness = 0;
    int joy = 0;


    public void getStats (ArrayList<Emotion> emotion_list) {
        String emotion_string;
        for (Emotion emotion: emotion_list) {
            emotion_string = emotion.getEmotion();
            switch (emotion_string) {
                case "LOVE":
                    love += 1;
                    break;
                case "SURPRISE":
                    surprise += 1;
                    break;
                case "JOY":
                    joy += 1;
                    break;
                case "FEAR":
                    fear += 1;
                    break;
                case "SADNESS":
                    sadness += 1;
                    break;
                case "ANGER":
                    anger += 1;
                    break;
            }
       }

    }

    public int getLove() {
        return love;
    }

    public int getSurprise() {
        return surprise;
    }

    public int getFear() {
        return fear;
    }

    public int getAnger() {
        return anger;
    }

    public int getSadness() {
        return sadness;
    }

    public int getJoy() {
        return joy;
    }

}
