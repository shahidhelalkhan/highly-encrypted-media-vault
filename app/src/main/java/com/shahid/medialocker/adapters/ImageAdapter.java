package com.shahid.medialocker.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.medialocker.ImagePreview;
import com.shahid.medialocker.R;
import com.shahid.medialocker.models.Image;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    ArrayList<Image> list;
    Context context;
    private static final String TAG = "ImageAdapter";
    public ImageAdapter(ArrayList<Image> list,Context context) {

        this.list = list;
        this.context=context;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_photo_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        String name = list.get(position).fileName;
        Log.d(TAG, "onBindViewHolder: ");
        final File image = new File(Environment.getExternalStorageDirectory().toString()+"/decrypted/"+list.get(position).fileName);
        if(image.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            holder.ivImage.setImageBitmap(myBitmap);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage(image);
            }
        });




    }

    private void openImage(File file) {
       Intent intent = new Intent(context, ImagePreview.class);
       intent.putExtra("file",file);
       context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

  public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        LinearLayout linearLayout;

      public ImageViewHolder(@NonNull View itemView) {
          super(itemView);
          ivImage = itemView.findViewById(R.id.ivPhotoThumb);
          linearLayout = itemView.findViewById(R.id.photoRow);



      }
  }
}
