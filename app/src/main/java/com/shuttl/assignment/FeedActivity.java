package com.shuttl.assignment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 *  Created by rahul on 9/18/2017.
 */
public class FeedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<FeedModel> feedModelArrayList;
    private List<ListItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        recyclerView=(RecyclerView)findViewById(R.id.feed_recycler_view);
        prepareFeedList();
        groupElements();
    }
    public void prepareFeedList(){
        String json=loadJSONFromAsset();
        try {
            JSONArray itemArray=new JSONArray(json);
            Type type = new TypeToken<ArrayList<FeedModel>>() {
            }.getType();
            feedModelArrayList = new Gson().fromJson(String.valueOf(itemArray), type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("sample.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    private void groupElements(){

        Map<Date, List<FeedModel>> feeds = toMap();

        for (Date date : feeds.keySet()) {
            HeaderItem header = new HeaderItem(date);
            items.add(header);
            for (FeedModel feedModel : feeds.get(date)) {
                FeedItem item = new FeedItem(feedModel);
                items.add(item);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FeedRecyclerAdapter(this,items));
    }


    @NonNull
    private Map<Date, List<FeedModel>> toMap() {
        Map<Date, List<FeedModel>> map = new TreeMap<>(Collections.reverseOrder());
        for (FeedModel feedModel : feedModelArrayList) {
            Date date=new Date(feedModel.getTime());
            List<FeedModel> value = map.get(date);
            if (value == null) {
                value = new ArrayList<>();
                map.put(date, value);
            }
            value.add(feedModel);
        }
        return map;
    }


}
