package com.pawankhandal52.meditime.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pawankhandal52.meditime.R;
import com.pawankhandal52.meditime.utills.MedicineImageSaver;
import com.pawankhandal52.meditime.utills.Utills;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMedicineActivity extends BaseActivity {
    
    @BindView(R.id.toolbar_add_medicine)
    Toolbar mToolbar;
    @BindView(R.id.medicine_image_view)
    ImageView mMedicineImage;
    @BindView(R.id.medicine_name_edit_text)
    EditText mMedicineNameEditText;
    @BindView(R.id.medicine_quantity_edit_text)
    EditText mMedicineQuantityEditText;
    @BindView(R.id.morning_cb)
    CheckBox mMorningCheckBox;
    @BindView(R.id.afternoon_cb)
    CheckBox mAfternoonCheckBox;
    @BindView(R.id.evening_cb)
    CheckBox mEveningCheckBox;
    @BindView(R.id.night_cb)
    CheckBox mNightCheckBox;
    @BindView(R.id.medicine_type_rg)
    RadioGroup mMedicineTypeRadioGroup;
    @BindView(R.id.liquid_rb)
    RadioButton mLiquidRadioButton;
    @BindView(R.id.tablet_rb)
    RadioButton mTabletRadioButton;
    @BindView(R.id.morning_time_edit_text)
    EditText mMorningEditText;
    @BindView(R.id.afternoon_time_edit_text)
    EditText mAfternoonEditText;
    @BindView(R.id.evening_time_edit_text)
    EditText mEveningEditText;
    @BindView(R.id.night_time_edit_text)
    EditText mNightEditText;
    
    private int  mHour, mMinute;
    private static final int CAMERA_REQUEST = 1888;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 1001;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1002;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Bitmap photo = null;
    private final String TAG = AddMedicineActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        ButterKnife.bind(this);
        setupToolbar();
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_medicine;
    }
    
    private void setupToolbar() {
        mToolbar.setTitle(R.string.add_medicine);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWite));
        mToolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back_white_24dp));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(mToolbar);
        
        
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_medicine_menu,menu);
        return  true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
    
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showToast("Save Button Clicked");
            saveMedicineDataInDb();
            return true;
        }
    
    
        return super.onOptionsItemSelected(item);
    }
    
    public void saveMedicine(View view) {
        showToast("medicine saved");
        //Check the permission to read and write .permission given
        //1.Check the android Api level
        saveMedicineDataInDb();
    
    }
    
    private void saveMedicineDataInDb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //Now check permission is given or not
        
            
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
                } else {
                    Uri uri = new MedicineImageSaver(this)
                            .setFileName(mMedicineNameEditText.getText().toString()+Utills.genrateRandomNumber()+".jpg")
                            .setExternal(true)//image save in external directory or app folder default value is false
                            .setDirectory("medicine_images")
                            .save(photo);
    
                    Log.e(TAG, "saveMedicine: image uri"+ uri);
                }
            }
        
        else{
            new MedicineImageSaver(this)
                    .setFileName(mMedicineNameEditText.getText().toString()+Utills.genrateRandomNumber()+".jpg")
                    .setExternal(true)//image save in external directory or app folder default value is false
                    .setDirectory("medicine_images")
                    .save(photo);
        }
    }
    
    @OnClick(R.id.edit_medicine_imagebutton)
    public void editMedicineImage(View view){
    
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.e(TAG, "editMedicineImage: "+(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) );
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }else{
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            
            
        }else{
            showToast(getString(R.string.your_phone_not_have_camera));
        }
        
        
    
    }
    
    @OnClick(R.id.afternoon_time_edit_text)
    public void editAfternoonTime(View view){
        if (mAfternoonCheckBox.isChecked()){
            setTimeForMedicine(mAfternoonEditText);
        }else{
            showToast(getString(R.string.please_check_afternoon));
        }
        
    }
    @OnClick(R.id.evening_time_edit_text)
    public void editEveningTime(View view){
        if (mEveningCheckBox.isChecked()) setTimeForMedicine(mEveningEditText);
        else showToast(getString(R.string.please_check_evening_session));
    }
    @OnClick(R.id.night_time_edit_text)
    public void editNightTime(View view){
        
        if(mNightCheckBox.isChecked()) setTimeForMedicine(mNightEditText);
        else showToast(getString(R.string.please_chcek_night_session));
    }
    @OnClick(R.id.morning_time_edit_text)
    public void editMorningTime(View view){
        if(mMorningCheckBox.isChecked())setTimeForMedicine(mMorningEditText);
        else showToast(getString(R.string.please_check_morning_session));
    }
    
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
    
        }else if(requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                new MedicineImageSaver(this)
                        .setFileName(mMedicineNameEditText.getText().toString()+Utills.genrateRandomNumber()+".jpg")
                        .setExternal(true)//image save in external directory or app folder default value is false
                        .setDirectory("medicine_images")
                        .save(photo);
            }else{
                Toast.makeText(this, "Write permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            
            if (data != null) {
                photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            }
            mMedicineImage.setImageBitmap(photo);
        }
    }
    
    private void setTimeForMedicine(final EditText editText){
        // Get Current Time
        final Calendar c = Calendar.getInstance(Locale.ENGLISH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String am_pm = "";
                        if(c.get(Calendar.AM_PM) == Calendar.AM){
                            am_pm = "AM";
                        }else if(c.get(Calendar.AM_PM) == Calendar.PM){
                            am_pm = "PM";
                        }
                        
                        editText.setText(String.format("%d:%d  %s", hourOfDay, minute,am_pm));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
