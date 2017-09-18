package com.shuttl.assignment;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rahul on 9/18/2017.
 */

public class DateUtils {

    public static String formatDate(@NonNull Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return df.format(date);
    }
}
