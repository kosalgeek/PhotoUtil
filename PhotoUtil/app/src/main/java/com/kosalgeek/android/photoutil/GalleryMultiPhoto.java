package com.kosalgeek.android.photoutil;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GalleryMultiPhoto {

    final String TAG = this.getClass().getSimpleName();

    private Context context;

    private ClipData clipData;
    private Uri uri;

    public void setPhotoData(ClipData clipData, Uri uri) {
        this.clipData = clipData;
        this.uri = uri;
    }


    public GalleryMultiPhoto(Context context){
        this.context = context;
    }

    public Intent openMultiPhotoGalleryIntent() throws IllegalAccessException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            return Intent.createChooser(intent, getChooserTitle());
        }
        else{
            throw new IllegalAccessException("This feature of multiple images selection is only available for KITKAT (API 19) or above.");
        }
    }

    public String getChooserTitle(){
        return "Select Pictures";
    }

    public List<String> getPhotoPathList() throws IllegalAccessException {
        List<String> pathList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(clipData != null){
                Log.d(TAG, "clipData");
                for(int i=0; i<clipData.getItemCount(); i++){
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri u = item.getUri();
                    String path = RealPathUtil.getRealPathFromURI_API19(context, u);
                    pathList.add(path);
                }
            }
            else{
                Log.d(TAG, "Uri");
                String path = RealPathUtil.getRealPathFromURI_API19(context, uri);
                pathList.add(path);
            }
        }
        else{
            throw new IllegalAccessException("This feature of multiple images selection is only available for KITKAT (API 19) or above.");
        }




        return pathList;
    }
}
