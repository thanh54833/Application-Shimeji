package com.example.app_shimeji;

import android.content.Context;
import android.content.res.Resources;

public class Constants {


    public static int SCREEN_WIDTH=getScreenWidth();
    public static int SCREEN_HEIGHT=400;

    public static Context CURRENT_CONTEXT;


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
