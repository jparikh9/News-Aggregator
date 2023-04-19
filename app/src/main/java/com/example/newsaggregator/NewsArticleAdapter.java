package com.example.newsaggregator;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewsArticleAdapter extends
        RecyclerView.Adapter<NewsArticlesViewHolder>{
    private final MainActivity mainActivity;
    private final ArrayList<NewsData> articleList;

    public NewsArticleAdapter(MainActivity mainActivity, ArrayList<NewsData> arrayList){
        this.mainActivity = mainActivity;
        this.articleList = arrayList;
    }
    @NonNull
    @Override
    public NewsArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsArticlesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticlesViewHolder holder, int position) {
        holder.headline.setVisibility(View.VISIBLE);
        holder.articleDate.setVisibility(View.VISIBLE);
        holder.author.setVisibility(View.VISIBLE);
        holder.articleText.setVisibility(View.VISIBLE);
        holder.image.setVisibility(View.VISIBLE);
        NewsData newsData = articleList.get(position);

        //final int resourceId = mainActivity.getResources().
        //       getIdentifier(zodiac.getName(), "drawable", mainActivity.getPackageName());
        if(newsData.getTitle().equalsIgnoreCase("null") || newsData.getTitle().length() == 0){
            holder.headline.setVisibility(View.INVISIBLE);
        }
        else{
            holder.headline.setText(
                    MessageFormat.format("{0}",newsData.getTitle()));
        }
        if(newsData.getPublishedAt().equalsIgnoreCase("null") || newsData.getPublishedAt().length() == 0){
            holder.articleDate.setVisibility(View.INVISIBLE);
        }
        else{
            String date = convertDate(newsData.getPublishedAt());
            holder.articleDate.setText(date);
        }
        if(newsData.getAuthor().equalsIgnoreCase("null") || newsData.getAuthor().length() == 0){
            holder.author.setVisibility(View.INVISIBLE);
        }
        else{
            holder.author.setText(newsData.getAuthor());
        }
        if(newsData.getDescription().equalsIgnoreCase("null") || newsData.getDescription().length() == 0){
            holder.articleText.setVisibility(View.INVISIBLE);
        }
        else{
            holder.articleText.setText(newsData.getDescription());
        }

        if(newsData.getUrlToImage().equalsIgnoreCase("null") || newsData.getUrlToImage().length() == 0){
            Glide.with(mainActivity)
                    .load(R.drawable.noimage).placeholder(R.drawable.noimage)
                    .into(holder.image);
        }
        else{
            String[] k = newsData.getUrlToImage().split("//");
            String imageurl = "https://"+ k[1];
            Glide.with(mainActivity)
                    .load(imageurl).error(R.drawable.brokenimage).placeholder(R.drawable.loading)
                    .into(holder.image);

        }





        holder.pageNo.setText((position+1) + " of " + articleList.size());

        //holder.zodiacImage.setImageResource(resourceId);
        //holder.zodiacDates.setText(zodiac.getDates());
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public String convertDate(String dt){
        DateTimeFormatter parser = null;
        Instant instant = null;
        LocalDateTime localDateTime = null;
        String d = "";
        DateTimeFormatter dateTimeFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            parser = DateTimeFormatter.ISO_DATE_TIME;
            instant = parser.parse(dt, Instant::from);
            localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            dateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm ");
            d = localDateTime.format(dateTimeFormatter);
        }


        return d;
    }




}
