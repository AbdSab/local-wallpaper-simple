package com.wallpaper.wallpapers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.activities.sub.NavMenuSubActivity;
import com.wallpaper.wallpapers.adapters.WallpaperAdapter;
import com.wallpaper.wallpapers.components.AdManager;
import com.wallpaper.wallpapers.models.OnResponse;
import com.wallpaper.wallpapers.models.Wallpaper;
import com.wallpaper.wallpapers.models.WallpaperService;
import com.wallpaper.wallpapers.utils.FavoriteData;

import java.util.List;

public class FavWallpapersActivity extends AppCompatActivity {

    private WallpaperAdapter wallpaperAdapter;
    private RecyclerView wallpaperRecycleView;
    private List<Wallpaper> savedWallpapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_wallpapers);
        NavMenuSubActivity.getInstance(findViewById(R.id.nav_menu_main), 1);

        LinearLayout adLayout = findViewById(R.id.ad_layout);
        AdManager adManager = new AdManager(findViewById(android.R.id.content).getRootView());
        adManager.showBanner(adLayout);

        loadWallpapers();
    }

    private void loadWallpapers() {
        WallpaperService.wallpapers((OnResponse<List<Wallpaper>>) wallpapers -> {
            savedWallpapers = FavoriteData.getInstance().getSavedWallpapers(wallpapers);
            wallpaperAdapter = new WallpaperAdapter(this, savedWallpapers);
            wallpaperRecycleView = findViewById(R.id.wallpaper_recycle_view);
            wallpaperRecycleView.setAdapter(wallpaperAdapter);
            wallpaperRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        });
    }
}