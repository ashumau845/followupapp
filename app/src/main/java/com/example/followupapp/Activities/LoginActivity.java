package com.example.followupapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidhiddencamera.HiddenCameraUtils;
import com.example.followupapp.R;
import com.example.followupapp.Utils.DemoCamService;
import com.example.followupapp.Utils.GpsTracker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    public Button login;
    MyReceiver myReceiver;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    EditText usernameEditText,passwordEditText;
    private ProgressDialog dialog;
    GpsTracker gpsTracker;
    String latitudeString = "",longitudeString="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        usernameEditText=(EditText) findViewById(R.id.email);
        passwordEditText=(EditText) findViewById(R.id.password);

        if (checkAndRequestPermissions()) {
        } else {
            requestPerems();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

      /*  findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(usernameEditText.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(passwordEditText.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();

                }
                else {

                    if (checkAndRequestPermissions()) {
                        if (HiddenCameraUtils.canOverDrawOtherApps(LoginActivity.this)) {
                            getLocation();
                          *//*  if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .remove(mHiddenCameraFragment)
                                        .commit();
                                mHiddenCameraFragment = null;
                            }
*//*

                            myReceiver = new MyReceiver();
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction(DemoCamService.MY_ACTION);
                            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(myReceiver, intentFilter);
                            startService(new Intent(LoginActivity.this, DemoCamService.class));
                        } else {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                            startActivityForResult(intent, 5469);
                        }
                    } else {
                        requestPerems();
                    }
                }
            }
        });*/
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            byte[] byteArray =arg1.getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            LoginUpload(bmp);


        }

        private void LoginUpload(Bitmap bmp) {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/req_images");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-" + n + ".jpg";
            File file = new File(myDir, fname);
            Log.i("TAG", "" + file);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {

            }
        }
    }

    private void getLocation() {
        gpsTracker = new GpsTracker(LoginActivity.this);
        // cs_check if GPS enabled
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            latitudeString = String.valueOf(latitude);
            longitudeString = String.valueOf(longitude);
            Log.e("latlong", latitudeString + "," + longitudeString);

        }
    }
    private void requestPerems() {
        String[] permissions = new String[4];
        permissions[0] = Manifest.permission.CAMERA;
        permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[2] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[3]=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        /* permissions[1] = Manifest.permission.SYSTEM_ALERT_WINDOW;*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_ID_MULTIPLE_PERMISSIONS);
        }
    }

    private boolean checkAndRequestPermissions() {
        int res;
        String[] permissions = new String[4];
        permissions[0] = Manifest.permission.CAMERA;
        permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permissions[2] = Manifest.permission.ACCESS_FINE_LOCATION;
        permissions[3]=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }



}
