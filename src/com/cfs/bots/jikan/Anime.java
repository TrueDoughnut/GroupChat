package com.cfs.bots.jikan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Anime extends Instance {

    Anime(int id, String request){
        super(id, request);
        getData();
    }

    int episodes, score, rank, popularity;
    String type, source, status, airing, airedDates, duration,
        rating, synopsis, background, premiered, broadcast,
        adaptationType, adaptationName;
    ArrayList<String> producer, licensor, studio, genre,
        openingTheme, endingTheme = new ArrayList<>();

    @Override
    public void assignValues(JSONObject jsonObject){
        switch(request){
            case "characters_staff":
                charactersStaff(jsonObject);
                return;

            case "episodes":
                episodes(jsonObject);
                return;

            case "news":
                news(jsonObject);
                return;

            case "pictures":
                pictures(jsonObject);
                return;

            case "videos":
                videos(jsonObject);
                return;

            case "stats":
                stats(jsonObject);
                return;
        }
    }

    @Override
    public void defaultInfo(JSONObject jsonObject){
        name = jsonObject.getString("title_english");
        type = jsonObject.getString("type");
        source = jsonObject.getString("source");
        episodes = jsonObject.getInt("episodes");
        status = jsonObject.getString("status");
        airing = String.valueOf(jsonObject.getBoolean("airing"));
        airedDates = jsonObject.getString("aired_string");
        duration = jsonObject.getString("duration");
        rating = jsonObject.getString("rating");
        score = jsonObject.getInt("score");
        rank = jsonObject.getInt("rank");
        popularity = jsonObject.getInt("popularity");
        synopsis = jsonObject.getString("synopsis")
                .replaceAll("&#039;", "'");

        background = jsonObject.getString("background")
                .replaceAll("&#039;", "'");
        background = background.substring(
                0, background.lastIndexOf("."));

        premiered = jsonObject.getString("premiered");
        broadcast = jsonObject.getString("broadcast");

        JSONObject adapation = jsonObject.getJSONObject("related")
                .getJSONArray("Adaptation")
                .getJSONObject(0);
        adaptationType = adapation.getString("type");
        adaptationName = adapation.getString("title");

        parseList(jsonObject.getJSONArray("producer"), producer);
        parseList(jsonObject.getJSONArray("licensor"), licensor);
        parseList(jsonObject.getJSONArray("studio"), studio);
        parseList(jsonObject.getJSONArray("genre"), genre);

        parseThemes(jsonObject.getJSONArray("opening_theme"), openingTheme);
        parseThemes(jsonObject.getJSONArray("ending_theme"), endingTheme);

    }
    private void parseList(JSONArray jsonArray, ArrayList<String> arrayList){
        for(int i = 0; i < jsonArray.length(); i++){
            arrayList.add(jsonArray.getJSONObject(i).getString("name"));
        }
    }
    private void parseThemes(JSONArray jsonArray, ArrayList<String> arrayList){
        for(int i = 0; i < jsonArray.length(); i++){
            arrayList.add(jsonArray.getString(i));
        }
    }

    private void charactersStaff(JSONObject jsonObject){
        defaultInfo(jsonObject);
    }

    private void episodes(JSONObject jsonObject){
        defaultInfo(jsonObject);
    }

    private void news(JSONObject jsonObject){
        defaultInfo(jsonObject);
    }

    private void pictures(JSONObject jsonObject){
        defaultInfo(jsonObject);
    }

    private void videos(JSONObject jsonObject){
        defaultInfo(jsonObject);
    }

    private void stats(JSONObject jsonObject){
        defaultInfo(jsonObject);
    }

    @Override
    public String toString(){
        return this.name;
    }
}
