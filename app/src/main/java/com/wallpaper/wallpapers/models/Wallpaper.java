package com.wallpaper.wallpapers.models;

import java.io.Serializable;

public class Wallpaper implements Serializable {
    public int thumb_id;
    public int full_id;

    public Wallpaper(int thumb, int full) {
        this.thumb_id = thumb;
        this.full_id = full;
    }
}
