package com.example.downloadpicture.di.module;

import android.app.Application;
import android.app.WallpaperManager;
import android.content.Context;
import com.example.downloadpicture.di.MainActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class MainActivityModule {
    private Application application;

    public MainActivityModule(Application application) {
        this.application = application;
    }

    @MainActivityScope
    @Provides
    Application providesApplication(){
        return application;
    }

    @Provides
    @MainActivityScope
    public WallpaperManager wallpaperManager(Context context){
         return WallpaperManager.getInstance(context);
    }
}
