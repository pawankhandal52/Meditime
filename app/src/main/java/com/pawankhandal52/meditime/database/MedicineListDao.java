package com.pawankhandal52.meditime.database;

import android.arch.persistence.room.Insert;

import com.pawankhandal52.meditime.models.Medicine;

/**
 * Created by Pawan Khandal on 12/12/18,46
 */
public interface MedicineListDao {
    
    //Insert Medicine in database
    @Insert
    void insertMedicine(Medicine medicine);
}
