package com.shuttl.assignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by rahul on 9/18/2017.
 */


public class FeedRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ListItem> feeds;

    public FeedRecyclerAdapter(Context context, List<ListItem> feeds) {

        this.context = context;
        this.feeds = feeds;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                View itemView = inflater.inflate(R.layout.view_list_item_header, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case ListItem.TYPE_EVENT: {
                View itemView = inflater.inflate(R.layout.feed_item, parent, false);
                return new FeedViewHolder(itemView);
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ListItem.TYPE_HEADER: {
                HeaderItem header = (HeaderItem) feeds.get(position);
                HeaderViewHolder holder = (HeaderViewHolder) viewHolder;

                holder.dateTextView.setText(DateUtils.formatDate(header.getDate()));
                break;
            }
            case ListItem.TYPE_EVENT: {
                final FeedItem feedItem = (FeedItem) feeds.get(position);
                final FeedViewHolder holder = (FeedViewHolder) viewHolder;

                holder.titleTextView.setText(feedItem.getFeed().getTitle());
                holder.fromTextView.setText("From " + feedItem.getFeed().getName());
                holder.text.setText(feedItem.getFeed().getText());
                Picasso.with(context).load(feedItem.getFeed().getImageUrl())
                        .transform(new RoundedCornersTransformation(20,20)).into(holder.image);

                if (feedItem.getFeed().getText()==null){
                    holder.text.setVisibility(View.GONE);
                    holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                }else{
                    holder.text.setVisibility(View.VISIBLE);
                }

                if(feedItem.getFeed().getImageUrl()==null){
                    holder.image.setVisibility(View.GONE);
                    holder.text.setTextSize(22);
                }else {
                    holder.image.setVisibility(View.VISIBLE);
                    holder.text.setTextSize(13);
                }
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,FeedDetailActivity.class);
                        intent.putExtra("feed",feedItem.getFeed());
                        context.startActivity(intent);
                    }
                });

                holder.likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (feedItem.getFeed().isLike()) {
                            holder.likeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.grey_round_bg));
                            holder.likeButton.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                            holder.likeButton.setText("LIKE");
                            feedItem.getFeed().setLike(false);
                        }else{
                            holder.likeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.cyan_round_bg));
                            holder.likeButton.setText("UNLIKE");
                            holder.likeButton.setTextColor(ContextCompat.getColor(context,android.R.color.white));
                            feedItem.getFeed().setLike(true);
                        }
                    }
                });
                break;
            }
            default:
                throw new IllegalStateException("unsupported item type");
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return feeds.get(position).getType();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;

        HeaderViewHolder(View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
        }
    }

    private static class FeedViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView image;
        TextView text;
        Button likeButton;
        TextView fromTextView;
        RelativeLayout relativeLayout;

        FeedViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            image = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.text);
            likeButton = (Button) itemView.findViewById(R.id.like_button);
            fromTextView = (TextView) itemView.findViewById(R.id.from_text_view);
            relativeLayout=(RelativeLayout) itemView.findViewById(R.id.parent_relative);
        }

    }
}
