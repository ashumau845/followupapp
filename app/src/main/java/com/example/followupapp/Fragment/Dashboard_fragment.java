package com.example.followupapp.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.followupapp.Activities.HomeActivity;
import com.example.followupapp.DatabaseHelper.DatabaseClient;
import com.example.followupapp.DatabaseHelper.User;
import com.example.followupapp.R;
import com.example.followupapp.Utils.AppConstants;
import com.example.followupapp.Utils.GpsUtils;
import com.example.followupapp.Utils.MyPreferenceHelper;
import com.example.followupapp.Utils.PreferenceHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class Dashboard_fragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    public static double latitude=0,longitude=0;
    ArrayList<LatLng> latlngs_list;
    public ImageView addsurvey, followuplist, closedlist, addfollowup;
    public static int status = 0;
    private GoogleMap mMap;
    MapView mapView;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    boolean isGPS = false;
    //PreferenceHelper pref;
    MyPreferenceHelper pref;
    List<User> userList;
    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    LinearLayout layout_addsurvey, layout_addvisit, layout_closedlist, layout_addservice;
    GpsUtils gpsUtils;
    private MarkerOptions markerOptions;
    private Marker currentLocationMarker;
    private GoogleApiClient googleApiClient;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String TAG = "Dashboard_fragement";
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;
    Location myloc = new Location("");

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstants.GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MapsInitializer.initialize(getContext());
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menusample, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                /*CloseFragment homeFragment = new CloseFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();*/

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ActualDashboard(), "NewFragmentTag");
                ft.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        pref = MyPreferenceHelper.getInstance(getContext());


       /* if(pref.getDeviceCheckIn().equals(new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()))){
            Log.e("Chekinpref",pref.getDeviceCheckIn().toString());
            Log.e("Chekincomp",new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()));



        }else{
            Log.e("Chekinprefnot",pref.getDeviceCheckIn().toString());
            Log.e("Chekincompnot",new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()));
            CustomDialog_Checkin customDialog_checkin=new CustomDialog_Checkin(getContext());
            customDialog_checkin.setCancelable(false);
            customDialog_checkin.show();
        }*/
        try {
            displaylist();
        }catch (Exception ex){

        }



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        ((HomeActivity) getActivity())
                .setcustomActionBar("Dashboard");


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission
            checkLocationPermission();

        } else {
            // already permission granted
            startLocationUpdates();
            gpsUtils = new GpsUtils(getContext());
            gpsUtils.turnGPSOn(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    isGPSEnable = true;
                }
            });
        }

        //Fragment mapfrag1 = getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        mapView = myView.findViewById(R.id.mapview12);
        addsurvey = myView.findViewById(R.id.navigation_previous);
        followuplist = myView.findViewById(R.id.totalsvisits);
        closedlist = myView.findViewById(R.id.detail_closedlist);
        addfollowup = myView.findViewById(R.id.navigation_next);
        layout_addsurvey = myView.findViewById(R.id.layout_add_survey);
        layout_addvisit = myView.findViewById(R.id.layout_add_visit);
        layout_closedlist = myView.findViewById(R.id.layout_closedlist);
        layout_addservice = myView.findViewById(R.id.layout_add_servicereport);


        layout_addsurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowupAdd homeFragment = new FollowupAdd();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
            }
        });

        layout_addvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFollowupfragment homeFragment = new AddFollowupfragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
            }
        });

        layout_closedlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseFragment homeFragment = new CloseFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                fragmentTransaction.commit();
            }
        });
        layout_addservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLead homefragment = new AddLead();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, homefragment);
                fragmentTransaction.commit();

            }
        });


        return myView;
    }

    private void displaylist() throws ExecutionException, InterruptedException {
        DatabaseClient userServices=new DatabaseClient(getContext());
        userList=new ArrayList<>();
        latlngs_list=new ArrayList<>();
        if(userServices.getAllUsers().size()!=0){
            Log.e("Size",String.valueOf(userServices.getAllUsers().size()));
            userList=userServices.getAllUsers();

            userServices.close();
        }


    }

    private void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(120000);
        mLocationRequest.setFastestInterval(120000);

        //https://guides.codepath.com/android/Retrieving-Location-with-LocationServices-API
        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        getFusedLocationProviderClient(getContext()).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());

    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mMap.addMarker(new MarkerOptions().position(latLng).title("Followup"));
                    }
                });

            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void startLocationMonitor() {
        Log.d("TAG", "start location monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            if (currentLocationMarker != null) {
                                currentLocationMarker.remove();
                            }
                            markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                            markerOptions.title("Current Location=" + location.getLatitude() + "," + location.getLongitude());




                            myloc.setLatitude(location.getLatitude());

                            myloc.setLongitude(location.getLongitude());
                            latitude=location.getLatitude();
                            Log.e("latitude",latitude+"");
                            longitude=location.getLongitude();

                            currentLocationMarker = mMap.addMarker(markerOptions);
                            Log.d("TAG", "Location Change Lat Lng " + location.getLatitude() + " " + location.getLongitude());
                        }
                    });
        } catch (SecurityException e) {
            Log.d("TAG", e.getMessage());
        }

    }

    protected Marker createMarker(double latitude, double longitude) {

        Log.e("Locating",latitude+"");
        return mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title("sometitle")
                .snippet("someDesc")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_red_24dp)));
    }


    @Override
    public void onResume() {
        super.onResume();
        checkLocationPermission();
        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());
        if (response != ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Service Not Available");
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), response, 1).show();
        } else {
            Log.d(TAG, "Google play service available");
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationMonitor();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onMapReady(GoogleMap argmap) {
        mGoogleMap = argmap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        for ( User point : userList) {
            Log.e("Marking",point.getLatitude()+"");
             mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.valueOf(point.getLatitude()),Double.valueOf(point.getLongitude())))
                    .anchor(0.5f, 0.5f)
                    .title(point.getCompanyName())
                    .snippet(point.getContactPerson())
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_location_on_red_24dp)));

        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }


    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                //mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        }
    };


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
      /*  String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps*/
        //  LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

    }


}

