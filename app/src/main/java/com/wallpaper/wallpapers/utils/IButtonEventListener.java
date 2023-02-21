package com.wallpaper.wallpapers.utils;

import java.io.IOException;

public interface IButtonEventListener {
    String getName();
    int getButton();
    void execute() throws IOException;
}
