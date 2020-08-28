package com.example.followupapp.Utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by brijesh on 15/4/17.
 */

public class Constants {


    public static final String GEOFENCE_ID_MURAL1 = "MURAL_1";
    public static final String GEOFENCE_ID_MURAL2 = "MURAL_2";
    public static final String GEOFENCE_ID_MURAL3 = "MURAL_3";
    public static final String GEOFENCE_ID_MURAL4= "MURAL_4";
    public static final String GEOFENCE_ID_MURAL5 = "MURAL_5";
    public static final String GEOFENCE_ID_LAKE1 = "LAKE_1";
    public static final String GEOFENCE_ID_LASTMURAL= "LAST_MURAL";
    public static final String GEOFENCE_ID_WELCOMEHALL= "WELCOME_HALL";
    public static final String GEOFENCE_ID_MURAL= "MURAL";
    public static final float GEOFENCE_RADIUS_IN_METERS = 50;


    public static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        // stanford university.
       /* AREA_LANDMARKS.put(GEOFENCE_ID_MURAL1, new LatLng(20.89116183, 72.79885014));
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL2, new LatLng(20.89115479, 72.79888884));*/
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL1, new LatLng(19.1766564, 72.9603897));
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL2, new LatLng(20.89115479, 72.79888884));
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL3, new LatLng(20.89112679, 72.79899562));
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL4, new LatLng(20.89121687, 72.79912262));
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL5, new LatLng(20.89113436, 72.79915292));
        AREA_LANDMARKS.put(GEOFENCE_ID_LAKE1, new LatLng(20.89147154, 72.79948434));
        AREA_LANDMARKS.put(GEOFENCE_ID_LASTMURAL, new LatLng(20.89182562, 72.80079053));
        AREA_LANDMARKS.put(GEOFENCE_ID_WELCOMEHALL, new LatLng(20.89201647, 72.8009891));
        AREA_LANDMARKS.put(GEOFENCE_ID_MURAL, new LatLng(20.89211793, 72.80089034));

    }
}
