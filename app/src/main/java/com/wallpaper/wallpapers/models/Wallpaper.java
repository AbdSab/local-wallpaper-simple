package com.wallpaper.wallpapers.models;

import java.io.Serializable;

public class Wallpaper implements Serializable {
    public String thumb;
    public String full;

    public Wallpaper(String thumb, String full) {
        this.thumb = thumb;
        this.full = full;
    }
}
