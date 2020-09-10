package com.example.downloadpicture.di;

import com.example.downloadpicture.di.module.MainActivityModule;
import com.example.downloadpicture.view.MainActivity;

import dagger.Component;

@MainActivityScope
@Component(modules = MainActivityModule.class , dependencies = PictureComponent.class)
public interface ApplicationComponent {

    void injectMainActivity(MainActivity mainActivity);
}


