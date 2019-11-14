package com.shahid.medialocker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.shahid.medialocker.adapters.ImageAdapter;
import com.shahid.medialocker.models.Image;
import com.shahid.medialocker.utils.FileUtility;

import java.io.File;
import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ArrayList<Image> list = FetchImages();



        Log.d(TAG, "onCreate: "+list.size());
        recyclerView = findViewById(R.id.rvImages);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((GridLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        ImageAdapter adapter = new ImageAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<Image> FetchImages() {

        Log.d(TAG, "FetchImages: ");

        ArrayList<Image> filenames = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "decrypted";

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            String extension = FileUtility.getExtension(files[i].getName());
            Log.d(TAG, "FetchImages: "+extension);
            String file_name = files[i].getName();

            if (files[i].getName().toLowerCase().endsWith(".jpg") || files[i].getName().toLowerCase().endsWith(".png")) {
                Log.d(TAG, "FetchImages: IS IMAGE");
                filenames.add(new Image(files[i].getPath(),files[i].getName()));
            }
            

        }
        return filenames;
    }
}
