package com.whatsapp.statusdownloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.github.clans.fab.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ImageViewActivity extends AppCompatActivity {

    ImageView imageView;
    FloatingActionButton floatingActionButton3;
    private Toolbar mToolbar;
    VideoView videoView;
    private String Type, imageUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                onBackPressed();
            }
        });

        imageView = findViewById(R.id.detail_imageViews);
        videoView = findViewById(R.id.videoView);

        if (getIntent() != null) {
            final Intent intent = getIntent();
            imageUrl = intent.getStringExtra("path");
            Type = intent.getStringExtra("Type");
        }

        if (Type.equalsIgnoreCase("Image")) {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            final Bitmap bitmap = BitmapFactory.decodeFile(imageUrl);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);

            videoView.setVideoPath(imageUrl);
            videoView.setMediaController(new MediaController(ImageViewActivity.this));
            videoView.pause();
        }

        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Type.equalsIgnoreCase("Image")) {
                    addImageToGallery(getApplicationContext(), imageUrl);
                } else {
                   addVideoToGallery(getApplicationContext(), imageUrl);
                }


            }
        });
    }


    private void addVideoToGallery(final Context context, String videoUrl) {
        Toast.makeText(context, "Save", Toast.LENGTH_LONG).show();
        File sourceFIle = new File(videoUrl);
        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StatusDownload/" + sourceFIle.getName();
        File destination = new File(destinationPath);
        MediaScannerConnection.scanFile(context, new String[]  {destination.getPath()} , new String[]{"video/*"}, null);
        try {
            FileUtils.copyFile(sourceFIle, destination);
          //  MediaStore.Images.Media.insertImage(context.getApplicationContext().getContentResolver(), BitmapFactory.decodeFile(destinationPath), "", "");
            MediaStore.Video.Media.getContentUri(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void addImageToGallery(Context context, String source) {
        Toast.makeText(context, "Save", Toast.LENGTH_LONG).show();
        File sourceFIle = new File(source);
        String destinationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StatusDownload/" + sourceFIle.getName();
        File destination = new File(destinationPath);
        MediaScannerConnection.scanFile(context, new String[]  {destination.getPath()} , new String[]{"image/*"}, null);

        try {
            FileUtils.copyFile(sourceFIle, destination);
            MediaStore.Images.Media.insertImage(context.getApplicationContext().getContentResolver(), BitmapFactory.decodeFile(destinationPath), "", "");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
