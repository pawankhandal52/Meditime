package com.pawankhandal52.meditime.utills;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Pawan Khandal on 12/12/18,01
 */
public class MedicineImageSaver {
    private String directoryName = "medicine_images";
    private String fileName = "image.png";
    private Context context;
    private File dir;
    private boolean external=false;
    
    public MedicineImageSaver(Context context) {
        this.context = context;
    }
    
    public MedicineImageSaver setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
    
    public MedicineImageSaver setExternal(boolean external) {
        this.external = external;
        return this;
    }
    
    public MedicineImageSaver setDirectory(String directoryName) {
        this.directoryName = directoryName;
        return this;
    }
    
    public Uri save(Bitmap bitmapImage) {
        FileOutputStream fileOutputStream = null;
        try {
            File f = createFile();
            fileOutputStream = new FileOutputStream(f);
            
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            return Uri.fromFile(f);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @NonNull
    private File createFile() {
        File directory;
        if (external) {
            directory = getAlbumStorageDir(directoryName);
            Log.e("MedicineImageSaver", "createFile: "+context.getFilesDir() );
            if (!directory.exists()){
                directory.mkdir();
            }
        } else {
            Log.e("MedicineImageSaver", "createFile: "+context.getFilesDir() );
            directory = new File(context.getFilesDir()+"/"+directoryName);
            if (!directory.exists()){
                directory.mkdir();
            }
        }
        
        return new File(directory, fileName);
    }
    
    private File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("ImageSaver", "Directory not created");
        }
        return file;
    }
    
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    
    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public boolean deleteFile() {
        File file = createFile();
        return file.delete();
    }
}

