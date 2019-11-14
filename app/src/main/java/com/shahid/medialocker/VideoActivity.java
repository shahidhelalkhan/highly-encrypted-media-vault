package com.shahid.medialocker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.shahid.medialocker.adapters.VideoAdapter;
import com.shahid.medialocker.models.Image;
import com.shahid.medialocker.models.Video;
import com.shahid.medialocker.utils.FileUtility;

import java.io.File;
import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        recyclerView = findViewById(R.id.rvVideos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        VideoAdapter adapter = new VideoAdapter(FetchVideos(),this);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Video> FetchVideos() {

        Log.d(TAG, "FetchVideos: ");

        ArrayList<Video> filenames = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "decrypted";

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            String extension = FileUtility.getExtension(files[i].getName());
            Log.d(TAG, "FetchVideo: "+extension);
            String file_name = files[i].getName();

            if (files[i].getName().toLowerCase().endsWith(".mp4")) {
                Log.d(TAG, "FetchVideo: IS Video");
                filenames.add(new Video(files[i].getName(),files[i].getPath()));
            }


        }
        return filenames;
    }
}
