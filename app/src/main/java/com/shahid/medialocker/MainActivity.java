package com.shahid.medialocker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shahid.medialocker.utils.Constants;
import com.shahid.medialocker.utils.FileUtility;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;


public class MainActivity extends AppCompatActivity {
    ImageButton btnImage;
    private static final String TAG = "MainActivity";

    ImageButton vidBtn;

    ImageButton imageButton;

    ImageButton videoButton;

    ImageButton cloud;

    FloatingActionButton fab;

    ImageButton btnRefresh;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRefresh = findViewById(R.id.refreshBtn);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(MainActivity.this,"Fetching New Files",Toast.LENGTH_LONG).show();
                    FileUtility.decode();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //fab

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //cloud

        cloud = findViewById(R.id.cloud);
        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Backing Up Files",Toast.LENGTH_LONG).show();

            }
        });


        try {
            FileUtility.decode();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Dashboard
        imageButton = findViewById(R.id.ImageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent);
            }
        });

        //ListVideos

        videoButton = findViewById(R.id.videoBtn);
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });



        //Videos
        vidBtn = findViewById(R.id.btnVid);
        vidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent();
                intent.setType("video/*");
                startActivityForResult(intent,Constants.PICK_VIDEO_REQUEST);

            }
        });


        //Images
        btnImage = findViewById(R.id.btnImg);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);

                mediaChooser.setType("image/*");
                startActivityForResult(mediaChooser, Constants.PICK_FILE_REQUEST);

            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode==RESULT_OK){

            switch (requestCode){
                case 1:
                    Uri uri = data.getData();
                    String path = getRealPathFromURI(uri);
                    Log.d(TAG, "onActivityResult: Data" + uri.toString());
                    Log.d(TAG, "onActivityResult:" + getRealPathFromURI(uri));
                    File source = new File(path);
                    String name = source.getName();
                    File dest = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MediaLocker/"+ name);

                    try {
                        dest.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(FileUtility.moveFile(source,dest)){
                            Log.d(TAG, "onActivityResult: Moved");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidAlgorithmParameterException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    Log.d(TAG, "onActivityResult: "+data.getData().getPath());
                    Uri vidUri = data.getData();
                    String vidPath = getRealPathFromURI(vidUri);
                    Log.d(TAG, "onActivityResult: Data" + vidUri.toString());
                    Log.d(TAG, "onActivityResult:" + getRealPathFromURI(vidUri));
                    File sourceVideo = new File(vidPath);
                    String videoName= sourceVideo.getName();
                    File destVideo = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MediaLocker/"+ videoName);

                    if(!destVideo.exists()){
                        try {
                            destVideo.createNewFile();
                            FileUtility.moveFile(sourceVideo,destVideo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }



        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Audio.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileUtility.deleteDecryptedData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FileUtility.deleteDecryptedData();
        finish();
    }
}
