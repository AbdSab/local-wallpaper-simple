package com.wallpaper.wallpapers.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("nature/api.json")
    Call<List<Wallpaper>> wallpapers();
}
