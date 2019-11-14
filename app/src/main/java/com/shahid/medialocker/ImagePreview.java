package com.shahid.medialocker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImagePreview extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        imageView = findViewById(R.id.imagePrev);
        if(getIntent().hasExtra("file")){
            File pictureFile = (File)getIntent().getExtras().get("file");
            Picasso.get().load(pictureFile).into(imageView);
        }

    }
}
