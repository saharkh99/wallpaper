package com.example.downloadpicture.model;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.downloadpicture.service.ImageAPIService;
import com.example.downloadpicture.service.RetrofitConnection;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ImageDataSource extends PageKeyedDataSource<Long, Picture> {
    private ImageAPIService imageAPIService;
    private Disposable disposable;


    @Inject
    public ImageDataSource(ImageAPIService imageDataSource) {
        this.imageAPIService = imageDataSource;

    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Picture> callback) {
        imageAPIService = RetrofitConnection.getService();
        final List<Picture> pictureList = new ArrayList<>();
        Single<List<JsonObject>> service = imageAPIService.getPicture("27r-ZGf09dCrzG7vEQJIaa6t3jj9oziPNY529RHlZTI", 2);
        service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<JsonObject>>() {
            @Override
            public void onSubscribe(Disposable d) {
               disposable=d;
            }

            @Override
            public void onSuccess( List<JsonObject> jsonObjects) {

                for (int i = 0; i < jsonObjects.size(); i++) {
                    try {

                        JsonObject js = jsonObjects.get(i);
                        Picture picture1 = new Picture();
                        picture1.setId(js.get("id").getAsString());
                        JsonObject linkDownload = js.get("links").getAsJsonObject();
                        picture1.setImagePath(linkDownload.get("download").getAsString());
                        Log.d("be2",picture1.getId()+picture1.getImagePath()+picture1.getTitle());
                        pictureList.add(picture1);
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                }
                callback.onResult(pictureList,null,(long) 3);
            }


            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });



    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Picture> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Picture> callback) {
        imageAPIService = RetrofitConnection.getService();
        final List<Picture> pictureList = new ArrayList<>();
        Single<List<JsonObject>> service = imageAPIService.getPicture("27r-ZGf09dCrzG7vEQJIaa6t3jj9oziPNY529RHlZTI", params.key);
        service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<JsonObject>>() {
            @Override
            public void onSubscribe(Disposable d) {
               disposable=d;
            }

            @Override
            public void onSuccess(List<JsonObject> jsonArray) {

                for (int i = 0; i < jsonArray.size(); i++) {
                    try {

                        JsonObject js = jsonArray.get(i);
                        Picture picture1 = new Picture();
                        picture1.setId(js.get("id").getAsString());
                        JsonObject linkDownload = js.getAsJsonObject("links");
                        picture1.setImagePath(linkDownload.get("download_location").getAsString());
                        pictureList.add(picture1);
                    } catch (JsonIOException e) {
                        e.printStackTrace();
                    }
                }
                callback.onResult(pictureList,params.key+1);
            }

            @Override
            public void onError(Throwable e) {

                e.printStackTrace();
            }
        });

    }

    public Disposable getDisposable() {

        return disposable;
    }
}
