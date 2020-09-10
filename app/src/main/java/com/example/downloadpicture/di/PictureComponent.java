package com.example.downloadpicture.di;
import com.example.downloadpicture.di.module.ImageDataSourceFactoryModule;
import com.example.downloadpicture.model.ImageDataSourceFactory;

import dagger.Component;

@PictureScope
@Component(modules = {ImageDataSourceFactoryModule.class})
public interface PictureComponent {

    void InjectDataSourceFactory(ImageDataSourceFactory imageDataSourceFactory);



}
