package com.wallpaper.wallpapers.adapters;

import com.wallpaper.wallpapers.models.Wallpaper;

public class Item {
    Wallpaper wallpaper;
    int type;

    public Item(Wallpaper wallpaper, int type) {
        this.type = type;
        this.wallpaper = wallpaper;
    }
}
