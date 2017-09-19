package com.shuttl.assignment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 *  Created by rahul on 9/18/2017.
 */

public class FeedDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView dateTextView;
    private ImageView profileImageView;
    private TextView sampleText;
    private Button likeButton;
    private TextView fromTextView;
    private TextView descriptionTextView;

    FeedModel feedModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        dateTextView = (TextView) findViewById(R.id.date_text_view);
        profileImageView = (ImageView) findViewById(R.id.image);
        sampleText = (TextView) findViewById(R.id.text);
        likeButton = (Button) findViewById(R.id.like_button);
        fromTextView = (TextView) findViewById(R.id.from_text_view);
        descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        setData();
        likeButton.setOnClickListener(this);
    }


    private void setData() {

        if (getIntent() != null) {

            feedModel = getIntent().getParcelableExtra("feed");
            Date date = new Date(feedModel.getTime());
            dateTextView.setText(DateUtils.formatDate(date));
            Picasso.with(this).load(feedModel.getImageUrl())
                    .transform(new RoundedCornersTransformation(20,20)).into(profileImageView);
            sampleText.setText(feedModel.getText());
            fromTextView.setText("From " + feedModel.getName());
            descriptionTextView.setText(feedModel.getDescription());
            imageTextCondition();
            checkCondition();
        }
    }

    @Override
    public void onClick(View v) {
        checkCondition();
    }

    private void imageTextCondition(){
        if (feedModel.getText()==null){
            sampleText.setVisibility(View.GONE);
            profileImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }else{
            sampleText.setVisibility(View.VISIBLE);
        }

        if(feedModel.getImageUrl()==null){
            profileImageView.setVisibility(View.GONE);
            sampleText.setTextSize(22);
        }else {
            profileImageView.setVisibility(View.VISIBLE);
            sampleText.setTextSize(13);
        }
    }

    private void checkCondition(){

        if (feedModel.isLike()) {
            likeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.grey_round_bg));
            likeButton.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
            likeButton.setText("LIKE");
            feedModel.setLike(false);
        }else{
            likeButton.setBackground(ContextCompat.getDrawable(this, R.drawable.cyan_round_bg));
            likeButton.setText("UNLIKE");
            likeButton.setTextColor(ContextCompat.getColor(this,android.R.color.white));
            feedModel.setLike(true);
        }
    }
}
