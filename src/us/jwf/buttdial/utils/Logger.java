package us.jwf.buttdial.utils;

import android.util.Log;

/**
 * Logging
 */
public final class Logger {
    public static String LOG_TAG = "ButtDial-log";

    public static void i(String str) {
        Log.i(LOG_TAG, str);
    }

    public static void w(String str) {
        Log.w(LOG_TAG, str);
    }

    public static void e(String str) {
        Log.e(LOG_TAG, str);
    }

    public static void e(String str, Throwable t) {
        Log.e(LOG_TAG, str, t);
    }

    public static void v(String str) {
        Log.v(LOG_TAG, str);
    }
}
