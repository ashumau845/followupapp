package com.example.followupapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.followupapp.DatabaseHelper.DatabaseClient;
import com.example.followupapp.DatabaseHelper.User;
import com.example.followupapp.Fragment.Dashboard_fragment;
import com.example.followupapp.R;
import com.example.followupapp.Utils.CheckInternet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;



public class AddCustomer extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerDragListener {
    TextView txt_content_latitude;
    Button btn_fetch,btnok,btncancel;
    private GoogleMap pgmap;
    LatLng points_address;
    CameraPosition googlePlex;
    Marker marker_map;
    EditText edt_address,edt_comp_name,edt_custName,edt_contact_person,edt_mobileno,edt_emailid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btncancel=findViewById(R.id.btn_cancel_dialog);
        btn_fetch=findViewById(R.id.btn_fetch_dialog);
        btnok=findViewById(R.id.btn_ok_dialog);
        txt_content_latitude=findViewById(R.id.txt_latlng);
        edt_address=findViewById(R.id.edt_address);
        edt_comp_name=findViewById(R.id.edt_comp_name);
        edt_custName=findViewById(R.id.edt_cust_name);
        edt_contact_person=findViewById(R.id.edt_cust_name);
        edt_mobileno=findViewById(R.id.edt_mobile);
        edt_emailid=findViewById(R.id.edt_email);

       // ExplosionField explosionField = new ExplosionField(getApplicationContext())
       // explosionField.explode(edt_custName.getRootView());
        edt_address.setOnClickListener(this);
        btnok.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btn_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_address.getText().toString().isEmpty()){
                    edt_address.setError("Please Provide the Address");

                }else{
                    if(CheckInternet.isConnectedToInternet(getApplicationContext())) {
                        points_address = getLocationFromAddress1(getApplicationContext(), edt_address.getText().toString().trim());
                        if (points_address != null) {
                            //Marker marker = new Marker().position(points_address).title("point");
                            CheckInternet.hideSoftKeyboard(AddCustomer.this);
                            pgmap.clear();
                            marker_map= pgmap.addMarker(new MarkerOptions()
                                    .position(points_address)
                                    .title(edt_address.getText().toString()));
                            marker_map.setDraggable(true);

                            googlePlex = CameraPosition.builder()
                                    .target(points_address)
                                    .zoom(16)
                                    .bearing(0)
                                    .tilt(45)
                                    .build();
                            pgmap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                        }else{
                            Toast.makeText(getApplicationContext(),"Cant't Recognnise the Address",Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(getApplicationContext(),"Please Connected to Internet",Toast.LENGTH_SHORT).show();

                    }

                   // txt_content_latitude.setVisibility(View.VISIBLE);
                   // txt_content_latitude.setText(points_address.latitude+" "+points_address.longitude);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddCustomer.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

    public void func_validation(){
        if(CheckInternet.isEmptyString(edt_comp_name.getText().toString().trim())){
            edt_comp_name.setError("Please Enter Company Name");
            edt_comp_name.requestFocus();
        } else if(CheckInternet.isEmptyString(edt_custName.getText().toString().trim())){
            edt_custName.setError("Please Enter Customer Name");
            edt_custName.requestFocus();

        } else if(CheckInternet.isEmptyString(edt_contact_person.getText().toString().trim())){
            edt_contact_person.setError("Please Enter Contact Person Name");
            edt_contact_person.requestFocus();

        } else if(CheckInternet.isEmptyString(edt_mobileno.getText().toString().trim()) || edt_mobileno.getText().toString().length()!=10){
            edt_mobileno.setError("Please Enter Mobile Number");
            edt_mobileno.requestFocus();

        } else if(!CheckInternet.isValid(edt_emailid.getText().toString().trim())){
            edt_emailid.setError("Please Enter Email ID");
            edt_emailid.requestFocus();

        } else if(CheckInternet.isEmptyString(edt_address.getText().toString().trim())){
            edt_address.setError("Please Enter Address");
            edt_address.requestFocus();

        } else if(points_address==null){
            Toast.makeText(getApplicationContext(),"Please fetch the location address",Toast.LENGTH_SHORT).show();
        } else{

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                User user=new User();
                user.setCompanyName(edt_comp_name.getText().toString());
                user.setContactPerson(edt_contact_person.getText().toString());
                user.setCustomerName(edt_custName.getText().toString());
                user.setEmail_id(edt_emailid.getText().toString());
                user.setLatitude(String.valueOf(points_address.latitude));
                user.setLongitude(String.valueOf(points_address.longitude));

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao().insertAll(user);
                DatabaseClient.getInstance(getApplicationContext()).close();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //spnr_select_customer.setVisibility(View.VISIBLE);

                //spnr_select_customer.setSelection(0);
                //dismiss();
                Toast.makeText(getApplicationContext(), "Customer Added Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddCustomer.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();



        }


    }
    public LatLng getLocationFromAddress1(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address.size()<= 0) {
                //Toast.makeText(getApplicationContext(),"Can't Recognise the Address",Toast.LENGTH_SHORT).show();
                return null;

            }else {


                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude());
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        pgmap=googleMap;
        pgmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googlePlex = CameraPosition.builder()
                .target(new LatLng(37.4219999,-122.0862462))
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();

        pgmap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        pgmap.setOnMarkerDragListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_ok_dialog:
                func_validation();
                break;
            case R.id.btn_cancel_dialog:
                Intent intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker_map=marker;

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        marker_map=marker;
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker_map=marker;
        points_address=marker_map.getPosition();
        Log.e("OnDragEnd",marker_map.getPosition()+" ");
    }


}
