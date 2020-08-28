package com.example.followupapp.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public  class UtilityClass {
    public static String InternetText = "Connect To Internet";


//    public static boolean isConnectedToInternet(Context context){
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null)
//        {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null)
//                for (int i = 0; i < info.length; i++)
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
//                    {
//                        return true;
//                    }
//
//        }
//        return false;
//    }
public static boolean isConnectedToInternet(Context context) {

    if (android.os.Build.VERSION.SDK_INT > 9) {
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE);

      NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if (activeNetwork != null && activeNetwork.isConnected()) {
        try {
            URL url = new URL("http://www.google.com/");
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setRequestProperty("User-Agent", "test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1000); // mTimeout is in seconds
            urlc.connect();
            if (urlc.getResponseCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            Log.i("warning", "Error checking internet connection", e);
            return false;
        }
    }

    return false;
}
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }


    public static ProgressDialog showProgressDialog(Context context, String msg ) {
        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);
        pDialog.show();
        return pDialog;
    }

    public static void hideProgressDialog(ProgressDialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
