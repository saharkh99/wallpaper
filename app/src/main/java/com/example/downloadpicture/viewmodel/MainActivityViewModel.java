package com.example.downloadpicture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.example.downloadpicture.model.ImageDataSource;
import com.example.downloadpicture.model.ImageDataSourceFactory;
import com.example.downloadpicture.model.Picture;
import com.example.downloadpicture.service.ImageAPIService;
import com.example.downloadpicture.service.RetrofitConnection;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.disposables.Disposable;

public class MainActivityViewModel extends ViewModel {
    LiveData<ImageDataSource> dataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<Picture>> pagedListLiveData;
    private Disposable d;

    public MainActivityViewModel() {
        ImageAPIService imageAPIService = RetrofitConnection.getService();
        ImageDataSourceFactory factory = new ImageDataSourceFactory(imageAPIService);
        dataSourceLiveData = factory.getDataSourceMutableLiveData();
        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(6)
                .setPrefetchDistance(4)
                .build();
        executor = Executors.newFixedThreadPool(4);
        pagedListLiveData = (new LivePagedListBuilder<Long, Picture>(factory, config)).setFetchExecutor(executor).build();

    }

    public LiveData<PagedList<Picture>> getPagedListLiveData() {

        return pagedListLiveData;
    }

    private Disposable getDisposable() {
        return dataSourceLiveData.getValue().getDisposable();
    }

    @Override
    protected void onCleared() {
        getDisposable();
        super.onCleared();
    }
}
