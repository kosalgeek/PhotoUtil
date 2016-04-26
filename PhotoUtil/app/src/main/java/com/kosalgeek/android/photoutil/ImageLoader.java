package com.kosalgeek.android.photoutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageLoader {

    private String filePath;

    private static ImageLoader instance;

    private int width = 128, height = 128; //default

    protected ImageLoader(){
    }


    public static ImageLoader init(){
        if(instance == null){
            synchronized (ImageLoader.class) {
                if(instance == null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public ImageLoader from(String filePath) {
        this.filePath = filePath;
        return instance;
    }

    public ImageLoader requestSize(int width, int height) {
        this.height = width;
        this.width = height;
        return instance;
    }


    public Bitmap getBitmap() throws FileNotFoundException {

        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException();
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, width, height);

        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    public Drawable getImageDrawable() throws FileNotFoundException{
        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException();
        }
        Drawable drawable = Drawable.createFromPath(filePath);
        return drawable;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
