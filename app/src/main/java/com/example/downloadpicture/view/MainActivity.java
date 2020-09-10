package com.example.downloadpicture.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.WallpaperManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.downloadpicture.ImageAdapter;
import com.example.downloadpicture.R;
import com.example.downloadpicture.databinding.ActivityMainBinding;
import com.example.downloadpicture.model.Picture;
import com.example.downloadpicture.viewmodel.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private PagedList<Picture> pictures;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private Button download;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        getImages();

    }

    public void getImages() {

        mainActivityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Picture>>() {
            @Override
            public void onChanged(@Nullable PagedList<Picture> picturePagedList) {
                pictures = picturePagedList;
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView() {

        final LinearLayout linearLayout = activityMainBinding.linear;
        recyclerView = activityMainBinding.recyclerView;
        download = activityMainBinding.download;
        imageAdapter = new ImageAdapter(this);
        imageAdapter.submitList(pictures);

        imageAdapter.setOnItemClickListener(new ImageAdapter.onItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(getBaseContext())
                                .load(pictures.get(position).getImagePath())
                                .asBitmap()
                                .fitCenter()
                                .into(new SimpleTarget<Bitmap>(700, 700) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                                        try {
                                            WallpaperManager.getInstance(getBaseContext()).setBitmap(bitmap);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                        Snackbar snackbar = Snackbar
                                .make(linearLayout, "wallpaper changed! very beautiful :)", Snackbar.LENGTH_LONG);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.purple));
                        snackbar.show();
                    }
                });

            }
        });

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();

    }


}

