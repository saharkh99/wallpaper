package com.example.downloadpicture.di.module;

import com.example.downloadpicture.di.PictureScope;
import com.example.downloadpicture.model.ImageDataSource;
import com.example.downloadpicture.service.ImageAPIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = OkHttpClientModule.class)
public class ImageDataSourceFactoryModule {

    @Provides
    public ImageDataSource createDataSource(ImageAPIService imageAPIService){
        return new ImageDataSource(imageAPIService);
    }

    @PictureScope
    @Provides
    public ImageAPIService getService(Retrofit retrofit){
         return retrofit.create(ImageAPIService.class);
    }
    @PictureScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build();
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }
    @Provides
    public RxJava2CallAdapterFactory java2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

}
