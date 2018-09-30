package com.kzhang1.feelsbook_kzhang1;

public class Emotion {
    private String emotion;
    private String date;
    private String comment;

    // constructor
    Emotion(String emotion, String date, String comment) {
        this.emotion = emotion;
        this.date = date;
        this.comment = comment;
    }

    Emotion() {
        this.emotion = "";
        this.date = "";
        this.comment = "";
    }

    // getter
    public String getEmotion() {
        return emotion;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }


    // setter
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
