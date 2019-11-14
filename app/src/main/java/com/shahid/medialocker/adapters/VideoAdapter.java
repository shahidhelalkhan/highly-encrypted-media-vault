package com.shahid.medialocker.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.shahid.medialocker.R;
import com.shahid.medialocker.VideoPreview;
import com.shahid.medialocker.models.Video;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends Adapter<VideoAdapter.VideoViewHolder> {
    Context context;
    ArrayList<Video> list;
    public VideoAdapter(ArrayList<Video> list , Context context) {
        this.list = list;
        this.context =context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View layoutInflater = LayoutInflater.from(context).inflate(R.layout.rv_video_layout,parent,false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(layoutInflater);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        File image = new File(Environment.getExternalStorageDirectory().toString()+"/decrypted/"+list.get(position).videoName);

            holder.ivThumb.setImageResource(R.drawable.video_play);

        holder.tvVidName.setText(list.get(position).videoName);
        final File video = new File(Environment.getExternalStorageDirectory().toString()+"/decrypted/"+list.get(position).videoName);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideo(video,context);
            }
        });

    }

    private void openVideo(File video, Context context) {
        Intent intent = new Intent(context, VideoPreview.class);
        intent.putExtra("file",video);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

public class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvVidName;
        LinearLayout linearLayout;
    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        ivThumb = itemView.findViewById(R.id.ivVideoThumb);
        tvVidName = itemView.findViewById(R.id.tvVidName);
        linearLayout = itemView.findViewById(R.id.videoLayout);


    }
}
}
