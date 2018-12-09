package com.pawankhandal52.meditime.activity;

import android.content.Intent;
import android.os.Bundle;

import com.pawankhandal52.meditime.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showRecipeActivity();
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
    
    @Override
    protected void bindViews() {
        ButterKnife.bind(this);
    }
    
    private void showRecipeActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intnet = new Intent(SplashActivity.this, LoginRegisterActivity.class);
                intnet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intnet);
            }
        }, 2000);
    }
}
