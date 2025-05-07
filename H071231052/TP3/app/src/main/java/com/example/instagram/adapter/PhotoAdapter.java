package com.example.instagram.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.R;
import com.example.instagram.model.PhotoModel;

import java.io.FileNotFoundException;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    Context context;
    List<PhotoModel> photoList;
    public PhotoAdapter(Context context, List<PhotoModel> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoViewHolder holder, int position) {
        PhotoModel photo = photoList.get(position);
        Uri uri = photo.getUri();
        String url = photo.getImageUrl();

        if (url != null) {
            Glide.with(context)
                    .load(photo.getImageUrl())
                    .into(holder.imageView);
        }
        else if (uri != null) {
            Glide.with(context)
                    .load(photo.getUri())
                    .into(holder.imageView);
        } else {
            int imageResId = photo.getImageRes();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageResId, options);

            int imgWidth = bitmap.getWidth();
            int imgHeight = bitmap.getHeight();
            float aspectRatio = (float) imgHeight / imgWidth;


            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int calculatedHeight = (int) (screenWidth * aspectRatio);

            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = calculatedHeight;
            holder.imageView.setLayoutParams(params);

            holder.imageView.setImageResource(imageResId);
        }
    }




    @Override
    public int getItemCount() {
        return photoList.size();
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics()
        );
    }

    static class PhotoViewHolder extends  RecyclerView.ViewHolder {
        ImageView ivHeartAnimation;
        ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPhoto);
            ivHeartAnimation = itemView.findViewById(R.id.ivHeartAnimation);
        }
    }
}
