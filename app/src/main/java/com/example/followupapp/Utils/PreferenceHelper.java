package com.example.followupapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {

    private static final String PREF_NAME = "FollowUpApp";
    private static SharedPreferences app_preferences;
    private static PreferenceHelper preferenceHelper = new PreferenceHelper();
    private final String DEVICE_TOKEN = "device_token";
    private final String FIRST_NAME = "first_name";
    private final String IsLogIN = "is_log_in";
    private final String CHECK_IN="22/10/19";
    private PreferenceHelper() {
    }

    public static PreferenceHelper getInstance(Context context) {
        app_preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferenceHelper;
    }

    public void putDeviceToken(String deviceToken) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(DEVICE_TOKEN, deviceToken);
        editor.commit();
    }

    public String getDeviceToken() {
        return app_preferences.getString(DEVICE_TOKEN, null);
    }

    public void putFirstName(String firstName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FIRST_NAME, firstName);
        edit.commit();
    }

    public void putDeviceCheckIn(String checkin) {
        SharedPreferences.Editor editor_checkin = app_preferences.edit();
        editor_checkin.putString(CHECK_IN, checkin);
        editor_checkin.commit();
    }

    public String getDeviceCheckIn(){
        return app_preferences.getString(CHECK_IN,"");
    }

    public String getFirstName() {
        return app_preferences.getString(FIRST_NAME, "");

    }

    public void putIsLogIN(boolean isLog) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IsLogIN, isLog);
        edit.commit();
    }

    public boolean isLogIN() {
        return app_preferences.getBoolean(IsLogIN, false);
    }
}
