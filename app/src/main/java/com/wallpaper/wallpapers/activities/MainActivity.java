package com.wallpaper.wallpapers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.activities.sub.NavMenuSubActivity;
import com.wallpaper.wallpapers.adapters.WallpaperAdapter;
import com.wallpaper.wallpapers.components.AdManager;
import com.wallpaper.wallpapers.models.OnResponse;
import com.wallpaper.wallpapers.models.Wallpaper;
import com.wallpaper.wallpapers.models.WallpaperService;
import com.wallpaper.wallpapers.utils.FavoriteData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WallpaperAdapter wallpaperAdapter;
    private List<Wallpaper> wallpaperList;

    private RecyclerView wallpaperRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavMenuSubActivity.getInstance(findViewById(R.id.nav_menu_main), 0);

        FavoriteData.getInstance().init(this);

        LinearLayout adLayout = findViewById(R.id.ad_layout);
        AdManager adManager = new AdManager(this, true);
        adManager.showBanner(adLayout);

        loadWallpapers();
    }

    private void loadWallpapers() {
        WallpaperService.wallpapers((OnResponse<List<Wallpaper>>) wallpapers -> {
            wallpaperList = wallpapers;
            wallpaperAdapter = new WallpaperAdapter(this, wallpapers);
            wallpaperRecycleView = findViewById(R.id.wallpaper_recycle_view);
            wallpaperRecycleView.setAdapter(wallpaperAdapter);
            wallpaperRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        });
    }
}
