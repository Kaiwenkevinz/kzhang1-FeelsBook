package com.kzhang1.feelsbook_kzhang1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

        String date;

    Date() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(Calendar.getInstance().getTime());
    }

    public String getStringDate() {
        return this.date;
    }



}
