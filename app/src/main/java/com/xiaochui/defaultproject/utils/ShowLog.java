package com.xiaochui.defaultproject.utils;

import android.util.Log;

import com.xiaochui.defaultproject.AppBuildConfig;


/**
 * Created by Death丶Love on 2016/7/20.
 */
public class ShowLog {

    public static void e(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            if (msg.length() > 2000) {
                for (int i = 0; i < msg.length(); i += 2000) {
                    if (i + 2000 < msg.length())
                        Log.e(tag, msg.substring(i, i + 2000));
                    else
                        Log.e(tag, msg.substring(i, msg.length()));
                }
            } else {
                Log.e(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            if (msg.length() > 2000) {
                for (int i = 0; i < msg.length(); i += 2000) {
                    if (i + 2000 < msg.length())
                        Log.i(tag, msg.substring(i, i + 2000));
                    else
                        Log.i(tag, msg.substring(i, msg.length()));
                }
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            if (msg.length() > 2000) {
                for (int i = 0; i < msg.length(); i += 2000) {
                    if (i + 2000 < msg.length())
                        Log.d(tag, msg.substring(i, i + 2000));
                    else
                        Log.d(tag, msg.substring(i, msg.length()));
                }
            } else {
                Log.d(tag, msg);
            }
        }
    }
    public static void w(String tag, String msg) {
        if (AppBuildConfig.DEBUG) {
            if (msg.length() > 2000) {
                for (int i = 0; i < msg.length(); i += 2000) {
                    if (i + 2000 < msg.length())
                        Log.w(tag, msg.substring(i, i + 2000));
                    else
                        Log.w(tag, msg.substring(i, msg.length()));
                }
            } else {
                Log.w(tag, msg);
            }
        }
    }
}
