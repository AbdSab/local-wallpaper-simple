package com.wallpaper.wallpapers.adapters;

import com.wallpaper.wallpapers.models.Wallpaper;

public class Item {
    int type;
    Wallpaper wallpaper;

    public Item(int type, Wallpaper wallpaper) {
        this.type = type;
        this.wallpaper = wallpaper;
    }
}
