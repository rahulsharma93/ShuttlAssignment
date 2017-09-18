package com.shuttl.assignment;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by rahul on 9/18/2017.
 */

public class HeaderItem extends ListItem {

    @NonNull
    private Date date;

    public HeaderItem(@NonNull Date date) {
        this.date = date;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    // here getters and setters
    // for title and so on, built
    // using date

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

}