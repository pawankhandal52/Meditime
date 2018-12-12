package com.pawankhandal52.meditime.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.pawankhandal52.meditime.R;
import com.pawankhandal52.meditime.session.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.toolbar_settings)
    Toolbar mToolbar;
    @BindView(R.id.sync_your_data_switch)
    Switch mSyncData;
    private final String TAG = SettingsActivity.class.getSimpleName();
    private SharedPreference mSharedPreference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreference = new SharedPreference(getApplicationContext());
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }
    
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        setupToolbar();
        
        mSyncData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e(TAG, "onCheckedChanged: "+isChecked );
                if (isChecked){
                    if (!mSharedPreference.isUserIsLogin()){
                        showToast(getString(R.string.sync_error));
                        mSyncData.setChecked(false);
                    }
                    else mSharedPreference.setUserAllowDataToSync(isChecked);
                }
                
            }
        });
    }
    
    private void setupToolbar() {
        mToolbar.setTitle(R.string.settings);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWite));
        mToolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    
    
    
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    @OnClick(R.id.about_us_text_view)
    public void aboutUs(View view) {
        showToast(getString(R.string.about_us_text));
    }
    
    @OnClick(R.id.rate_us_text_view)
    public void rateUsAction(View view){
        showToast("Rate us on Playstore. This Function coming soon");
    }
    
    @OnClick(R.id.share_with_text_view)
    public void shareWithFriends(View view){
        showToast("Share with your friends.This Function coming soon");
    }
    
    @OnClick(R.id.logout_text_view)
    public void logout(View view){
        showToast("Logout");
        FirebaseAuth.getInstance().signOut();
    }
}
