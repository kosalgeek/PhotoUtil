# PhotoUtil
This is an Android library for camera, loading photos in gallery, and encoding/decoding an image to a base64.

#### You can watch video tutorials of using PhotoUtil: 
#### 1. Part 1 at https://www.youtube.com/watch?v=4LCnoVqQ6N4
#### 2. Part 2 at https://www.youtube.com/watch?v=EmOzhbGFEAk

#### (MUST watch) A complete PhotoUtil tutorials that combines with Volley to upload multiple images to a server at the same time:
#### 1. https://www.youtube.com/watch?v=o1sMaD2yyXA
#### 2. https://www.youtube.com/watch?v=7itJq7Ehu_8
#### * a complete Volley tutorial: http://goo.gl/EfaFUs

## SETUP
1. Download PhotoUtil.jar
2. Copy it and paste into your Android project at *App* > *libs* > right click on the jar file and choose *Add as Library*

## Take a Photo using Camera
### 1. Add Permissions
To use the camera, first you need to add permissions in AndroidManifest.xml:
```java
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    ...>
    <uses-feature android:name="android.hardware.camera2" android:required="true"/>
    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    ...
</manifest>
```
### 2. Dispatch a Camera and Save it to External Storage
#### 2.1. To dispatch a camera and save a photo:
```java
CameraPhoto cameraPhoto = new CameraPhoto(getApplicationContext());
Intent in = cameraPhoto.takePhotoIntent();
startActivityForResult(in, CAMERA_REQUEST);
```
#### 2.2. To get the photo path
```java
String photoPath = cameraPhoto.getPhotoPath(); //call it in onActivityResult() method
```

#### Snippet of code:
```java
//declare them as global variables
CameraPhoto cameraPhoto;
final int CAMERA_REQUEST = 1100;
protected void onCreate(Bundle savedInstanceState) {
  ...
  //initialize it inside onCreate()
  cameraPhoto = new CameraPhoto(getApplicationContext());
  
  //call it to open the camera
  startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
  cameraPhoto.addToGallery();
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == RESULT_OK){
      if(requestCode == CAMERA_REQUEST){
          String photoPath = cameraPhoto.getPhotoPath();
         try {
              Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
              imageView.setImageBitmap(bitmap); //imageView is your ImageView
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
      }
    }//end if resultCode
}
```

## Select a Photo from Gallery
#### Dispatch a gallery
```java
GalleryPhoto galleryPhoto = new GalleryPhoto(getApplicationContext());
Intent in = galleryPhoto.openGalleryIntent();
startActivityForResult(in, GALLERY_REQUEST);
```

#### Get the Photo Path
```java
//Call the code in onActivityResult() method
galleryPhoto.setPhotoUri(data.getData());
String photoPath = galleryPhoto.getPath();
```

#### Snippet of Code:
```java
//declare them as global variables
GalleryPhoto galleryPhoto;
final int GALLERY_REQUEST = 2200;
protected void onCreate(Bundle savedInstanceState) {
  ...
  //initialize it inside onCreate()
  galleryPhoto = new GalleryPhoto(getApplicationContext());
  
  //call it to open the gallery
  startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == RESULT_OK){
      if(requestCode == GALLERY_REQUEST){
          galleryPhoto.setPhotoUri(data.getData());
          String photoPath = galleryPhoto.getPath();
          try {
              Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
              imageView.setImageBitmap(bitmap);
              selectedImage = photoPath;
          } catch (FileNotFoundException e) {
              e.printStackTrace();
          }
      }
    }//end if resultCode
}
```

## ImageLoader (alias PhotoLoader)
It is used to load a ``Bitmap`` or a ``Drawable`` from a file. One important method is ``requestSize(width, height)`` which is neccesary for scaling. 
### To Get a Bitmap
```java
String photoPath = "your_photo_path";
Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
```
or
```java
String photoPath = "your_photo_path";
Bitmap bitmap = PhotoLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
```
### To Get a Drawable
```java
Drawable drawable = ImageLoader.init().from(photoPath).requestSize(512, 512).getImageDrawable();
```
or
```java
Drawable drawable = PhotoLoader.init().from(photoPath).requestSize(512, 512).getImageDrawable();
```

### ImageBase64
It is for encoding from a ``Bitmap`` to a ``String`` or decoding from a ``String`` to a ``Bitmap``.
```java
String encoded = ImageBase64.encode(bitmap); 

Bitmap bitmap = ImageBase64.decode(encodedString);
```

# Follow Me
 * Get more free source code at https://github.com/kosalgeek
 * Watch my video tutorials at my YouTube channel https://youtube.com/user/oumsaokosal
 * Like my Facebook Page at https://facebook.com/kosalgeek
 * Follow me on Twitter https://twitter.com/okosal
 * Get more tutorials at http://www.kosalgeek.com and http://www.top12review.com

# Donation
#### If you think this library have saved your life, please support me by donating a few bucks, just for my coffee :)
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](
https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=oumsaokosal01%40gmail%2ecom&lc=US&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted)

<input type="image" src="https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal - The safer, easier way to pay online!">
<img alt="" border="0" src="https://www.paypalobjects.com/en_US/i/scr/pixel.gif" width="1" height="1">
</form>
 
# LICENSE

(The MIT License)
Copyright (c) 2016 KosalGeek. (kosalgeek at gmail dot com)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.




