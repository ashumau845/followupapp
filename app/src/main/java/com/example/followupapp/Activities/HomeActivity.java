package com.example.followupapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.followupapp.Fragment.AddFollowupfragment;
import com.example.followupapp.Fragment.AddLead;
import com.example.followupapp.Fragment.AddSurvey;
import com.example.followupapp.Fragment.Chat_Messages;
import com.example.followupapp.Fragment.CloseFragment;
import com.example.followupapp.Fragment.Dashboard_fragment;
import com.example.followupapp.Fragment.DetailsFollowup;
import com.example.followupapp.Fragment.FollowupAdd;
import com.example.followupapp.Fragment.Form2;
import com.example.followupapp.R;
import com.example.followupapp.Utils.MyPreferenceHelper;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    HomeActivity homeActivity;
    Switch aswitch;
    TextView punchin;
    MyPreferenceHelper pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeActivity = new HomeActivity();
        pref=pref = MyPreferenceHelper.getInstance(getApplicationContext());
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        aswitch = header.findViewById(R.id.nav_switch);
        punchin = header.findViewById(R.id.nav_item_title);
        aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (!isChecked) {


                    CustomDialogTogle customDialogTogle = new CustomDialogTogle(HomeActivity.this);
                    customDialogTogle.setCancelable(false);
                    customDialogTogle.show();
                } else if (isChecked) {
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,h:mm a", Locale.US);
                    String dateString = sdf.format(date);

                    punchin.setText("Punch In at " + dateString);
                }
            }
        });

        //if(pref.getDeviceCheckIn().equals(new SimpleDateFormat("MMM dd,h:mm a",Locale.US).format(System.currentTimeMillis()))){

        /*if(new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(pref.getDeviceCheckIn().toString()).equals(new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()))){
            Log.e("CurrentTimemillis", String.valueOf(System.currentTimeMillis()));
            Log.e("Chekinpreference",pref.getDeviceCheckIn().toString());
            Log.e("Chekincomp",new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()));
//            if(pref.getcheckout().equals(new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()))){
            if(new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(pref.getcheckout()).equals(new SimpleDateFormat("dd/MM/YYYY",Locale.US).format(System.currentTimeMillis()))){

                aswitch.setChecked(false);
                aswitch.setVisibility(View.INVISIBLE);

                punchin.setText("Punch Out at " + new SimpleDateFormat("MMM dd,h:mm a",Locale.US).format(pref.getcheckout()));
                //pref.putDevicecheckout(str_date);
            }

        }else{
            Log.e("Chekinpreferencenot",pref.getDeviceCheckIn().toString());
            Log.e("CurrentTimemillis", String.valueOf(System.currentTimeMillis()));
            Log.e("Chekincompnot",new SimpleDateFormat("MMM dd,h:mm a",Locale.US).format(System.currentTimeMillis()));
            CustomDialog_Checkin customDialog_checkin=new CustomDialog_Checkin(this);
            customDialog_checkin.setCancelable(false);
            customDialog_checkin.show();
        }*/
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard_fragment()).commit();

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }

    public void setAswitch(){
        aswitch.setChecked(true);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,h:mm a", Locale.US);
        String dateString = sdf.format(date);
        punchin.setText("CheckIn at " + dateString);
    }

    public void setcustomActionBar(String title) {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from((this));
        View v = inflator.inflate(R.layout.custom_titlebar, null);

//if you need to customize anything else about the text, do it here.
//I'm using a custom TextView with a custom font in my layout xml so all I need to do is set title
        ((TextView) v.findViewById(R.id.title)).setText(title);

//assign the view to the actionbar
        getSupportActionBar().setCustomView(v);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard_fragment()).commit();
                break;

            case R.id.nav_logout:
                /*if (UtilityClass.isConnectedToInternet(getApplicationContext())) {*/
                AlertDialog alertbox = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {

                                    // do something when the button is clicked
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
               /* } else {
                    Toast.makeText(getApplicationContext(), "Connect To Internet", Toast.LENGTH_SHORT).show();
                }*/


                break;

            case R.id.nav_visit:
                AddFollowupfragment fragment = new AddFollowupfragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment f : fragments) {
                if (f != null && (f instanceof AddSurvey || f instanceof FollowupAdd || f instanceof AddFollowupfragment || f instanceof FollowupAdd||
                        f instanceof Form2 || f instanceof CloseFragment || f instanceof Chat_Messages || f instanceof AddLead) || f instanceof FollowupAdd) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard_fragment()).commit();
                } else if (f instanceof Dashboard_fragment) {
                    finishAffinity();
                } else if (f instanceof DetailsFollowup) {
                    if (DetailsFollowup.disable == 0) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFollowupfragment()).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CloseFragment()).commit();
                    }
                }
            }
        }
    }


    public class CustomDialogTogle extends Dialog implements View.OnClickListener {
        TextView btnok, btncancel;
        public CustomDialogTogle(@NonNull Context context) {
            super(context);
        }

        //ProgressBar pbar_loader;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.custom_dialog_toggle);
            this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btncancel = findViewById(R.id.btnOK_toggle);
            btnok = findViewById(R.id.btncancel_toggle);
            btnok.setOnClickListener(this);
            btncancel.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnOK_toggle:
                    aswitch.setChecked(false);
                    aswitch.setVisibility(View.INVISIBLE);
                    long date = System.currentTimeMillis();
                    pref.putDevicecheckout(String.valueOf(date));
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, h:mm a", Locale.US);
                    String dateString = sdf.format(date);
                    punchin.setText("Punch Out at " + dateString);
                    this.dismiss();
                    this.cancel();
                    break;

                case R.id.btncancel_toggle:
                    aswitch.setChecked(true);
                    this.dismiss();
                    this.cancel();
                    break;

                default:
                    break;
            }
            this.dismiss();
        }
    }


    public class CustomDialog_Checkin extends Dialog {

        TextView btncheckin_dialog,btnignore_dialog;

        public CustomDialog_Checkin(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_dialog_checkindialog);
            this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            btncheckin_dialog=findViewById(R.id.btn_checkindialog);

           /* lp.copyFrom(this.getWindow().getAttributes());
            lp.width = 800;
            lp.height = 800;
            lp.x = 0;
            lp.y = 0;
            this.getWindow().setAttributes(lp);*/
            btncheckin_dialog = findViewById(R.id.btn_checkindialog);
            btncheckin_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                   // pref.putDeviceCheckIn(new SimpleDateFormat("MMM dd,h:mm a", Locale.US).format(new Date()));
                    pref.putDeviceCheckIn(String.valueOf(System.currentTimeMillis()));
                    CustomDialogCheckIn customDialogCheckIn = new CustomDialogCheckIn(getContext());
                    customDialogCheckIn.setCancelable(false);
                    customDialogCheckIn.show();

                    //pref.putDeviceCheckIn(new SimpleDateFormat("dd/MM/yy", Locale.US).format(new Date()));
                }
            });
            btnignore_dialog=findViewById(R.id.btn_cancel_checkin);
            btnignore_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();

                }
            });
        }
    }


    public class CustomDialogCheckIn extends Dialog {

        ImageView img_close;

        public CustomDialogCheckIn(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_checkin);
            //this.getWindow().setAttributes(800,800);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

            lp.copyFrom(this.getWindow().getAttributes());
            lp.width = 800;
            lp.height = 800;
            lp.x = 0;
            lp.y = 0;
            this.getWindow().setAttributes(lp);
            img_close = findViewById(R.id.img_close);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    setAswitch();
                   // pref.putDeviceCheckIn(new SimpleDateFormat("MMM dd,h:mm a", Locale.US).format(System.currentTimeMillis()));
                }
            });
        }
    }

}




