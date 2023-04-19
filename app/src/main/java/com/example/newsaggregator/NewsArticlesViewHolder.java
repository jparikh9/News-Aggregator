package com.example.newsaggregator;

import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsaggregator.databinding.ArticleLayoutBinding;

public class NewsArticlesViewHolder extends RecyclerView.ViewHolder{
TextView headline;
TextView articleDate;
TextView author;
TextView articleText;
TextView pageNo;
ImageView image;
ArticleLayoutBinding binding;
    public NewsArticlesViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = ArticleLayoutBinding.bind(itemView);
        headline = binding.articleHeadline;
        articleDate = binding.articleDate;
        author = binding.articleAuthor;
        articleText = binding.articleText;
        pageNo = binding.articlePageno;
        image = binding.articleImage;
        articleText.setMovementMethod(new ScrollingMovementMethod());

    }


}
