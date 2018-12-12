package com.pawankhandal52.meditime.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.pawankhandal52.meditime.R;
import com.pawankhandal52.meditime.utills.AppConstant;

/**
 * Created by Pawan Khandal on 12/9/18,16
 */
public class SharedPreference {
    //TAG
    private final String TAG = SharedPreference.class.getSimpleName();
    
    //Shared preference to store the session data for delivery chops.
    private final SharedPreferences SHAREDPREFERENCES;
    
    //Editor for shared prefrance
    private final SharedPreferences.Editor SHAREDPREFERENCES_EDITOR;
    //Context
    private Context mContext;
    
    //Key to check
    private final String KEY_IS_LOGGEDIN = "isLoggedIn";
    
    
    public SharedPreference(Context context) {
        String PREF_NAME = AppConstant.PREF_NAME;
        int PRIVATE_MODE = 0;
        this.mContext = context;
        SHAREDPREFERENCES = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        SHAREDPREFERENCES_EDITOR = SHAREDPREFERENCES.edit();
        SHAREDPREFERENCES_EDITOR.apply();
    }
    
    /**
     * method to set user is loggged in
     * @param isLoggedIn boolean
     */
    public void setLogin(boolean isLoggedIn){
        SHAREDPREFERENCES_EDITOR.putBoolean(KEY_IS_LOGGEDIN,isLoggedIn);
        Log.d(TAG, "setLogin: User is logged in");
    }
    public boolean isUserIsLogin(){
        return SHAREDPREFERENCES.getBoolean(KEY_IS_LOGGEDIN,false);
    }
    
    public void setUID(String userId){
        SHAREDPREFERENCES_EDITOR.putString(mContext.getResources().getString(R.string.key_user_id),userId);
    }
    
    public String getUSER_ID() {
        return SHAREDPREFERENCES.getString(mContext.getResources().getString(R.string.key_user_id),null);
    }
    
    public void setUserAllowDataToSync(boolean isSync){
        SHAREDPREFERENCES_EDITOR.putBoolean(mContext.getResources().getString(R.string.key_is_sync),isSync);
    }
    
    public boolean isUserAllowSync(){
        return SHAREDPREFERENCES.getBoolean(mContext.getResources().getString(R.string.key_is_sync),false);
    
    }
}
