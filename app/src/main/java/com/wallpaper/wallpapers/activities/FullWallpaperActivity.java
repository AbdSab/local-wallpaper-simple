package com.wallpaper.wallpapers.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.components.AdManager;
import com.wallpaper.wallpapers.models.Wallpaper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FullWallpaperActivity extends AppCompatActivity implements View.OnClickListener {

    private Wallpaper wallpaper;
    private Bitmap wallPaperBitmap = null;

    private ImageView wallpaperImage;
    private AdManager adManager;
    private Button wallpaperButton;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_wallpaper);

        wallpaper = (Wallpaper) getIntent().getSerializableExtra("wallpaper");

        wallpaperImage = findViewById(R.id.wallpaper_full_image);
        wallpaperImage.setImageResource(wallpaper.full_id);

        adManager = new AdManager(findViewById(android.R.id.content).getRootView());
        //adManager.showBanner(adLayout);

        wallpaperButton = findViewById(R.id.wallpaper_button);
        wallpaperButton.setOnClickListener(this);

        toast = Toast.makeText(this, "Wallpaper set succesfully", Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wallpaper_button:
                setAsWallpaper();
        }
    }

    private void setAsWallpaper(){
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setResource(wallpaper.full_id);
            toast.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
