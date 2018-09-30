package com.kzhang1.feelsbook_kzhang1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class DateComparator implements Comparator<Emotion> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Override
    public int compare(Emotion obj1, Emotion obj2) {
        try {
            return dateFormat.parse(obj1.getDate()).compareTo(dateFormat.parse(obj2.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
