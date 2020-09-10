package com.example.downloadpicture.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.example.downloadpicture.BR;
import com.example.downloadpicture.R;

public class Picture extends BaseObservable {

    private String id;
    private String title;
    private String imagePath;


    @BindingAdapter({"imagePath"})
    public static void loadImage(ImageView imageView, String imageURL) {

        Glide.with(imageView.getContext())
                .load(imageURL)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String posterPath) {
        this.imagePath = posterPath;
        notifyPropertyChanged(BR.imagePath);
    }

    public static final DiffUtil.ItemCallback<Picture> CALLBACK = new DiffUtil.ItemCallback<Picture>() {
        @Override
        public boolean areItemsTheSame(Picture oldItem, Picture newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Picture oldItem, Picture newItem) {
            return true;
        }
    };

}