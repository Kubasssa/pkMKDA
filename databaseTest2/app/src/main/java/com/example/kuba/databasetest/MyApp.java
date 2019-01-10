package com.example.kuba.databasetest;

import android.app.Application;

public class MyApp extends Application {

    private static MyApp instance;

    public MyApp() {
        instance = this;
    }

    public static MyApp getInstance() {
        return instance;
    }
}