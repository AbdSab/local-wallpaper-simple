package com.wallpaper.wallpapers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;
import android.util.Log;

import com.wallpaper.wallpapers.models.Wallpaper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class FavoriteData {
    static String FAVORITES_KEY = "favs";
    SharedPreferences sharedpreferences;
    public static FavoriteData favoriteData;

    public static FavoriteData getInstance() {
        if(favoriteData == null) {
            favoriteData = new FavoriteData();
        }
        return favoriteData;
    }

    public void init(Context context) {
        sharedpreferences = context.getApplicationContext().getSharedPreferences("Favorites", 0);
    }

    public boolean isFavorite(String name) {
        String savedFav = sharedpreferences.getString(FAVORITES_KEY, "");
        Log.d("HELLOOOO", savedFav);
        return savedFav.contains(name);
    }

    public void toggleFavorite(String name) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String savedFav = sharedpreferences.getString(FAVORITES_KEY, "");
        List<String> savedList = new ArrayList(Arrays.asList(savedFav.split("\\|")));
        if(savedList.contains(name)) {
            savedList.remove(name);
        } else {
            savedList.add(name);
        }
        editor.putString(FAVORITES_KEY, String.join("|", savedList));
        editor.commit();
    }

    public List<Wallpaper> getSavedWallpapers(List<Wallpaper> allWallpapers) {
        String savedFav = sharedpreferences.getString(FAVORITES_KEY, "");
        List<Wallpaper> wallpapers = new ArrayList<>();
        if(savedFav == "") {
            return wallpapers;
        }
        for(Wallpaper wallpaper: allWallpapers) {
            if(savedFav.contains(wallpaper.full)) {
                wallpapers.add(wallpaper);
            }
        }
        return wallpapers;
    }
}
