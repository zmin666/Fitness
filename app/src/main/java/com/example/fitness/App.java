package com.example.fitness;

import android.app.Application;
import android.content.Context;

/**
 * @author: ZhangMin
 * @date: 2020/7/4 11:38
 * @version: 1.0
 * @desc:
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getInstance() {
        return mContext;
    }
}
