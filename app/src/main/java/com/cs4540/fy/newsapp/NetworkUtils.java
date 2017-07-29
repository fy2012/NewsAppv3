package com.cs4540.fy.newsapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class NetworkUtils {

    public static final String NEWS_BASE_URL=
            "https://newsapi.org/v1/articles";

    public static final String PARAM_SOURCE=
            "source";
    public static final String source =
            "the-next-web";

    public static final String PARAM_SORTBY=
            "sortBy";
    public static final String sortBy =
            "latest";

    public static final String PARAM_APIKEY=
            "apiKey";

    //    TODO insert your apikey here
    public  static final String apiKey=
            "7369b6d11aad4d7899f70be9cae53ff8";

    public static URL buildUrl(){
        Uri uri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORTBY,sortBy)
                .appendQueryParameter(PARAM_APIKEY,apiKey)
                .build();

        URL url= null;

        try{
            url= new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for (int i = 0; i < articles.length(); i++) {
            JSONObject item = articles.getJSONObject(i);
            String author = item.getString("author");
            String title = item.getString("title");
            String description = item.getString("description");
            String url = item.getString("url");
            String urlToImage = item.getString("urlToImage");
            String publishedAt = item.getString("publishedAt");

            NewsItem newsItem = new NewsItem(author, title, description, url, urlToImage, publishedAt);
            result.add(newsItem);
        }
        return result;
    }

}
