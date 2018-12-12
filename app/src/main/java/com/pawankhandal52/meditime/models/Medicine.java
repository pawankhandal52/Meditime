package com.pawankhandal52.meditime.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Pawan Khandal on 12/12/18,37
 */
@Entity(tableName = "medicine_list")
public class Medicine {
    @PrimaryKey(autoGenerate = true)
    private int mId;
    @ColumnInfo(name = "medicine_image_path")
    private String mMedicineImagePath;
    @ColumnInfo(name = "medicine_name")
    private String mMedicineName;
    @ColumnInfo(name = "medicne_quantity")
    private String mMedicineQuantity;
    @ColumnInfo(name = "medicine_morning_session")
    private Boolean mMedicineMorningSession;
    
    @ColumnInfo(name = "medicine_afternoon_session")
    private Boolean mMedicineAfternoonSession;
    
    @ColumnInfo(name = "medicine_evening_session")
    private Boolean mMedicineEveningSession;
    
    @ColumnInfo(name = "medicine_night_session")
    private Boolean mMedicineNightSession;
    
    @ColumnInfo(name = "medicine_type")
    private String mMedicineType;
    
    @ColumnInfo(name = "medicine_morning_time")
    private Long mMedicineMorningTime;
    
    @ColumnInfo(name = "medicine_afternoon_time")
    private Long mMedicineAfternoonTime;
    
    @ColumnInfo(name = "medicine_evening_time")
    private Long mMedicineEveningTime;
    
    @ColumnInfo(name = "medicine_night_time")
    private Long mMedicineNightTime;
    
    @ColumnInfo(name = "medicine_extra_instruction")
    private String mMedicineExtraSession;
    
    @ColumnInfo(name = "medicine_reminder_on_of")
    private Boolean mMedicineReminderOnOf;
}
