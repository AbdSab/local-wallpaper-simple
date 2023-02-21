package com.wallpaper.wallpapers.models;

public interface OnResponse<T> {
    public void execute(T args);
}
