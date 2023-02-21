package com.wallpaper.wallpapers.activities;

import static com.wallpaper.wallpapers.utils.AlertDialogUtils.showAlertDialog;

import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.components.AdManager;
import com.wallpaper.wallpapers.models.Wallpaper;
import com.wallpaper.wallpapers.utils.FavoriteData;
import com.wallpaper.wallpapers.utils.IButtonEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class FullWallpaperActivity extends AppCompatActivity implements View.OnClickListener {

    private Wallpaper wallpaper;
    private Bitmap wallPaperBitmap = null;

    private ImageView wallpaperImage;
    private AdManager adManager;
    private ImageButton wallpaperButton, backButton, favoriteButton;
    private ProgressBar progressBar;

    private Toast toast;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_wallpaper);

        wallpaper = (Wallpaper) getIntent().getSerializableExtra("wallpaper");

        wallpaperImage = findViewById(R.id.wallpaper_full_image);
        progressBar = findViewById(R.id.progressBar);

        Picasso.get().load(wallpaper.full).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap b, Picasso.LoadedFrom from) {
                bitmap = b;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        Picasso.get().load(wallpaper.thumb).into(wallpaperImage);
        Picasso.get().load(wallpaper.full).fetch(new Callback() {
            @Override
            public void onSuccess() {
                Picasso.get().load(wallpaper.full).into(wallpaperImage);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        adManager = new AdManager(findViewById(android.R.id.content).getRootView());
        adManager.showInter();

        wallpaperButton = findViewById(R.id.wallpaper_button);
        backButton = findViewById(R.id.back_button);
        favoriteButton = findViewById(R.id.favorite_button);
        wallpaperButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        favoriteButton.setOnClickListener(this);

        checkIfIsFavorite();

        toast = Toast.makeText(this, "Wallpaper set succesfully", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfIsFavorite();
    }

    private void checkIfIsFavorite() {
        boolean isFav = FavoriteData.getInstance().isFavorite(wallpaper.full);
        if(isFav) {
            favoriteButton.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            favoriteButton.setImageResource(R.drawable.baseline_favorite_border_24);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.wallpaper_button:
                try {
                    setAsWallpaper();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.favorite_button:
                toggleFavorite();
                break;
        }
    }

    private void toggleFavorite() {
        FavoriteData.getInstance().toggleFavorite(wallpaper.full);
        Log.d("HELLO", FavoriteData.getInstance().isFavorite(wallpaper.full) + "");
        checkIfIsFavorite();
    }

    private void setAsWallpaper() throws IOException {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Set wallpaper");
        alertDialog.setMessage("Please choose your selected image as wallpaper for Homescreen or Lockscreen or both screens");



        alertDialog.setPositiveButton( "Homescreen", (dialogInterface, i) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_SYSTEM);
                    toast.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        alertDialog.setNegativeButton( "Lockscreen", (dialogInterface, i) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK);
                    toast.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        alertDialog.setNeutralButton( "Both", (dialogInterface, i) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
                    toast.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        alertDialog.create().show();
    }
}
