package com.example.downloadpicture.service;

import com.google.gson.JsonObject;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ImageAPIService {



    @GET("photos/")
    Single<List<JsonObject>> getPicture(@Query("client_id") String apiKey, @Query("page") long page);
}
