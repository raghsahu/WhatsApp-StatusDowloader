package com.whatsapp.statusdownloader.adapter;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.whatsapp.statusdownloader.R;

import java.util.ArrayList;

public class StatusVideoAdapter extends RecyclerView.Adapter<StatusVideoAdapter.MyViewHolder> {

    private ArrayList<String> videos;
    String path;
    Context context;

    public StatusVideoAdapter(ArrayList<String> videos, String path, Context context){
        this.videos=videos;
        this.path=path;
        this.context=context;
    }


    @NonNull
    @Override
    public StatusVideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_item, null);
        StatusVideoAdapter.MyViewHolder viewHolder = new StatusVideoAdapter.MyViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusVideoAdapter.MyViewHolder myViewHolder, int i) {

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path+"/"+videos.get(i),
                MediaStore.Images.Thumbnails.MINI_KIND);
        myViewHolder.imageView.setImageBitmap(thumb);

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //videoView=itemView.findViewById(R.id.statusItem_videoView);
            imageView=itemView.findViewById(R.id.statusItem_imageView);
        }
    }
}
