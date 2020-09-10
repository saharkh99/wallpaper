package com.example.downloadpicture.viewmodel;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.function.Supplier;

import javax.inject.Singleton;

@Singleton
public class MainActivityVMFactory<T extends ViewModel> extends ViewModelProvider.NewInstanceFactory {

    private final Class<T> viewModelClass;
    private final Supplier<T> viewModelSupplier;

    public MainActivityVMFactory(Class<T> viewModelClass, Supplier<T> viewModelSupplier){
        this.viewModelClass = viewModelClass;
        this.viewModelSupplier = viewModelSupplier;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(viewModelClass)){
            return (T) viewModelSupplier.get();
        }else {
            throw new IllegalArgumentException("Unknown Class name "+viewModelClass.getName());
        }
    }
}