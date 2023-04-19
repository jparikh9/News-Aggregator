package com.example.newsaggregator;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDataDownload {
    private static RequestQueue queue;
    private static final String TAG = "NewsDataDownloader";
    private String apiKey = "9b4bccbe04e745afb6947adf0519bbcd";
    private String urlString;
    private ArrayList<NewsData> newsDataList = new ArrayList<>();

    NewsDataDownload(String source){
        this.urlString = "https://newsapi.org/v2/top-headlines?sources=" + source + "&apiKey=" + this.apiKey;
    }

    public void downloadNewsArticlesData(MainActivity mainActivity){
        queue = Volley.newRequestQueue(mainActivity);
        Response.Listener<JSONObject> listener = response ->{
            try{
                JSONArray articles = new JSONArray();
                //JSONArray officials = new JSONArray();
                //officialArrayList = new ArrayList<>();

                JSONObject normalizedInput = new JSONObject();

                articles = response.getJSONArray("articles");
                for (int i = 0;i< articles.length();i++){
                    NewsData newsData = new NewsData();
                    JSONObject ar = new JSONObject();
                    ar =articles.getJSONObject(i);
                    newsData.setAuthor(ar.getString("author"));
                    newsData.setDescription(ar.getString("description"));
                    newsData.setUrl(ar.getString("url"));
                    newsData.setTitle(ar.getString("title"));
                    newsData.setUrlToImage(ar.getString("urlToImage"));
                    newsData.setPublishedAt(ar.getString("publishedAt"));
                    newsDataList.add(newsData);
                }

                mainActivity.updateArticlesList(newsDataList);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        };
        Response.ErrorListener error = error_msg ->
                Log.d(TAG, "downloadNewsArticlesData Error: " + error_msg.getMessage());
        Uri.Builder urlBuildObj = Uri.parse(urlString).buildUpon();
        String finalUrl = urlBuildObj.build().toString();
        try {

            //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, finalUrl, null, listener, error);
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET, finalUrl,
                            null, listener, error) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            headers.put("User-Agent", "News-App");
                            return headers;
                        }
                    };
            queue.add(jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
