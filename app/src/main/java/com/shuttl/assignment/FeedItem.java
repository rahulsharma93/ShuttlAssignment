package com.shuttl.assignment;

/**
 * Created by rahul on 9/18/2017.
 */

public class FeedItem extends ListItem {

    private FeedModel feed;

    public FeedItem(FeedModel feed) {
        this.feed = feed;
    }

    public FeedModel getFeed() {
        return feed;
    }

    // here getters and setters
    // for title and so on, built
    // using event

    @Override
    public int getType() {
        return TYPE_EVENT;
    }

}
