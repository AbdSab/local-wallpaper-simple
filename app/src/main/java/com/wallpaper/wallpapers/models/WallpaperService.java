package com.wallpaper.wallpapers.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WallpaperService {
    static final String BASE_URL = "https://raw.githubusercontent.com/AbdSab/wallpapers/master/data/";
    static List<Wallpaper> wallpapers = null;

    public static void wallpapers(final OnResponse onResponse) {
        if(wallpapers != null) {
            onResponse.execute(wallpapers);
        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Api service = retrofit.create(Api.class);
        Call<List<Wallpaper>> call = service.wallpapers();
        call.enqueue(new Callback<List<Wallpaper>>() {
            @Override
            public void onResponse(Call<List<Wallpaper>> call, Response<List<Wallpaper>> response) {
                List<Wallpaper> changesList = response.body();
                wallpapers = new ArrayList<>();
                for(Wallpaper wallpaper: changesList) {
                    wallpaper.full = "https://github.com/AbdSab/wallpapers/raw/master/data/ronaldo/" + wallpaper.full;
                    wallpaper.thumb = "https://github.com/AbdSab/wallpapers/raw/master/data/ronaldo/" + wallpaper.thumb;
                    wallpapers.add(wallpaper);
                }
                onResponse.execute(wallpapers);
            }

            @Override
            public void onFailure(Call<List<Wallpaper>> call, Throwable t) {

            }
        });
    }
}
