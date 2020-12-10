package com.livesdk.utils;

import android.util.Log;

public class Logger {
    private final static String TAG = "LiveSdk";
    private static boolean isDebug = false;

    public static void setIsDebug(boolean isDebug) {
        Logger.isDebug = isDebug;
    }

    public static void v(String tag, Object msg) {
        if (isDebug){
            Log.v(TAG, "[" + tag + "]:" + msg);
        }
    }
    
    public static void d(String tag, Object msg) {
        if (isDebug){
            Log.d(TAG, "[" + tag + "]:" + msg);
        }
    }
    
    public static void i(String tag, Object msg) {
        if (isDebug){
            Log.i(TAG, "[" + tag + "]:" + msg);
        }
    }
    
    public static void w(String tag, Object msg) {
        if (isDebug){
            Log.w(TAG, "[" + tag + "]:" + msg);
        }
    }
    
    public static void e(String tag, Object msg) {
        if (isDebug){
            Log.e(TAG, "[" + tag + "]:" + msg);
        }
    }
}
