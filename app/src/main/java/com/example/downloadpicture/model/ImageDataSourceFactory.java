package com.example.downloadpicture.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.downloadpicture.service.ImageAPIService;

public class ImageDataSourceFactory extends DataSource.Factory {
    private ImageAPIService imageAPIService;
    private ImageDataSource imageDataSource;
    private MutableLiveData<ImageDataSource>dataSourceMutableLiveData;

    public ImageDataSourceFactory(ImageAPIService imageAPIService) {
        this.imageAPIService = imageAPIService;
        dataSourceMutableLiveData=new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        imageDataSource=new ImageDataSource(imageAPIService);
        dataSourceMutableLiveData.postValue(imageDataSource);
        return imageDataSource;
    }

    public MutableLiveData<ImageDataSource> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}
