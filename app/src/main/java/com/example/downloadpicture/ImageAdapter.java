
package com.example.downloadpicture;

import android.content.Context;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.downloadpicture.databinding.ListItemBinding;
import com.example.downloadpicture.model.Picture;


public class ImageAdapter extends PagedListAdapter<Picture, ImageAdapter.PictureViewHolder> {

    private Context context;
    int row_index=-1;
    private ImageAdapter.onItemClickListener mlistener;


    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ImageAdapter.onItemClickListener listener) {
        mlistener = listener;
    }

    public ImageAdapter(Context context) {
        super(Picture.CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding b = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.list_item, parent, false);

        return new PictureViewHolder(b,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.PictureViewHolder holder,final int position) {


        Picture picture = getItem(position);
        Log.d("picture", picture.getId() + picture.getImagePath());
        holder.imgListItemBinding.setPicture(picture);

        if(row_index==position){
            holder.imgListItemBinding.cardview.setBackgroundColor(Color.parseColor("#673AB7"));
            holder.imgListItemBinding.pic.setAlpha(0.7f);

        }
        else
        {
            holder.imgListItemBinding.cardview.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.imgListItemBinding.pic.setAlpha(1f);
        }

    }

    public class PictureViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding imgListItemBinding;


        public PictureViewHolder(@NonNull final ListItemBinding itemBinding, final ImageAdapter.onItemClickListener listener) {
            super(itemBinding.getRoot());
            this.imgListItemBinding = itemBinding;

            itemBinding.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    row_index=position;
                    Log.d("p",position+"");
                    listener.onItemClick(position);
                    notifyDataSetChanged();

                }
            });

        }
    }
}
