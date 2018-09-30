package com.kzhang1.feelsbook_kzhang1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeOfDay {

    String time;

    TimeOfDay() {
        DateFormat dateFormat = new SimpleDateFormat("'T'HH:mm");
        time = dateFormat.format(Calendar.getInstance().getTime());
    }

    public String getStringTime() {
        return this.time;
    }



}
