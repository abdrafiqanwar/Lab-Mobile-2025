package com.example.instagram.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;
import com.example.instagram.model.PhotoModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class SelectedPhotoAdapter extends RecyclerView.Adapter<SelectedPhotoAdapter.PhotoViewHolder> {

    private final Context context;
    private final List<PhotoModel> photoList;

    public SelectedPhotoAdapter(Context context, List<PhotoModel> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selected_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        PhotoModel photo = photoList.get(position);

        if (photo.getUri() != null) {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(photo.getUri());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.photoImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                holder.photoImageView.setImageResource(R.drawable.oip);
            }
        } else {
            holder.photoImageView.setImageResource(photo.getImageRes());
        }
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.imageViewPhotoPost);
        }
    }
}
