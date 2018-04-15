package com.cfs.bots.starwars.helper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class Helper {

    public static JSONObject getJSONObject(URL url) throws IOException, JSONException, NullPointerException {
        OkHttpClient client = new OkHttpClient();
        return new JSONObject((client.newCall(new Request.Builder().url(url).build())).execute().body().string());
    }

}
