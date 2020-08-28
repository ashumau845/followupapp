package com.example.followupapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MyPreferenceHelper {

    private static KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
    private static String myMaterKeyAlias;
    private static SharedPreferences app_preferences;
    private static MyPreferenceHelper preferenceHelper = new MyPreferenceHelper();
    private final String DEVICE_TOKEN = "device_token";
    private final String FIRST_NAME = "first_name";
    private final String IsLogIN = "is_log_in";
    private final String CHECK_IN="22/10/2019";
    private final String CHECK_OUT="checkout";
    private MyPreferenceHelper() {
    }




    public static MyPreferenceHelper getInstance(Context context) {
        try {
            myMaterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            app_preferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    myMaterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return preferenceHelper;
    }


    public void putDeviceCheckIn(String checkin) {
        SharedPreferences.Editor editor_checkin = app_preferences.edit();
        editor_checkin.putString(CHECK_IN, checkin).apply();
        editor_checkin.commit();
    }

    public void putDevicecheckout(String str_checkout){
        SharedPreferences.Editor editor_checkin = app_preferences.edit();
        editor_checkin.putString(CHECK_OUT,str_checkout).apply();
        editor_checkin.commit();
    }

    public String getcheckout(){
        return app_preferences.getString(CHECK_OUT,"");
    }

    public String getDeviceCheckIn(){
        return app_preferences.getString(CHECK_IN,"");
    }

    public void putDeviceToken(String deviceToken) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString(DEVICE_TOKEN, deviceToken).apply();
        editor.commit();
    }

    public String getDeviceToken() {
        return app_preferences.getString(DEVICE_TOKEN, null);
    }

    public void putFirstName(String firstName) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putString(FIRST_NAME, firstName).apply();
        edit.commit();
    }

    public String getFirstName() {
        return app_preferences.getString(FIRST_NAME, "");

    }

    public void putIsLogIN(boolean isLog) {
        SharedPreferences.Editor edit = app_preferences.edit();
        edit.putBoolean(IsLogIN, isLog).apply();
        edit.commit();
    }

    public boolean isLogIN() {
        return app_preferences.getBoolean(IsLogIN, false);
    }



}
