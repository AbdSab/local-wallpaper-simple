package com.wallpaper.wallpapers.activities.sub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import com.wallpaper.wallpapers.R;
import com.wallpaper.wallpapers.activities.FavWallpapersActivity;
import com.wallpaper.wallpapers.activities.MainActivity;

public class NavMenuSubActivity implements View.OnClickListener {
    private View view;

    private ImageButton allButton, favButton;

    public static NavMenuSubActivity getInstance(View view, int active) {
        return new NavMenuSubActivity(view, active);
    }

    public NavMenuSubActivity(View view, int active) {
        this.view = view;

        allButton = view.findViewById(R.id.wallpapers_all);
        favButton = view.findViewById(R.id.wallpapers_fav);

        allButton.setOnClickListener(this);
        favButton.setOnClickListener(this);

        if(active == 0) {
            allButton.setBackgroundColor(Color.argb(50, 0, 0, 0));
            favButton.setBackgroundColor(Color.TRANSPARENT);
        }else {
            favButton.setBackgroundColor(Color.argb(50, 0, 0, 0));
            allButton.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == R.id.wallpapers_all) {
            intent = new Intent(view.getContext(), MainActivity.class);
        } else if(view.getId() == R.id.wallpapers_fav) {
            intent = new Intent(view.getContext(), FavWallpapersActivity.class);
        } else {
            return;
        }
        view.getContext().startActivity(intent);
    }
}
