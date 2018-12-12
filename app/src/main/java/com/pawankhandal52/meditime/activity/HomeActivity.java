package com.pawankhandal52.meditime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pawankhandal52.meditime.R;
import com.pawankhandal52.meditime.session.SharedPreference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.toolbar_home)
    Toolbar mToolbar;
    
    SharedPreference mSharedPreference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreference = new SharedPreference(getApplicationContext());
    
        
    }
    
    private void setupToolbar() {
        mToolbar.setTitle(R.string.medicine_list);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWite));
        mToolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_vert_black_24dp));
        setSupportActionBar(mToolbar);
        
        
    }
    
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        setupToolbar();
    }
    
    
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
    
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(HomeActivity.this, "Action clicked settings", Toast.LENGTH_LONG).show();
            startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
            return true;
        }else if(id == R.id.action_sort_by_name) {
            Toast.makeText(HomeActivity.this, "Action clicked sort by name", Toast.LENGTH_LONG).show();
            return true;
        }else if(id == R.id.action_sort_by_time) {
            Toast.makeText(HomeActivity.this, "Action clicked sort by time", Toast.LENGTH_LONG).show();
            return true;
        }
        
    
        return super.onOptionsItemSelected(item);
    }
    
    public void addNewMedicine(View view) {
        Intent intent = new Intent(HomeActivity.this,AddMedicineActivity.class);
        startActivity(intent);
    }
    
    @Override
    public void onBackPressed() {
        if (mSharedPreference.isUserIsLogin()){
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }else{
            super.onBackPressed();
        }
    }
}

