package com.example.animalsound;

import android.app.Application;

public class App extends Application {
    private Storage storage;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        storage = new Storage();
        instance = this;
    }

    public Storage getStorage() {
        return storage;
    }

    public static App getInstance() {
        return instance;
    }
}
