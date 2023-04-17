package com.example.newsaggregator;

import java.util.ArrayList;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.util.Map;
import java.util.HashMap;
import com.android.volley.AuthFailureError;

public class NewsSourceDataDownload {
    private static RequestQueue queue;
    private static final String TAG = "CivicDataDownloader";
    private String apiKey = "9b4bccbe04e745afb6947adf0519bbcd";
    private String urlString;
    private List<NewsSource> newsSourceList = new ArrayList<>();
    //private ArrayList<Official> officialArrayList;

    NewsSourceDataDownload(){
        this.urlString = "https://newsapi.org/v2/sources?apiKey=" + this.apiKey;
    }

    public void downloadNewsSourceData(MainActivity mainActivity){
        queue = Volley.newRequestQueue(mainActivity);
        Response.Listener<JSONObject> listener = response ->{
            try{
                JSONArray sources = new JSONArray();
                //JSONArray officials = new JSONArray();
                //officialArrayList = new ArrayList<>();

                JSONObject normalizedInput = new JSONObject();

                sources = response.getJSONArray("sources");
                for (int i = 0;i< sources.length();i++){
                    NewsSource newsSource = new NewsSource();
                    JSONObject source = new JSONObject();
                    source =sources.getJSONObject(i);
                    newsSource.setCategory(source.getString("category"));
                    newsSource.setId(source.getString("id"));
                    newsSource.setName(source.getString("name"));
                    newsSourceList.add(newsSource);
                }

                mainActivity.updateDrawer(newsSourceList);
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        };
        Response.ErrorListener error = error_msg ->
                Log.d(TAG, "downloadNewsSourceData Error: " + error_msg.getMessage());
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
