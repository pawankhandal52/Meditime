package com.pawankhandal52.meditime.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Pawan Khandal on 12/9/18,15
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);
        
    }
    
    /**
     * Every object annotated with  its gonna injected trough butter knife
     */
    protected  void bindViews(){
        ButterKnife.bind(this);
    }
    
    protected  void initView(@Nullable Bundle savedInstanceState){
    
    }
    protected abstract int getLayoutId();
    
    /**
     * This simple method to show the Toast.
     * @param msg String value for message
     */
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    
    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
    
    public Intent getFileChooserIntent(boolean isForImageAndPancard) {
        String[] mimeTypes = {"image/*", "application/pdf"};
        
        Intent intent = new Intent();
        if(!isForImageAndPancard){
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        } else {
            StringBuilder mimeTypesStr = new StringBuilder();
            
            for (String mimeType : mimeTypes) {
                mimeTypesStr.append(mimeType).append("|");
            }
            
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        
        return intent;
    }
}
