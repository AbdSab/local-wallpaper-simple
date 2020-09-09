package com.wallpaper.wallpapers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.adapters.WallpaperAdapter;
import com.wallpaper.wallpapers.components.AdManager;
import com.wallpaper.wallpapers.models.Wallpaper;

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

        LinearLayout adLayout = findViewById(R.id.ad_layout);
        AdManager adManager = new AdManager(findViewById(android.R.id.content).getRootView());
        adManager.showBanner(adLayout);

        loadWallpapers();
    }

    private void loadWallpapers() {
        wallpaperList = new ArrayList<>();
        Field[] fields=R.drawable.class.getFields();
        for(int count=0; count < fields.length; count++){
            if(fields[count].getName().contains("thumb_bg_")) {
                wallpaperList.add(new Wallpaper(
                        getResources().getIdentifier(fields[count].getName(), "drawable", getPackageName()),
                        getResources().getIdentifier(fields[count].getName().replace("thumb_", ""), "drawable", getPackageName()))
                );
            }
        }
        wallpaperAdapter = new WallpaperAdapter(this, wallpaperList);
        wallpaperRecycleView = findViewById(R.id.wallpaper_recycle_view);
        wallpaperRecycleView.setAdapter(wallpaperAdapter);
        wallpaperRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
